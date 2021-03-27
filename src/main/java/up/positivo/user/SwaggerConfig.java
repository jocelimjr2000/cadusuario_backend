package up.positivo.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket apiModelo() {
		return apiMount("EndPoints", "(/.*)");
	}
	
	private Docket apiMount(String groupName, String regex) {
		return new Docket(DocumentationType.SWAGGER_2).groupName(groupName).select()
				.apis(RequestHandlerSelectors.basePackage("up.positivo.user.resources"))
				.paths(PathSelectors.regex(regex)).build().apiInfo(metaData(groupName));
	}
	
	private ApiInfo metaData(String description) {
		return new ApiInfoBuilder().title("Trabalho Escobar - Cadastro de Usuário Rest API").description(description)
				.version("1.0.0")
//        .license("Apache License Version 2.0")
//        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.build();
	}
}