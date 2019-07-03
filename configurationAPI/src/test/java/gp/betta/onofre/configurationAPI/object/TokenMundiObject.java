package gp.betta.onofre.configurationAPI.object;

import gp.betta.onofre.configurationAPI.enity.TokenMundi;

public class TokenMundiObject {
	
	public final String TOKEN_NOT_SAVED = "TOKEN_NOT_SAVED";

	public TokenMundi getTokenMundiValid() {
		return new TokenMundi("TOKEN_MUNDI_TEST");
	}

	public TokenMundi mustBeReturnTokenMundiWithIdWhenSentTokenMundiValidForSaveOrUpdate() {
		return new TokenMundi("TOKEN_MUNDI_TEST_909877jukoise");
	}

	public TokenMundi mustBeReturnTokenMundiWithIdWhenFoundTokenMundiSaved() {
		return new TokenMundi("TOKEN_MUNDI_TEST_989*&7689jukoise");
	}

	public TokenMundi mustBeReturnStatusBadRequestWhenRequestWhithutValidToken() {
		return new TokenMundi("TOKEN_MUNDI_TEST_989*&7689j==-_pp00ise");
	}

	public TokenMundi mustBeReturnStatusForbiddenWhenRequestWhithInvalidToken() {
		return new TokenMundi("TOKEN_MUNDI_TEST_989*&764234asdlosasp00ise");
	}

	public TokenMundi mustBeReturnStatus202WhenRequestSaveValidTokenMund() {
		return new TokenMundi("TOKEN_MUNDI_TEST_989*-098kiJUhseosasp00ise");
	}

	public TokenMundi mustBeReturnStatus417WhenRequestSaveInvalidTokenMund() {
		return new TokenMundi();
	}

	public TokenMundi mustBeReturnStatus417WhenRequestUpdateInvalidTokenMund() {
		return new TokenMundi();
	}

	public TokenMundi mustBeReturnStatus200WhenRequestUpdateValidTokenMund() {
		return new TokenMundi("TOKEN_MUNDI_TEST_UPDATE_VALJUhseosasp00ise");
	}

	public TokenMundi mustBeReturnStatus200AndTokenMundWhenFoundAnyToken() {
		return new TokenMundi("TOKEN_MUNDI_TGESTD009T_UPDATE_VALJUhseosasp00ise");
	}

	public TokenMundi mustBeReturnStatus404WhenRequestUpdateTokenMundiNotSaved() {
		return new TokenMundi("TOKEN_MUNDI_TGESTD00DNOOK)(8898DATE_VALJUhseosasp00ise");
	}
}
