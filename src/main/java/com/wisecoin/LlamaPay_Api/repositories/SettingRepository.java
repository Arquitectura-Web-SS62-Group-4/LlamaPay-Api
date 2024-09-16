package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    public Setting findByClient(Client client);
}
