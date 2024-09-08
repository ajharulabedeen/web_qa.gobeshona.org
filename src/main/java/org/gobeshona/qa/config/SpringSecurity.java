package org.gobeshona.qa.config;

import org.gobeshona.qa.security.CustomAuthenticationProvider;
import org.gobeshona.qa.security.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

//    @Autowired
//    private UserDetailsService userDetailsService;
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }
    private CustomAuthenticationProvider customAuthenticationProvider;
    public SpringSecurity(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/", "/register/**", "/public/**", "/css/**", "/js/**","/img/**", "/images/**", "/webjars/**", "/favicon.ico").permitAll()
                                .requestMatchers("/index").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login_lte")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/dashboard", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")  // Specify the URL to trigger logout
                        .logoutSuccessUrl("/login")  // URL to redirect to after successful logout
                        .invalidateHttpSession(true)  // Invalidate the session
                        .deleteCookies("JSESSIONID")  // Delete cookies
                        .addLogoutHandler((request, response, authentication) -> {
                            // Custom logout handler to perform additional actions
                            // e.g., logging, token revocation, etc.
                            System.out.println("Custom Logout Handler: User logged out successfully.");
                        })
                        .logoutSuccessHandler((request, response, authentication) -> {
                            // Custom success handler to perform redirect or custom action
                            response.sendRedirect("/login?logoutSuccess");  // Redirect with a custom parameter
                        })
                        .clearAuthentication(true)  // Clear authentication
                        .permitAll()
                )
                .authenticationProvider(customAuthenticationProvider);

        return http.build();
    }



//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeHttpRequests((authorize) ->
//                        authorize.requestMatchers("/","/register/**","/public/**","/css/**", "/js/**", "/images/**", "/webjars/**", "/favicon" +
//                                        ".ico").permitAll()
//                                .requestMatchers("/index").permitAll()
////                                .requestMatchers("/users").hasRole("ADMIN")
//                                .anyRequest().authenticated()
////                                .requestMatchers("/**").authenticated()
//                ).formLogin(
//                        form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/dash", true)
//                                .permitAll()
//                ).logout(logout ->
//                        logout
//                                .logoutUrl("/perform_logout")
//                                .logoutSuccessUrl("/login")
//                                .invalidateHttpSession(true)
//                                .deleteCookies("JSESSIONID")
//                                .permitAll()
//                )
//                .authenticationProvider(customAuthenticationProvider);
//
//        return http.build();
//    }



//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customAuthenticationProvider);
//    }
//
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/static/**", "/favicon.ico", "/assets/**", "/css/**", "/img" +
//                "/**", "/js**", "/admin/**", "/webjars/**", "/templates/**");
//    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> response.sendRedirect("/dashboard");
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("error", "Authentication failed");
            response.sendRedirect("/login?error");
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
