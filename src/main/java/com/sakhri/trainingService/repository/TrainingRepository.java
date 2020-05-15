package com.sakhri.trainingService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sakhri.trainingService.model.Training;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long>{

	public Training findByTrainingId(String trainingId);
}
