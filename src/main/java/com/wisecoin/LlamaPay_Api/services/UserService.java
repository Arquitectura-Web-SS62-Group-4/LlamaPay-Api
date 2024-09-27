package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.UserDTO;
import com.wisecoin.LlamaPay_Api.entities.User;

public interface UserService {
    public void logicalDeleteUser(Long id);
    public User findById(Long id);
    public User addUser(UserDTO userDTO);
    public User findByUsername(String userName);
}
