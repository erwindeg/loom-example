package nl.edegier.loomexample.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitStreamTemplateConfigurer;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private  RestTemplate restTemplate;
    private final String host = "http://localhost:8081/hello";

    @GetMapping
    public String hello() throws URISyntaxException {
        URI uri = new URI(host);
        return restTemplate.getForObject(uri, String.class);
    }
}
