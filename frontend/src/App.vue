<template>
  <nav class="navbar" role="navigation" aria-label="main navigation">
    <div class="navbar-brand">
      <router-link class="navbar-item" @click="hideForms" to="/">
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

        <a class="navbar-item" @click="$router.push('/vehicles')" v-if="(isCustomer === true && isEmployee === false && loggedIn() )">
          Vehicles
        </a>

        <a class="navbar-item"  @click="$router.push('/localization')" v-if="(isEmployee === true && isCustomer === false && loggedIn())">
          Localization
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
            <a class="button is-primary" @click="showSignUpPage">
              <strong>Sign up</strong>
            </a>
            <a class="button is-light" @click="showLoginForm">
              Log in
            </a>
          </div>
        </div>
      </div>
    </div>
  </nav>

  <WelcomePage v-if="showWelcomePage"/>
  <LoginForm v-else-if="loginFormVisible" @login="onLogin" :invalidAccount="invalidAccount"/>
  <SignupForm v-else-if="signUpFromVisible" @login="onSignup" :invalidAccount="invalidAccount"/>
  <router-view v-else/>

</template>

<script>
import WelcomePage from '@/views/WelcomePage.vue'
import LoginForm from '@/components/LoginForm.vue'
import SignupForm from '@/components/SignupForm.vue'
import DatabaseService from '@/service/DatabaseService.js'

export default {
  name: 'App',
  components: {
    LoginForm,
    SignupForm,
    WelcomePage,
  },
  data() {
    return {
      email: '', // DEBUG
      loginFormVisible: false,
      signUpFromVisible: false,
      invalidAccount: false,
      isEmployee: false,
      isCustomer:false,

    }
  },
  methods: {
    loggedIn(){
      return this.email != '';
    },
    showLoginForm() {
      this.loginFormVisible = true;
    },
    showSignUpPage() {
      this.signUpFromVisible = true;
    },

    onLogin(event) {
      var result = DatabaseService.validateLogin(event.email, event.password, event.employer);
      if ( result == "Employer") {
        this.isEmployee = true;
        this.isCustomer = false;
        this.email = event.email;
        this.loginFormVisible = false;
      }else if(result == "Customer"){
        this.isCustomer = true;
        this.isEmployee = false;
        this.email = event.email;
        this.loginFormVisible = false;
      }
      else {
        console.log(event.email, event.password);
        this.invalidAccount = true;
        alert("Account doesn't exist");
      }
    },
    onSignup(event) {
      if (!DatabaseService.isRegistered(event.email) && event.password == event.passwordRep) {
        alert(`Account already exists with email ${event.email}`);
        this.invalidAccount = true;
      }
      else {
        createUser(event.email, event.password, event.employer);
        this.signUpFromVisible = false;
      }
    },
    hideForms() {
      this.loginFormVisible = false;
    }
  },
  computed: {
    showWelcomePage(){ 
      return !this.email && !this.loginFormVisible && !this.signUpFromVisible;
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
