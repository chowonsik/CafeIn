<template>
  <q-layout>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">찜 카페</q-toolbar-title>
          <q-avatar @click="$router.push({ path: `/tag/taste`})">
            <img src="../assets/image/touch.png">
          </q-avatar>
      </q-toolbar>
    </q-header>
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
  },
  methods: {
    goBack() {
      window.history.back()
    },
  }
})
</script>

<style>

</style>