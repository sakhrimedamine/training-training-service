package com.sakhri.trainingService.proxy;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class ExerciceServiceProxyFactory implements FallbackFactory<ExerciceServiceProxy> {

	@Override
	public ExerciceServiceProxy create(Throwable cause) {
		// TODO Auto-generated method stub
		return new ExerciceServiceProxyFallback(cause);
	}

}