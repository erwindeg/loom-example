package nl.edegier.mockserver.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.time.Duration.ofMillis;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @Autowired
    WebClient webClient;

    @GetMapping
    public Mono<String> hello() {
        return webClient.get().uri("hello").retrieve().bodyToMono(String.class);
    }
}
