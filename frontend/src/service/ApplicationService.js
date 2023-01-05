import axios from "axios";

const serverHostname = "https://localhost:8443";

let user_public_key = "NOPUBLICKEYSET";

const apiClient = axios.create({
    baseURL: serverHostname,
    withCredentials: false,
    headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
    },
});

export default {
    getAvailableVehicles() {
        return apiClient.get("/vehicle");
    },

    getReservedVehicles() {
        return apiClient.post("/vehicle/reserved", {key: user_public_key});
    },

    requestReservation(id){
        console.log(`Sending Reservation request with user public key: ${user_public_key}`);
        return apiClient.post("/vehicle/reserve/" + id, {key: user_public_key});
    },

    cancelReservation(id){
        return apiClient.post("/vehicle/unlock/" + id);
    },

    async login(_email) {
        return await apiClient.post("/login/" + _email)
            .then(response => {
                user_public_key = response.data.publicKey;
                console.log(`Successfuly logged in -> New user public key: ${user_public_key}`);
                return response.data;
            })
            .catch(error => {
                console.log(error)
            });
    },
} 