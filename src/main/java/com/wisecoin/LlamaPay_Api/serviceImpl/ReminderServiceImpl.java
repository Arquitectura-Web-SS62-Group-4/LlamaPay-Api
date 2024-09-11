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
import java.util.Date;
import java.util.List;

@Service
public class ReminderServiceImpl implements ReminderService {
    @Autowired
    ReminderRepository reminderRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Reminder Reminderadd(ReminderDTO reminderDTO, Long Client_id) {
        Client client = clientRepository.findById(Client_id).orElse(null);
        if(client==null){
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        if(reminderDTO.getAmount()<0){
            throw new ValidationException("No se puede ingresar esa cantidad");
        }

        if(reminderDTO.getExpiration_date().isBefore(LocalDate.now())){
            throw new ValidationException("Ingresar una fecha correcta");
        }

        if(reminderDTO.getTitle().length()>30){
            throw new ValidationException("Ingresar un titulo de menos 20 caracteres");
        }

        if(reminderDTO.getDetails().length()>200){
            throw new ValidationException("Ingresar un titulo de menos 200 caracteres");
        }

        Reminder reminder= new Reminder(reminderDTO.getId(),client,reminderDTO.getTitle(),reminderDTO.getDetails(),
                reminderDTO.getAmount(),reminderDTO.getExpiration_date());

        reminder.setClient(client);
        return reminderRepository.save(reminder);

    }

    @Override
    public Reminder Reminderupdate(ReminderRequestDTO reminderRequestDTO, Long id) {

        Reminder reminderFound=getReminderById(id);

        if(!reminderRepository.existsById(id)){
            throw new ValidationException("No se encontró el recordatorio");
        }

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
                reminderFound.setTitle(reminderRequestDTO.getTitle());
            }

            if(reminderRequestDTO.getExpiration_date()!=null){
                if(reminderRequestDTO.getExpiration_date().isBefore(LocalDate.now())){
                    throw new ValidationException("La fecha debe que ser posterior a la actual");
                }
                reminderFound.setExpiration_date(reminderRequestDTO.getExpiration_date());
            }


            reminderRepository.save(reminderFound);

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
    public void Reminderdelete(Long id) {

        if(!reminderRepository.existsById(id)){
            throw new ValidationException("No se encontró el recordatorio");
        }
        reminderRepository.deleteById(id);
    }


    @Override
    public List<Reminder> findByClient(Long id) {
        Client client=clientRepository.findById(id).orElse(null);
        if(client==null){
            throw new ValidationException("Cliente no encontrado");
        }
        return reminderRepository.findByClient(client);
    }

    @Override
    public List<Reminder> ReminderlistAll() {
        return reminderRepository.findAll();
    }


}
