package com.example.Spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index() {
        // Redireciona para a página index.html
        return "forward:/js/index.html"; // Caminho relativo ao diretório /static/
    }
}
