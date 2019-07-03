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

import gp.betta.onofre.configurationAPI.enity.TokenPayment;
import gp.betta.onofre.configurationAPI.enity.response.ResponseAPI;
import gp.betta.onofre.configurationAPI.service.TokenPaymentService;
import gp.betta.onofre.configurationAPI.utils.BuildErrorsValidator;

@RestController
@RequestMapping("/api/payment/token")
@CrossOrigin(origins = "*")
public class TokenPaymentController {

	private final Logger logger = LogManager.getLogger(TokenPaymentController.class);

	@Autowired
	private TokenPaymentService service;

	@Autowired
	private BuildErrorsValidator buildErrorsValidator;

	@PostMapping()
	public ResponseEntity<ResponseAPI<TokenPayment>> saveTokenPayment(@Valid @RequestBody TokenPayment tokenPayment,
			BindingResult result) {
		this.logger.info("Inicio saveTokenPayment");
		ResponseAPI<TokenPayment> response = new ResponseAPI<>();
		HttpStatus httpStatus = null;
		String message = "";

		try {
			if (result.hasErrors()) {
				message = this.buildErrorsValidator.buildMessageValidation(result.getAllErrors()).toString();
				httpStatus = HttpStatus.EXPECTATION_FAILED;
			} else {
				tokenPayment = this.service.saveOrUpate(tokenPayment);
				message = "TokenPayment registrado com sucesso";
				httpStatus = HttpStatus.CREATED;
			}
		} catch (Exception e) {
			message = "Erro no servidor ao salvar o TokenPayment";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error(message, e);
		}

		response.setData(tokenPayment);
		response.setMessage(message);
		this.logger.info("Fim saveTokenPayment. Status: " + httpStatus + ", mensagem: " + message);
		return ResponseEntity.status(httpStatus).body(response);
	}

	@PutMapping()
	public ResponseEntity<ResponseAPI<TokenPayment>> updateTokenPayment(@Valid @RequestBody TokenPayment tokenPayment,
			BindingResult result) {
		this.logger.info("Inicio updateTokenPayment");
		ResponseAPI<TokenPayment> response = new ResponseAPI<>();
		HttpStatus httpStatus = null;
		String message = "";

		try {
			if (result.hasErrors()) {
				message = this.buildErrorsValidator.buildMessageValidation(result.getAllErrors()).toString();
				httpStatus = HttpStatus.EXPECTATION_FAILED;
			} else {
				TokenPayment tokenSaved = this.service.getById(tokenPayment.getId());
				if (tokenSaved.isTokenEmpty()) {
					message = "Erro ao atualizar o TokenPayment, registro não localizado";
					httpStatus = HttpStatus.NOT_FOUND;
				} else {
					tokenSaved.changeTokenValue(tokenPayment.getToken());
					tokenPayment = this.service.saveOrUpate(tokenSaved);
					message = "TokenPayment atualizado com sucesso";
					httpStatus = HttpStatus.OK;
				}
			}
		} catch (Exception e) {
			message = "Erro no servidor ao atualizar o TokenPayment";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error(message, e);
		}

		response.setData(tokenPayment);
		response.setMessage(message);
		this.logger.info("Fim updateTokenPayment. Status: " + httpStatus + ", mensagem: " + message);
		return ResponseEntity.status(httpStatus).body(response);
	}

	@GetMapping()
	public ResponseEntity<ResponseAPI<TokenPayment>> getTokenPayment() {
		this.logger.info("Inicio getTokenPayment");
		ResponseAPI<TokenPayment> response = new ResponseAPI<>();
		TokenPayment tokenPayment = new TokenPayment();
		HttpStatus httpStatus = null;
		String message = "";

		try {
			tokenPayment = this.service.getFirstToken();
			if (tokenPayment.isTokenEmpty()) {
				message = "TokenPayment, registro não localizado";
				httpStatus = HttpStatus.NOT_FOUND;
			} else {
				message = "TokenPayment registro localizado";
				httpStatus = HttpStatus.OK;
			}
		} catch (Exception e) {
			message = "Erro no servidor ao atualizar o TokenPayment";
			httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			logger.error(message, e);
		}

		response.setData(tokenPayment);
		response.setMessage(message);
		this.logger.info("Fim getTokenPayment. Status: " + httpStatus + ", mensagem: " + message);
		return ResponseEntity.status(httpStatus).body(response);
	}

}
