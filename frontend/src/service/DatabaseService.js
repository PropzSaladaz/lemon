import axios from "axios";

const serverHostname = "http://localhost:8080/";

const users = [
    {
        email: "admin@gmail.com",
        password: "1234",
        type: "Employer",
    },
    {
        email: "cust@gmail.com",
        password: "1234",
        type: "Customer",
    },
]

const vehicles = [
    {
        id: 1,
        type: "Scooter",
        price: 10,
    },
    {
        id: 2,
        type: "Scooter",
        price: 10,
    },
    {
        id: 3,
        type: "Bycicle",
        price: 5,
    },
]

const DataBase = {
    users: users,
    vehicles: vehicles
}

const apiClient = axios.create({
    baseURL: serverHostname,
    withCredentials: false,
    headers: {
      Accept: "application/json",
      "Content-Type": "application/json",
    },
});

export default {
    emailExists(email) {
        for (var user of users) {
            if (user.email === email) return true;
        }
        return false;
    },
    validateLogin(email, password) {
        for (var user of users) {
            if ((user.email == email) && (user.password == password)) {
                console.log("here");
                return true ;
            }
        }
        return false;
    },
    getUserType(email) {
        for (var user of users) {
            if (user.email === email) return user.type;
        }
    },

    getVehicles() {
        return apiClient.get("/vehicle");
    },

    requestReservation(id){
        return apiClient.post("/vehicle/lock/" + id);
    }
}