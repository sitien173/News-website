package ptit.ltw.Configuration.SpringSercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import ptit.ltw.Service.IService.UserService;

@Configuration
@EnableWebSecurity
@Order(2)
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final Environment environment;
    private final DaoAuthenticationProvider daoAuthenticationProvider;

    @Autowired
    public SpringSecurity(UserService userService, Environment environment, DaoAuthenticationProvider daoAuthenticationProvider) {
        this.userService = userService;
        this.environment = environment;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    .csrf()
                     .disable()
                .antMatcher("/**/user/**")
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                    .antMatchers("/**/user/**")
                        .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                    .loginProcessingUrl("/user/j_spring_security_login")
                            .failureUrl("/login?info=Email or Password incorrect. Please again!")
                                .permitAll()
                .and()
                .logout()
                    .logoutUrl("/user/j_spring_security_logout")
                        .logoutSuccessUrl("/login?info=logout success!")
                            .deleteCookies("JSESSIONID","remember-me")
                                 .clearAuthentication(true)
                                    .invalidateHttpSession(true)
                .and()
                .rememberMe()
                    .key("uniqueAndSecret")
                        .tokenValiditySeconds(Integer.parseInt(environment.getProperty("remember.me.expiredAt")))
                            .userDetailsService(userService);
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider);
    }
}
