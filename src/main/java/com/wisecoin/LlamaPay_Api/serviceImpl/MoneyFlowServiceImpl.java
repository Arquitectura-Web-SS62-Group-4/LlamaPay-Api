package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.MoneyFlowRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowResponseDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowSummaryDTO;
import com.wisecoin.LlamaPay_Api.entities.Category;
import com.wisecoin.LlamaPay_Api.entities.Client;

import java.util.ArrayList;

import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.repositories.MoneyFlowRepository;
import com.wisecoin.LlamaPay_Api.services.CategoryService;
import com.wisecoin.LlamaPay_Api.services.MoneyFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MoneyFlowServiceImpl implements MoneyFlowService {

    @Autowired
    MoneyFlowRepository moneyFlowRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CategoryService categoryService;

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
        if(moneyFlowRepository.existsByName(moneyFlowDto.getName())){
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
                if(moneyFlowRepository.existsByName(moneyFlowRequestDto.getName())){
                    throw new ValidationException("El nombre ya se encuentra registrado");
                }
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
    public List<MoneyFlowResponseDTO> getMoneyFlowByTypeAndMonth(String type, int month) {
        if(!("Ingreso".equals(type) || "Gasto".equals(type))){
            throw new ValidationException("Tipo inválido. Debe ser 'Ingreso' o 'Gasto'.");
        }

        if(month < 0 || month > 12){
            throw new ValidationException("El mes ingresado es incorrecto");
        }
        List<MoneyFlow> moneyFlows = moneyFlowRepository.getListByTypeAndMonth(type, month);
        List<MoneyFlowResponseDTO> moneyFlowDTOS = new ArrayList<>();

        for (MoneyFlow moneyFlow : moneyFlows) {
            String categoryName = moneyFlow.getCategory().getNameCategory();
            Double currentAmount = moneyFlow.getAmount();

            boolean categoryExists = false;
            for (MoneyFlowResponseDTO dto : moneyFlowDTOS) {
                if (dto.getNameCategory().equals(categoryName)) {
                    // Se suma al monto acumulado
                    dto.setTotal(dto.getTotal() + currentAmount);
                    categoryExists = true;
                    break;
                }
            }

            if (!categoryExists) {
                moneyFlowDTOS.add(new MoneyFlowResponseDTO(categoryName, currentAmount));
            }
        }
        return moneyFlowDTOS;
    }

    @Override
    public List<MoneyFlowSummaryDTO> getMoneyFlowNeto(int firstMonth, int finalMonth) {
        if(firstMonth < 0 || finalMonth < 0){
            throw new ValidationException("Los meses ingresados son invalidos");
        }
        if(firstMonth > finalMonth){
            throw new ValidationException("El primer debe ser menor al ultimo mes");
        }

        List<MoneyFlow> moneyFlows = moneyFlowRepository.getListByRange(firstMonth, finalMonth);
        List<MoneyFlowSummaryDTO> monetFlowSummaryDTOS = new ArrayList<>();

        for (int month = firstMonth; month <= finalMonth; month++) {
            double totalIngresos = 0;
            double totalGastos = 0;
            String monthName = "";

            for (MoneyFlow moneyFlow : moneyFlows) {
                if (moneyFlow.getDate().getMonthValue() == month) {
                    // Nombre del mes
                    monthName = moneyFlow.getDate().getMonth().name();

                    if (moneyFlow.getType().equals("Ingreso")) {
                        totalIngresos += moneyFlow.getAmount();
                    } else if (moneyFlow.getType().equals("Gasto")) {
                        totalGastos += moneyFlow.getAmount();
                    }
                }
            }

            if (!monthName.isEmpty()) {
                double montoNeto = totalIngresos - totalGastos;
                monetFlowSummaryDTOS.add(new MoneyFlowSummaryDTO(monthName, montoNeto));
            }
        }
        return monetFlowSummaryDTOS;
    }

    @Override
    public List<MoneyFlow> findByClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if(client==null){
            throw new ValidationException("Cliente no encontrado");
        }
        return moneyFlowRepository.findByClient(client);
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
}
