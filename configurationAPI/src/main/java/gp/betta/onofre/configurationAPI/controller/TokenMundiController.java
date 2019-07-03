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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gp.betta.onofre.configurationAPI.enity.TokenMundi;
import gp.betta.onofre.configurationAPI.enity.response.ResponseAPI;
import gp.betta.onofre.configurationAPI.service.TokenMundiService;
import gp.betta.onofre.configurationAPI.utils.BuildErrorsValidator;

@RestController
@RequestMapping("/api/mundi/token")
@CrossOrigin(origins = "*")
public class TokenMundiController {

	private final Logger logger = LogManager.getLogger(TokenMundiController.class);

	@Autowired
	private TokenMundiService service;

	@Autowired
	private BuildErrorsValidator buildErrorsValidator;

	@PostMapping()
	public ResponseEntity<ResponseAPI<TokenMundi>> saveTokenMundi(@Valid @RequestBody TokenMundi tokenMundi,
			BindingResult result) {
		this.logger.info("Inicio saveTokenMundi");
		ResponseAPI<TokenMundi> response = new ResponseAPI<>();
		HttpStatus httpStatus = null;
		String message = "";

		try {
			if (result.hasErrors()) {
				message = this.buildErrorsValidator.buildMessageValidation(result.getAllErrors()).toString();
				httpStatus = HttpStatus.EXPECTATION_FAILED;
			} else {
				tokenMundi = this.service.saveOrUpate(tokenMundi);
				message = "TokenMundi registrado com sucesso";
				httpStatus = HttpStatus.CREATED;
			}
		} catch (Exception e) {
			message = "Erro no servidor ao salvar o TokenMundi";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error(message, e);
		}

		response.setData(tokenMundi);
		response.setMessage(message);
		this.logger.info("Fim saveTokenMundi. Status: " + httpStatus + ", mensagem: " + message);
		return ResponseEntity.status(httpStatus).body(response);
	}

	@PutMapping()
	public ResponseEntity<ResponseAPI<TokenMundi>> updateTokenMundi(@Valid @RequestBody TokenMundi tokenMundi,
			BindingResult result) {
		this.logger.info("Inicio updateTokenMundi");
		ResponseAPI<TokenMundi> response = new ResponseAPI<>();
		HttpStatus httpStatus = null;
		String message = "";

		try {
			if (result.hasErrors()) {
				message = this.buildErrorsValidator.buildMessageValidation(result.getAllErrors()).toString();
				httpStatus = HttpStatus.EXPECTATION_FAILED;
			} else {
				TokenMundi tokenMundiSaved = this.service.getById(tokenMundi.getId());
				if (tokenMundiSaved.isTokenEmpty()) {
					message = "TErro ao atualizar o TokenMundi, registro não localizado";
					httpStatus = HttpStatus.NOT_FOUND;
				} else {
					tokenMundi = this.service.saveOrUpate(tokenMundi);
					message = "TokenMundi atualizado com sucesso";
					httpStatus = HttpStatus.OK;
				}
			}
		} catch (Exception e) {
			message = "Erro no servidor ao atualizar o TokenMundi";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error(message, e);
		}

		response.setData(tokenMundi);
		response.setMessage(message);
		this.logger.info("Fim updateTokenMundi. Status: " + httpStatus + ", mensagem: " + message);
		return ResponseEntity.status(httpStatus).body(response);
	}

	@GetMapping()
	public ResponseEntity<ResponseAPI<TokenMundi>> getTokenMundi() {
		this.logger.info("Inicio getTokenMundi");
		ResponseAPI<TokenMundi> response = new ResponseAPI<>();
		TokenMundi tokenMundi = new TokenMundi();
		HttpStatus httpStatus = null;
		String message = "";

		try {
			tokenMundi = this.service.getFirstToken();
			if (tokenMundi.isTokenEmpty()) {
				message = "Nenhum tokenMundi localizado";
				httpStatus = HttpStatus.NOT_FOUND;
			} else {
				message = "TokenMundi localizado com sucesso";
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			message = "Erro no servidor ao localizar um TokenMundi";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error(message, e);
		}

		response.setData(tokenMundi);
		response.setMessage(message);
		this.logger.info("Fim getTokenMundi. Status: " + httpStatus + ", mensagem: " + message);
		return ResponseEntity.status(httpStatus).body(response);
	}

}
