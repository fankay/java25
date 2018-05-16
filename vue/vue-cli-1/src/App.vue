<template>
  <div id="app">
    <router-link to="/">Home</router-link>
    <router-link to="/news">News</router-link>
    <router-link to="/about">About</router-link>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
 
}
</style>
