package com.sakhri.trainingService.service.impl;

import java.util.List;
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
		log.info("Create Training from DTO {}", trainingDto);
		Training training = modelMapper.map(trainingDto, Training.class);
		trainingDto.getExercicesId()
					.stream()
					.forEach(exerciceID -> {
						Boolean exist = exerciceServiceProxy.verifExercice(exerciceID);
						log.info(exist);
						if(!exist)
							throw new IllegalArgumentException(EXERCICE_NOT_FOUND_EXECP);
					});
		training.setExercicesId(trainingDto.getExercicesId()
											.stream().map(id -> id+"")
											.collect(Collectors.joining(",")));
		log.info("Saving Training to DB", training);
		trainingRepository.save(training);
		return true;
	}
	
	@Override
	public Training getTraining(Long id) {
		log.info("Getting Training With ID {}", id);
		Training training = trainingRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException(TRAINING_NOT_FOUND_EXECP)
				);
		log.info("Training retreived", training);
		return training;
	}

	@Override
	public List<Training> getAll() {
		return trainingRepository.findAll();
	}

}
