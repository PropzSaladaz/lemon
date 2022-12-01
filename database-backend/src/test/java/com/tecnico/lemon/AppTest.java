package com.tecnico.lemon;

import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.DatabaseManagerImpl;
import com.tecnico.lemon.database.Queries;
import com.tecnico.lemon.database.Tables;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;


public class AppTest 
{
    static DatabaseManager db = new DatabaseManagerImpl();

    @BeforeAll
    public static void startDatabase(){
        db.buildSchema();
    }
    @AfterAll
    public static void closeDatabase(){
        db.clean();
    }
    @Test
    public void builSchema()
    {
        db.buildSchema();
    }

    @Test
    public void populate()
    {
        db.populate();
    }

    @Test
    public void queryVehicle() throws SQLException {
        db.executeQuery(Queries.insertVehicle(3, 100, "ali","Scooter"));
        ResultSet rs = db.executeQuery(Queries.SELECT_ALL_FROM_VEHICLES);
        while (rs.next()){
            String localization = rs.getString(Tables.Vehicle.LOCALIZATION);
            String description  = rs.getString(Tables.Vehicle.DESCRIPTION);
            int id = rs.getInt(Tables.Vehicle.ID);
            double price = rs.getDouble(Tables.Vehicle.PRICE);
            boolean lock = rs.getBoolean(Tables.Vehicle.LOCKED);
            boolean payed = rs.getBoolean(Tables.Vehicle.PAYED);
            assertEquals(id, 3);
            assertEquals(price, 100);
            assertEquals(localization, "ali");
            assertEquals(description, "Scooter");
            assertFalse(lock);
            assertFalse(payed);
        }
        rs.close();
    }


}
