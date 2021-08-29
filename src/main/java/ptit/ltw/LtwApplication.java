package ptit.ltw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@Configuration
@EnableAsync
@ComponentScan({ "ptit.ltw.*" })
public class LtwApplication {
    public static void main(String[] args) {
        SpringApplication.run(LtwApplication.class, args);
    }
}
