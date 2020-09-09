package com.example.authserver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserEntity {
    private String username;
    private String password;
    private List<String> roles;
}
