<template>
    

     <section class="form-register">
        <h4>¡Bienvenido!</h4>
        <input class="controls" type="text" name="" id="usuario" placeholder="Crea tu usuario">
        <input class="controls" type="password" name="correo" id="correo" placeholder="Ingrese su Contraseña">
        <input class="controls" type="text" name="nombres" id="nombres" placeholder="Ingrese su Nombre">
        <input class="controls" type="text" name="apellidos" id="apellidos" placeholder="Ingrese su Apellido">
        <input class="controls" type="email" name="correo" id="correo" placeholder="Ingrese su Correo">
        
        <p>Estoy de acuerdo con <a href="#">Terminos y Condiciones</a></p>
        <input class="botons" type="submit" value="Registrarse">
        <p><a href="#">¡Ya tengo una Cuenta!</a></p>
      </section>
  
    

</template>

<script>

export default {
    name: 'registro'
}
</script>

<style >
@import '../assets/Css/Registro.css';
</style>