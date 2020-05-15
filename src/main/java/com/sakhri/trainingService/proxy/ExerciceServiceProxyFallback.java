package com.sakhri.trainingService.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.FeignException;

public class ExerciceServiceProxyFallback implements ExerciceServiceProxy {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	private final Throwable cause;

	public ExerciceServiceProxyFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public Boolean verifExercice(Long id) {
		
		if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			logger.error("404 error took place when verifExercice was called with id: " + id + ". Error message: "
					+ cause.getLocalizedMessage());
		} else {
			logger.error("Other error took place: " + cause.getLocalizedMessage());
		}
		
		return true;

	}

}