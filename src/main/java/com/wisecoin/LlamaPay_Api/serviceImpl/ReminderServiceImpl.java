package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.ReminderDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.ReminderRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.Reminder;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.repositories.ReminderRepository;
import com.wisecoin.LlamaPay_Api.services.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {
    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    ClientRepository clientRepository;

    public Long reminderIdByTitle(String title, Long clientId){
        List<Reminder> listNombreDuplicados = reminderRepository.findByTitleAndClient_id(title, clientId);
        if (!listNombreDuplicados.isEmpty()) {
            return listNombreDuplicados.get(0).getId();
        }
        return null;
    }

    @Override
    public Reminder Reminderadd(ReminderDTO reminderDTO, Long Client_id) {
        Client client = clientRepository.findById(Client_id).orElse(null);
        if(client==null){
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        if(reminderDTO.getAmount()<0){
            throw new ValidationException("No se puede ingresar esa cantidad");
        }

        if(reminderDTO.getExpirationDate().isBefore(LocalDate.now())){
            throw new ValidationException("Ingresar una fecha correcta");
        }

        if(reminderRepository.existsByTitleAndClient_id(reminderDTO.getTitle(), Client_id)){
            throw new ValidationException("El titulo ya se encuentra registrado");
        }
        
        if(reminderDTO.getTitle().length()>30){
            throw new ValidationException("Ingresar un titulo de menos 20 caracteres");
        }

        if(reminderDTO.getDetails().length()>200){
            throw new ValidationException("Ingresar un titulo de menos 200 caracteres");
        }

        Reminder reminder= new Reminder(reminderDTO.getId(),reminderDTO.getTitle(),reminderDTO.getDetails(),
                reminderDTO.getAmount(),reminderDTO.getExpirationDate(),client);

        reminder.setClient(client);
        return reminderRepository.save(reminder);

    }

    @Override
    public Reminder Reminderupdate(ReminderRequestDTO reminderRequestDTO, Long id) {
        Reminder reminderFound=getReminderById(id);

        if(reminderFound!=null){

            if(!reminderRequestDTO.getDetails().isBlank()){
                if(reminderRequestDTO.getDetails().length()>200) {
                    throw new ValidationException("La descripcion de los detalles no puden superar los 100 caracteres");
                }
                reminderFound.setDetails(reminderRequestDTO.getDetails());
            }

            if(reminderRequestDTO.getAmount()!=null){
                if(reminderRequestDTO.getAmount()<=0){
                    throw new ValidationException("La cantidad no puede ser menor a 0 ni vacia");
                }
                reminderFound.setAmount(reminderRequestDTO.getAmount());
            }

            if(!reminderRequestDTO.getTitle().isBlank()){
                if(reminderRequestDTO.getTitle().length()>30){
                    throw new ValidationException("El titulo no puede tener más de 100 caracteres");
                }
                Long existingReminderId = reminderIdByTitle(reminderRequestDTO.getTitle(), id);
                if (existingReminderId != null && !existingReminderId.equals(id)) {
                    throw new ValidationException("El titulo ya se encuentra registrado");
                }
                // Asignar el nuevo titulo
                reminderFound.setTitle(reminderRequestDTO.getTitle());
            }

            if(reminderRequestDTO.getExpirationDate()!=null){
                if(reminderRequestDTO.getExpirationDate().isBefore(LocalDate.now())){
                    throw new ValidationException("La fecha debe que ser posterior a la actual");
                }
                reminderFound.setExpirationDate(reminderRequestDTO.getExpirationDate());
            }


            return reminderRepository.save(reminderFound);

        }
        return null;
    }

    @Override
    public Reminder getReminderById(Long id) {
        Reminder reminder=reminderRepository.findById(id).orElse(null);
        if(reminder==null){
            throw new ValidationException("No se encontro el recodatorio");
        }
        return reminder;
    }

    @Override
    public ReminderDTO getReminderResponseById(Long id) {
        Reminder reminder = getReminderById(id);
        ReminderDTO reminderDTO = new ReminderDTO(reminder.getId(), reminder.getTitle(),
                reminder.getDetails(), reminder.getAmount(), reminder.getExpirationDate());
        return reminderDTO;
    }


    @Override
    public void Reminderdelete(Long id) {

        if(!reminderRepository.existsById(id)){
            throw new ValidationException("No se encontró el recordatorio");
        }
        reminderRepository.deleteById(id);
    }


    @Override
    public List<ReminderDTO> findByClient(Long id) {
        Client client=clientRepository.findById(id).orElse(null);
        if(client==null){
            throw new ValidationException("Cliente no encontrado");
        }
        List<Reminder> list = reminderRepository.findByClient(client);
        List<ReminderDTO> reminderDTOS = new ArrayList<>();
        for (Reminder reminder : list){
            ReminderDTO reminderDTO = new ReminderDTO(reminder.getId(), reminder.getTitle(),
                    reminder.getDetails(), reminder.getAmount(), reminder.getExpirationDate());
            reminderDTOS.add(reminderDTO);
        }
        return reminderDTOS;
    }

    @Override
    public List<Reminder> ReminderlistAll() {
        return reminderRepository.findAll();
    }


}
