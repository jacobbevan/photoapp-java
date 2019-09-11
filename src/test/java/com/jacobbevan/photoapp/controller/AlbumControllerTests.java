package com.jacobbevan.photoapp.controller;

import com.jacobbevan.photoapp.service.ImageProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlbumControllerTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @TestConfiguration
    static class AlbumControllerTestsContextConfiguration {

        @Bean
        @Primary
        public ImageProvider imageProvider() {
            return Mockito.mock(ImageProvider.class);
        }
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {


        String response = this.restTemplate.getForObject("http://localhost:" + port + "/api/albums", String.class);

        assertThat(response, containsString("a"));

    }
}
