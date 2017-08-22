package org.launchcode.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.launchcode.models.User;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("login")
public class LoginController {

    static ArrayList<User> users = new ArrayList<>();

    //Displays Login Page
    @RequestMapping(value= "")
    public String displayLoginPage(Model model){
        model.addAttribute("title", "Login to RandNums");
        return "login/index";
    }

    //Displays Register Page
    @RequestMapping(value="register", method = RequestMethod.GET)
    public String displayRegisterForm(Model model){
        model.addAttribute("title", "Register to RandNums");
        model.addAttribute("user", new User());

        return "login/register";
    }

    //Processes Register Form and validates user input
    @RequestMapping(value= "register", method = RequestMethod.POST)
    public String processRegisterForm(@ModelAttribute @Valid User user,
                                      Errors errors, @RequestParam String newVerifyPassword, Model model){

        if (errors.hasErrors()){

            model.addAttribute("title", "Register to RandNums");

            if(!user.getPassword().equals(newVerifyPassword)){
                model.addAttribute("verifyError", "Passwords do not match.");
            }
            for(User existing: users){
                if(existing.getUsername().equals(user.getUsername())){
                    model.addAttribute("existingError", "User already exists.");
                }
            }

            return "login/register";
        }

        users.add(user);
        model.addAttribute("user", user);
        model.addAttribute("welcome", "Welcome " + user.getUsername());
        model.addAttribute("title", "Welcome!");
        return "home/index";
    }


    //Processes Login Form and validates user input
    @RequestMapping(value="", method = RequestMethod.POST)
    public String processLoginForm(Model model, @RequestParam String usernameLogin,
                                   @RequestParam String passwordLogin){

        String templateName = "login/index";
        String titleDisplay = "Login to RandNums!";

        for (User user: users){
            if(user.getUsername().equals(usernameLogin)){
                if(user.getPassword().equals(passwordLogin)){
                    model.addAttribute("welcome", "Welcome " + user.getUsername());
                    model.addAttribute("title", "Welcome!");
                    return "home/index";
                }else{
                    model.addAttribute("loginError", "Incorrect Password");
                    model.addAttribute("title", "Login to Randums");
                    return "login/index";
                }
            }
        }

        model.addAttribute("loginError", "User does not exist");
        model.addAttribute("title", titleDisplay);
        return templateName;
    }


}

