package com.ccx.jsj.validate.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("domainUserDetailService")
    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                (UsernamePasswordAuthenticationToken)authentication;

        Object principal = usernamePasswordAuthenticationToken.getPrincipal();//username
        Object credentials = usernamePasswordAuthenticationToken.getCredentials();//password

        if(credentials==null){
            throw new BadCredentialsException("TokenAuthenticationProvider.badCredentials");
        }

        if(principal==null){
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(principal));


        boolean matches = passwordEncoder.matches(String.valueOf(credentials), userDetails.getPassword());

        if(!matches){
            throw new BadCredentialsException("用户名或密码错误");
        }
        return this.createSuccessAuthentication(principal, credentials, userDetails);
    }

    protected Authentication createSuccessAuthentication(Object principal, Object credentials,  UserDetails userDetails) {
        UsernamePasswordAuthenticationToken result =
                new UsernamePasswordAuthenticationToken(principal,credentials,userDetails.getAuthorities());
        result.setDetails(userDetails);
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
