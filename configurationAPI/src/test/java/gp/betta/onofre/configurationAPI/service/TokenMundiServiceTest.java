package gp.betta.onofre.configurationAPI.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import gp.betta.onofre.configurationAPI.ConfigurationApiApplication;
import gp.betta.onofre.configurationAPI.annotations.DefaultPropertiesTest;
import gp.betta.onofre.configurationAPI.enity.TokenMundi;
import gp.betta.onofre.configurationAPI.object.TokenMundiObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ConfigurationApiApplication.class)
@DefaultPropertiesTest
public class TokenMundiServiceTest {
	
	@Autowired
	private TokenMundiService service;
	
	private TokenMundiObject object = new TokenMundiObject();
	
	@Test
	public void mustBeReturnTokenMundiWithIdWhenSentTokenMundiValidForSaveOrUpdate() {
		//Build scenario
		Exception exception = null;
		TokenMundi tokenSaved = null;
		TokenMundi tokenForSave = this.object.mustBeReturnTokenMundiWithIdWhenSentTokenMundiValidForSaveOrUpdate();
		
		//Execute operation
		try {
			tokenSaved = this.service.saveOrUpate(tokenForSave);
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute test
		assertThat(exception).isNull();
		assertThat(tokenSaved).isNotNull();
		assertThat(tokenSaved.getId()).isPositive();
		assertThat(tokenSaved.getToken()).isEqualTo(tokenForSave.getToken());
		
	}
	
	@Test
	public void throwTransactionSystemExceptiondWhenSentTokenMundiValidForSaveOrUpdate() {
		//Build scenario
		Exception exception = null;
		TransactionSystemException transactionException = null;
		TokenMundi tokenSaved = null;
		TokenMundi tokenForSave = new TokenMundi();
		
		//Execute operation
		try {
			tokenSaved = this.service.saveOrUpate(tokenForSave);
		}catch(TransactionSystemException tE) {
			transactionException = tE;
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute test
		assertThat(exception).isNull();
		assertThat(transactionException).isNotNull();
		assertThat(tokenSaved).isNull();
		
	}
	
	@Test
	public void mustBeReturnTokenMundiWithIdWhenFoundTokenMundiSaved() {
		//Build scenario
		Exception exception = null;
		TokenMundi tokenSaved = null;
		TokenMundi tokenForSave = this.object.mustBeReturnTokenMundiWithIdWhenFoundTokenMundiSaved();
		this.service.saveOrUpate(tokenForSave);
		
		//Execute operation
		try {
			tokenSaved = this.service.getById(tokenForSave.getId());
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute test
		assertThat(exception).isNull();
		assertThat(tokenSaved).isNotNull();
		assertThat(tokenSaved.getToken()).isEqualTo(tokenForSave.getToken());
		
	}
	
	@Test
	public void mustBeReturnTokenMundiEmptyWhenFoundRecordNotSaved() {
		//Build scenario
		Exception exception = null;
		TokenMundi tokenFound = null;
		
		//Execute operation
		try {
			tokenFound = this.service.getById(new TokenMundi().getId());
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute test
		assertThat(exception).isNull();
		assertThat(tokenFound).isNotNull();
		assertThat(tokenFound.isTokenEmpty()).isTrue();
		
	}

}
