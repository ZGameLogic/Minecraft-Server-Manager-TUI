package com.zgamelogic;

import org.springframework.web.client.RestClient;

public class MsmService {
    private final RestClient restClient;

    public MsmService() {
        this.restClient = RestClient.builder()
            .baseUrl("https://msm-dev.zgamelogic.com")
            .defaultHeader("Content-Type", "application/x-www-form-urlencoded")
            .build();
    }
}
