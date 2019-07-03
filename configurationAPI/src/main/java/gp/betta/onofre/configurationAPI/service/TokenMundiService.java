package gp.betta.onofre.configurationAPI.service;

import org.springframework.stereotype.Component;

import gp.betta.onofre.configurationAPI.enity.TokenMundi;

@Component
public interface TokenMundiService {

	public TokenMundi saveOrUpate(TokenMundi tokenPayment);
	
	public TokenMundi getById(Integer id);
	
	public TokenMundi getFirstToken();
}
