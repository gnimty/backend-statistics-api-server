package onlysolorank.apiserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(
        title = "Gnimty 전적 API 서버 Specification",
        description = "Swagger api spec",
        version = "v1",
        contact = @Contact(
            name = "Gnimty",
            email = "gnimty@gmail.com"
        )
    )
)

@Configuration
public class SwaggerConfig {

//
//    @Bean
//    public GroupedOpenApi sampleGroupOpenApi() {
//        String[] paths = {"/member/**"};
//
//        return GroupedOpenApi.builder().group("샘플 멤버 API").pathsToMatch(paths)
//            .build();
//    }

}