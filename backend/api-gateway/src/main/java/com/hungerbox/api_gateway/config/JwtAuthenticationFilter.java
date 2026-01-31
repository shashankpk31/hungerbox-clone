package com.hungerbox.api_gateway.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {
	@Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
	private String secretKey;

	public JwtAuthenticationFilter() {
		super(Config.class);
	}

	public static class Config {
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {

			// // 0. Check if the route is marked as 'public' in metadata
			// Route route = exchange
			// 		.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

			// if (route != null && "true".equals(route.getMetadata().get("public"))) {
			// 	return chain.filter(exchange); // Skip auth and continue
			// }
			// 1. Check if token exists
			if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, HttpStatus.UNAUTHORIZED);
			}

			String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				authHeader = authHeader.substring(7);
			}

			try {
				byte[] keyBytes = Decoders.BASE64.decode(secretKey);
				Key key = Keys.hmacShaKeyFor(keyBytes);

				// 1. Extract Claims
				var claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authHeader).getBody();

				String username = claims.getSubject();

				// Mutate request to add structured headers
				ServerWebExchange mutatedExchange = exchange.mutate()
						.request(exchange.getRequest().mutate()
								.header(HttpHeaders.AUTHORIZATION, "Bearer " + authHeader)
								.header("X-Auth-User-Id", String.valueOf(claims.get("userId")))
								.header("X-Auth-User-Username", username)
								.header("X-Auth-User-Role", claims.get("role", String.class))
								.header("X-Auth-User-Org-Id", String.valueOf(claims.get("orgId")))
								.header("X-Auth-User-Office-Id", String.valueOf(claims.get("officeId"))).build())
						.build();

				return chain.filter(mutatedExchange);
			} catch (Exception e) {
				return onError(exchange, HttpStatus.UNAUTHORIZED);
			}
		};
	}

	private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
		exchange.getResponse().setStatusCode(httpStatus);
		return exchange.getResponse().setComplete();
	}
}