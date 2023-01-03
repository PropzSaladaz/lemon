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
    
    public static final String CREATE_TABLE_USER_RESERVATION =
            String.format("create table %s (" +
                    "%s varchar(3000) unique," +
                    "%s varchar(3000), " +
                    "primary key (%s))",
                    Tables.UserReservation.TABLE_NAME,
                    Tables.UserReservation.PUBLIC_KEY,
                    Tables.UserReservation.RESERVATION_ID /*Encrypted*/,
                    Tables.UserReservation.PUBLIC_KEY);

    public static final String CREATE_TABLE_RESERVATIONS = 
            String.format("create table %s (" +
                    "%s varchar(3000) unique," +
                    "%s varchar(3000), " +
                    "primary key (%s))",
                    Tables.Reservation.TABLE_NAME,
                    Tables.Reservation.RESERVATION_ID,
                    Tables.Reservation.VEHICLE_ID,
                    Tables.Reservation.LOCALIZATION,
                    Tables.Reservation.RESERVATION_ID,
                    Tables.Reservation.PRICE,
                    Tables.Reservation.DESCRIPTION);

    public static final String CREATE_TABLE_VEHICLES =
            String.format("create table %s (" +
                    "%s integer unique, " +
                    "%s numeric," +
                    "%s text," +
                    "%s varchar(255)," +
                    "%s boolean," +
                    "%s boolean," +
                    "primary key (%s))",
                    Tables.Vehicle.TABLE_NAME,
                    Tables.Vehicle.VEHICLE_ID,
                    Tables.Vehicle.PRICE,
                    Tables.Vehicle.LOCALIZATION,
                    Tables.Vehicle.DESCRIPTION,
                    Tables.Vehicle.PAYED,
                    Tables.Vehicle.LOCKED,
                    Tables.Vehicle.VEHICLE_ID);
    

    
    

    public static final String DROP_TABLE_USERS = "drop table if exists " + Tables.User.TABLE_NAME;
    public static final String DROP_TABLE_VEHICLES = "drop table if exists " + Tables.Vehicle.TABLE_NAME;
    public static final String DROP_TABLE_USER_RESERVATION = "drop table if exists " + Tables.UserReservation.TABLE_NAME;
    public static final String DROP_TABLE_RESERVATION = "drop table if exists " + Tables.Reservation.TABLE_NAME;

    public static final String DELETE_ALL_USERS = "delete from " + Tables.User.TABLE_NAME + " *";
    public static final String DELETE_ALL_VEHICLES = "delete from " + Tables.Vehicle.TABLE_NAME + " *";
    public static final String DELETE_ALL_USER_RESERVATION = "delete from " + Tables.UserReservation.TABLE_NAME + " *"; 
    public static final String DELETE_ALL_RESERVATION = "delete from " + Tables.Reservation.TABLE_NAME + " *";

    public static final String SELECT_ALL_FROM_USERS = "select * from " + Tables.User.TABLE_NAME;
    public static final String SELECT_ALL_FROM_VEHICLES = "select * from " + Tables.Vehicle.TABLE_NAME;
    public static final String SELECT_ALL_FROM_USER_RESERVATION = "select * from " + Tables.UserReservation.TABLE_NAME;
    public static final String SELECT_ALL_FROM_RESERVATION = "select * from " + Tables.Reservation.TABLE_NAME;


    public static String insertUser(String publicKey, String email, String type) {
        return String.format("insert into %s values ('%s', '%s', '%s')", Tables.User.TABLE_NAME, publicKey, email, type);
    }

    public static String lookupUserByEmail(String email) {
        return String.format("select * from %s where email='%s'", Tables.User.TABLE_NAME, email);
    }


    public static String insertVehicle(int id, int price, String localization, String description) {
        return String.format("insert into %s values ('%s', '%s', '%s', '%s', false, false)",
                Tables.Vehicle.TABLE_NAME, id, price, localization, description);
    }


    public static String insertReservation(int id, int vehicle_id, String loc, int reservation_id, float price, String description) {
        return String.format("insert into %s values ('%s', '%s', '%s', '%s', '%s', '%s')",
                Tables.Reservation.TABLE_NAME, id, vehicle_id, loc, reservation_id, price, description);
    }


    public static String insertUserReservation(int public_key, int encrypted_reservation_id) {
        return String.format("insert into %s values ('%s', '%s')",
                Tables.UserReservation.TABLE_NAME, public_key, encrypted_reservation_id);
    }
}