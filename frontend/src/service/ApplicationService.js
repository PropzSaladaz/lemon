import axios from "axios";

const serverHostname = "https://localhost:8443";

let user_public_key = 0;

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

    getLockedVehicles() {
        return apiClient.get("/vehicle/reserved");
    },

    requestReservation(id){
        return apiClient.post("/vehicle/reserve/" + id, {key: user_public_key});
    },

    cancelReservation(id){
        return apiClient.post("/vehicle/unlock/" + id);
    },
    login(_email) {
        return apiClient.post("/login/" + _email);
    },
} 