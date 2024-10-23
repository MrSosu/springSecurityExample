package com.example.springSecurityExample.response;

import lombok.Builder;

@Builder
public record ErrorResponse (
        String exception,
        String message
) { }
