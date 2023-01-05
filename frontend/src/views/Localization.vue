<template>
     <div class="container is-max-desktop table-container">
        <table class="table is-hoverable is-fullwidth">
        
                <tr>
                    <th>Vehicle</th>
                    <th>Price</th>
                    <th>Location</th>
                </tr>
            
        
                <tr v-for="vehicle in vehicles" key="vehicle.id">
                    <td>  {{ vehicle.title }} </td>
                    <td>  {{ vehicle.price }} </td>
                    <td><p>
                        <iframe :src="vehicle.location"
                        width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                        </p>
                    </td>   
                </tr>
            
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
            ApplicationService.getReservedVehicles()
            .then((response) => {
                this.vehicles = response.data;
                console.log(this.vehicles);
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