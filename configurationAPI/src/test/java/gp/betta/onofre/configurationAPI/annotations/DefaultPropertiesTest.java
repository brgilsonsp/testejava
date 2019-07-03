package gp.betta.onofre.configurationAPI.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.TestPropertySource;

/**
 * Configure the file application.properties default for all tests
 * @author gilson.souza
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@TestPropertySource("classpath:application-dev.properties")
public @interface DefaultPropertiesTest {

}
