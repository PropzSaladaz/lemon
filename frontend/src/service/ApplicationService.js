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

    getLockedVehicles() {
        return apiClient.get("/vehicle/reserved");
    },

    requestReservation(id){
        return apiClient.post("/vehicle/reserve/" + id, {key: user_public_key});
    },

    cancelReservation(id){
        return apiClient.post("/vehicle/unlock/" + id, {key: user_public_key});
    },

    async login(_email) {
        return await apiClient.post("/login/" + _email)
            .then(response => {
                user_public_key = response.data.publicKey;
                return response.data;
            })
            .catch(error => {
                console.log(error)
            });
    },
} 