package com.sakhri.trainingService.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sakhri.trainingService.enums.Muscle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Training {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String trainingId;
	
	@Column(nullable=false, length=50)
	private String name;
	
	private String description;
	
	@Column(nullable=false)
	private Muscle muscle;
	
	@Column(nullable=false, length=150)
	private String exercicesId;
//	
//	@JsonManagedReference
//	@OneToMany(mappedBy = "training", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Exercice> exercices = new ArrayList<>();
//	
//    public void addExercice(Exercice exercice) {
//    	exercices.add(exercice);
//    	exercice.setTraining(this);
//    }
// 
//    public void removeExercice(Exercice exercice) {
//    	exercices.remove(exercice);
//    	exercice.setTraining(null);
//    }
}
