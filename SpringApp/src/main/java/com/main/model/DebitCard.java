package com.main.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
public class DebitCard implements Payment{

    @Override
    public String process() {
        return "Debit Card payment Processed";
    }
}
