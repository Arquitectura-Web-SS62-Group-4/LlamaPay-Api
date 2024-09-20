package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.entities.Premiun;

import java.util.List;

public interface PremiunService {
    public Premiun findById(Long id);
    public List<Premiun> findALl();
}
