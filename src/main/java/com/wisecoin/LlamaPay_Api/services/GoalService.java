package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.GoalDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.GoalRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Goal;

import java.util.List;

public interface GoalService {
    public Goal addGoal(Long ClientId, GoalDTO goalDto);
    public List<Goal> findByClient(Long clientId);
    public List<Goal> listAll();
    public void deleteGoal(Long id);
    public Goal getGoalById(Long id);
    public Goal updateGoal(Long id, GoalRequestDTO goalRequestDto);
}
