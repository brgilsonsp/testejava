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
import gp.betta.onofre.configurationAPI.enity.TokenPayment;
import gp.betta.onofre.configurationAPI.object.TokenPaymentObject;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationApiApplication.class)
@DefaultPropertiesTest
public class TokenPaymentServiceTest {

	@Autowired
	private TokenPaymentService service;

	private TokenPaymentObject object = new TokenPaymentObject();

	@Test
	public void mustBeReturnTokenPaymentWithIdWhenSentTokenMundiValidForSaveOrUpdate() {
		// Build scenario
		Exception exception = null;
		TokenPayment tokenSaved = null;
		TokenPayment tokenForSave = this.object.mustBeReturnTokenPaymentWithIdWhenSentTokenMundiValidForSaveOrUpdate();

		// Execute operation
		try {
			tokenSaved = this.service.saveOrUpate(tokenForSave);
		} catch (Exception e) {
			exception = e;
		}

		// Execute test
		assertThat(exception).isNull();
		assertThat(tokenSaved).isNotNull();
		assertThat(tokenSaved.getId()).isPositive();
		assertThat(tokenSaved.getToken()).isEqualTo(tokenForSave.getToken());

	}

	@Test
	public void throwTransactionSystemExceptiondWhenSentTokenPaymentValidForSaveOrUpdate() {
		// Build scenario
		Exception exception = null;
		TransactionSystemException transactionException = null;
		TokenPayment tokenSaved = null;
		TokenPayment tokenForSave = new TokenPayment();

		// Execute operation
		try {
			tokenSaved = this.service.saveOrUpate(tokenForSave);
		} catch (TransactionSystemException tE) {
			transactionException = tE;
		} catch (Exception e) {
			exception = e;
		}

		// Execute test
		assertThat(exception).isNull();
		assertThat(transactionException).isNotNull();
		assertThat(tokenSaved).isNull();

	}
	
	@Test
	public void mustBeReturnTokenPaymentWhenRequestedTheAnyTokenPayment() {
		// Build scenario
		Exception exception = null;
		TokenPayment tokenSaved = null;
		TokenPayment tokenForSave = this.object.mustBeReturnTokenPaymentWhenRequestedTheAnyTokenPayment();
		this.service.saveOrUpate(tokenForSave);

		// Execute operation
		try {
			tokenSaved = this.service.getFirstToken();
		} catch (Exception e) {
			exception = e;
		}

		// Execute test
		assertThat(exception).isNull();
		assertThat(tokenSaved).isNotNull();
	}
	
	@Test
	public void mustBeReturnTokenPaymentWhenFindByTokenValue() {
		// Build scenario
		Exception exception = null;
		TokenPayment tokenFound = null;
		TokenPayment tokenForSave = this.object.mustBeReturnTokenPaymentWhenFindByTokenValue();
		TokenPayment tokenSaved = this.service.saveOrUpate(tokenForSave);

		// Execute operation
		try {
			tokenFound = this.service.getById(tokenSaved.getId());
		} catch (Exception e) {
			exception = e;
		}

		// Execute test
		assertThat(exception).isNull();
		assertThat(tokenFound).isNotNull();
		assertThat(tokenFound.getId()).isPositive();
		assertThat(tokenFound.getToken()).isEqualTo(tokenForSave.getToken());
	}
	
	@Test
	public void mustBeReturnTokenPaymentBlankWhenNotSentIdForSearchToken() {
		// Build scenario
		Exception exception = null;
		TokenPayment tokenFound = null;

		// Execute operation
		try {
			tokenFound = this.service.getById(new TokenPayment().getId());
		} catch (Exception e) {
			exception = e;
		}

		// Execute test
		assertThat(exception).isNull();
		assertThat(tokenFound).isNotNull();
		assertThat(tokenFound.isTokenEmpty()).isTrue();
	}

}