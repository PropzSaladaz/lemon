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
        }
    },
    methods: {
        resetInputs() {
            this.email= '';
           
        },
        login: function(event) {
            let email = this.email;
            this.resetInputs();
            this.$emit('login', {email: email});
        },
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