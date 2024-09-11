package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.entities.User;

public interface UserService {
    public void logicalDeleteUser(Long id);
    public User getUserById(Long id);
}
