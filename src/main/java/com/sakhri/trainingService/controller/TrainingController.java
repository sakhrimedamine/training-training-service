package com.sakhri.trainingService.controller;

import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sakhri.trainingService.dto.CreateTraining;
import com.sakhri.trainingService.dto.TrainingDto;
import com.sakhri.trainingService.service.TrainingService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController()
@RequestMapping("training")
public class TrainingController {
	
	@Autowired
	private TrainingService trainingService;
	
	@Autowired
	private ModelMapper modelMapper;

	
	@PostMapping
	public ResponseEntity<Boolean> createTraining(@Valid @RequestBody(required = true) 
						CreateTraining createTraining) {
		
		log.info("Request for Creating a training with DTO {}", createTraining);
		
		TrainingDto trainingDto = modelMapper.map(createTraining, TrainingDto.class);

		final boolean isCreated = trainingService.createTraining(trainingDto);
		
		log.info("Training succesfully created  {}", isCreated);

		return ResponseEntity.ok(isCreated);
	}
	
	
	@GetMapping
	public ResponseEntity<List<TrainingDto>> getAllTraining() {
		
		log.info("Request for getting all trainings ");
		
		final List<TrainingDto> allTrainings = trainingService.getAll();
		
		log.info("All trainings {}", allTrainings);

		return ResponseEntity.ok(allTrainings);
	}
}