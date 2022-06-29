package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service
public class TestService {

    public double test(double num) {
        return ((num / (1000 * 60)) % 60);
    }

    public int test2(int num) {
        return ((num / (1000 * 60 * 60)) % 24);
    }

}
