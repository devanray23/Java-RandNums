package org.launchcode.models;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Cuisine{

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "cuisines")
    private Set<User> users;


    public Cuisine(String name){
        this.name = name;
    }

    public Cuisine() {}

    public String getName() {
        return name;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public Set<User> getUsers(){
        return users;
    }

    public void setUsers(Set<User> users) { this.users = users; }
}
