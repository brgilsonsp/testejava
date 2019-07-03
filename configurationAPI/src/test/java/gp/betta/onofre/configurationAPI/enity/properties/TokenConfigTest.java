package gp.betta.onofre.configurationAPI.enity.properties;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import gp.betta.onofre.configurationAPI.ConfigurationApiApplication;
import gp.betta.onofre.configurationAPI.annotations.DefaultPropertiesTest;
import gp.betta.onofre.configurationAPI.object.ObjectTokenConfigTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ConfigurationApiApplication.class)
@DefaultPropertiesTest
public class TokenConfigTest {

	@Autowired
	private TokenConfig tokenConfig;
	
	private ObjectTokenConfigTest object = new ObjectTokenConfigTest();
	
	@Test
	public void mustBeReturnTheSameValueInTheApplicationPropertiesFileForTokenValue() {
		//Build scenario
		Exception exception = null;
		String tokenKey = null;
		String tokenValue = null;
		
		//Execute operation
		try {
			tokenKey = this.tokenConfig.getKey();
			tokenValue = this.tokenConfig.getValue();
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute test
		assertThat(exception).isNull();
		assertThat(this.tokenConfig).isNotNull();
		assertThat(tokenKey).isNotNull();
		assertThat(tokenKey).isEqualTo(this.object.KEY_HEADER_VALID);
		assertThat(tokenValue).isNotNull();
		assertThat(tokenValue).isEqualTo(this.object.VALUE_TOKEN_VALID);
		
	}
}
