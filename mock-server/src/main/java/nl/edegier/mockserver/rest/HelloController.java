package nl.edegier.mockserver.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static java.time.Duration.ofMillis;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping
    public Mono<String> hello() {
        return Mono.delay(ofMillis(300)).map(i -> "hello");
    }
}
