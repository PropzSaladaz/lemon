package com.tecnico.lemon.database;

public class Queries {
    public static final String CREATE_TABLE_USERS =
            String.format("create table %s (" +
            "%s integer unique, " +
            "%s varchar(60) unique, " +
            "%s char(64)," +
            "%s varchar(32)," +
            "primary key (%s))",
                    Tables.User.TABLE_NAME,
                    Tables.User.ID,
                    Tables.User.EMAIL,
                    Tables.User.PASSWORD,
                    Tables.User.TYPE,
                    Tables.User.ID);

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
                    Tables.Vehicle.ID,
                    Tables.Vehicle.PRICE,
                    Tables.Vehicle.LOCALIZATION,
                    Tables.Vehicle.DESCRIPTION,
                    Tables.Vehicle.PAYED,
                    Tables.Vehicle.LOCKED,
                    Tables.Vehicle.ID);

    public static final String DROP_TABLE_USERS = "drop table " + Tables.User.TABLE_NAME;
    public static final String DROP_TABLE_VEHICLES = "drop table " + Tables.Vehicle.TABLE_NAME;

    public static final String SELECT_ALL_FROM_USERS = "select * from " + Tables.User.TABLE_NAME;
    public static final String SELECT_ALL_FROM_VEHICLES = "select * from " + Tables.Vehicle.TABLE_NAME;

    public static String insertUser(int id,String email,String password,String type) {
        return String.format("insert into %s values ('%s', '%s', '%s', '%s')", Tables.User.TABLE_NAME, id, email, password, type);
    }

    public static String insertVehicle(int id, int price, String localization, String description) {
        return String.format("insert into %s values ('%s', '%s', '%s', '%s', false, false)",
                Tables.Vehicle.TABLE_NAME, id, price, localization, description);
    }
}
