package gp.betta.onofre.configurationAPI.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import gp.betta.onofre.configurationAPI.enity.Attemps;
import gp.betta.onofre.configurationAPI.enity.properties.AttempsProperties;
import gp.betta.onofre.configurationAPI.repository.AttempsRepository;
import gp.betta.onofre.configurationAPI.service.AttempsService;

@Service
public class AttempsServiceImpl implements AttempsService {

	@Autowired
	private AttempsRepository repository;

	@Autowired
	private AttempsProperties attempsProp;

	@Override
	public Attemps saveOrUpdate(Attemps attemps) {
		return this.repository.save(attemps);
	}

	@Override
	public Attemps findById(Integer id) {
		try {
			Optional<Attemps> optional = this.repository.findById(id);
			return optional.isPresent() ? optional.get() : new Attemps();
		} catch (InvalidDataAccessApiUsageException ex) {
			return new Attemps();
		}
	}

	/***
	 * Find the the first record, if not found any record, save the attemps default and return it
	 */
	@Override
	public Attemps findFirst() {
		List<Attemps> all = this.repository.findAll();
		if (all == null || all.isEmpty()) {
			Attemps attempTransient = new Attemps(this.attempsProp.getLimit());
			return this.saveOrUpdate(attempTransient);
		}
		return all.get(0);
	}

}
