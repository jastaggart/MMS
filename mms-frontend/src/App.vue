<template>
  <div>
    <keep-alive>
      <Navbar />
    </keep-alive>
    <router-view></router-view>
  </div>
</template>


<script>
import Navbar from "./components/Navbar.vue";
export default {
  components: { Navbar },
  name: "app"
};
</script>

<style scoped>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

body {
    margin:0;
    background: #eeee
}
</style>
