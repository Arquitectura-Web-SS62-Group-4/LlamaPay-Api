package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.ReminderDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.ReminderRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Reminder;
import com.wisecoin.LlamaPay_Api.services.ReminderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ReminderController {
    @Autowired
    ReminderService reminderService;

    @GetMapping("/reminders")
    public ResponseEntity<List<Reminder>> listAllReminders(){
        return new ResponseEntity<List<Reminder>>(reminderService.ReminderlistAll(),
                HttpStatus.OK);
    }

    @GetMapping("/reminders/client/{id}")
    public ResponseEntity<List<ReminderDTO>> listReminderByClient( @PathVariable("id") Long id){
        return new ResponseEntity<List<ReminderDTO>>(reminderService.findByClient(id),
                HttpStatus.OK);
    }

    @GetMapping("/reminders/{id}")
    public ResponseEntity<ReminderDTO> getReminderResponseById(@PathVariable("id") Long id){
        return new ResponseEntity<ReminderDTO>(reminderService.getReminderResponseById(id),
                HttpStatus.OK);
    }

    @PostMapping("/reminders/client/{id}")
    public ResponseEntity<Reminder> Reminderadd(@PathVariable("id") Long id, @Valid @RequestBody ReminderDTO reminderDTO){
        Reminder reminderCreated =reminderService.Reminderadd(reminderDTO,id);
        return new ResponseEntity<Reminder>(reminderCreated,
                HttpStatus.OK);
    }

    @PutMapping("/reminders/{id}")
    public ResponseEntity<Reminder> Reminderupdate(@PathVariable("id") Long id, @Valid @RequestBody ReminderRequestDTO reminderRequestDTO){
        Reminder reminderUpdate = reminderService.Reminderupdate(reminderRequestDTO,id);
        if (reminderUpdate!=null) {
            return new ResponseEntity<>(reminderUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/reminders/{id}")
    public ResponseEntity<HttpStatus> Reminderdelete(@PathVariable("id") Long id){
        reminderService.Reminderdelete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
