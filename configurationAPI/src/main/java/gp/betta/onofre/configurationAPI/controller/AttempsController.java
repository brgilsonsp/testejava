package gp.betta.onofre.configurationAPI.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gp.betta.onofre.configurationAPI.enity.Attemps;
import gp.betta.onofre.configurationAPI.enity.response.ResponseAPI;
import gp.betta.onofre.configurationAPI.service.AttempsService;
import gp.betta.onofre.configurationAPI.utils.BuildErrorsValidator;

@RestController
@RequestMapping("/api/attemps")
@CrossOrigin(origins = "*")
public class AttempsController {
	
	private final Logger logger = LogManager.getLogger(AttempsController.class);
	
	@Autowired
	private AttempsService service;
	
	@Autowired
	private BuildErrorsValidator buildErrorsValidator;
	
	@PutMapping()
	public ResponseEntity<ResponseAPI<Attemps>> updateAttemps(@Valid @RequestBody Attemps attemps,
			BindingResult result) {
		this.logger.info("Inicio updateAttemps");
		ResponseAPI<Attemps> response = new ResponseAPI<>();
		HttpStatus httpStatus = null;
		String message = "";
		
		try {
			if(result.hasErrors()) {
				message = this.buildErrorsValidator.buildMessageValidation(result.getAllErrors()).toString();
				httpStatus = HttpStatus.EXPECTATION_FAILED;
			}else {
				Attemps attempsManaged = this.service.findById(attemps.getId());
				if(attempsManaged.isAttempsNotValid()) {
					message = "Attemps, registro não localizado";
					httpStatus = HttpStatus.NOT_FOUND;
				}else {
					attempsManaged.alterValueLimit(attemps.getLimitTry());
					attemps = this.service.saveOrUpdate(attempsManaged);
					message = "Attemps salvo com sucesso";
					httpStatus = HttpStatus.OK;
				}
			}
		}catch (Exception e) {
			message = "Erro no servidor ao atualizar o Attemps";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error(message, e);
		}
		
		response.setData(attemps);
		response.setMessage(message);
		this.logger.info("Fim updateAttemps. Status: " + httpStatus + ", mensagem: " + message);
		return ResponseEntity.status(httpStatus).body(response);
	}
	
	@GetMapping()
	public ResponseEntity<ResponseAPI<Attemps>> getAnyAttemps() {
		this.logger.info("Inicio getAnyAttemps");
		ResponseAPI<Attemps> response = new ResponseAPI<>();
		HttpStatus httpStatus = null;
		Attemps attemps = new Attemps();
		String message = "";
		
		try {
			attemps = this.service.findFirst();
			message = "Attemps localizado com sucesso";
			httpStatus = HttpStatus.OK;
		}catch (Exception e) {
			message = "Erro no servidor ao localizar um Attemps";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error(message, e);
		}
		
		
		response.setData(attemps);
		response.setMessage(message);
		this.logger.info("Fim getAnyAttemps. Status: " + httpStatus + ", mensagem: " + message);
		return ResponseEntity.status(httpStatus).body(response);
	}

}
