package com.sakhri.trainingService.controller;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.sakhri.trainingService.dto.ApiResponseDto;
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
	public ResponseEntity<ApiResponseDto> createUser(@Valid @RequestBody(required = true) TrainingDto trainingDto,
			WebRequest request) {
		log.info("Request for Creating a training with DTO {}", trainingDto);
		trainingService.createTraining(trainingDto);
		return ResponseEntity.ok(ApiResponseDto.builder()
								.message("Training successfully created")
								.timestamp(LocalDateTime.now())
								.details(request.getDescription(false))
								.build());
	}
	
	
	@GetMapping
	public ResponseEntity<List<TrainingDto>> getAll() {
		log.info("Request for getting all training ");
		return ResponseEntity.ok(trainingService.getAll()
				.stream()
				.map(tr ->  {
					TrainingDto dto = modelMapper.map(tr, TrainingDto.class);
					List<Long> asList = Arrays.asList(tr.getExercicesId().split(","))
												.stream()
												.map(x -> Long.parseLong(x))
												.collect(Collectors.toList());
					dto.setExercicesId(asList);
					return dto;
				})
				.collect(Collectors.toList()));
	}
}