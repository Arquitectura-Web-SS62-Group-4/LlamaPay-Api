package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.SettingDTO;
import com.wisecoin.LlamaPay_Api.entities.Setting;
import com.wisecoin.LlamaPay_Api.services.SettingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SettingController {
    @Autowired
    SettingService settingService;

    @GetMapping("/settings/user/{id}")
    public ResponseEntity<Setting> listSettingByClient(@PathVariable("id") Long id){
        return new ResponseEntity<Setting>(settingService.findByClient(id),
                HttpStatus.OK);
    }

    @PutMapping("/settings/{id}")
    public ResponseEntity<Setting> updateSetting(@PathVariable("id") Long id, @Valid @RequestBody SettingDTO settingDTO){
        Setting settingUpdate = settingService.updateSetting(id,settingDTO);
        if (settingUpdate!=null) {
            return new ResponseEntity<Setting>(settingUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
