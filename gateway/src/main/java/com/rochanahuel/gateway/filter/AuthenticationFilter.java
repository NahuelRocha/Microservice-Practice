package com.rochanahuel.gateway.filter;

import com.rochanahuel.gateway.config.JwtService;
import com.rochanahuel.gateway.exception.MissingTheTokenException;
import com.rochanahuel.gateway.exception.TokenInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    @Autowired
    private JwtService jwtService;

    public AuthenticationFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {

            if (routeValidator.isSecured.test(exchange.getRequest())) {

                boolean token = exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION);

                if (!token) {
                    throw new MissingTheTokenException("The authentication token is missing");
                }

                String authHeader = exchange.getRequest().getHeaders().get("Authorization").get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {

                    authHeader = authHeader.substring(7);

                    try {

                        jwtService.validateToken(authHeader);

                    } catch (Exception e) {

                        throw new TokenInvalidException("Token is not valid");
                    }

                }

            }

            return chain.filter(exchange);
        });
    }

    public static class Config {

    }

}
