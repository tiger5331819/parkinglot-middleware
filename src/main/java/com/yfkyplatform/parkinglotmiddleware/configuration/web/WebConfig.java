package com.yfkyplatform.parkinglotmiddleware.configuration.web;

import com.yfkyplatform.parkinglotmiddleware.universal.extension.IExtensionFuction;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置
 *
 * @author Suhuyuan
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final IExtensionFuction extensionFuction;

    public WebConfig(IExtensionFuction extensionFuction) {
        this.extensionFuction = extensionFuction;
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        AntPathMatcher matcher = new AntPathMatcher();
        matcher.setCaseSensitive(false);
        configurer.setPathMatcher(matcher);
    }

    @Bean
    public FilterRegistrationBean registFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TestBoxRequestFilter(extensionFuction));
        registration.addUrlPatterns("/api/*", "/Daoer/*",
                "/Lifang/*", "/Hongmen/*");
        registration.setName("TestBoxRequestFilter");
        registration.setOrder(1);
        return registration;
    }

/*    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath*:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath*:/META-INF/resources/webjars/");
    }*/
}
