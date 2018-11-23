package com.example.demo.one.configuration;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Objects;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Value("${spring.profiles.active}")
    private String active;

    public static final String ACTIVE_PROD = "prod";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(!StringUtils.equals(active, ACTIVE_PROD))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(path -> !Objects.equals(path, "/error"))
                .build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes())
                .apiInfo(this.apiInfo());
    }

    private List<ApiKey> securitySchemes() {
        //WARN Apikey 中name 需和SecurityReference 的reference对应
        return Lists.newArrayList(new ApiKey("Authorization", "asoco-token", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return Lists.newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        //除Login接口外,都进行验证
                        .forPaths(PathSelectors.regex("^(?!/login).*$"))
                        .build()
        );
    }

    private List<SecurityReference> defaultAuth() {
        return Lists.newArrayList(
                new SecurityReference("Authorization", Lists.newArrayList(
                        new AuthorizationScope("global", "对该范围的接口访问需要使用token")).toArray(new AuthorizationScope[1])));
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("智慧化工园区安监领域")
                .description("危化品全生命周期-危废监管服务")
                .version("0.0.1")
                .build();
    }

}

