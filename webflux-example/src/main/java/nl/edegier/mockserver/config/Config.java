package nl.edegier.mockserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class Config {
    @Bean
    public WebClient webClient(){
        ConnectionProvider connectionProvider = ConnectionProvider.builder("myConnectionPool")
                .maxConnections(20000)
        .pendingAcquireMaxCount(20000)
        .build();
        ReactorClientHttpConnector clientHttpConnector = new ReactorClientHttpConnector(HttpClient.create(connectionProvider));
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .clientConnector(clientHttpConnector)
                .build();
    }
}
