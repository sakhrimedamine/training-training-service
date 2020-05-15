package com.sakhri.trainingService.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sakhri.trainingService.dto.TrainingDto;
import com.sakhri.trainingService.model.Training;
import com.sakhri.trainingService.proxy.ExerciceServiceProxy;
import com.sakhri.trainingService.repository.TrainingRepository;
import com.sakhri.trainingService.service.TrainingService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class TrainingServiceImpl implements TrainingService {

	private static final String TRAINING_NOT_FOUND_EXECP = "Training not found.";
	private static final String EXERCICE_NOT_FOUND_EXECP = "Exercice not found.";

	@Autowired
	private TrainingRepository trainingRepository;
	
	@Autowired
	private ExerciceServiceProxy exerciceServiceProxy;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public boolean createTraining(TrainingDto trainingDto) {
		
		log.info("Create Training Entity from DTO {}", trainingDto);
		
		trainingDto.setTrainingId(UUID.randomUUID().toString());

		Training training = modelMapper.map(trainingDto, Training.class);
		
		trainingDto.getExercicesId()
					.stream()
					.forEach(exerciceID -> verifyExercice(exerciceID));
		
		training.setExercicesId(trainingDto.getExercicesId()
											.stream().map(id -> id+"")
											.collect(Collectors.joining(",")));
		log.info("Saving Training to DB", training);
		
		trainingRepository.save(training);
		
		return true;
	}
	
	@Override
	public TrainingDto getTraining(String trainingId) {
		
		log.info("Getting Training With trainingId {}", trainingId);
		
		final Training findByTrainingId = trainingRepository.findByTrainingId(trainingId);
		
		Training training = Optional.ofNullable(findByTrainingId).orElseThrow(
				() -> new IllegalArgumentException(TRAINING_NOT_FOUND_EXECP)
				);
		
		log.info("Training retreived {} ", training);
		
		TrainingDto dto = modelMapper.map(training, TrainingDto.class);

		return dto;
	}

	@Override
	public List<TrainingDto> getAll() {
		
		log.info("Getting all Trainings");

		return trainingRepository.findAll()
				.stream()
				.map(training -> createTrainingDtoFromEntity(training))
				.collect(Collectors.toList());
	}

	private TrainingDto createTrainingDtoFromEntity(Training tr) {
		
		log.info("Creating TrainingDto From Entity {}", tr);

		TrainingDto dto = modelMapper.map(tr, TrainingDto.class);
		
		List<Long> asList = Arrays.asList(tr.getExercicesId().split(","))
									.stream()
									.map(x -> Long.parseLong(x))
									.collect(Collectors.toList());
		dto.setExercicesId(asList);
		
		log.info("TrainingDto : {}", dto);

		return dto;
	}

	private void verifyExercice(Long exerciceID) {
		
		log.info("Verifying exerciceId {}", exerciceID);

		Boolean exist = exerciceServiceProxy.verifExercice(exerciceID);
		
		log.info("ExerciceId {} exist : {}", exerciceID, exist);
		
		if(!exist)
			throw new IllegalArgumentException(EXERCICE_NOT_FOUND_EXECP);
	}

	@Override
	public void deleteAll() {

		log.info("Deleting all Trainings");

		trainingRepository.deleteAll();
	}
}
