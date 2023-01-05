package com.tecnico.lemon.database;

public class Queries {
    public static final String CREATE_TABLE_USERS =
            String.format("create table %s (" +
                    "%s varchar(3000) unique," +
                    "%s varchar(3000), " +
                    "%s varchar(32)," +
                    "primary key (%s))",
                    Tables.User.TABLE_NAME,
                    Tables.User.PUBLIC_KEY,
                    Tables.User.EMAIL /*Encrypted*/,
                    Tables.User.TYPE,
                    Tables.User.PUBLIC_KEY);
    
    public static final String CREATE_TABLE_USER_RESERVATIONS =
            String.format("create table %s (" +
                    "%s varchar(3000) unique," +
                    "%s varchar(3000), " +
                    "primary key (%s))",
                    Tables.UserReservations.TABLE_NAME,
                    Tables.UserReservations.RESERVATION_ID /*Encrypted*/,
                    Tables.UserReservations.PUBLIC_KEY,
                    Tables.UserReservations.RESERVATION_ID);

    public static final String CREATE_TABLE_RESERVATIONS = 
            String.format("create table %s (" +
                    "%s varchar(3000) unique," +
                    "%s integer, " +
                    "%s text," +
                    "%s numeric, " +
                    "%s varchar(255)," +
                    "primary key (%s))",
                    Tables.Reservations.TABLE_NAME,
                    Tables.Reservations.RESERVATION_ID,
                    Tables.Reservations.VEHICLE_ID,
                    Tables.Reservations.LOCALIZATION,
                    Tables.Reservations.PRICE,
                    Tables.Reservations.DESCRIPTION,
                    Tables.Reservations.RESERVATION_ID);

    public static final String CREATE_TABLE_VEHICLES =
            String.format("create table %s (" +
                    "%s integer unique, " +
                    "%s numeric," +
                    "%s text," +
                    "%s varchar(255)," +
                    "%s boolean," +
                    "%s varchar(255)," +
                    "primary key (%s))",
                    Tables.Vehicle.TABLE_NAME,
                    Tables.Vehicle.VEHICLE_ID,
                    Tables.Vehicle.PRICE,
                    Tables.Vehicle.LOCALIZATION,
                    Tables.Vehicle.DESCRIPTION,
                    Tables.Vehicle.PAYED,
                    Tables.Vehicle.RESERVATION_ID,
                    Tables.Vehicle.VEHICLE_ID);
    

    
    

    public static final String DROP_TABLE_USERS = "drop table if exists " + Tables.User.TABLE_NAME;
    public static final String DROP_TABLE_VEHICLES = "drop table if exists " + Tables.Vehicle.TABLE_NAME;
    public static final String DROP_TABLE_USER_RESERVATIONS = "drop table if exists " + Tables.UserReservations.TABLE_NAME;
    public static final String DROP_TABLE_RESERVATIONS = "drop table if exists " + Tables.Reservations.TABLE_NAME;

    public static final String DELETE_ALL_USERS = "delete from " + Tables.User.TABLE_NAME + " *";
    public static final String DELETE_ALL_VEHICLES = "delete from " + Tables.Vehicle.TABLE_NAME + " *";
    public static final String DELETE_ALL_USER_RESERVATIONS = "delete from " + Tables.UserReservations.TABLE_NAME + " *"; 
    public static final String DELETE_ALL_RESERVATIONS = "delete from " + Tables.Reservations.TABLE_NAME + " *";

    public static final String SELECT_ALL_FROM_USERS = "select * from " + Tables.User.TABLE_NAME;
    public static final String SELECT_ALL_FROM_VEHICLES = "select * from " + Tables.Vehicle.TABLE_NAME;
    public static final String SELECT_ALL_FROM_USER_RESERVATIONS = "select * from " + Tables.UserReservations.TABLE_NAME;
    public static final String SELECT_ALL_FROM_RESERVATIONS = "select * from " + Tables.Reservations.TABLE_NAME;


    public static String insertUser(String publicKey, String email, String type) {
        return String.format("insert into %s values ('%s', '%s', '%s')", Tables.User.TABLE_NAME, publicKey, email, type);
    }

    public static String lookupUserByEmail(String email) {
        return String.format("select * from %s where email='%s'", Tables.User.TABLE_NAME, email);
    }


    public static String insertVehicle(int id, int price, String localization, String description) {
        return String.format("insert into %s values ('%s', '%s', '%s', '%s', false, 'NULL')",
                Tables.Vehicle.TABLE_NAME, id, price, localization, description);
    }

    public static String lookupVehicleById(int id) {
        return String.format("select * from %s where vehicle_id='%s'",
                Tables.Vehicle.TABLE_NAME, id);
    }

    public static String updateVehicleReservationId(String reservation_id, int vehicle_id) {
        return String.format("update %s set %s = '%s' where %s = '%s';", 
                Tables.Vehicle.TABLE_NAME, Tables.Vehicle.RESERVATION_ID, reservation_id, Tables.Vehicle.VEHICLE_ID, vehicle_id);
    }


    public static String insertReservation(String id, int vehicle_id, String loc, int price, String description) {
        return String.format("insert into %s values ('%s', '%s', '%s', '%s', '%s')",
                Tables.Reservations.TABLE_NAME, id, vehicle_id, loc, price, description);
    }

    public static String deleteReservation(String id) {
        return String.format("delete from %s where reservation_id='%s'",
                Tables.Reservations.TABLE_NAME, id);
    }
    
    public static String lookupReservationVehicleId(String reservation_id) {
        return String.format("select %s from %s where %s = '%s';",
               Tables.Reservations.VEHICLE_ID , Tables.Reservations.TABLE_NAME, Tables.Reservations.RESERVATION_ID, reservation_id);
    }

    public static String insertUserReservation(String reservation_id, String user_id) {
        return String.format("insert into %s values ('%s', '%s')",
                Tables.UserReservations.TABLE_NAME, reservation_id, user_id);
    }

    public static String deleteUserReservation(String reservation_id) {
        return String.format("delete from %s where reservation_id='%s'",
                Tables.UserReservations.TABLE_NAME, reservation_id);
    }

    public static String lookupUserReservations(String user_id) {
        return String.format("select %s from %s where %s = '%s';",
                Tables.UserReservations.RESERVATION_ID, Tables.UserReservations.TABLE_NAME, Tables.UserReservations.PUBLIC_KEY, user_id);
    }
}