package org.launchcode.controllers;
import org.launchcode.models.forms.LoginForm;
import org.launchcode.models.forms.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.launchcode.models.User;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("login")
public class LoginController extends AbstractController {

    //Displays Login Page
    @RequestMapping(value= "", method = RequestMethod.GET)
    public String displayLoginPage(Model model, HttpServletRequest request){
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Login");
        model.addAttribute("loginForm", new LoginForm());
        return "login/index";
    }

    //Displays Register Page
    @RequestMapping(value="register", method = RequestMethod.GET)
    public String displayRegisterForm(Model model, HttpServletRequest request){
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Register");
        model.addAttribute("registerForm", new RegisterForm());

        return "login/register";
    }

    //Processes Register Form and validates user input
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String register(@ModelAttribute @Valid RegisterForm registerForm, Errors errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Register to RandNums");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            return "login/register";
        }

        User existingUser = userDao.findByUsername(registerForm.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            model.addAttribute("title", "Register");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            return "login/register";
        }

        User newUser = new User(registerForm.getUsername(), registerForm.getEmail(), registerForm.getPassword());

        userDao.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:/home";
    }

    //Processes Login Form and validates user input
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String login(@ModelAttribute @Valid LoginForm loginForm, Errors errors, HttpServletRequest request, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Login");
            return "login/index";
        }

        User theUser = userDao.findByUsername(loginForm.getUsername());
        String password = loginForm.getPassword();

        if (theUser == null) {
            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("title", "Login");
            return "login/index";
        }

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password", "password.invalid", "Invalid password");
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("title", "Login");
            return "login/index";
        }

        setUserInSession(request.getSession(), theUser);

        return "redirect:/home";
    }
}

