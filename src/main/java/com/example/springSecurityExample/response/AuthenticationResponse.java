package com.example.springSecurityExample.response;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
) { }
