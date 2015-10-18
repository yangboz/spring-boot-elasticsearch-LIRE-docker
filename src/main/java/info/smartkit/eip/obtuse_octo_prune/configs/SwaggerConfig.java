package info.smartkit.eip.obtuse_octo_prune.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import com.wordnik.swagger.model.ApiInfo;

/**
 * The Class SwaggerConfig.
 */
@Configuration
@EnableSwagger
public class SwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig) {
        this.springSwaggerConfig = springSwaggerConfig;
    }

    @Bean //Don't forget the @Bean annotation
    public SwaggerSpringMvcPlugin customImplementation() {
//		  AbsoluteSwaggerPathProvider pathProvider = new AbsoluteSwaggerPathProvider();
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig)
                .apiInfo(apiInfo())
//	            .pathProvider(pathProvider)
                .includePatterns("/es/.*");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Obtuse-Octo-Prune Restful API",
                "API for Obtuse-Octo-Prune",
                "Obtuse-Octo-Prune API terms of service",
                "youngwelle@gmail.com",
                "Obtuse-Octo-Prune API Licence Type",
                "Obtuse-Octo-Prune API License URL"
        );
        return apiInfo;
    }

}
