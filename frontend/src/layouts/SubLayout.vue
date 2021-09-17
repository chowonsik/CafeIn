<template>
  <q-layout view="lHr LpR lFr">

    <q-page-container>
      <router-view />
    </q-page-container>

    <Footer />

  </q-layout>
</template>

<script>
import { defineComponent } from 'vue'
import Footer from '../components/common/Footer.vue'

export default defineComponent({
  name: 'MainLayout',
  components: {
    Footer,
  }
})
</script>

<style>

</style>