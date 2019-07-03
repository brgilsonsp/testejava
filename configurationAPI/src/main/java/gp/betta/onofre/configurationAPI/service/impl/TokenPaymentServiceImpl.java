package gp.betta.onofre.configurationAPI.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import gp.betta.onofre.configurationAPI.enity.TokenPayment;
import gp.betta.onofre.configurationAPI.repository.TokenPaymentRepository;
import gp.betta.onofre.configurationAPI.service.TokenPaymentService;

@Service
public class TokenPaymentServiceImpl implements TokenPaymentService {

	@Autowired
	private TokenPaymentRepository repository;

	@Override
	public TokenPayment saveOrUpate(TokenPayment tokenPayment) {
		return repository.save(tokenPayment);
	}

	@Override
	public TokenPayment getFirstToken() {
		List<TokenPayment> all = this.repository.findAll();
		if (all == null || all.isEmpty())
			return new TokenPayment();

		return all.get(0);
	}

	@Override
	public TokenPayment getById(Integer id) {
		try {
			Optional<TokenPayment> optional = this.repository.findById(id);
			return optional.isPresent() ? optional.get() : new TokenPayment();
		} catch (InvalidDataAccessApiUsageException ex) {
			return new TokenPayment();
		}
	}

}
