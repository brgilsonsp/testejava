package gp.betta.onofre.configurationAPI.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import gp.betta.onofre.configurationAPI.enity.Attemps;
import gp.betta.onofre.configurationAPI.enity.response.ResponseAPI;
import gp.betta.onofre.configurationAPI.object.AttempsObjectTest;
import gp.betta.onofre.configurationAPI.object.ObjectTokenConfigTest;
import gp.betta.onofre.configurationAPI.service.AttempsService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ConfigurationApiApplication.class)
@AutoConfigureMockMvc
@DefaultPropertiesTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AttempsControllerTest {

	@Autowired
	private MockMvc mock;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private AttempsService serivce;

	private AttempsObjectTest objectAttemp = new AttempsObjectTest();
	
	private ObjectTokenConfigTest objectTokenConfig = new ObjectTokenConfigTest();

	private final String PATH_RESOURCE = "/api/attemps";
	
	@Test
	public void mustBeReturnStatusBadRequestWhenRequestWhithoutValidToken() throws Exception {
		//Build scenario
		Attemps attemps = this.objectAttemp.mustBeReturnStatusBadRequestWhenRequestWhithoutValidToken();
		String attempsString = this.mapper.writeValueAsString(attemps);
		
		//Execute operation
		ResultActions putResult = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(attempsString));
		ResultActions getResult = this.mock.perform(get(this.PATH_RESOURCE));
		
		//Execute test
		putResult.andExpect(status().isBadRequest());
		getResult.andExpect(status().isBadRequest());
	}
	
	@Test
	public void mustBeReturnStatusForbiddenWhenRequestWhithInvalidToken() throws Exception {
		//Build scenario
		Attemps attemps = this.objectAttemp.mustBeReturnStatusForbiddenWhenRequestWhithInvalidToken();
		String attempsString = this.mapper.writeValueAsString(attemps);
		
		//Execute operation
		ResultActions putResult = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(attempsString).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_INVALID));
		ResultActions getResult = this.mock.perform(get(this.PATH_RESOURCE).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_INVALID));
		
		//Execute test
		putResult.andExpect(status().isForbidden());
		getResult.andExpect(status().isForbidden());
	}
	
	@Test
	public void mustBeReturnStatus417WhenRequestUpdateInvalidAttemps() throws Exception {
		//Build scenario
		Attemps attemps = this.objectAttemp.mustBeReturnStatus417WhenRequestUpdateInvalidAttemps();
		String attempsJSON = this.mapper.writeValueAsString(attemps);
		ResponseAPI<Attemps> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(attempsJSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<Attemps>> typeReference = new TypeReference<ResponseAPI<Attemps>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isExpectationFailed());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("Errors");
		assertThat(responseAPI.getMessage()).containsIgnoringCase("limitTry");
	}
	
	@Test
	public void mustBeReturnStatus404WhenRequestUpdateAttempsNotSaved() throws Exception {
		//Build scenario
		Attemps attemps = this.objectAttemp.mustBeReturnStatus404WhenRequestUpdateAttempsNotSaved();
		String attempsJSON = this.mapper.writeValueAsString(attemps);
		ResponseAPI<Attemps> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(attempsJSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<Attemps>> typeReference = new TypeReference<ResponseAPI<Attemps>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isNotFound());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("registro não localizado");
	}
	
	@Test
	public void mustBeReturnStatus200WhenRequestUpdateValidAttemps() throws Exception {
		//Build scenario
		Attemps attempsTransient = this.objectAttemp.mustBeReturnStatus200WhenRequestUpdateValidAttemps();
		Attemps attempsManaged = this.serivce.saveOrUpdate(attempsTransient);
		attempsManaged.alterValueLimit(attempsManaged.getLimitTry() + 5);
		String attempsJSON = this.mapper.writeValueAsString(attempsManaged);
		ResponseAPI<Attemps> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(put(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				content(attempsJSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<Attemps>> typeReference = new TypeReference<ResponseAPI<Attemps>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isOk());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("salvo com sucesso");
		assertThat(responseAPI.getData().isAttempsNotValid()).isFalse();
		assertThat(responseAPI.getData().getId()).isEqualByComparingTo(attempsManaged.getId());
		assertThat(responseAPI.getData().getLimitTry()).isEqualByComparingTo(attempsManaged.getLimitTry());
	}
	
	
	@Test
	public void mustBeReturnStatus200WhenRequestThaAnyValid() throws Exception {
		//Build scenario
		ResponseAPI<Attemps> responseAPI = null;
		
		//Execute operation
		ResultActions resultActions = this.mock.perform(get(this.PATH_RESOURCE).contentType(MediaType.APPLICATION_JSON).
				header(this.objectTokenConfig.KEY_HEADER_VALID, this.objectTokenConfig.VALUE_TOKEN_VALID));
		String body = resultActions.andReturn().getResponse().getContentAsString();
		TypeReference<ResponseAPI<Attemps>> typeReference = new TypeReference<ResponseAPI<Attemps>>() {};
		responseAPI = this.mapper.readValue(body, typeReference);
		
		//Execute test
		resultActions.andExpect(status().isOk());
		assertThat(responseAPI.getMessage()).containsIgnoringCase("attemps localizado com sucesso");
		assertThat(responseAPI.getData().isAttempsNotValid()).isFalse();
		assertThat(responseAPI.getData().getId()).isPositive();
	}

}
