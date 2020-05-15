package com.sakhri.trainingService.service.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.test.context.SpringBootTest;

import com.sakhri.trainingService.dto.TrainingDto;
import com.sakhri.trainingService.enums.Muscle;
import com.sakhri.trainingService.model.Training;
import com.sakhri.trainingService.proxy.ExerciceServiceProxy;
import com.sakhri.trainingService.repository.TrainingRepository;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class TrainingServiceImplTest {

	@Spy
	private ModelMapper modelMapper;
	
	@Mock
	private ExerciceServiceProxy exerciceServiceProxy;

	@Mock
	private TrainingRepository trainingRepository;

	@InjectMocks
	private TrainingServiceImpl trainingService;


	@BeforeEach
	public void configModelMapper() {
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}


	@Test
	public void WhenCreateUserWithValidDtoThenReturnTrue() {
		
		// Given
			final TrainingDto dto = TrainingDto.builder()
						.name("tr1")
						.muscle(Muscle.BICEPS)
						.exercicesId(Arrays.asList(1L,2L,3L))
						.build();
		
			when(exerciceServiceProxy.verifExercice(any(Long.class))).thenReturn(true);
		// When
			
			final boolean isCreated = trainingService.createTraining(dto);
		
		// Then
			assertTrue(isCreated);
	}
	
	@Test
	public void whenGettingAllTrainingsThenReturnListOfTrainingDTO() {
		
		// Given
			final Training training1 = Training.builder()
					.id(1L)
					.name("tr1")
					.muscle(Muscle.BICEPS)
					.exercicesId("1,2,3")
					.build();
			List<Training> allTrainings = Arrays.asList(training1);
			
			when(trainingRepository.findAll()).thenReturn(allTrainings);
		// When
		
			final List<TrainingDto> all = trainingService.getAll();
		// Then
		
		assertEquals(1, all.size());
	}
}
