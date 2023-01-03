import axios from "axios";

const serverHostname = "https://localhost:8443";

let user_id = 0;

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
        return apiClient.get("/vehicle/locked");
    },

    requestReservation(id){
        return apiClient.post("/vehicle/lock/" + id);
    },

    cancelReservation(id){
        return apiClient.post("/vehicle/unlock/" + id);
    },
    login(_email) {
        return apiClient.post("/login/" + _email);
    },
} 