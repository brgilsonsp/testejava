package gp.betta.onofre.configurationAPI.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import gp.betta.onofre.configurationAPI.ConfigurationApiApplication;
import gp.betta.onofre.configurationAPI.annotations.DefaultPropertiesTest;
import gp.betta.onofre.configurationAPI.enity.TokenPayment;
import gp.betta.onofre.configurationAPI.enity.response.ResponseAPI;
import gp.betta.onofre.configurationAPI.object.ObjectTokenConfigTest;
import gp.betta.onofre.configurationAPI.object.TokenPaymentObject;
import gp.betta.onofre.configurationAPI.service.TokenPaymentService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationApiApplication.class)
@AutoConfigureMockMvc
@DefaultPropertiesTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TokenPaymentControllerTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TokenPaymentService service;

	private TokenPaymentObject objectTokenPayment = new TokenPaymentObject();

	private ObjectTokenConfigTest objectTokenConfig = new ObjectTokenConfigTest();

	private final String PATH_RESOURCE = "/api/payment/token";

	@Test
	public void mustBeReturnStatusBadRequestWhenRequestWhithoutValidToken() throws Exception {
		// Build scenario
		TokenPayment tokenPayment = this.objectTokenPayment.mustBeReturnStatusBadRequestWhenRequestWhithoutValidToken();
		String tokenPaymentString = this.mapper.writeValueAsString(tokenPayment);

		// Execute operation
		ResultActions postResult = this.mock
				.perform(post(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).content(tokenPaymentString));
		ResultActions putResult = this.mock
				.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).content(tokenPaymentString));
		ResultActions getResult = this.mock.perform(get(this.PATH_RESOURCE));

		// Execute test
		postResult.andExpect(status().isBadRequest());
		putResult.andExpect(status().isBadRequest());
		getResult.andExpect(status().isBadRequest());
	}

	@Test
	public void mustBeReturnStatusForbiddenWhenRequestWhithInvalidToken() throws Exception {
		// Build scenario
		TokenPayment tokenPayment = this.objectTokenPayment.mustBeReturnStatusForbiddenWhenRequestWhithInvalidToken();
		String tokenPaymentString = this.mapper.writeValueAsString(tokenPayment);

		// Execute operation
		ResultActions postResult = this.mock
				.perform(post(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).content(tokenPaymentString)
						.header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_INVALID));
		ResultActions putResult = this.mock
				.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).content(tokenPaymentString)
						.header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_INVALID));
		ResultActions getResult = this.mock.perform(get(this.PATH_RESOURCE)
				.header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_INVALID));

		// Execute test
		postResult.andExpect(status().isForbidden());
		putResult.andExpect(status().isForbidden());
		getResult.andExpect(status().isForbidden());
	}
	
	@Test
	public void mustBeReturnStatus201WhenRequestSaveValidTokenPayment() throws Exception {
		//Build scenario
		TokenPayment tokenPayment = this.objectTokenPayment.mustBeReturnStatus202WhenRequestSaveValidTokenPayment();
		String tokenPaymentString = this.mapper.writeValueAsString(tokenPayment);
		ResponseAPI<TokenPayment> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(post(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenPaymentString).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenPayment>> typeReference = new TypeReference<ResponseAPI<TokenPayment>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isCreated());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("sucesso");
	}
	
	@Test
	public void mustBeReturnStatus417WhenRequestSaveInvalidTokenPayment() throws Exception {
		//Build scenario
		TokenPayment tokenPayment = this.objectTokenPayment.mustBeReturnStatus417WhenRequestSaveInvalidTokenPayment();
		String tokenPaymentString = this.mapper.writeValueAsString(tokenPayment);
		ResponseAPI<TokenPayment> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(post(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenPaymentString).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenPayment>> typeReference = new TypeReference<ResponseAPI<TokenPayment>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isExpectationFailed());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("Errors");
		assertThat(responseAPI.getMessage()).containsIgnoringCase("token");
		assertThat(responseAPI.getMessage()).containsIgnoringCase("it's necessary");
	}
	
	@Test
	public void mustBeReturnStatus417WhenRequestUpdateInvalidTokenPayment() throws Exception {
		//Build scenario
		TokenPayment tokenPayment = this.objectTokenPayment.mustBeReturnStatus417WhenRequestUpdateInvalidTokenPayment();
		String tokenPaymentString = this.mapper.writeValueAsString(tokenPayment);
		ResponseAPI<TokenPayment> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenPaymentString).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenPayment>> typeReference = new TypeReference<ResponseAPI<TokenPayment>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isExpectationFailed());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("Errors");
		assertThat(responseAPI.getMessage()).containsIgnoringCase("token");
		assertThat(responseAPI.getMessage()).containsIgnoringCase("it's necessary");
	}
	
	@Test
	public void mustBeReturnStatus200henRequestUpdateValidTokenPayment() throws Exception {
		//Build scenario
		TokenPayment tokenPayment = this.objectTokenPayment.mustBeReturnStatus200henRequestUpdateValidTokenPayment();
		this.service.saveOrUpate(tokenPayment);
		String tokenPaymentString = this.mapper.writeValueAsString(tokenPayment);
		ResponseAPI<TokenPayment> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenPaymentString).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenPayment>> typeReference = new TypeReference<ResponseAPI<TokenPayment>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isOk());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("sucesso");
	}
	
	@Test
	public void mustBeReturnStatus404WhenRequestUpdateTokenPaymentNotSaved() throws Exception {
		//Build scenario
		TokenPayment tokenPayment = this.objectTokenPayment.mustBeReturnStatus404WhenRequestUpdateTokenPaymentNotSaved();
		String tokenPaymentString = this.mapper.writeValueAsString(tokenPayment);
		ResponseAPI<TokenPayment> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenPaymentString).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenPayment>> typeReference = new TypeReference<ResponseAPI<TokenPayment>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isNotFound());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("registro não localizado");
	}
	
	@Test
	public void mustBeReturnStatus200AndTokenSavedWhenFoundTokenPayment() throws Exception {
		//Build scenario
		TokenPayment tokenPayment = this.objectTokenPayment.mustBeReturnStatus200AndTokenSavedWhenFoundTokenPayment();
		this.service.saveOrUpate(tokenPayment);
		ResponseAPI<TokenPayment> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(get(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenPayment>> typeReference = new TypeReference<ResponseAPI<TokenPayment>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isOk());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("registro localizado");
		assertThat(responseAPI.getData().isTokenEmpty()).isFalse();
	}
	
	@Test
	public void mustBe0000ReturnStatus404WhenNotFoundAnyTokenPayment() throws Exception {
		//Build scenario
		ResponseAPI<TokenPayment> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(get(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenPayment>> typeReference = new TypeReference<ResponseAPI<TokenPayment>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isNotFound());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("registro não localizado");
		assertThat(responseAPI.getData().isTokenEmpty()).isTrue();
	}
}
