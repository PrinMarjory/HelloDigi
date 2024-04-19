package fr.diginamic.HelloDigi.controller;

import java.util.Locale;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LanguageController {
    @GetMapping("/setLanguage")
    public String setLanguage(@RequestParam("lang") String language, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("userLanguage", language);
        
        if ("fr".equals(language)) {
            LocaleContextHolder.setLocale(Locale.FRENCH);
        } else {
            LocaleContextHolder.setLocale(Locale.ENGLISH);
        }
        
        return "redirect:/"; 
    }
}
