package br.com.furb.restapifurb.common;

import br.com.furb.restapifurb.repositories.UsuarioRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // Disables any HTML security resources
                .csrf().disable()
                // Disable's the creation of sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                // Allow's for any user to request a authentication token
                .antMatchers("/auth/**", "/usuarios").permitAll()
                // Requires that any request's should have been authenticated
                .anyRequest().authenticated();

        httpSecurity
                .addFilterBefore(new FilterAux(), UsernamePasswordAuthenticationFilter.class);

        httpSecurity.headers().cacheControl();
    }


    private class FilterAux extends GenericFilterBean {


        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            var authToken = ((HttpServletRequest) servletRequest).getHeader("Authentication");
            var emailFromToken = Spring.bean(JwtTokenUtil.class).getEmailFromToken(authToken);

            if (emailFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                Spring.bean(UsuarioRepository.class).findAllByEmail(emailFromToken).ifPresent(userList -> {
                    userList.forEach(usuario -> {
                        if (Spring.bean(JwtTokenUtil.class).validateToken(authToken, usuario)) {
                            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, Collections.singletonList(() -> "ADMIN"));
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) servletRequest));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        }
                    });
                });

            }


            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

}