package com.Management.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class EmailValidation implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return true;
    }
}
