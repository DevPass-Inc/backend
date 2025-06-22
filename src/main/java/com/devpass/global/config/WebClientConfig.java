package com.devpass.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://devpass-python-backend:8000").build();
    }

    /**
     * OAuth2AuthorizedClientManager 로 자동 토큰 부착
     */
    @Bean
    public ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2(
            OAuth2AuthorizedClientManager manager
    ) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2 =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(manager);
        oauth2.setDefaultClientRegistrationId("github");
        return oauth2;
    }

    @Bean
    public WebClient githubWebClient(
            WebClient.Builder builder,
            ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2
    ) {
        return builder
                .baseUrl("https://api.github.com")
                //.filter(oauth2)
                .build();
    }

    @Bean
    public WebClient githubGraphQlClient(
            WebClient.Builder builder,
            ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2
    ) {
        return builder
                .baseUrl("https://api.github.com/graphql")
                .filter(oauth2)
                .build();
    }

}