package gp.betta.onofre.configurationAPI.service;

import org.springframework.stereotype.Component;

import gp.betta.onofre.configurationAPI.enity.TokenPayment;

@Component
public interface TokenPaymentService {

	public TokenPayment saveOrUpate(TokenPayment tokenMundi);
	
	public TokenPayment getById(Integer id);

	public TokenPayment getFirstToken();
}
