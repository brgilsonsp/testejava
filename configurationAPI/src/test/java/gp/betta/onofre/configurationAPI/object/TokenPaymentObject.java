package gp.betta.onofre.configurationAPI.object;

import gp.betta.onofre.configurationAPI.enity.TokenPayment;

public class TokenPaymentObject {

	public final String TOKEN_NOT_SAVED = "TOKEN_PAYMENT_NOT_SAVED";

	public TokenPayment mustBeReturnTokenPaymentByTokenValue() {
		return new TokenPayment("TOKEN_PAY_SDK$KDSJJ$KDSSLksdj44j45");
	}

	public TokenPayment mustBeReturnTokenPaymentWithIdWhenSentTokenMundiValidForSaveOrUpdate() {
		return new TokenPayment("TOKEN_PAY_SDK$KDSVaLidJJ$KDSSLksdj44j45");
	}

	public TokenPayment mustBeReturnTokenPaymentWhenRequestedTheAnyTokenPayment() {
		return new TokenPayment("TOKEN_PAY_Sder4545345fgfd885asdSVaLidJJ$KDSSLksdj44j45");
	}

	public TokenPayment mustBeReturnStatusBadRequestWhenRequestWhithoutValidToken() {
		return new TokenPayment("TOKEN_PAY_Sder4545345fgfd885asdSVaLidJdsdsdJ$KDSSLksdj44j45");
	}

	public TokenPayment mustBeReturnStatusForbiddenWhenRequestWhithInvalidToken() {
		return new TokenPayment("TOKEN_PAY_Sder4545345dsde3we343fgfd885asdSVaLidJdsdsdJ$KDSSLksdj44j45");
	}

	public TokenPayment mustBeReturnStatus202WhenRequestSaveValidTokenPayment() {
		return new TokenPayment("TOKEN_PAY_Sder45se434$$#45345dsde3we343fgfd885asdSV");
	}

	public TokenPayment mustBeReturnStatus417WhenRequestSaveInvalidTokenPayment() {
		return new TokenPayment();
	}

	public TokenPayment mustBeReturnStatus417WhenRequestUpdateInvalidTokenPayment() {
		return new TokenPayment();
	}

	public TokenPayment mustBeReturnStatus200henRequestUpdateValidTokenPayment() {
		return new TokenPayment("TOKEN_PAY_Sder4ds**77665se434$$#45345dsde3we343fgfd885asdSV");
	}

	public TokenPayment mustBeReturnTokenPaymentWhenFindByTokenValue() {
		return new TokenPayment("TOKEN_PAY_Sder4ds**77665sFrd#432dsde3we343fgfd885asdSV");
	}

	public TokenPayment mustBeReturnStatus404WhenRequestUpdateTokenPaymentNotSaved() {
		return new TokenPayment("TOKEN_PAY_Sder4ds*fr56t)98j&&88**de3we343fgfd885asdSV");
	}

	public TokenPayment mustBeReturnStatus200AndTokenSavedWhenFoundTokenPayment() {
		return new TokenPayment("TOKEN_PAY_Sder4ds*fr56tdse454$$332556688**de3we343fgfd885asdSV");
	}

}
