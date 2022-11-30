<template>
  <div class="home">
    <HelloWorld msg="Lemon is a distribution lorem in the theory of the black moon of "/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'


export default {
  name: 'HomeView',
  components: {
    HelloWorld,
  },
}
</script>
