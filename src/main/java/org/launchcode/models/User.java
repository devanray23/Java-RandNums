package org.launchcode.models;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.*;

@Entity
public class User{
    @Id
    @GeneratedValue
    private int id;

    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "[a-zA-Z][a-zA-Z0-9_-]{4,11}", message = "Invalid username")
    private String username;

    @NotNull
    private String pwHash;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @NotNull
    private Float budget;

    @NotNull
    private Integer calorieIntake;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_cuisine",
            joinColumns =  @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cuisine_id", referencedColumnName = "id"))
    private Set<Cuisine> cuisines;

    public Set<Cuisine> getCuisines(){ return cuisines; }

    public void setCuisines(Set<Cuisine> cuisines){
        this.cuisines = cuisines;
    }

    public User() {}

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.pwHash = hashPassword(password);
        this.calorieIntake = 400;
        this.budget = (float) 10.0;
    }

    private static String hashPassword(String password) { return encoder.encode(password); }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email; }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public Integer getCalorieIntake() {
        return calorieIntake;
    }

    public void setCalorieIntake(Integer calorieIntake) {
        this.calorieIntake = calorieIntake;
    }

}
