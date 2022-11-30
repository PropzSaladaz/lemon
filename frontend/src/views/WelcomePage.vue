<template>
    <div class="Welcome!">
      <h1>{{ msg }}</h1>
      <img src="https://www.creativefabrica.com/wp-content/uploads/2020/09/11/Lemon-logo-icon-vector-fresh-lemonade-Graphics-5408610-1-1-580x387.jpg" alt="">
      <h2>Welcome to Lemon!</h2>
    </div>
  </template>
  
  <script>
  export default {
    name: 'Please log in or sign up!',
    props: {
      msg: String
    }
  }
  </script>
  
  <!-- Add "scoped" attribute to limit CSS to this component only -->
  <style scoped>
  h3 {
    margin: 40px 0 0;
  }
  ul {
    list-style-type: none;
    padding: 0;
  }
  li {
    display: inline-block;
    margin: 0 10px;
  }
  a {
    color: #42b983;
  }
  </style>