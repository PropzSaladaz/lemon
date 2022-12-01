import axios from "axios";

const serverHostname = "http://localhost:8080/";

let user_id = 0;

const users = [
    {
        id: 0,
        email: "admin@gmail.com",
        password: "1234",
        type: "Employer",
    },
    {
        id: 1,
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
    validateLogin(email, password, employer) {
        for (var user of users) {
            console.log(employer)
            if ((user.email == email) && (user.password == password) && (user.employer == employer)) {
                console.log("here");
                if (user.type == "Employer"){
                    console.log(user.type);
                    return "Employer" ;
                }
                else return "Customer"
            }
        }
        return "";
    },
    isRegistered(email) {
        for (var user of users) {
            console.log(employer)
            if (user.email == email) {
                return false;
            }
        }
        return true;
    },
    getUserType(email) {
        for (var user of users) {
            if (user.email === email) return user.type;
        }
    },

    getAvailableVehicles() {
        return apiClient.get("/vehicle");
    },

    getLockedVehicles() {
        return apiClient.get("/vehicle/locked");
    },

    requestReservation(id){
        return apiClient.post("/vehicle/lock/" + id);
    },

    createUser(_email, _password, _type) {
        apiClient.post("/user/signup/_signup" + 
            JSON.stringify({
                email: _email,
                password: _password,
                type: _type,
            })
        );
    },
    
    isRegistered(_email) {
        return apiClient.get("/user/lookup/" + _email);
    },
}