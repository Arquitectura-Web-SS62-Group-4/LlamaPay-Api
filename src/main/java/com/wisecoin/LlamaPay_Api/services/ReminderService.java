package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.ReminderDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.ReminderRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Goal;
import com.wisecoin.LlamaPay_Api.entities.Reminder;

import java.util.List;

public interface ReminderService {

    public Reminder Reminderadd(ReminderDTO reminderDTO, Long Client_id);
    public Reminder Reminderupdate(ReminderRequestDTO reminderRequestDTO, Long id);
    public Reminder getReminderById(Long id);
    public void Reminderdelete(Long id);
    List<Reminder> findByClient(Long id);
    public List<Reminder> ReminderlistAll();


}
