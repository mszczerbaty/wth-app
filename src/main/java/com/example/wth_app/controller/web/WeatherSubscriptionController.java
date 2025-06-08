package com.example.wth_app.controller.web;

import com.example.wth_app.service.WeatherSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/email")
@RequiredArgsConstructor
public class WeatherSubscriptionController {

    private final WeatherSubscriptionService subscriptionService;

    @GetMapping("/subscribe")
    public String showSubscriptionPage(Model model) {
        return "subscribe";
    }

    @PostMapping("/subscribe")
    public String subscribe(@RequestParam String email, @RequestParam String city, Model model) {
        String message = subscriptionService.subscribe(email, city);
        model.addAttribute("message", message);
        return "subscription-result";
    }

    @PostMapping("/unsubscribe")
    public String unsubscribe(@RequestParam String email, @RequestParam String city, Model model) {
        String message = subscriptionService.unsubscribe(email, city);
        model.addAttribute("message", message);
        return "subscription-result";
    }
}
