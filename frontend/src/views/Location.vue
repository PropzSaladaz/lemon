<template>
  
</template>
  
<script>
import DatabaseService from '@/service/DatabaseService.js'
  
export default {
    name: 'HomeView',
    created(){
        //console.log('Params: ', this.$route.params);
    },
    data() {
        return {
            vehicles: null,
        }
    },
    methods: {
        requestReservation(id) {
            console.log("aqui");
            DatabaseService.requestReservation(id)
            .then((response) => {
                this.getVehicles();
            })
            .catch((error) => {
                console.log(error);
            });
        },

        getVehicles() {
            DatabaseService.getLockedVehicles()
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