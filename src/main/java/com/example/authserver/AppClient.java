package com.example.authserver;

import lombok.Data;

import java.util.List;

@Data
public class AppClient {
    private String clientId;
    private String clientSecret;
    List<String> authorizedGrantTypes;
    List<String> authorities;
    List<String> scopes;
}
