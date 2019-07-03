package gp.betta.onofre.configurationAPI.object;

import java.util.Random;

import gp.betta.onofre.configurationAPI.enity.Attemps;

public class AttempsObjectTest {

	public Attemps mustBeReturnThAttempsManagedWhenUpdatedTheValidAttemps() {
		return new Attemps(58);
	}

	public Attemps mustBethrowTransactionSystemExceptiondWhenUpdateTheInalidAttemps() {
		return new Attemps();
	}

	public Attemps mustBeReturnStatusBadRequestWhenRequestWhithoutValidToken() {
		return new Attemps(new Random().nextInt(5000));
	}

	public Attemps mustBeReturnStatusForbiddenWhenRequestWhithInvalidToken() {
		return new Attemps(new Random().nextInt(5000));
	}

	public Attemps mustBeReturnStatus417WhenRequestUpdateInvalidAttemps() {
		return new Attemps();
	}

	public Attemps mustBeReturnAttempsWhenFindById() {
		return new Attemps(586);
	}

	public Attemps mustBeReturnAttempsWhenFindAnyAttemps() {
		return  new Attemps(8745);
	}

	public Attemps mustBeReturnStatus404WhenRequestUpdateAttempsNotSaved() {
		return new Attemps(14598);
	}

	public Attemps mustBeReturnEmptyAttempsWhenNotFoundById() {
		return new Attemps(8955);
	}

	public Attemps mustBeReturnStatus200WhenRequestUpdateValidAttemps() {
		return new Attemps(89585);
	}

}
