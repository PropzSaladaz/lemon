<template>
     <div class="container is-max-desktop table-container">
        <table class="table is-hoverable is-fullwidth">
            <thead>
                <tr>
                    <th>Vehicle</th>
                    <th>Price</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="vehicle in vehicles" key="vehicle.id">
                    <th class="normal-text"> {{ vehicle.description }} </th>
                    <th class="normal-text"> {{ vehicle.price }} </th>
                    <th class="button-column flex" > <button class="button is-primary is-outlined" @click="requestReservation(vehicle.id)">Make Reservation</button> </th>
                </tr>
            </tbody>
        </table>
    </div>
</template>
  
<script>
import ApplicationService from '@/service/ApplicationService.js'
  
export default {
    name: 'HomeView',
    created(){
       this.getVehicles();
    },
    data() {
        return {
            vehicles: null,
        }
    },
    methods: {
        requestReservation(id) {
            console.log("aqui");
            ApplicationService.requestReservation(id)
            .then((response) => {
                this.getVehicles();
            })
            .catch((error) => {
                console.log(error);
            });
        },

        getVehicles() {
            ApplicationService.getAvailableVehicles()
            .then((response) => {
                this.vehicles = response.data;
            })
            .catch((error) => {
                console.log(error);
            });
        }
    }
}
</script>

<style>
.table-container {
    display: flex;
    justify-content: center;
}
table { 
    max-width: 600px;
}
.flex {
    display: flex;
    align-items: flex-end;
    justify-content: flex-end;
}
.normal-text {
    font-weight: normal;
}

</style>