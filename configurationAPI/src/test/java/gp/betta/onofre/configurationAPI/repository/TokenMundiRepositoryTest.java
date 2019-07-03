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
import gp.betta.onofre.configurationAPI.enity.TokenMundi;
import gp.betta.onofre.configurationAPI.object.TokenMundiObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ConfigurationApiApplication.class)
@DefaultPropertiesTest
public class TokenMundiRepositoryTest {
	
	@Autowired
	private TokenMundiRepository tokenMundiRepository;
	
	private TokenMundiObject object = new TokenMundiObject();
	
	@Test
	public void mustBeReturnTokenMundiByTokenValue() {
		//Build scenario
		Exception exception = null;
		this.tokenMundiRepository.save(this.object.getTokenMundiValid());
		Optional<TokenMundi> optional = null;
		String valueTokenMundFound = null;
		
		//Execute operation
		try {
			optional = this.tokenMundiRepository.findByToken(this.object.getTokenMundiValid().getToken());
			valueTokenMundFound = optional.isPresent() ? optional.get().getToken() : null;
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute test
		assertThat(exception).isNull();
		assertThat(optional).isNotNull();
		assertThat(valueTokenMundFound).isNotNull();
		assertThat(valueTokenMundFound).isEqualTo(this.object.getTokenMundiValid().getToken());
		
	}
	
	@Test
	public void mustBeReturnOptionFalseWhenNotFoundTokenMundi() {
		//Build scenario
		Exception exception = null;
		Optional<TokenMundi> optional = null;
		
		//Execute operation
		try {
			optional = this.tokenMundiRepository.findByToken(this.object.TOKEN_NOT_SAVED);
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute test
		assertThat(exception).isNull();
		assertThat(optional).isNotNull();
		assertThat(optional.isPresent()).isFalse();
		
	}

}
