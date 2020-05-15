package com.sakhri.trainingService.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sakhri.trainingService.dto.CreateTraining;
import com.sakhri.trainingService.enums.Muscle;
import com.sakhri.trainingService.proxy.ExerciceServiceProxy;
import com.sakhri.trainingService.service.TrainingService;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
class TrainingControllerIntegrationTest {

	@MockBean
	private ExerciceServiceProxy exerciceServiceProxy;

	@Autowired
	private TrainingService trainingService;

    private static MockMvc mvc;

    @BeforeAll
    public static void init(WebApplicationContext context) {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }
    
    @AfterEach
    public void cleanDB() {
    	trainingService.deleteAll();
    }
    
	@Test
	void whenCreateTrainingWithValidDataThenReturnTrue() throws Exception {
		// Given
			final CreateTraining dto = CreateTraining.builder()
				.name("tr1")
				.muscle(Muscle.BICEPS)
				.exercicesId(Arrays.asList(1L,2L,3L))
				.build();
					
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(dto);
		
			when(exerciceServiceProxy.verifExercice(any(Long.class))).thenReturn(true);

		// When
			
			MvcResult mvcResult= mvc.perform(post("/training")
									.contentType(MediaType.APPLICATION_JSON)
									.accept(MediaType.APPLICATION_JSON)
									.content(json)
								).andReturn();

		// then
			String responseAsString = mvcResult.getResponse().getContentAsString();
			
			ObjectMapper objectMapper = new ObjectMapper(); 
					
			Boolean myResponse = objectMapper.readValue(responseAsString, Boolean.class);
			
			assertThat(mvcResult.getResponse().getStatus()).isEqualTo(200);

			assertTrue(myResponse);
			
			verify(exerciceServiceProxy).verifExercice(1L);
			verify(exerciceServiceProxy).verifExercice(2L);
			verify(exerciceServiceProxy).verifExercice(3L);

		
	}

}
