package com.example.springSecurityExample.request;

import lombok.Builder;

@Builder
public record AuthenticationRequest (
        String username,
        String password
) { }
