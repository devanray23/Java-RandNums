package org.launchcode.models.data;

import org.launchcode.models.Cuisine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.ArrayList;


@Repository
@Transactional
public interface CuisineDao extends CrudRepository<Cuisine, Integer> {

   /* public static ArrayList<Cuisine> getAllCuisines() throws ClassNotFoundException, SQLException {

        ArrayList<Cuisine> cuisines = new ArrayList<>();

        Connection c = null;

        try {
            c = DriverManager.getConnection("jdbc:mysql://localhost:8889/randnums-mvc",
                    "randnums-mvc", "23isBACK!");

            Cuisine cuisine = null;

            Statement stm = c.createStatement();

            ResultSet resultSet = stm.executeQuery("SELECT * FROM cuisine");

            while (resultSet.next()) {

                cuisine.setId(resultSet.getInt("id"));
                cuisine.setName(resultSet.getString("name"));

                cuisines.add(cuisine);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return cuisines;
    }*/

    ArrayList<Cuisine> findAll();

    Cuisine findByName(String name);
}