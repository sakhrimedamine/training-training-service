package com.sakhri.trainingService.dto;


import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.sakhri.trainingService.enums.Muscle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciceDto implements Serializable{
	
	private static final long serialVersionUID = -3582025453412517013L;

	private Long id;
	
	@NotBlank(message="Le nom de l'exercice ne peut pas etre vide")
	private String name;
	
	@NotBlank(message="La description de l'exercice ne peut pas etre vide")
	private String description;
	
	@NotNull(message="Le muscle de l'exercice ne peut pas etre vide")
	private Muscle muscle;
	
}
