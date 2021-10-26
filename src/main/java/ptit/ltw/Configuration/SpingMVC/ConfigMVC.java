package ptit.ltw.Configuration.SpingMVC;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ptit.ltw.Configuration.Interceptor.InitializeDataInterceptor;
import ptit.ltw.Service.IService.CategoryService;
import ptit.ltw.Service.IService.PostService;

@Configuration
@AllArgsConstructor
public class ConfigMVC implements WebMvcConfigurer {
    private final PostService postService;
    private final CategoryService categoryService;
    private final Environment env;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure the resource handler to serve files uploaded with CKFinder.
        String publicFilesDir = String.format("file:%s/src/main/resources/static/assets/", System.getProperty("user.dir"));

        registry.addResourceHandler("/src/main/resources/static/assets/**")
                .addResourceLocations(publicFilesDir);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new InitializeDataInterceptor(postService,categoryService, env))
                .addPathPatterns("","/","/index","/home","/post/*","/category/*","/search");
    }

}
