package com.mo2christian.recognition.web.action;

import com.mo2christian.recognition.web.model.Token;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;

/**
 * Intercepteur pour verifier l'authentification oauth VIA IAP
 */
public class IapInterceptor extends AbstractInterceptor {

    private final Logger logger = LogManager.getLogger(IapInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = ServletActionContext.getRequest();
        String jwt = request.getHeader("x-goog-iap-jwt-assertion");
        logger.debug("Token JWT : {}", jwt);
        if (jwt == null) {
            jwt = System.getenv("JWT");
        }
        if (jwt == null)
            return "login";
        String email = getEmail(jwt);
        if (email == null)
            return "login";
        Token token = new Token();
        token.setEmail(email);
        token.setValue(jwt);
        request.getSession().setAttribute(Token.NAME, token);
        return invocation.invoke();
    }

    private String getEmail(String jwtToken) throws ParseException {
        SignedJWT signedJwt = SignedJWT.parse(jwtToken);
        JWTClaimsSet claims = signedJwt.getJWTClaimsSet();
        return (String)claims.getClaim("email");
    }
}
