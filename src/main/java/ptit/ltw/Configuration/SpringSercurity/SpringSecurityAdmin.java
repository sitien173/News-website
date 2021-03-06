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
import ptit.ltw.Model.Role;

@Configuration
@EnableWebSecurity
@Order(1)
public  class SpringSecurityAdmin extends WebSecurityConfigurerAdapter {
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final Environment environment;
    private final UserService userService;

    @Autowired
    public SpringSecurityAdmin(DaoAuthenticationProvider daoAuthenticationProvider, Environment environment, UserService userService) {
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.environment = environment;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .antMatcher("/**/admin/**")
                    .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                    .authorizeRequests()
                        .antMatchers("/**/admin/**")
                            .hasAuthority(Role.ADMIN.name())
                .and()
                .formLogin()
                    .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/j_spring_security_login")
                                .failureUrl("/admin/login?messenger=failed")
                                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/admin/j_spring_security_logout")
                        .logoutSuccessUrl("/admin/login")
                            .deleteCookies("JSESSIONID", "remember-me")
                .and()
                .rememberMe()
                    .key("uniqueAndSecret")
                        .tokenValiditySeconds(Integer.parseInt(environment.getProperty("remember.me.expiredAt")))
                            .userDetailsService(userService);
    }
}
