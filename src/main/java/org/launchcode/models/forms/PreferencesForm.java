package org.launchcode.models.forms;

import javax.validation.constraints.NotNull;


public class PreferencesForm {

    private String [] cuisines = new String[10];

    @NotNull
    private float budget;

    @NotNull
    private Integer calorieIntake;

    public PreferencesForm() {}

    public String [] getCuisines() { return cuisines; }

    public void setCuisines(String [] cuisines) { this.cuisines = cuisines; }

    public float getBudget() { return budget; }

    public void setBudget(float budget) { this.budget = budget; }

    public Integer getCalorieIntake() { return calorieIntake; }

    public void setCalorieIntake(Integer calorieIntake) { this.calorieIntake = calorieIntake; }
}
