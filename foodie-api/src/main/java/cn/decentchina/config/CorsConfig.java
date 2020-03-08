package cn.decentchina.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jiangyu
 * @date 2020/1/22
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Autowired
    private UploadFaceConfig uploadFaceConfig;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080");
        corsConfiguration.addAllowedOrigin("http://shop.jiangy.top:8080");
        corsConfiguration.addAllowedOrigin("http://center.jiangy.top:8080");
        corsConfiguration.addAllowedOrigin("http://shop.jiangy.top");
        corsConfiguration.addAllowedOrigin("http://center.jiangy.top");
        corsConfiguration.addAllowedOrigin("http://api.jiangy.top");

        // 是否允许携带cookie
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(source);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:" + uploadFaceConfig.getUploadUrl());
    }
}
