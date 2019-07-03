package gp.betta.onofre.configurationAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gp.betta.onofre.configurationAPI.enity.TokenMundi;

public interface TokenMundiRepository extends JpaRepository<TokenMundi, Integer> {

	public Optional<TokenMundi> findByToken(String token);
}
