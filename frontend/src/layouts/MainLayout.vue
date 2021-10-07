<template>
  <q-layout view="lHr LpR lFr">

    <Header />

    <q-page-container>
      <router-view />
    </q-page-container>

    <HomeFooter />

  </q-layout>
</template>


<script>
import { defineComponent } from 'vue'
import Header from '../components/common/Header.vue'
import HomeFooter from '../components/common/HomeFooter.vue'

export default defineComponent({
  name: 'MainLayout',
  components: {
    Header,
    HomeFooter,
  }
})
</script>
