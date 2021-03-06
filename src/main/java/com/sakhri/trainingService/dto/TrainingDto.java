package com.sakhri.trainingService.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sakhri.trainingService.enums.Muscle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TrainingDto implements Serializable{
		
	private static final long serialVersionUID = 8434182099458320367L;

	private String trainingId;

	@NotBlank(message="Le nom du training ne peut pas etre vide")
	private String name;
		
	@NotNull(message="Le muscle du training ne peut pas etre vide")
	private Muscle muscle;
	
	private String description;

	@NotNull(message="Les exercices du training ne peuvent pas etre vide")
	@Size(min=1, message="Au moins un exercice")
	private List<Long> exercicesId;

}
