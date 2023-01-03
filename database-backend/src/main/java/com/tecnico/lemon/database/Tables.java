package com.tecnico.lemon.database;

public class Tables {
    public class User {
        public static final String TABLE_NAME = "users";
        public static final String EMAIL = "email";
        public static final String PUBLIC_KEY = "key";
        public static final String TYPE = "type";
    }

    public class Vehicle {
        public static final String TABLE_NAME = "vehicles";
        public static final String VEHICLE_ID = "id";
        public static final String PRICE = "price";
        public static final String DESCRIPTION = "description";
        public static final String LOCALIZATION = "localization";
        public static final String PAYED = "payed";
        public static final String LOCKED = "locked";
    }

    public class UserReservations {
        public static final String TABLE_NAME = "user_reservations";
        public static final String PUBLIC_KEY = "public_key";
        public static final String RESERVATION_ID = "reservation_id";
    }

    public class Reservations {
        public static final String TABLE_NAME = "reservation";
        public static final String RESERVATION_ID = "reservation_id";
        public static final String VEHICLE_ID = "id";
        public static final String LOCALIZATION = "localization";
        public static final String PRICE = "price";
        public static final String DESCRIPTION = "description";
    }

}