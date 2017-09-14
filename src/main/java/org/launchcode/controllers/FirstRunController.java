package org.launchcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("")
public class FirstRunController extends AbstractController {

    @RequestMapping(value = "")
    public String displayBootRunPage(Model model, HttpServletRequest request){
        model.addAttribute("title", "Welcome to RandNums!");
        clearSession(request.getSession());
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        return "index";
    }
}
