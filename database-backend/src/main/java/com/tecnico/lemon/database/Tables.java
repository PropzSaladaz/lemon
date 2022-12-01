package com.tecnico.lemon.database;

public class Tables {
    public class User {
        public static final String TABLE_NAME = "users";
        public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
        public static final String TYPE = "type";
    }

    public class Vehicle {
        public static final String TABLE_NAME = "vehicles";
        public static final String ID = "id";
        public static final String PRICE = "price";
        public static final String DESCRIPTION = "description";
        public static final String LOCALIZATION = "localization";
        public static final String PAYED = "payed";
        public static final String LOCKED = "locked";
    }

}