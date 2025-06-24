package web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final RoleConverter roleConverter;
    public WebConfig(RoleConverter roleConverter) {
        this.roleConverter = roleConverter;
    }
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(roleConverter);
    }
}
