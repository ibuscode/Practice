package com.main.model;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")

public class UPIPayment implements Payment {

    public String process() {

        return "UPI processed";
    }
}
