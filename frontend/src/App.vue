<template>
  <nav class="navbar" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
      <router-link class="navbar-item" to="/">
        <span>
          <img src="https://d29fhpw069ctt2.cloudfront.net/icon/image/85173/preview.svg" width="112" height="28">
          <h1>Lemon</h1>
        </span>

      </router-link>
      <a role="button" class="navbar-burger" aria-label="menu" aria-expanded="false" data-target="navbarBasicExample">
      </a>
    </div>

    <div id="navbarBasicExample" class="navbar-menu">
      <div class="navbar-start" v-if="loggedIn()">
        <div>

        </div>
        <a class="navbar-item" @click="$router.push('/')">
          Home
        </a>

        <a class="navbar-item" @click="$router.push('/user/vehicles')" v-if="(isCustomer === true && isEmployee === false && loggedIn() )">
          Vehicles
        </a>

        <a class="navbar-item"  @click="$router.push('/user/localization')" v-if="(isEmployee === true && isCustomer === false && loggedIn())">
          Localization
        </a>

        <a class="navbar-item" @click="$router.push('/user/reservation')" v-if="(isCustomer === true && isEmployee === false && loggedIn() )">
          My Reservations
        </a>

        
        <div class="navbar-item has-dropdown is-hoverable">
          <a class="navbar-link">
            More
          </a>

          <div class="navbar-dropdown">
            <a class="navbar-item">
              About
            </a>
            <a class="navbar-item">
              Jobs
            </a>
            <a class="navbar-item">
              Contact
            </a>
            <hr class="navbar-divider">
            <a class="navbar-item">
              Report an issue
            </a>
          </div>
        </div>
      </div>

      <div class="navbar-end" v-if="!loggedIn()">
        <div class="navbar-item">
          <div class="buttons">
            <a class="button is-light" @click="gotoLogin">
              Log in
            </a>
          </div>
        </div>
      </div>
    </div>
  </nav>

  <router-view @login="onLogin" :invalidAccount="invalidAccount" :success="showMessageSuccess" :failure="showMessageFailure"/>

</template>

<script>
import ApplicationService from '@/service/ApplicationService.js'

export default {
  name: 'App',
  data() {
    return {
      email: '', // DEBUG
      invalidAccount: false,
      isEmployee: false,
      isCustomer:false,
      showMessageSuccess: false,
      showMessageFailure: false,

    }
  },
  methods: {
    showNotification(type) {
      if (type === "success") this.showMessageSuccess = true;
      else this.showMessageFailure = true;
      setTimeout(()=> {
        this.showMessageSuccess = false;
        this.showMessageFailure = false;
      }, 3000)
    },
    loggedIn(){
      return this.email != '';
    },
    gotoLogin() {
      this.$router.push('/user/login');
    },
    async onLogin(event) {
      await ApplicationService.login(event.email)
        .then((data) => {
          console.log(data);
          console.log("here", data.signup);
          let customerType = data.customerType;
          if (data.signup) {
            if (customerType === ""){
              console.log("failure");
              this.showNotification("failure");
            }
            else {
              console.log("success");
              this.showNotification("success");
            }
          }
          else{
            
            console.log(customerType);
            if (customerType === "Employer") {
              this.isEmployee = true;
              this.isCustomer = false;
              this.email = event.email;
              this.$router.push('/user/localization')
            }
            else if(customerType === "Customer"){
              console.log("customer here i am");
              this.isCustomer = true;
              this.isEmployee = false;
              this.email = event.email;
              this.$router.push('/user/vehicles');
            }
            else {
              console.log(event.email, event.password);
              this.invalidAccount = true;
              alert("Account doesn't exist");
            }
          }

        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  computed: {
    showWelcomePage(){ 
      return !this.email && !this.loginFormVisible;
    },
    invalidAccount() {
      return this.invalidAccount;
    }
  }
}
</script>

<style>
@import '~bulma';

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
