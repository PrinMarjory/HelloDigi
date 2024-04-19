package fr.diginamic.HelloDigi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class IndexViewController {
	
    @Autowired
    private MessageSource messageSource;
	
	@GetMapping("/")
	public String getIndex(HttpServletRequest request, Model model, Authentication authentication) {
		
		String userLanguage = (String) request.getSession().getAttribute("userLanguage");
		model.addAttribute("userLanguage", userLanguage);
		model.addAttribute("authentication", authentication);
		
		/*
        String title = messageSource.getMessage("index.title", null, LocaleContextHolder.getLocale());
        String profile = messageSource.getMessage("index.profile", null, LocaleContextHolder.getLocale());
        String username = messageSource.getMessage("index.username", null, LocaleContextHolder.getLocale());
        String authorities = messageSource.getMessage("index.authorities", null, LocaleContextHolder.getLocale());
        String townList = messageSource.getMessage("index.townList", null, LocaleContextHolder.getLocale());
        model.addAttribute("title", title);
        model.addAttribute("profile", profile);
        model.addAttribute("username", username);
        model.addAttribute("authorities", authorities);
        model.addAttribute("townList", townList);
		*/
		return "index";
	}
}
