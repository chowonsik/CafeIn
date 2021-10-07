<template>
  <q-layout>
    <q-page-container>
      <router-view />
    </q-page-container>
    <NearFooter />
  </q-layout>
</template>

<script>
import { defineComponent } from 'vue'
import NearFooter from '../components/common/NearFooter.vue'
export default defineComponent ({
  name: 'NearLayout',
  components: {
    NearFooter
  }
})
</script>

<style>

</style>