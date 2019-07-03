package gp.betta.onofre.configurationAPI.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import gp.betta.onofre.configurationAPI.enity.TokenMundi;
import gp.betta.onofre.configurationAPI.repository.TokenMundiRepository;
import gp.betta.onofre.configurationAPI.service.TokenMundiService;

@Service
public class TokenMundiServiceImpl implements TokenMundiService {

	@Autowired
	private TokenMundiRepository repository;

	@Override
	public TokenMundi saveOrUpate(TokenMundi tokenMundi) {
		return this.repository.save(tokenMundi);
	}

	@Override
	public TokenMundi getById(Integer id) {
		try {
			Optional<TokenMundi> optional = this.repository.findById(id);
			return optional.isPresent() ? optional.get() : new TokenMundi();
		} catch (InvalidDataAccessApiUsageException ex) {
			return new TokenMundi();
		}
	}

	@Override
	public TokenMundi getFirstToken() {
		List<TokenMundi> all = this.repository.findAll();
		if (all == null || all.isEmpty())
			return new TokenMundi();

		return all.get(0);
	}

}
