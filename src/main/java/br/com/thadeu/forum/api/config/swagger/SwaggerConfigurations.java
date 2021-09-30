package br.com.thadeu.forum.api.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.thadeu.forum.api.modelo.Usuario;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {

	@Bean
	public Docket forumApi() {
//		return new Docket(DocumentationType.SWAGGER_2)
//		          .select()
//		          .apis(RequestHandlerSelectors.any())
//		          .paths(PathSelectors.any())
//		          .build();
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.thadeu.forum.api"))
				.paths(PathSelectors.ant("/**")).build()
				.ignoredParameterTypes(Usuario.class)
				.globalRequestParameters(
                        Arrays.asList(
                                new RequestParameterBuilder()
                                	.in(ParameterType.HEADER)    
                                	.name("Authorization")
                                    .description("Header para Token JWT")
                                    .query(q -> q.model(modelSpecificationBuilder -> modelSpecificationBuilder.scalarModel(ScalarType.STRING)))
                                    .required(false)
                                    .build()));

	}

	

}
