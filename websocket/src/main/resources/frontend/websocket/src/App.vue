<template>
  <div id="app">
    <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
    <HelloWorldUser1 msg="WebSocket User1"/>

    <HelloWorldUser2 msg="WebSocket User2"/>
  </div>
</template>

<script>
import HelloWorldUser1 from './components/HelloWorld_User1.vue'
import HelloWorldUser2 from './components/HelloWorld_User2.vue'

export default {
  name: 'App',
  components: {
    HelloWorldUser1,
    HelloWorldUser2
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
