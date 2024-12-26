package com.treinamentoJasper.spring_jasper_treinamento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
       return "redirect:swagger-ui.html";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "/";
    }

}
