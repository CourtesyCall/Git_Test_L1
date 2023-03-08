package telran.security;


import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import telran.security.service.ExpiredPasswordFilter;

public class AuthorizationConfigurations {

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.httpBasic();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new ExpiredPasswordFilter(), BasicAuthenticationFilter.class);
        http.authorizeHttpRequests(autorize -> autorize.requestMatchers("/account/register")
                .permitAll()
                //TODO .hasRole()
                .requestMatchers(HttpMethod.DELETE,"/account/user/{login}")
                .access(new WebExpressionAuthorizationManager("#login == authentication.name or" +
                        "hasRole('ADMIN')"))
                .requestMatchers(HttpMethod.DELETE,"/account/user/{login}").
                        access(new WebExpressionAuthorizationManager("@customWebSecurity.checkOwner(#login)"))
                .requestMatchers(HttpMethod.GET,"/account/*/{login}")
                .access(new WebExpressionAuthorizationManager("#login == authentication.name or" +
                        "hasRole('ADMIN')"))
                .requestMatchers(HttpMethod.PUT, "account/*").authenticated()
                .anyRequest().denyAll());
        return http.build();
    }
}
