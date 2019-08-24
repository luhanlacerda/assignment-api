package luhanlacerda.config;

import static org.springframework.web.cors.CorsConfiguration.ALL;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config implements WebMvcConfigurer {

	/**
	 * CORS configuration
	 * 
	 * @return
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins(ALL).allowedMethods(ALL).allowedHeaders(ALL).allowCredentials(true);
    }

}
