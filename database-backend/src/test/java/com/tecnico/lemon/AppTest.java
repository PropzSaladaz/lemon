package com.tecnico.lemon;

import com.tecnico.lemon.contract.VehicleTableServiceOuterClass;
import com.tecnico.lemon.database.DatabaseManager;
import com.tecnico.lemon.database.DatabaseManagerImpl;
import com.tecnico.lemon.database.Queries;
import com.tecnico.lemon.database.Tables;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
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

    @BeforeEach
    public void cleanDatabase(){
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
        RowSetFactory factory = RowSetProvider.newFactory();
        CachedRowSet crs = factory.createCachedRowSet();
        crs.populate(rs);
        while(crs.next()) {
            if (!crs.getBoolean(Tables.Vehicle.LOCKED)) {
                String localization = crs.getString(Tables.Vehicle.LOCALIZATION);
                String description  = crs.getString(Tables.Vehicle.DESCRIPTION);
                int id = crs.getInt(Tables.Vehicle.VEHICLE_ID);
                double price = crs.getDouble(Tables.Vehicle.PRICE);
                boolean lock = crs.getBoolean(Tables.Vehicle.LOCKED);
                boolean payed = crs.getBoolean(Tables.Vehicle.PAYED);
                assertEquals(3, id);
                assertEquals(100, price, 0.5);
                assertEquals("ali", localization);
                assertEquals("Scooter", description);
                assertFalse(lock);
                assertFalse(payed);
            }
        }
        rs.close();
    }


}
