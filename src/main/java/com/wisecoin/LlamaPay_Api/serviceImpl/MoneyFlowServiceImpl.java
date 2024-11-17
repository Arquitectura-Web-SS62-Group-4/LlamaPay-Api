package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.MoneyFlowRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowCategoryDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowResponseDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowSummaryDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowTypeDTO;
import com.wisecoin.LlamaPay_Api.entities.Category;
import com.wisecoin.LlamaPay_Api.entities.Client;

import java.util.ArrayList;

import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.repositories.MoneyFlowRepository;
import com.wisecoin.LlamaPay_Api.services.CategoryService;
import com.wisecoin.LlamaPay_Api.services.ClientService;
import com.wisecoin.LlamaPay_Api.services.MoneyFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

@Service
public class MoneyFlowServiceImpl implements MoneyFlowService {

    @Autowired
    MoneyFlowRepository moneyFlowRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    CategoryService categoryService;

    public Long flowIdByName(String name, Long clientId){
        List<MoneyFlow> listNombreDuplicados = moneyFlowRepository.findByNameAndClient_id(name, clientId);
        if (!listNombreDuplicados.isEmpty()) {
            return listNombreDuplicados.get(0).getId();
        }
        return null;
    }

    @Override
    public MoneyFlow addMoneyFlow(Long clientId, Long categoryId, MoneyFlowDTO moneyFlowDto) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client==null) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        LocalDate today = LocalDate.now();

        //Validar amount
        if(moneyFlowDto.getAmount()<=0.0 || Double.isNaN(moneyFlowDto.getAmount())){
            throw new ValidationException("El monto no es valido");
        }

        //validar date
        if(moneyFlowDto.getDate().isAfter(today)){
            throw new ValidationException("La fecha no puede ser mayor a la actual");
        }

        //validar name
        if(moneyFlowRepository.existsByNameAndClient_id(moneyFlowDto.getName(), clientId)){
            throw new ValidationException("El nombre ya se encuentra registrado");
        }
        if(moneyFlowDto.getName().length()<=2){
            throw new ValidationException("El nombre no puede tener menos de tres caracteres");
        }

        //validar type
        if (!moneyFlowDto.getType().equals("Ingreso") && !moneyFlowDto.getType().equals("Gasto")) {
            throw new ValidationException("Tipo inválido. Debe ser 'Ingreso' o 'Gasto'.");
        }

        Category category = categoryService.getCategoryById(categoryId);

        if (!category.getType().equals(moneyFlowDto.getType())) {
            throw new ValidationException("La categoría asignada no va acorde con el tipo.");
        }

        MoneyFlow moneyFlow = new MoneyFlow(moneyFlowDto.getId(),moneyFlowDto.getName(),moneyFlowDto.getType(),moneyFlowDto.getAmount(),
                moneyFlowDto.getDate(), client, category);

        moneyFlow.setClient(client);
        moneyFlow.setCategory(category);
        return moneyFlowRepository.save(moneyFlow);
    }

    @Override
    public MoneyFlow updateMoneyFlow(Long id, Long categoryId, MoneyFlowRequestDTO moneyFlowRequestDto) {
        MoneyFlow moneyFlowFound = getMoneyFlowById(id);
        if(moneyFlowFound!=null){
            LocalDate today = LocalDate.now();

            //validando amount
            if(moneyFlowRequestDto.getAmount()!=null){
                if(moneyFlowRequestDto.getAmount()<=0.0 || Double.isNaN(moneyFlowRequestDto.getAmount())){
                    throw new ValidationException("El monto no es valido");
                }
                moneyFlowFound.setAmount(moneyFlowRequestDto.getAmount());
            }

            //Validando date
            if(moneyFlowRequestDto.getDate()!=null){
                if(moneyFlowRequestDto.getDate().isAfter(today)){
                    throw new ValidationException("La fecha no puede ser mayor a la actual");
                }
                moneyFlowFound.setDate(moneyFlowRequestDto.getDate());
            }

            //Validando name
            if(!moneyFlowRequestDto.getName().isBlank()){
                if(moneyFlowRequestDto.getName().length()<=2){
                    throw new ValidationException("El nombre no puede tener menos de tres caracteres");
                }
                Long existingFlowId = flowIdByName(moneyFlowRequestDto.getName(),id);
                if (existingFlowId != null && !existingFlowId.equals(id)) {
                    throw new ValidationException("El nombre ya se encuentra registrado");
                }

                // Asignar el nuevo nombre
                moneyFlowFound.setName(moneyFlowRequestDto.getName());
            }

            //Validando type
            if(!moneyFlowRequestDto.getType().isBlank()){
                if (!moneyFlowRequestDto.getType().equals("Ingreso") && !moneyFlowRequestDto.getType().equals("Gasto")) {
                    throw new ValidationException("Tipo inválido. Debe ser 'Ingreso' o 'Gasto'.");
                }
                moneyFlowFound.setType(moneyFlowRequestDto.getType());
            }

            //Validando Categoria
            Category category = categoryService.getCategoryById(categoryId);

            if (!category.getType().equals(moneyFlowRequestDto.getType())) {
                throw new ValidationException("La categoría asignada no va acorde con el tipo.");
            }

            return moneyFlowRepository.save(moneyFlowFound);
        }

        if(!moneyFlowRepository.existsById(id)){
            throw new ResourceNotFoundException("Flujo de dinero no encontrado");
        }
        return null;
    }

    @Override
    public List<MoneyFlowCategoryDTO> getMoneyFlowByTypeAndMonth(Long idClient, String type, int year, int month) {
        Client client = clientService.getClientById(idClient);

        if(!("Ingreso".equals(type) || "Gasto".equals(type))){
            throw new ValidationException("Tipo inválido. Debe ser 'Ingreso' o 'Gasto'.");
        }

        if(month < 0 || month > 12){
            throw new ValidationException("El mes ingresado es incorrecto");
        }

        if(year < 0){
            throw new ValidationException("El año ingresado no es valido");
        }

        List<MoneyFlow> moneyFlows = moneyFlowRepository.getListByTypeAndMonth(type,month,year);
        List<MoneyFlowCategoryDTO> moneyFlowDTOS = new ArrayList<>();

        for (MoneyFlow moneyFlow : moneyFlows) {
            if(moneyFlow.getClient().getId().equals(idClient)){
                String categoryName = moneyFlow.getCategory().getNameCategory();
                Double currentAmount = moneyFlow.getAmount();

                boolean categoryExists = false;
                for (MoneyFlowCategoryDTO dto : moneyFlowDTOS) {
                    if (dto.getNameCategory().equals(categoryName)) {
                        // Se suma al monto acumulado
                        dto.setTotal(dto.getTotal() + currentAmount);
                        categoryExists = true;
                        break;
                    }
                }

                if (!categoryExists) {
                    moneyFlowDTOS.add(new MoneyFlowCategoryDTO(categoryName, currentAmount));
                }
            }
        }
        return moneyFlowDTOS;
    }

    @Override
    public List<MoneyFlowSummaryDTO> getMoneyFlowNeto(Long idClient, int year, int firstMonth, int finalMonth) {
        Client client = clientService.getClientById(idClient);

        if(firstMonth < 0 || finalMonth < 0){
            throw new ValidationException("Los meses ingresados son invalidos");
        }
        if(firstMonth > finalMonth){
            throw new ValidationException("El primer debe ser menor al ultimo mes");
        }

        if(year < 0){
            throw new ValidationException("El año ingresado no es valido");
        }

        List<MoneyFlow> moneyFlows = moneyFlowRepository.getListByRange(firstMonth,finalMonth,year);
        List<MoneyFlowSummaryDTO> monetFlowSummaryDTOS = new ArrayList<>();
        for (int month = firstMonth; month <= finalMonth; month++) {
            double totalIngresos = 0;
            double totalGastos = 0;

            for (MoneyFlow moneyFlow : moneyFlows) {
                if (moneyFlow.getClient().getId().equals(idClient)) {
                    if (moneyFlow.getDate().getMonthValue() == month) {
                        if (moneyFlow.getType().equals("Ingreso")) {
                            totalIngresos += moneyFlow.getAmount();
                        } else if (moneyFlow.getType().equals("Gasto")) {
                            totalGastos += moneyFlow.getAmount();
                        }
                    }
                }
            }

            // Solo añade el resumen si hay ingresos o gastos para el mes
            if (totalIngresos != 0 || totalGastos != 0) {
                double montoNeto = totalIngresos - totalGastos;
                monetFlowSummaryDTOS.add(new MoneyFlowSummaryDTO((long)month, montoNeto)); // Usar el número del mes
            }
        }

        return monetFlowSummaryDTOS;
    }

    //
    @Override
    public MoneyFlowSummaryDTO getMoneyFlowNetoByMonth(Long idClient, int year, int month) {
        Client client = clientService.getClientById(idClient);
        if(month < 1 || month > 12){
            throw new ValidationException("El mes ingresa no es correcto");
        }
        List<MoneyFlow> moneyFlows = moneyFlowRepository.getListByMonth(month,year);

        Double totalIngresos = 0.0;
        Double totalGastos = 0.0;
        for (MoneyFlow flow : moneyFlows) {
            if(flow.getClient().getId().equals(idClient)){
                if (flow.getCategory().getType().equals("Ingreso")) {
                    totalIngresos += flow.getAmount();
                } else if (flow.getCategory().getType().equals("Gasto")) {
                    totalGastos += flow.getAmount();
                }
            }
        }

        double montoNeto = totalIngresos - totalGastos;

        MoneyFlowSummaryDTO moneyFlowSummaryDTO =new  MoneyFlowSummaryDTO((long)month, montoNeto);
        return moneyFlowSummaryDTO;
    }

    @Override
    public Double getMoneyFlowNetoByRange(Long idClient, LocalDate startDate, LocalDate endDate) {
        Client client = clientService.getClientById(idClient);
        List<MoneyFlow> moneyFlows = moneyFlowRepository.getListByDateRange(startDate, endDate);
        Double amountNet = 0.0;

        for (MoneyFlow flow : moneyFlows) {
            if(flow.getClient().getId().equals(idClient)) {
                if (flow.getType().equals("Ingreso")) {
                    amountNet += flow.getAmount();  // Sumar si es un ingreso
                } else if (flow.getType().equals("Gasto")) {
                    amountNet -= flow.getAmount();  // Restar si es un gasto
                }
            }
        }
        return amountNet;
    }

    @Override
    public List<MoneyFlowTypeDTO> getListByTypeAndMonth(Long idClient, String type, int year, int month) {
        Client client = clientService.getClientById(idClient);
        List<MoneyFlow> moneyFlows = moneyFlowRepository.getListByTypeAndMonth(type,month,year);
        List<MoneyFlowTypeDTO> list = new ArrayList<>();

        for (MoneyFlow moneyFlow : moneyFlows) {
            if (moneyFlow.getClient().getId().equals(idClient)) {
                LocalDate date = moneyFlow.getDate();
                MoneyFlowTypeDTO existingDTO = null;

                // Verificar si ya existe un MoneyFlowTypeDTO con esta fecha en list
                for (MoneyFlowTypeDTO dto : list) {
                    if (dto.getDate().equals(date)) {
                        existingDTO = dto;
                        break;
                    }
                }

                if (existingDTO == null) {
                    // Crear un nuevo MoneyFlowTypeDTO si no existe para esta fecha
                    MoneyFlowTypeDTO newDTO = new MoneyFlowTypeDTO(date, type, moneyFlow.getAmount());
                    list.add(newDTO);
                } else {
                    // Si ya existe, sumar el monto al existente
                    existingDTO.setTotal(existingDTO.getTotal() + moneyFlow.getAmount());
                }
            }
        }
        Collections.sort(list, Comparator.comparing(MoneyFlowTypeDTO::getDate));
        return list;
    }

    @Override
    public MoneyFlowTypeDTO getTotalByTypeAndMonth(Long idClient, String type, int year, int month) {
        Client client = clientService.getClientById(idClient);
        List<MoneyFlow> moneyFlows = moneyFlowRepository.getListByTypeAndMonth(type,month,year);
        Double total =0.0;
        for (MoneyFlow moneyFlow : moneyFlows) {
            if (moneyFlow.getClient().getId().equals(idClient)) {
                total+=moneyFlow.getAmount();
            }
        }
        MoneyFlowTypeDTO moneyFlowTypeDTO = new MoneyFlowTypeDTO(null,type, total);
        return moneyFlowTypeDTO;
    }

    @Override
    public List<MoneyFlowResponseDTO> findByClient(Long clientId, Long year, Long month) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client == null) {
            throw new ValidationException("Cliente no encontrado");
        }
        List<MoneyFlowResponseDTO> list = new ArrayList<>();
        for (MoneyFlow moneyFlow : moneyFlowRepository.findByClient(client)) {
            if (moneyFlow.getDate().getYear() == year.intValue() && moneyFlow.getDate().getMonthValue() == month.intValue()) {
                MoneyFlowResponseDTO moneyFlowResponseDTO = new MoneyFlowResponseDTO(
                        moneyFlow.getId(),
                        moneyFlow.getName(),
                        moneyFlow.getType(),
                        moneyFlow.getAmount(),
                        moneyFlow.getDate(),
                        moneyFlow.getCategory().getNameCategory()
                );
                list.add(moneyFlowResponseDTO);
            }
        }
        return list;
    }

    @Override
    public List<MoneyFlow> listAll() {
        return moneyFlowRepository.findAll();
    }

    @Override
    public void deleteMoneyFlow(Long id) {
        MoneyFlow moneyFlowDelete = getMoneyFlowById(id);
        moneyFlowRepository.deleteById(id);
    }

    @Override
    public MoneyFlow getMoneyFlowById(Long id) {
        MoneyFlow moneyFlow = moneyFlowRepository.findById(id).orElse(null);
        if (moneyFlow==null) {
            throw new ResourceNotFoundException("Flujo de dinero no encontrado");
        }
        return moneyFlow;
    }

    @Override
    public MoneyFlowResponseDTO getMoneyFlowResponseById(Long id) {
        MoneyFlow moneyFlow = getMoneyFlowById(id);
        MoneyFlowResponseDTO moneyFlowResponseDTO = new MoneyFlowResponseDTO(moneyFlow.getId(),
                moneyFlow.getName(), moneyFlow.getType(), moneyFlow.getAmount(),moneyFlow.getDate(),
                moneyFlow.getCategory().getNameCategory());
        return moneyFlowResponseDTO;
    }

}
