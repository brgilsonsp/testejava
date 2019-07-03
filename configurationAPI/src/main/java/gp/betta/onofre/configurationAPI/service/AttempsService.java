package gp.betta.onofre.configurationAPI.service;

import org.springframework.stereotype.Component;

import gp.betta.onofre.configurationAPI.enity.Attemps;

@Component
public interface AttempsService {

	public Attemps saveOrUpdate(Attemps attemps);
	
	public Attemps findById(Integer id);
	
	public Attemps findFirst();
}
