package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.request.UserLoginDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.ClientResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.User;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.UserRepository;
import com.wisecoin.LlamaPay_Api.services.ClientService;
import com.wisecoin.LlamaPay_Api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientService clientService;

    @Override
    public void logicalDeleteUser(Long id) {
        User userDelete = getUserById(id);
        if (userDelete.getEnabled() == false) {
            throw new ValidationException("El usuario ya se encuentra eliminado");
        }
        userDelete.setEnabled(false);
        userRepository.save(userDelete);
    }

    @Override
    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return user;
    }

    @Override
    public ClientResponseDTO getClientResponseByUsernameAndPassword(UserLoginDTO userLoginDto) {

        Long client_id = userRepository.getIdByUsernameAndPassword(userLoginDto.getUsername(),userLoginDto.getPassword());
        if(client_id == null){
            throw new ValidationException("El nombre de usuario o contraseña no son válidos");
        }
        Boolean enabled = userRepository.getEnabledByUsername(userLoginDto.getUsername());

        if(enabled == false){
            throw new ValidationException("La cuenta se encuentra eliminada");
        }
        ClientResponseDTO clientResponseDTO = clientService.getClientResponseById(client_id);

        return clientResponseDTO;
    }
}