package gp.betta.onofre.configurationAPI.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gp.betta.onofre.configurationAPI.enity.TokenPayment;

public interface TokenPaymentRepository extends JpaRepository<TokenPayment, Integer> {

	public Optional<TokenPayment> findByToken(String token);
}
