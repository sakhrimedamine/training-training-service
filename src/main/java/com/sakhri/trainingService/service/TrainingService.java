package com.sakhri.trainingService.service;

import java.util.List;

import com.sakhri.trainingService.dto.TrainingDto;

public interface TrainingService {
	
	public boolean createTraining(TrainingDto training);
	
	public TrainingDto getTraining(String trainingId);
	
	public List<TrainingDto> getAll();
	
	public void deleteAll();

}
