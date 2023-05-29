package nl.edegier.loomexample.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/hello")
public class HelloController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String host = "http://localhost:8081/hello";

    @GetMapping
    public String hello() throws URISyntaxException {
        URI uri = new URI(host );
        return restTemplate.getForObject(uri, String.class);
    }
}
