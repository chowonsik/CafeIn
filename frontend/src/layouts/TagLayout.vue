<template>
  <q-layout>
    <Header />
    <q-page-container>
      <router-view />
    </q-page-container>
    <TagFooter />
  </q-layout>
</template>

<script>
import { defineComponent } from 'vue'
import Header from '../components/common/Header.vue'
import TagFooter from '../components/common/TagFooter.vue'
export default defineComponent ({
  name: 'TagLayout',
  components: {
    TagFooter,
    Header,
  }
})
</script>

<style>

</style>