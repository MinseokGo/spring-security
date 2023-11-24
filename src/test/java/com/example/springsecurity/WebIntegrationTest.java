package com.example.springsecurity;

import static java.lang.String.format;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebIntegrationTest {
    @LocalServerPort
    int port;

    public URI uri(String path) {
        try {
            return new URI(format("http://localhost:%d%s", port, path));
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException();
        }
    }
}
