package org.activiti.rest.servlet;

import java.util.List;

import org.activiti.rest.service.api.PutAwareCommonsMultipartResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan({"org.activiti.rest.exception", "org.activiti.rest.service.api"})
@EnableSwagger2
@EnableAsync
public class DispatcherServletConfiguration extends WebMvcConfigurationSupport {

  private final Logger log = LoggerFactory.getLogger(DispatcherServletConfiguration.class);

  @Autowired
  private ObjectMapper objectMapper;
  
  @Autowired
  private Environment environment;

  @Bean
  public Docket customImplementation(){
    ApiInfo info = new ApiInfo("","","","",new Contact(null,null,null),"","");
    return new Docket(DocumentationType.SWAGGER_2).apiInfo(info);
  }

  @Bean
  public SessionLocaleResolver localeResolver() {
    return new SessionLocaleResolver();
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    log.debug("Configuring localeChangeInterceptor");
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("language");
    return localeChangeInterceptor;
  }

  @Bean
  public MultipartResolver multipartResolver() {
    PutAwareCommonsMultipartResolver multipartResolver = new PutAwareCommonsMultipartResolver();
    return multipartResolver;
  }

  @Bean
  public RequestMappingHandlerMapping requestMappingHandlerMapping() {
    log.debug("Creating requestMappingHandlerMapping");
    RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
    requestMappingHandlerMapping.setUseSuffixPatternMatch(false);
    Object[] interceptors = {localeChangeInterceptor()};
    requestMappingHandlerMapping.setInterceptors(interceptors);
    return requestMappingHandlerMapping;
  }
  
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    addDefaultHttpMessageConverters(converters);
    for (HttpMessageConverter<?> converter: converters) {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = (MappingJackson2HttpMessageConverter) converter;
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        break;
      }
    }
  }

  @Override
  protected void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
    configurer.favorPathExtension(false);
  }
  
}
