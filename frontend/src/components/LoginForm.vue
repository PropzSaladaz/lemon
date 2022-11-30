<template>
    <div class="form-container">
        <div class="form-input">
            <div class="field">
                <label class="label">Email</label>
                <div class="control has-icons-left has-icons-right">
                    <input class="input " type="email" placeholder="Text input" v-model="email">
                    <span class="icon is-small is-left">
                        <i class="fas fa-user"></i>
                            </span>
                            <span class="icon is-small is-right">
                        <i class="fas fa-check"></i>
                    </span>
                </div>
            </div>

            <div class="field">
                <label class="label">Password</label>
                <div class="control has-icons-left has-icons-right">
                    <input class="input is-danger" type="password" placeholder="Email input" v-model="password">
                        <span class="icon is-small is-left">
                        <i class="fas fa-envelope"></i>
                            </span>
                            <span class="icon is-small is-right">
                        <i class="fas fa-exclamation-triangle"></i>
                    </span>
                </div>
                <p class="help is-danger" v-if="invalidAccount">Invalid account</p>
            </div>

            <div class="field">
                <label class="label">User type</label>
                <div class="control">
                    <div class="select">
                    <select @change="onSelectUserType($event)">
                        <option :value="inputDropDown.customer">Customer</option>
                        <option :value="inputDropDown.employer">Employer</option>
                    </select>
                    </div>
                </div>
            </div>

            <div class="field">
                <div class="control">
                    <button class="button is-link" @click="login">Login</button>
                </div>
            </div>
        </div>
    </div>


</template>

<script>
export default {
    name: 'LoginForm',
    props: ['invalidAccount'],
    data() {
        return {
            email: '',
            password: '',
            type: '',
            inputDropDown: {
                customer: "Costumer",
                employer: "Employer"
            }
        }
    },
    methods: {
        resetInputs() {
            this.password = '';
            this.type = '';
        },
        login: function(event) {
            let email = this.email;
            let password = this.password;
            let employer = this.employer;
            console.log();
            this.resetInputs();
            this.$emit('login', { email: email, password: password, employer: employer});
        },
        onSelectUserType: function(event) {
            this.type = event.target.value;
        }
    }
}
</script>

<style>
.form-container {
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content:center;

}
</style>