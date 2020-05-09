package com.sakhri.trainingService.proxy ;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name="exercise-service",url="localhost:8082")
//@FeignClient(name="exercise-service")
@FeignClient(name="netflix-zuul-api-gateway-server")
@RibbonClient(name="exercice-service")
public interface ExerciceServiceProxy {
	
	@GetMapping("/exercice-service/exercices/verify/{id}")
	public Boolean verifExercice(@PathVariable("id")Long id);

}
