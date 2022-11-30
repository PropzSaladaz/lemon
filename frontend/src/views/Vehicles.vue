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
                    <th > {{ vehicle.type }} </th>
                    <th > {{ vehicle.price }} </th>
                    <th class="button-column flex" > <button class="button is-primary is-outlined">Make Reservation</button> </th>
                </tr>
            </tbody>
        </table>
    </div>
</template>
  
<script>
import DatabaseService from '@/service/DatabaseService.js'
  
export default {
    name: 'HomeView',
    created(){
        this.vehicles = DatabaseService.getVehicles();
        console.log(this.vehicles);
    },
    data() {
        return {
            vehicles: null,
        }
    },
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

</style>