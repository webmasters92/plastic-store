package rs.dev.plasticstore.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class ResourceConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:images/");
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/pdf/**").addResourceLocations("classpath:/static/pdf/");
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:security");
        messageSource.setDefaultEncoding("UTF-8");
        //  messageSource.setDefaultEncoding("Latin-1");
        return messageSource;
    }
}
