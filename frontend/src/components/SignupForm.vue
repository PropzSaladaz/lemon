<template>
    <div class="form-container">
      <div class="form-input">
        <div class="field">
          <label class="label">Email</label>
          <div class="control has-icons-left has-icons-right">
            <input class="input" type="email" placeholder="Text input" v-model="email">
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
        <button class="button is-link" @click="signup">Signup</button>
      </div>
    </div>
  </div>
</div>
</template>
<script>
export default {
    name: 'SignupForm',
    data() {
        return {
            email: ''
        }
    },
    methods: {
        resetInputs() {
            this.email= '';
           
        },
        signup: function() {
            let email = this.email;
            console.log();
            this.resetInputs();
            this.$emit('signup', { email: email});
        },
    }
}
</script>
