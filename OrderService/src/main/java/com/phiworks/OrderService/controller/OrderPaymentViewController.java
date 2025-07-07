package com.phiworks.OrderService.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderPaymentViewController {
    @Value("${spring.toss.clientKey}")
    private String clientKey;


    @GetMapping("/payments")
    public String payments(
            Model model,
            HttpServletRequest request,
            @RequestParam Long price,
            @RequestParam(defaultValue = "KRW") String currency,
            @RequestParam String orderName,
            @RequestParam Long productId,
            @RequestParam Long productCount,
            @RequestParam Long memberId,
            @RequestParam String deliveryCompany,
            @RequestParam String customerName,
            @RequestParam String customerEmail,
            @RequestParam(required = false) String customerMobilePhone
    ) {
        String baseUrl =String.format("%s://%s:%d",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort()
        );
        String orderId = "ORD" + UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 30);

        model.addAttribute("clientKey", clientKey);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("orderId", orderId);
        model.addAttribute("price", price);
        model.addAttribute("currency", currency);
        model.addAttribute("orderName", orderName);
        model.addAttribute("customerName", customerName);
        model.addAttribute("customerEmail", customerEmail);
        model.addAttribute("customerMobilePhone", customerMobilePhone);

        //metadata
        model.addAttribute("productId", productId);
        model.addAttribute("memberId", memberId);
        model.addAttribute("deliveryCompany", deliveryCompany);
        model.addAttribute("productCount", productCount);

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
