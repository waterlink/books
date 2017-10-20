package nl.rws.books;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@Controller
//@EnableOAuth2Sso
public class BooksApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
//        if ("true".equals(System.getenv("SKIP_SSL_VALIDATION"))) {
//            SSLValidationDisabler.disableSSLValidation();
//        }
        SpringApplication.run(BooksApplication.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @GetMapping("/login")
    public String login() {
        return "templates/login.html";
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/login**").permitAll()
                .anyRequest().authenticated();
    }
}
