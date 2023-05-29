package io.examples.helidon.nima;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.helidon.nima.webclient.http1.Http1Client;
import io.helidon.nima.webserver.http.HttpRules;
import io.helidon.nima.webserver.http.HttpService;
import io.helidon.nima.webserver.http.ServerRequest;
import io.helidon.nima.webserver.http.ServerResponse;

class BlockingService implements HttpService {

    // we use this approach as we are calling the same service
    // in a real application, we would use DNS resolving, or k8s service names
    private static Http1Client client;

    static void client(Http1Client client) {
        BlockingService.client = client;
    }

    @Override
    public void routing(HttpRules httpRules) {
        httpRules.get("/hello",this::hello);
//                .get("/one", this::one)
//                .get("/sequence", this::sequence)
//                .get("/parallel", this::parallel)
//                .get("/sleep", this::sleep);
    }

    private void hello(ServerRequest req, ServerResponse res) {
        String response = callRemote(client());

        res.send(response);
    }

    private static Http1Client client() {
        if (client == null) {
            throw new RuntimeException("Client must be configured on BlockingService");
        }
        return client;
    }

    private static String callRemote(Http1Client client) {
        return client.get()
                .path("/hello")
                .request(String.class);
    }

    private int count(ServerRequest req) {
        return req.query().first("count").map(Integer::parseInt).orElse(3);
    }
}
