package gp.betta.onofre.configurationAPI.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gp.betta.onofre.configurationAPI.ConfigurationApiApplication;
import gp.betta.onofre.configurationAPI.annotations.DefaultPropertiesTest;
import gp.betta.onofre.configurationAPI.enity.TokenPayment;
import gp.betta.onofre.configurationAPI.object.TokenPaymentObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationApiApplication.class)
@DefaultPropertiesTest
public class TokenPaymentRepositoryTest {

	@Autowired
	private TokenPaymentRepository tokenPaymentRepository;

	private TokenPaymentObject object = new TokenPaymentObject();

	@Test
	public void mustBeReturnTokenPaymentByTokenValue() {
		// Build scenario
		Exception exception = null;
		this.tokenPaymentRepository.save(this.object.mustBeReturnTokenPaymentByTokenValue());
		Optional<TokenPayment> optional = null;
		String valueTokenMundFound = null;

		// Execute operation
		try {
			optional = this.tokenPaymentRepository.findByToken(this.object.mustBeReturnTokenPaymentByTokenValue().getToken());
			valueTokenMundFound = optional.isPresent() ? optional.get().getToken() : null;
		} catch (Exception e) {
			exception = e;
		}

		// Execute test
		assertThat(exception).isNull();
		assertThat(optional).isNotNull();
		assertThat(valueTokenMundFound).isNotNull();
		assertThat(valueTokenMundFound).isEqualTo(this.object.mustBeReturnTokenPaymentByTokenValue().getToken());

	}

	@Test
	public void mustBeReturnOptionFalseWhenNotFoundTokenPayment() {
		// Build scenario
		Exception exception = null;
		Optional<TokenPayment> optional = null;

		// Execute operation
		try {
			optional = this.tokenPaymentRepository.findByToken(this.object.TOKEN_NOT_SAVED);
		} catch (Exception e) {
			exception = e;
		}

		// Execute test
		assertThat(exception).isNull();
		assertThat(optional).isNotNull();
		assertThat(optional.isPresent()).isFalse();

	}

}