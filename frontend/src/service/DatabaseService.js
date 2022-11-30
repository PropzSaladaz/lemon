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
        return vehicles;
    }
}