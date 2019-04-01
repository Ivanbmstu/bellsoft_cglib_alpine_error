package com.example.server;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;

@RestController
public class SimpleController {

    @GetMapping("/")
    public String home() throws UnknownHostException {
        return "InetAddress.getLocalHost() " + TestHostUtils.getLocalHostname();
    }
    
}
