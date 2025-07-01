package com.example.PaymentService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentsController {


    @GetMapping("/payments")
    public String payments() {
        return "checkout";
    }
    @GetMapping("/success")
    public String success() {
        return "success";
    }
    @GetMapping("/failure")
    public String fail() {
        return "fail";
    }
}
