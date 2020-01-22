package cn.decentchina.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author jiangyu
 * @date 2020/1/20
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiinfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.decentchina.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiinfo() {
        Contact contact = new Contact("姜禹", "http://120.27.3.114:8081", "1056160293@qq.com");
        return new ApiInfoBuilder().contact(contact).title("==天天吃货文档==").description("just document fot foodie")
                .termsOfServiceUrl("http://1207.0.0.1:8088").version("1.0.0").build();
    }


}
