package com.foxrain.sheep.whileblack.config.configuration;

import com.foxrain.sheep.whileblack.config.configuration.security.UserPrincipal;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.Errors;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with intellij IDEA. by 2020 03 15/03/2020 4:43 오전 15 User we at 04 43 To change this
 * template use File | Settings | File Templates.
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

//    @Bean
//    public Docket authenticationApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .ignoredParameterTypes(UserPrincipal.class)
//                .groupName("인증 요청")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.tcp.iamlazy.auth"))
//                .paths(PathSelectors.ant("/account"))
//                .build()
//                .apiInfo(getApiInfo());
//    }

    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(UserPrincipal.class)
                .groupName("유저 정보 요청")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tcp.iamlazy.user"))
                .paths(PathSelectors.ant("/user"))
                .build()
                .apiInfo(getApiInfo());
    }

    @Bean
    public Docket reviewApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(UserPrincipal.class)
                .ignoredParameterTypes(Errors.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .groupName("회고 요청")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tcp.iamlazy.review"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    @Bean
    public Docket todoApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(UserPrincipal.class)
                .ignoredParameterTypes(Errors.class)
                .securityContexts(Lists.newArrayList(securityContext()))
                .securitySchemes(Lists.newArrayList(apiKey()))
                .groupName("할일 요청")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tcp.iamlazy.todo"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo());
    }

    @Bean
    public Docket statisticApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(UserPrincipal.class)
                .groupName("통계 요청")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tcp.iamlazy.stats"))
                .paths(PathSelectors.ant("/statistics/*"))
                .build()
                .apiInfo(getApiInfo());
    }

    @Bean
    public Docket settingApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .ignoredParameterTypes(UserPrincipal.class)
                .groupName("설정 요청")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tcp.iamlazy.setting"))
                .paths(PathSelectors.ant("/setting/*"))
                .build()
                .apiInfo(getApiInfo());
    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .ignoredParameterTypes(UserPrincipal.class)
//                .groupName("A. 전체")
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(Predicates.not(PathSelectors.regex("/error.*")))
//                .paths(PathSelectors.any())
//                .build();
//    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("나는 게으르다 API 명세")
                .description("I am Lazy 프로젝트에서 사용될 api 명세를 약술합니다.")
                .license("all rights reserved @joi")
                .version("latest")
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private springfox.documentation.spi.service.contexts.SecurityContext securityContext() {
        return springfox.documentation.spi.service.contexts.SecurityContext.builder()
            .securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global",
                                                                       "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }

//    출처: https://kimyhcj.tistory.com/363 [기억과 기록]
}
