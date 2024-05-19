package com.example.escuelasrest.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
    /**
 * Bean configuration for Cross-Origin Resource Sharing (CORS).
 * This configuration allows client applications from specified origins to access the resources.
 *
 * @return A WebMvcConfigurer instance with CORS mappings configured.
 */
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        /**
         * Configuration for CORS mappings.
         * This method allows all endpoints (/**) to be accessed by the client application running on http://localhost:4200.
         * It allows the HTTP methods GET, POST, PUT, DELETE.
         * It allows all headers in the HTTP requests.
         * It allows sending of credentials (cookies, HTTP authentication) in requests.
         * It sets the maximum age for the preflight request cache to 3600 seconds.
         *
         * @param registry The CorsRegistry instance to which the CORS mappings are to be added.
         */
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200")
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    };
}
}