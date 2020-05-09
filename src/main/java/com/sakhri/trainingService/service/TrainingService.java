package com.sakhri.trainingService.service;

import java.util.List;

import com.sakhri.trainingService.dto.TrainingDto;
import com.sakhri.trainingService.model.Training;

public interface TrainingService {
	
	public boolean createTraining(TrainingDto training);
	public Training getTraining(Long id);
	public List<Training> getAll();

}
