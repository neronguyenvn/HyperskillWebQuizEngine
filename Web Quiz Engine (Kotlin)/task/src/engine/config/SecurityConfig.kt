package engine.config

import engine.service.UserDetailsServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig(private val userDetailsService: UserDetailsServiceImpl) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic(Customizer.withDefaults()) // Default Basic auth config
            .csrf { it.disable() }                // for POST requests via Postman
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/register").permitAll()
                    .requestMatchers("/actuator/shutdown").permitAll()
                    .requestMatchers("/error").permitAll()
                    .requestMatchers("/api/quizzes").hasRole("USER")
                    .requestMatchers("/api/quizzes/**").hasRole("USER")
                    .anyRequest().denyAll()
            }

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
