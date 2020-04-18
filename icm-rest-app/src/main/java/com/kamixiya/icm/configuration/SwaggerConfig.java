package com.kamixiya.icm.configuration;

import com.fasterxml.classmate.ResolvedType;
import com.fasterxml.classmate.TypeResolver;
import com.kamixiya.icm.model.common.CommonErrorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * SwaggerConfig
 *
 * @author Zhu Jie
 * @date 2020/3/7
 */
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig{

    private static final String COMMON_ERROR_CLASS = "CommonErrorDTO";
    private static final String API_TITLE = "业务应用系统后端API";
    private static final String API_DESCRIPTION = "系统后台REST接口定义，开发桌面及移动端应用均需参考该接口规范。";
    private static final String API_LICENSE = "版权所有 朱杰";
    private static final String API_LICENSE_URL = "http://www.baidu.com";
    private static final String API_VERSION = "1.0.0.RELEASE";
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_TYPE = "Bearer";
    private static final String WORKING_ORGANIZATION = "Working-Organization";
    private static final String API_BASE_PACKAGE = "com.kamixiya.icm.controller";
    private final TypeResolver typeResolver;

    @Autowired
    public SwaggerConfig(TypeResolver typeResolver) {
        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket tokenApi() {
        return this.commonApi("Token Management", API_BASE_PACKAGE + ".token", false);
    }

    @Bean
    public Docket authorityApi() {
        return this.commonApi("Authority Management", API_BASE_PACKAGE + ".authority", true);
    }

    @Bean
    public Docket organizationApi() {
        return this.commonApi("Organization Management", API_BASE_PACKAGE + ".organization", true);
    }

    @Bean
    public Docket budgetApi() {
        return this.commonApi("Budget Management", API_BASE_PACKAGE + ".content.budget", true);
    }

    private Docket commonApi(String groupName, String basePackage, boolean requireToken) {
        return this.buildApiCommonPart(
                (new Docket(DocumentationType.SWAGGER_2))
                        .groupName(groupName)
                        .apiInfo(this.apiInfo()).select()
                        .apis(RequestHandlerSelectors.basePackage(basePackage)),
                requireToken);
    }

    private Docket buildApiCommonPart(ApiSelectorBuilder builder, boolean requireToken) {
        Docket docket = builder.paths(PathSelectors.any())
                .build()
                .consumes(Stream.of("application/json;charset=UTF-8").collect(Collectors.toSet()))
                .produces(Stream.of("application/json;charset=UTF-8").collect(Collectors.toSet()));
        if (requireToken) {
            docket.globalOperationParameters(Stream.of(
                    (new ParameterBuilder())
                    .name("Authorization")
                    .modelRef(new ModelRef("string"))
                    .parameterType("header")
                    .required(true)
                    .description("JWT令牌")
                    .defaultValue("Bearer ")
                    .build())
            .collect(Collectors.toList()));
        }
        docket.globalOperationParameters(
                Stream.of((new ParameterBuilder())
                        .name("Working-Organization")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .description("当前工作组织")
                        .build())
                .collect(Collectors.toList()));
        return docket.pathMapping("/")
                .additionalModels(this.typeResolver.resolve(CommonErrorDTO.class, new Type[0]), new ResolvedType[0])
                .useDefaultResponseMessages(false).globalResponseMessage(RequestMethod.GET, this.responseMessages())
                .globalResponseMessage(RequestMethod.PUT, this.responseMessages())
                .globalResponseMessage(RequestMethod.POST, this.responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, this.responseMessages());
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder())
                        .title(API_TITLE)
                        .description(API_DESCRIPTION)
                        .license(API_LICENSE)
                        .licenseUrl(API_LICENSE_URL)
                        .version(API_VERSION)
                        .build();
    }
    private List<ResponseMessage> responseMessages() {
        return Stream.of(
                (new ResponseMessageBuilder())
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .responseModel(new ModelRef("CommonErrorDTO"))
                        .build(),
                (new ResponseMessageBuilder())
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .responseModel(new ModelRef("CommonErrorDTO"))
                        .build(),
                (new ResponseMessageBuilder()).
                        code(HttpStatus.NOT_FOUND.value())
                        .message(HttpStatus.NOT_FOUND.getReasonPhrase())
                        .responseModel(new ModelRef("CommonErrorDTO"))
                        .build(),
                (new ResponseMessageBuilder()).
                        code(HttpStatus.FORBIDDEN.value())
                        .message(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .responseModel(new ModelRef("CommonErrorDTO"))
                        .build(),
                (new ResponseMessageBuilder())
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .responseModel(new ModelRef("CommonErrorDTO"))
                        .build())
                .collect(Collectors.toList());
    }

}
