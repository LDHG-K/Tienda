<template>
  <div id="app">
    <headerQuda></headerQuda>  
    <Registro></Registro>
  </div>
</template>

<script>
import headerQuda from './components/HeaderQuda.vue'
import Registro from './components/Registro.vue'
export default {
  name: 'App',
  components: {
    headerQuda,
    Registro
  }
}
</script>

<style >

</style>
