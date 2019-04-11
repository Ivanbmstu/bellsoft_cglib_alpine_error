package com.example.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.List;

@RestController
public class SimpleController {

    private static final Logger log = LoggerFactory.getLogger(SimpleController.class);
    
    @GetMapping("/")
    public List<String> home() throws UnknownHostException {
        log.info("Вызов метода с получением текста на русском языке");
        return List.of(
                "InetAddress.getLocalHost() " + TestHostUtils.getLocalHostname(),
                "Привет текст на русском языке",
                "Привет текст на русском языке",
                "Привет текст на русском языке",
                "Привет текст на русском языке",
                "Привет текст на русском языке?",
                "Привет текст на русском языке!"
        );
    }

}
