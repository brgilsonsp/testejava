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
import gp.betta.onofre.configurationAPI.enity.TokenMundi;
import gp.betta.onofre.configurationAPI.enity.response.ResponseAPI;
import gp.betta.onofre.configurationAPI.object.ObjectTokenConfigTest;
import gp.betta.onofre.configurationAPI.object.TokenMundiObject;
import gp.betta.onofre.configurationAPI.service.TokenMundiService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ConfigurationApiApplication.class)
@AutoConfigureMockMvc
@DefaultPropertiesTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TokenMundiControllerTest {

	@Autowired
	private MockMvc mock;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Autowired
	private TokenMundiService service;
	
	private TokenMundiObject objectTokenMundi = new TokenMundiObject();
	
	private ObjectTokenConfigTest objectTokenConfig = new ObjectTokenConfigTest();
	
	private final String PATH_RESOURCE = "/api/mundi/token";
	
	@Test
	public void mustBeReturnStatusBadRequestWhenRequestWhithoutValidToken() throws Exception {
		//Build scenario
		TokenMundi tokenMundi = this.objectTokenMundi.mustBeReturnStatusBadRequestWhenRequestWhithutValidToken();
		String tokenMundiString = this.mapper.writeValueAsString(tokenMundi);
		
		//Execute operation
		ResultActions postResult = this.mock.perform(post(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenMundiString));
		ResultActions putResult = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenMundiString));
		ResultActions getResult = this.mock.perform(get(this.PATH_RESOURCE));
		
		//Execute test
		postResult.andExpect(status().isBadRequest());
		putResult.andExpect(status().isBadRequest());
		getResult.andExpect(status().isBadRequest());
	}
	
	@Test
	public void mustBeReturnStatusForbiddenWhenRequestWhithInvalidToken() throws Exception {
		//Build scenario
		TokenMundi tokenMundi = this.objectTokenMundi.mustBeReturnStatusForbiddenWhenRequestWhithInvalidToken();
		String tokenMundiString = this.mapper.writeValueAsString(tokenMundi);
		
		//Execute operation
		ResultActions postResult = this.mock.perform(post(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenMundiString).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_INVALID));
		ResultActions putResult = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenMundiString).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_INVALID));
		ResultActions getResult = this.mock.perform(get(this.PATH_RESOURCE).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_INVALID));
		
		//Execute test
		postResult.andExpect(status().isForbidden());
		putResult.andExpect(status().isForbidden());
		getResult.andExpect(status().isForbidden());
	}
	
	@Test
	public void mustBeReturnStatus202WhenRequestSaveValidTokenMund() throws Exception {
		//Build scenario
		TokenMundi tokenMundi = this.objectTokenMundi.mustBeReturnStatus202WhenRequestSaveValidTokenMund();
		String tokenMundiJSON = this.mapper.writeValueAsString(tokenMundi);
		ResponseAPI<TokenMundi> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(post(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenMundiJSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenMundi>> typeReference = new TypeReference<ResponseAPI<TokenMundi>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isCreated());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("sucesso");
	}
	
	@Test
	public void mustBeReturnStatus417WhenRequestSaveInvalidTokenMund() throws Exception {
		//Build scenario
		TokenMundi tokenMundi = this.objectTokenMundi.mustBeReturnStatus417WhenRequestSaveInvalidTokenMund();
		String tokenMundiJSON = this.mapper.writeValueAsString(tokenMundi);
		ResponseAPI<TokenMundi> responseAPI = null;
		String headerLocation = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(post(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenMundiJSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenMundi>> typeReference = new TypeReference<ResponseAPI<TokenMundi>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		headerLocation = (String) resultActions.andReturn().getResponse().getHeader("location");
		
		//Execute test
		resultActions.andExpect(status().isExpectationFailed());
		assertThat(headerLocation).isBlank();
		assertThat(responseAPI.getMessage()).containsIgnoringCase("Errors");
		assertThat(responseAPI.getMessage()).containsIgnoringCase("token");
		assertThat(responseAPI.getMessage()).containsIgnoringCase("it's necessary");
	}
	
	@Test
	public void mustBeReturnStatus417WhenRequestUpdateInvalidTokenMund() throws Exception {
		//Build scenario
		TokenMundi tokenMundi = this.objectTokenMundi.mustBeReturnStatus417WhenRequestUpdateInvalidTokenMund();
		String tokenMundiJSON = this.mapper.writeValueAsString(tokenMundi);
		ResponseAPI<TokenMundi> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenMundiJSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenMundi>> typeReference = new TypeReference<ResponseAPI<TokenMundi>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isExpectationFailed());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("Errors");
		assertThat(responseAPI.getMessage()).containsIgnoringCase("token");
		assertThat(responseAPI.getMessage()).containsIgnoringCase("it's necessary");
	}
	
	@Test
	public void mustBeReturnStatus200WhenRequestUpdateValidTokenMund() throws Exception {
		//Build scenario
		TokenMundi tokenMundi = this.objectTokenMundi.mustBeReturnStatus200WhenRequestUpdateValidTokenMund();
		tokenMundi = this.service.saveOrUpate(tokenMundi);
		tokenMundi.changeTokenValue("NEW_VALUE_TOKEN_)(0909988998sdasdew");
		String tokenMundiJSON = this.mapper.writeValueAsString(tokenMundi);
		ResponseAPI<TokenMundi> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenMundiJSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenMundi>> typeReference = new TypeReference<ResponseAPI<TokenMundi>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isOk());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("sucesso");
		assertThat(responseAPI.getData().getToken()).isEqualTo(tokenMundi.getToken());
	}
	
	@Test
	public void mustBeReturnStatus404WhenRequestUpdateTokenMundiNotSaved() throws Exception {
		//Build scenario
		TokenMundi tokenMundi = this.objectTokenMundi.mustBeReturnStatus404WhenRequestUpdateTokenMundiNotSaved();
		String tokenMundiJSON = this.mapper.writeValueAsString(tokenMundi);
		ResponseAPI<TokenMundi> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(tokenMundiJSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenMundi>> typeReference = new TypeReference<ResponseAPI<TokenMundi>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isNotFound());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("registro não localizado");
	}
	
	@Test
	public void mustBeReturnStatus200AndTokenMundWhenFoundAnyToken() throws Exception {
		//Build scenario
		TokenMundi tokenMundi = this.objectTokenMundi.mustBeReturnStatus200AndTokenMundWhenFoundAnyToken();
		this.service.saveOrUpate(tokenMundi);
		ResponseAPI<TokenMundi> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(get(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenMundi>> typeReference = new TypeReference<ResponseAPI<TokenMundi>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isOk());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("TokenMundi localizado com sucesso");
		assertThat(responseAPI.getData().getToken()).isNotBlank();
	}
	
	@Test
	public void must000BeReturnStatus404WhenNotFoundToken() throws Exception {
		//Build scenario
		ResponseAPI<TokenMundi> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(get(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<TokenMundi>> typeReference = new TypeReference<ResponseAPI<TokenMundi>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isNotFound());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("Nenhum tokenMundi localizado");
		assertThat(responseAPI.getData().getToken()).isBlank();
	}
}
