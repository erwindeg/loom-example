package nl.edegier.loomexample.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static java.util.concurrent.Executors.newThreadPerTaskExecutor;

@Configuration
public class Config {
    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadFactory factory = Thread.ofVirtual().name("virtual", 0L).factory();
        return new TaskExecutorAdapter(newThreadPerTaskExecutor(factory));
    }

    @Bean
    TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        ThreadFactory factory = Thread.ofVirtual().name("virtual", 0L).factory();
        return protocolHandler -> protocolHandler.setExecutor(newThreadPerTaskExecutor(factory));
    }


    @Bean
    public RestTemplate restTemplate() {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(10000);
        connectionManager.setDefaultMaxPerRoute(10000);
        var httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();
        return new RestTemplateBuilder().requestFactory(() -> new HttpComponentsClientHttpRequestFactory(httpClient)).build();
    }
}
