package com.example.PaymentService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentsController {


    @GetMapping("/payments")
    public String payments(
            Model model,
            @RequestParam String orderId,
            @RequestParam Long price,
            @RequestParam(defaultValue = "KRW") String currency,
            @RequestParam String orderName,
            @RequestParam String customerName,
            @RequestParam String customerEmail,
            @RequestParam(required = false) String customerMobilePhone
    ) {
        model.addAttribute("orderId", orderId);
        model.addAttribute("price", price);
        model.addAttribute("currency", currency);
        model.addAttribute("orderName", orderName);
        model.addAttribute("customerName", customerName);
        model.addAttribute("customerEmail", customerEmail);
        model.addAttribute("customerMobilePhone", customerMobilePhone);
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
