<template>
  <q-header reveal bordered class="bg-white text-white">
    <q-toolbar>
      <q-toolbar-title class="row justify-between items-center">
        <q-btn flat>
          <q-avatar square width="100px" height="65px">
            <img src="../../assets/image/cafeinLogo.png">
          </q-avatar>
        </q-btn>
        <div hidden class="col-gutter-none content-center">
          <q-avatar color="grey" size="l" text-color="white" icon="search" />
        </div>
      </q-toolbar-title>
    </q-toolbar>
  </q-header>
</template>

<script>
export default {
  name: 'Header',
}
</script>

<style>

</style>