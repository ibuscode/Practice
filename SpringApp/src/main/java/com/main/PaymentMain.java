package com.main;

import com.main.config.AppConfig;
import com.main.model.Payment;
import com.main.model.UPIPayment;
import com.main.service.PaymentService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PaymentMain {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Payment payment1 = context.getBean(UPIPayment.class);
        Payment payment2 = context.getBean(UPIPayment.class);
        Payment payment3 = context.getBean(UPIPayment.class);
        System.out.println(payment1);
        System.out.println(payment2);
        System.out.println(payment3);

        PaymentService paymentService = context.getBean(PaymentService.class);
        System.out.println(paymentService.processPaymentForCustomer(payment1));
        System.out.println( paymentService.processPaymentForCustomer(payment2));
        System.out.println(paymentService.processPaymentForCustomer(payment3));
        context.close();

    }
}
