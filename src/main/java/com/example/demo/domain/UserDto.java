package com.example.demo.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class UserDto {

    private final long seq;

    private final String name;
}
