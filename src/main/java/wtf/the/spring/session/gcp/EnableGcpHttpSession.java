package wtf.the.spring.session.gcp;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@Import(GcpHttpSessionConfiguration.class)
@Configuration
public @interface EnableGcpHttpSession {

    String bucket();

}
