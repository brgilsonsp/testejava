package gp.betta.onofre.configurationAPI.conf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gp.betta.onofre.configurationAPI.enity.properties.TokenConfig;

@Component
public class AuthorizationAccessFilter implements Filter {
	
	private final Logger logger = LogManager.getLogger(AuthorizationAccessFilter.class);
	
	@Autowired
	private TokenConfig tokenConfig;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		String tokenSent = this.buildTokenSent(httpRequest);
		try {
			if(tokenSent == null || tokenSent.isEmpty()) {
				String messageBlanck = "Token não informado";
				logger.error(messageBlanck);
				int errorCode = HttpServletResponse.SC_BAD_REQUEST;
				httpResponse.sendError(errorCode, messageBlanck);
			}else if(this.tokenConfig.isTokenValid(tokenSent)) {
				String messageBlanck = "Token valido";
				logger.info(messageBlanck);
				chain.doFilter(request, response);
			}else {
				String messageBlanck = "Token inválido";
				logger.error(messageBlanck);
				int errorCode = HttpServletResponse.SC_FORBIDDEN;
				httpResponse.sendError(errorCode, messageBlanck);
			}
			
		}catch(Exception e) {
			String messageException = "Erro interno do servidor ao validar o token";
			logger.error(messageException, e);
			int errorCode = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
			httpResponse.sendError(errorCode, messageException);
		}
	}

	private String buildTokenSent(HttpServletRequest httpRequest) {
		String tokenSent = "";
		try {
			tokenSent = httpRequest.getHeader(this.tokenConfig.getKey());
		}catch(Exception e) {
			this.logger.error("Error build Token API sent", e);
		}
		return tokenSent;
	}

}
