package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.GoalDTO;
import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.GoalRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.GoalResponseDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.Goal;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.repositories.GoalRepository;
import com.wisecoin.LlamaPay_Api.services.GoalService;
import com.wisecoin.LlamaPay_Api.services.MoneyFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GoalServiceimpl implements GoalService {
    @Autowired
    GoalRepository goalRepository;

    @Autowired
    MoneyFlowService moneyFlowService;

    @Autowired
    ClientRepository clientRepository;

    public Long goalIdByName(String name, Long clientId){
        List<Goal> listNombreDuplicados = goalRepository.findByNameAndClient_id(name, clientId);
        if (!listNombreDuplicados.isEmpty()) {
            return listNombreDuplicados.get(0).getId();
        }
        return null;
    }

    @Override
    public Goal getGoalById(Long id) {
        Goal goal = goalRepository.findById(id).orElse(null);
        if (goal==null) {
            throw new ResourceNotFoundException("Objetivo no encontrado");
        }
        return goal;
    }

    @Override
    public GoalDTO getGoalResponseById(Long id) {
        Goal goal= getGoalById(id);
        GoalDTO goalDTO = new GoalDTO(goal.getId(), goal.getName(), goal.getDescription(),
                    goal.getAmount(), goal.getStartDate(), goal.getDeadline());
        return goalDTO;
    }

    @Override
    public Goal updateGoal(Long id, GoalRequestDTO goalRequestDto) {
        Goal goalFound = getGoalById(id);
        if(goalFound!=null){
            LocalDate today = LocalDate.now();

            //Validando name
            if(!goalRequestDto.getName().isBlank()){
                if(goalRequestDto.getName().length()<=2){
                    throw new ValidationException("El nombre no puede tener menos de tres caracteres");
                }
                Long existingGoalId = goalIdByName(goalRequestDto.getName(),id);
                if (existingGoalId != null && !existingGoalId.equals(id)) {
                    throw new ValidationException("El nombre ya se encuentra registrado");
                }

                // Asignar el nuevo nombre
                goalFound.setName(goalRequestDto.getName());
            }

            //Validando description
            if(!goalRequestDto.getDescription().isBlank()){
                if(goalRequestDto.getDescription().length()<=2){
                    throw new ValidationException("La descripcion no puede tener menos de tres caracteres");
                }
                goalFound.setDescription(goalRequestDto.getDescription());
            }

            //Validando amount
            if(goalRequestDto.getAmount()!=null){
                if(goalRequestDto.getAmount()<=0.0 || Double.isNaN(goalRequestDto.getAmount())){
                    throw new ValidationException("El monto no es valido");
                }
                goalFound.setAmount(goalRequestDto.getAmount());
            }

            //Validando startDate
            if(goalRequestDto.getStartDate()!=null){
                goalFound.setStartDate(goalRequestDto.getStartDate());
            }

            //validando deadline
            if(goalRequestDto.getDeadline()!=null){
                LocalDate date;
                if(goalRequestDto.getStartDate() == null){
                    date = goalFound.getStartDate();
                }
                else{
                    date =goalRequestDto.getStartDate();
                }
                if(goalRequestDto.getDeadline().isBefore(date)){
                    throw new ValidationException("La fecha limite debe ser mayor a la fecha de inicio");
                }
                goalFound.setDeadline(goalRequestDto.getDeadline());
            }
            return goalRepository.save(goalFound);
        }

        if(!goalRepository.existsById(id)){
            throw new ResourceNotFoundException("Objetivo no encontrado");
        }
        return null;
    }

    public void updateStatusGoal(Long id, List<Goal> goals) {
        for (Goal goal : goals) {
            Double amountNet = moneyFlowService.getMoneyFlowNetoByRange(id, goal.getStartDate(), goal.getDeadline());
            // Actualizar estado de la meta
            if (amountNet >= goal.getAmount()) {
                goal.setIsSuccessfull(true);
                goalRepository.save(goal);
            } else {
                goal.setIsSuccessfull(false);
                goalRepository.save(goal);
            }
        }
    }


    @Override
    public Goal addGoal(Long clientId, GoalDTO goalDto) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client==null) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        LocalDate today = LocalDate.now();
        //Validar name
        if(goalDto.getName().length()<=2){
            throw new ValidationException("El nombre no puede tener menos de tres caracteres");
        }
        if(goalRepository.existsByNameAndClient_id(goalDto.getName(), clientId)){
            throw new ValidationException("El nombre ya se encuentra registrado");
        }

        //Validar descripcion
        if(goalDto.getDescription().length()<=2){
            throw new ValidationException("La descripcion no puede tener menos de tres caracteres");
        }

        //Validar amount
        if(goalDto.getAmount()<=0.0 || Double.isNaN(goalDto.getAmount())){
            throw new ValidationException("El monto no es valido");
        }


        //validar startDate
        if(goalDto.getStartDate().isBefore(today)){
            throw new ValidationException("La fecha de inicio no puede ser anterior a la actual");
        }

        //validar deadline
        if(goalDto.getDeadline().isBefore(goalDto.getStartDate())){
            throw new ValidationException("La fecha limite debe ser mayor a la fecha de inicio");
        }
        Boolean isSuccessfull= false;

        Goal goal = new Goal(goalDto.getId(), goalDto.getName(), goalDto.getDescription(), goalDto.getAmount(),
                goalDto.getStartDate(), goalDto.getDeadline(),isSuccessfull,client);

        goal.setClient(client);
        return goalRepository.save(goal);
    }

    @Override
    public List<GoalResponseDTO> findByClient(Long clientId){
        Client client = clientRepository.findById(clientId).orElse(null);
        if(client==null){
            throw new ValidationException("Cliente no encontrado");
        }
        updateStatusGoal(clientId, goalRepository.findByClient(client));
        List<Goal> list = goalRepository.findByClient(client);
        List<GoalResponseDTO> goalResponseDTOS = new ArrayList<>();
        for(Goal goal : list){
            Double amountNet = moneyFlowService.getMoneyFlowNetoByRange(clientId, goal.getStartDate(), goal.getDeadline());
            GoalResponseDTO goalResponseDTO = new GoalResponseDTO(goal.getId(), goal.getName(), goal.getDescription(),
                    goal.getAmount(), goal.getStartDate(), goal.getDeadline(), goal.getIsSuccessfull(),amountNet);
            goalResponseDTOS.add(goalResponseDTO);
        }
        return goalResponseDTOS;
    }


    @Override
    public List<Goal> listAll() {
        return goalRepository.findAll();
    }

    @Override
    public void deleteGoal(Long id) {
        Goal goalDelete = getGoalById(id);
        goalRepository.deleteById(id);
    }

}
