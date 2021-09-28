package ptit.ltw.Configuration.SpingMVC;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ConfigMVC implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure the resource handler to serve files uploaded with CKFinder.
        String publicFilesDir = String.format("file:%s/src/main/resources/static/assets/", System.getProperty("user.dir"));

        registry.addResourceHandler("/src/main/resources/static/assets/**")
                .addResourceLocations(publicFilesDir);
    }
}
