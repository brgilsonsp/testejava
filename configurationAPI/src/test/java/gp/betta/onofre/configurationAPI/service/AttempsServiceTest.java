package gp.betta.onofre.configurationAPI.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import gp.betta.onofre.configurationAPI.ConfigurationApiApplication;
import gp.betta.onofre.configurationAPI.annotations.DefaultPropertiesTest;
import gp.betta.onofre.configurationAPI.enity.Attemps;
import gp.betta.onofre.configurationAPI.enity.properties.AttempsProperties;
import gp.betta.onofre.configurationAPI.object.AttempsObjectTest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ConfigurationApiApplication.class)
@DefaultPropertiesTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AttempsServiceTest {

	@Autowired
	private AttempsService service;
	
	@Autowired
	private AttempsProperties attempsProp;
	
	private AttempsObjectTest object = new AttempsObjectTest();
	
	@Test
	public void mustBeReturnThAttempsManagedWhenUpdatedTheValidAttemps() {
		//Build scenario
		Exception exception = null;
		Attemps attempsTransiente = this.object.mustBeReturnThAttempsManagedWhenUpdatedTheValidAttemps();
		Attemps attempsManaged = this.service.saveOrUpdate(attempsTransiente);
		attempsManaged.alterValueLimit(new Random().nextInt(1000));
		Attemps attempsManagedUpdated = null;
		
		//Execute operation
		try {
			attempsManagedUpdated = this.service.saveOrUpdate(attempsManaged);
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute tests
		assertThat(exception).isNull();
		assertThat(attempsManagedUpdated).isNotNull();
		assertThat(attempsManagedUpdated.getId()).isPositive();
		assertThat(attempsManagedUpdated.getLimitTry()).isEqualByComparingTo(attempsManaged.getLimitTry());
		
	}
	
	@Test
	public void mustBeThrowTransactionSystemExceptiondWhenUpdateTheInvalidAttemps() {
		//Build scenario
		Exception exception = null;
		TransactionSystemException transactionException = null;
		Attemps attempsTransiente = this.object.mustBethrowTransactionSystemExceptiondWhenUpdateTheInalidAttemps();
		Attemps attempsManaged = null;
		
		//Execute operation
		try {
			attempsManaged = this.service.saveOrUpdate(attempsTransiente);
		}catch(TransactionSystemException trancExc) {
			transactionException = trancExc;
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute tests
		assertThat(exception).isNull();
		assertThat(transactionException).isNotNull();
		assertThat(attempsManaged).isNull();
	}
	
	@Test
	public void mustBeReturnEmptyAttempsWhenNotFoundById() {
		//Build scenario
		Exception exception = null;
		Attemps attempsFound = this.object.mustBeReturnEmptyAttempsWhenNotFoundById();
		Attemps attempsEmpty = null;
		
		//Execute operation
		try {
			attempsEmpty = this.service.findById(attempsFound.getId());
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute tests
		assertThat(exception).isNull();
		assertThat(attempsEmpty).isNotNull();
		assertThat(attempsEmpty.isAttempsNotValid()).isTrue();
	}
	
	@Test
	public void mustBeReturnAttempsWhenFindById() {
		//Build scenario
		Exception exception = null;
		Attemps attempsTransient = this.object.mustBeReturnAttempsWhenFindById();
		Attemps attempsManaged = this.service.saveOrUpdate(attempsTransient);
		Attemps attempsFound = null;
		
		//Execute operation
		try {
			attempsFound = this.service.findById(attempsManaged.getId());
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute tests
		assertThat(exception).isNull();
		assertThat(attempsFound).isNotNull();
		assertThat(attempsFound.getId()).isPositive();
		assertThat(attempsFound.getLimitTry()).isPositive();
		assertThat(attempsFound.isAttempsNotValid()).isFalse();
	}
	
	
	
	@Test
	public void a0000MustBeReturnEmptyAttempsStandardWhenNotFoundAnyAttemps() {
		//Build scenario
		Exception exception = null;
		Attemps attempsEmpty = null;
		
		//Execute operation
		try {
			attempsEmpty = this.service.findFirst();
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute tests
		assertThat(exception).isNull();
		assertThat(attempsEmpty).isNotNull();
		assertThat(attempsEmpty.getId()).isPositive();
		assertThat(attempsEmpty.getLimitTry()).isEqualByComparingTo(this.attempsProp.getLimit());
		assertThat(attempsEmpty.isAttempsNotValid()).isFalse();
	}
	
	@Test
	public void mustBeReturnAttempsWhenFindAnyAttemps() {
		//Build scenario
		Exception exception = null;
		Attemps attempsTransient = this.object.mustBeReturnAttempsWhenFindAnyAttemps();
		Attemps attempsManaged = this.service.saveOrUpdate(attempsTransient);
		Attemps attempsFound = null;
		
		//Execute operation
		try {
			attempsFound = this.service.findById(attempsManaged.getId());
		}catch(Exception e) {
			exception = e;
		}
		
		//Execute tests
		assertThat(exception).isNull();
		assertThat(attempsFound).isNotNull();
		assertThat(attempsFound.getId()).isPositive();
		assertThat(attempsFound.getLimitTry()).isEqualByComparingTo(attempsManaged.getLimitTry());
		assertThat(attempsFound.isAttempsNotValid()).isFalse();
	}
}
