package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.UserDTO;
import com.wisecoin.LlamaPay_Api.entities.Authority;
import com.wisecoin.LlamaPay_Api.entities.User;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.UserRepository;
import com.wisecoin.LlamaPay_Api.security.SecurityUser;
import com.wisecoin.LlamaPay_Api.services.AuthorityService;
import com.wisecoin.LlamaPay_Api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthorityService authorityService;

    @Override
    public void logicalDeleteUser(Long id) {
        User user = findById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        User userFound= userRepository.findById(id).orElse(null);
        if (userFound==null){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return userFound;
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";
        return password != null && password.matches(regex);
    }

    @Override
    public User addUser(UserDTO userDTO) {
        //Validando username
        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new ValidationException("El nombre de usuario ya se encuentra registrado");
        }
        if(userDTO.getUsername().length()<=2){
            throw new ValidationException("El nombre de usuario no puede tener menos de tres caracteres");
        }

        //Validando password
        if(!isValidPassword(userDTO.getPassword())){
            throw new ValidationException("La contraseña no cumple con los requisitos");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        user.setEnabled(true);
        user.setRegistrationDate(LocalDateTime.now());
        user.setAuthorities(authoritiesFromString(userDTO.getAuthorities()));

        return userRepository.save(user);
    }

    private List<Authority> authoritiesFromString(String authoritiesString){
        List<Authority> authorityList = new ArrayList<>();
        List<String> authorityStringList = List.of(authoritiesString.split(";"));
        for(String auString : authorityStringList) {
            Authority authorityFound = authorityService.findByName(auString);
            if (authorityFound!=null) {
                authorityList.add(authorityFound);
            }
        }
        return authorityList;
    }

    @Override
    public User findByUsername(String userName) {
        User userFound= userRepository.findByUsername(userName);
        if (userFound==null){
            throw new ResourceNotFoundException("EL nombre de usuario no existe");
        }
        return userFound;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(this.findByUsername(username));
    }
}