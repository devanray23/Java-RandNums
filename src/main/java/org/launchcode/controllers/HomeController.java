package org.launchcode.controllers;
import org.launchcode.models.Cuisine;
import org.launchcode.models.User;
import org.launchcode.models.forms.PreferencesForm;
import org.launchcode.models.forms.RegisterForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("home")
public class HomeController extends AbstractController{

    //Displays Login Page
    @RequestMapping(value= "")
    public String displayHomePage(Model model, HttpServletRequest request){
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));
        model.addAttribute("title", "Home Page");
        User user = getUserFromSession(request.getSession());

        model.addAttribute("welcome", "Welcome " + user.getUsername());

        return "home/index";
    }

    @RequestMapping(value="preferences", method = RequestMethod.GET)
    public String displayViewPreferencesPage(Model model, HttpServletRequest request){
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));

        User user = getUserFromSession(request.getSession());


        model.addAttribute("cuisines", user.getCuisines());
        model.addAttribute("calorieIntake", user.getCalorieIntake());
        model.addAttribute("budget", user.getBudget());
        model.addAttribute("title", "View Preferences");

        return "home/viewpreferences";
    }

    //Displays Preferences Page
    @RequestMapping(value="setpreferences", method = RequestMethod.GET)
    public String displaySetPreferencesPage(Model model, HttpServletRequest request){

        model.addAttribute("sessionActive", isSessionActive(request.getSession()));

        model.addAttribute("title", "Set Preferences");

        model.addAttribute(new PreferencesForm());
        model.addAttribute("cuisinesList", cuisineDao.findAll());

        return "home/setpreferences";
    }

    @RequestMapping(value="setpreferences", method = RequestMethod.POST)
    public String processPreferenceForm(@Valid PreferencesForm preferencesForm, Errors errors, Model model, HttpServletRequest request){
        model.addAttribute("sessionActive", isSessionActive(request.getSession()));

        if(errors.hasErrors()){
            model.addAttribute("sessionActive", isSessionActive(request.getSession()));
            model.addAttribute("title", "View Preferences");
            return "home/preferences";
        }

        User user = getUserFromSession(request.getSession());

        Set <Cuisine> cuisineSet = new HashSet<>();

        for(String option : preferencesForm.getCuisines()){
            Cuisine found = cuisineDao.findByName(option);
            if(found != null){
                cuisineSet.add(found);
            }
        }

        user.setCuisines(cuisineSet);
        user.setBudget(preferencesForm.getBudget());
        user.setCalorieIntake(preferencesForm.getCalorieIntake());

        userDao.save(user);

        return "redirect:preferences";
    }

    @RequestMapping(value="logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request){

        clearSession(request.getSession());
        return "redirect:/login";
    }
}
