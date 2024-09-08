package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.ClientDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.ClientRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Override
    public Client addClient(ClientDTO clientDto) {
        LocalDate today = LocalDate.now();

        //Validando firstName
        if(clientDto.getFirstName().length()<=2){
            throw new ValidationException("El nombre del cliente no puede tener menos de tres caracteres");
        }

        //Validando lastName
        if(clientDto.getLastName().length()<=2){
            throw new ValidationException("El apellido del cliente no puede tener menos de tres caracteres");
        }

        //Validando email
        if(clientRepository.existsByEmail(clientDto.getEmail())){
            throw new ValidationException("El email ya se encuentra registrado");
        }


        //Validando phone
        if(clientRepository.existsByPhoneNumber(clientDto.getPhoneNumber())){
            throw new ValidationException("El número de teléfono se encuentra registrado");
        }

        if (!(clientDto.getPhoneNumber().matches("\\d+"))|| clientDto.getPhoneNumber().length() != 9) {
            throw new ValidationException("El número de teléfono debe tener exactamente 9 digitos");
        }

        //validando birthdate
        int age = Period.between(clientDto.getBirthdate(), today).getYears();
        if(clientDto.getBirthdate().isAfter(today) || age<18 ){
            throw new ValidationException("La fecha ingresada no es válida");
        }

        //Validando gender
        if (clientDto.getGender().length() != 1 || (!"M".equals(clientDto.getGender()) && !"F".equals(clientDto.getGender()))) {
            throw new ValidationException("El género del cliente es inválido. Debe ser 'M' o 'F'");
        }

        //Se crea en false
        boolean has_premiun = false;

        Client client = new Client(clientDto.getId(), clientDto.getFirstName(),
                clientDto.getLastName(), clientDto.getEmail(), clientDto.getPhoneNumber(), clientDto.getBirthdate(),
                clientDto.getGender(),has_premiun);

        return clientRepository.save(client);
    }

    @Override
    public List<Client> listAll() {
        return clientRepository.findAll();
    }

    @Override
    public void deleteClient(Long id) {
        Client client = getClientById(id);
        if (client !=null) {
            /*
            if (client.().isEmpty()) {
                brandRepository.delete(client);
            } else {
                throw new InvalidActionException("Brand with id: "+ id +" can not be deleted because it has FK dependencies");
            }

             */
        }
    }

    @Override
    public Client getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client==null) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        return client;
    }

    @Override
    public Client updateClient(Long id, ClientRequestDTO clientRequestDTO) {
        Client clientFound = getClientById(id);
        if(clientFound!=null){
            LocalDate today = LocalDate.now();

            //Validando firstName
            if(!clientRequestDTO.getFirstName().isBlank()){
                if(clientRequestDTO.getFirstName().length()<=2){
                    throw new ValidationException("El nombre del cliente no puede tener menos de tres caracteres");
                }
                clientFound.setFirstName(clientRequestDTO.getFirstName());
            }

            //Validando lastName
            if(!clientRequestDTO.getLastName().isBlank()){
                if(clientRequestDTO.getLastName().length()<=2){
                    throw new ValidationException("El apellido del cliente no puede tener menos de tres caracteres");
                }
                clientFound.setLastName(clientRequestDTO.getLastName());
            }

            //Validando email
            if(!clientRequestDTO.getEmail().isBlank()){
                if(clientRepository.existsByEmail(clientRequestDTO.getEmail())){
                    throw new ValidationException("El email ya se encuentra registrado");
                }
                clientFound.setEmail(clientRequestDTO.getEmail());
            }

            //Validando phone
            if(!clientRequestDTO.getPhoneNumber().isBlank()){
                if(clientRepository.existsByPhoneNumber(clientRequestDTO.getPhoneNumber())){
                    throw new ValidationException("El número de teléfono se encuentra registrado");
                }
                if (!(clientRequestDTO.getPhoneNumber().matches("\\d+"))|| clientRequestDTO.getPhoneNumber().length() != 9) {
                    throw new ValidationException("El número de teléfono debe tener exactamente 9 digitos");
                }
                clientFound.setPhoneNumber(clientRequestDTO.getPhoneNumber());
            }

            //validando birthdate
            if(clientRequestDTO.getBirthdate()!=null){
                int age = Period.between(clientFound.getBirthdate(), today).getYears();
                if(clientRequestDTO.getBirthdate().isAfter(today) || age<18 ){
                    throw new ValidationException("La fecha ingresada no es válida");
                }
                clientFound.setBirthdate(clientRequestDTO.getBirthdate());
            }


            //Validando gender
            if(!clientRequestDTO.getGender().isBlank()){
                if (clientRequestDTO.getGender().length() != 1 || (!"M".equals(clientRequestDTO.getGender()) && !"F".equals(clientRequestDTO.getGender()))) {
                    throw new ValidationException("El género del cliente es inválido. Debe ser 'M' o 'F'");
                }
                clientFound.setGender(clientRequestDTO.getGender());
            }
            return clientRepository.save(clientFound);
        }

        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        return null;
    }
}
