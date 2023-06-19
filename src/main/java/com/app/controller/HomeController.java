package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.Session;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String home() {
		return "forward:/main";
	}

	@RequestMapping("/main")
	public String main() {
		return "index";
	}

	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/chat")
	public String chat() {
        return "chat";
    }

}

