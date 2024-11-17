package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.GoalDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.GoalRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.GoalResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.Goal;
import com.wisecoin.LlamaPay_Api.services.GoalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class GoalController {
    @Autowired
    GoalService goalService;

    @GetMapping("/goals")
    public ResponseEntity<List<Goal>> listAllGoals(){
        return new ResponseEntity<List<Goal>>(goalService.listAll(),
                HttpStatus.OK);
    }

   @GetMapping("/goals/{id}")
    public ResponseEntity<GoalDTO> getGoalById(@PathVariable("id") Long id){
        return new ResponseEntity<GoalDTO>(goalService.getGoalResponseById(id),
                HttpStatus.OK);
    }

    @GetMapping("/goals/client/{id}")
    public ResponseEntity<List<GoalResponseDTO>> listGoalByClient(@PathVariable("id") Long id){
        return new ResponseEntity<List<GoalResponseDTO>>( goalService.findByClient(id),
                HttpStatus.OK);
    }

    @PostMapping("/goals/client/{id}")
    public ResponseEntity<Goal> addGoal(@PathVariable("id") Long id, @Valid @RequestBody GoalDTO goalDto){
        Goal goalCreated = goalService.addGoal(id, goalDto);
        return new ResponseEntity<Goal>(goalCreated,
                HttpStatus.OK);
    }

    @PutMapping("/goals/{id}")
    public ResponseEntity<Goal> updateGoal(@PathVariable("id") Long id, @Valid @RequestBody GoalRequestDTO goalRequestDTO){
        Goal goalUpdate = goalService.updateGoal(id,goalRequestDTO);
        if (goalUpdate!=null) {
            return new ResponseEntity<>(goalUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/goals/{id}")
    public ResponseEntity<HttpStatus> deleteGoal(@PathVariable("id") Long id){
        goalService.deleteGoal(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
