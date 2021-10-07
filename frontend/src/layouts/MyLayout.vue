<template>
  <q-layout>
    <q-page-container>
      <router-view />
    </q-page-container>
    <MyFooter />
  </q-layout>
</template>

<script>
import { defineComponent } from 'vue'
import MyFooter from '../components/common/MyFooter.vue'
export default defineComponent ({
  name: 'MyLayout',
  components: {
    MyFooter,
  }
})
</script>

<style>

</style>