package gp.betta.onofre.configurationAPI.utils;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.context.support.DefaultMessageSourceResolvable;

@Component
public class BuildErrorsValidator {
	
	private final String DEFAULT_FIELD = "ERROR";	
	
	/**
	 * Make the StringBuilder with the list errors
	 * 
	 * @param errors List<ObjectErrors>. The list with the all errors
	 * @return Object StringBuild with the errors formatted
	 */
	public StringBuilder buildMessageValidation(List<ObjectError> errors) {
		StringBuilder builder = new StringBuilder();
		int indexError = 0;
		builder.append("Errors: [");
		for (ObjectError error : errors) {
			indexError++;
			String fieldName = buildFieldName(error.getArguments(), indexError);
			builder.append("{");
			builder.append(fieldName);
			builder.append(" ");
			builder.append(error.getDefaultMessage());
			builder.append("}");

			if (indexError < errors.size())
				builder.append(", ");
		}
		builder.append("]");
		return builder;
	}

	private String buildFieldName(Object[] arguments, int step) {
		try {
			DefaultMessageSourceResolvable message = (DefaultMessageSourceResolvable)arguments[0];
			return message.getDefaultMessage();
		}catch(Exception e) {
			return DEFAULT_FIELD + " " + step;
		}
	}
	
	
}