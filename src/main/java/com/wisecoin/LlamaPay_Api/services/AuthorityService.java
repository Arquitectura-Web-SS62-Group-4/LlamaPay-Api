package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.entities.Authority;

public interface AuthorityService {

    public Authority findById(Long id);
    public Authority addAuthority(Authority authority);
    public Authority findByName(String name);
}
