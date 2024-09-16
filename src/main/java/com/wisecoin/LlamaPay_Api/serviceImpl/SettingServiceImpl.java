package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.SettingDTO;
import com.wisecoin.LlamaPay_Api.entities.Card;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.Setting;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.repositories.SettingRepository;
import com.wisecoin.LlamaPay_Api.services.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    SettingRepository settingRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Setting addSetting(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if (client==null) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        Setting setting = new Setting(0L,"B","M","S",client);

        setting.setClient(client);
        return settingRepository.save(setting);
    }

    @Override
    public Setting findByClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        if(client==null){
            throw new ValidationException("Cliente no encontrado");
        }
        return settingRepository.findByClient(client);
    }

    @Override
    public Setting getSettingById(Long id) {
        Setting setting = settingRepository.findById(id).orElse(null);
        if (setting==null) {
            throw new ResourceNotFoundException("Configuracion no encontrada");
        }
        return setting;
    }

    @Override
    public Setting updateSetting(Long id, SettingDTO settingDto) {
        Setting settingFound = getSettingById(id);
        if(settingFound!=null){
            if(!settingDto.getTheme().isBlank()){
                if(!("B".equals(settingDto.getTheme()) || "N".equals(settingDto.getTheme()))){
                    throw new ValidationException("El tema es inválaido. Solo puedo 'B' o 'N'");
                }
                settingFound.setTheme(settingDto.getTheme());
            }

            if(!settingDto.getSize().isBlank()){
                if(!("S".equals(settingDto.getSize()) || "M".equals(settingDto.getSize()) || "L".equals(settingDto.getSize()))){
                    throw new ValidationException("El tamaño es inválaido. Solo puedo 'S','M' o 'L'");
                }
                settingFound.setSize(settingDto.getSize());
            }

            if(!settingDto.getLanguage().isBlank()){
                if(!("E".equals(settingDto.getLanguage()) || "S".equals(settingDto.getLanguage()))){
                    throw new ValidationException("El lenguaje es inválaido. Solo puedo 'E' o 'S'");
                }
                settingFound.setLanguage(settingDto.getLanguage());
            }
            return settingRepository.save(settingFound);
        }

        if(!settingRepository.existsById(id)){
            throw new ResourceNotFoundException("Configuracion no encontrado");
        }
        return null;
    }
}
