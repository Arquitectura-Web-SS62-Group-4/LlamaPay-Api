package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.ClientDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.ClientRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.ClientHasPremiunDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.ClientResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import com.wisecoin.LlamaPay_Api.entities.User;
import com.wisecoin.LlamaPay_Api.exceptions.InvalidActionException;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.services.ClientService;
import com.wisecoin.LlamaPay_Api.services.SettingService;
import com.wisecoin.LlamaPay_Api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SettingService settingService;

    @Autowired
    UserService userService;


    public Long clientIdByEmail(String email){
        List<Client> listEmailsDuplicados = clientRepository.findByEmail(email);
        if (!listEmailsDuplicados.isEmpty()) {
            return listEmailsDuplicados.get(0).getId();
        }
        return null;
    }

    public Long clientIdByPhoneNumber(String phoneNumber){
        List<Client> listPhoneNumberDuplicados = clientRepository.findByPhoneNumber(phoneNumber);
        if (!listPhoneNumberDuplicados.isEmpty()) {
            return listPhoneNumberDuplicados.get(0).getId();
        }
        return null;
    }


    @Override
    public Client addClient(ClientDTO clientDto, User user) {
        //Se crea en false
        boolean has_premiun = false;

        Client client = new Client(clientDto.getId(), clientDto.getFirstName(),
                clientDto.getLastName(), clientDto.getEmail(), clientDto.getPhoneNumber(), clientDto.getBirthdate(),
                clientDto.getGender(),clientDto.getProfilePicture(),has_premiun,user);

        client= clientRepository.save(client);

        settingService.addSetting(client.getId());

        return client;
    }

    @Override
    public List<Client> listAll() {
        return clientRepository.findAll();
    }

    @Override
    public void deleteClient(Long id) {
        Client client = getClientById(id);
        if(client !=null){
            throw new InvalidActionException("No se puede realizar esta acción!");
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
                Long existingClientId = clientIdByEmail(clientRequestDTO.getEmail());
                if (existingClientId != null && !existingClientId.equals(id)) {
                    throw new ValidationException("El email ya se encuentra registrado");
                }

                // Asignar el nuevo nombre
                clientFound.setEmail(clientRequestDTO.getEmail());
            }

            //Validando phone
            if(!clientRequestDTO.getPhoneNumber().isBlank()){
                if (!(clientRequestDTO.getPhoneNumber().matches("\\d+"))|| clientRequestDTO.getPhoneNumber().length() != 9) {
                    throw new ValidationException("El número de teléfono debe tener exactamente 9 digitos");
                }
                Long existingClientId = clientIdByPhoneNumber(clientRequestDTO.getPhoneNumber());
                if (existingClientId != null && !existingClientId.equals(id)) {
                    throw new ValidationException("El número de teléfono ya se encuentra registrado");
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

            if(!clientRequestDTO.getProfilePicture().isBlank()){
                if (clientRequestDTO.getProfilePicture().length() < 3) {
                    throw new ValidationException("La foto de perfil no es válida");
                }
                clientFound.setProfilePicture(clientRequestDTO.getProfilePicture());
            }

            return clientRepository.save(clientFound);
        }

        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        return null;
    }

    @Override
    public ClientResponseDTO getClientResponseById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);
        if (client==null) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        ClientResponseDTO clientResponseDto = new ClientResponseDTO(
                client.getId(), client.getFirstName(), client.getLastName(),
                client.getEmail(), client.getPhoneNumber(), client.getBirthdate(),
                client.getGender(), client.getProfilePicture()
        );
        return clientResponseDto;
    }

    @Override
    public ClientHasPremiunDTO getClientPremiunById(Long id) {
        Client cLient = getClientById(id);
        ClientHasPremiunDTO clientHasPremiunDTO = new ClientHasPremiunDTO(cLient.getId(),
                cLient.getHasPremiun());
        return clientHasPremiunDTO;
    }

    @Override
    public Long getClientIdByUserId(Long userId) {
        Client client = clientRepository.getClientByUserId(userId);
        return client.getId();
    }
}
