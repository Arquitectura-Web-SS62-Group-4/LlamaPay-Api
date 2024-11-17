package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.SettingDTO;
import com.wisecoin.LlamaPay_Api.entities.Setting;

public interface SettingService {
    public Setting addSetting(Long clientId);
    public Setting findByClient(Long clientId);
    public Setting getSettingById(Long id);
    public SettingDTO getSettingResponseById(Long id);
    public Setting updateSetting(Long id, SettingDTO settingDto);
}
