<template>
  <q-header reveal bordered class="bg-white text-white">
    <q-toolbar v-if="tagName === 'taste'">
      <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
      <q-toolbar-title class="text-black text-weight-bold text-center no-padding">맛 태그 추천</q-toolbar-title>
        <q-avatar @click="$router.push({ path: `/tag/taste`})">
          <img src="../../assets/image/coffeecup.png">
        </q-avatar>
    </q-toolbar>
    <q-toolbar v-else-if="tagName === 'study'">
      <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
      <q-toolbar-title class="text-black text-weight-bold text-center no-padding">공부 태그 추천</q-toolbar-title>
        <q-avatar @click="$router.push({ path: `/tag/taste`})">
          <img src="../../assets/image/openbook.png">
        </q-avatar>
    </q-toolbar>
    <q-toolbar v-else-if="tagName === 'mood'">
      <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
      <q-toolbar-title class="text-black text-weight-bold text-center no-padding">분위기 태그 추천</q-toolbar-title>
        <q-avatar @click="$router.push({ path: `/tag/taste`})">
          <img src="../../assets/image/love.png">
        </q-avatar>
    </q-toolbar>
    <q-toolbar v-else-if="tagName === 'view'">
      <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
      <q-toolbar-title class="text-black text-weight-bold text-center no-padding">뷰 태그 추천</q-toolbar-title>
        <q-avatar @click="$router.push({ path: `/tag/taste`})">
          <img src="../../assets/image/camera.png">
        </q-avatar>
    </q-toolbar>
    <q-toolbar v-else-if="tagName === 'nice'">
      <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
      <q-toolbar-title class="text-black text-weight-bold text-center no-padding">서비스 태그 추천</q-toolbar-title>
        <q-avatar @click="$router.push({ path: `/tag/taste`})">
          <img src="../../assets/image/service.png">
        </q-avatar>
    </q-toolbar>
    <q-toolbar v-else-if="tagName === 'wide'">
      <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
      <q-toolbar-title class="text-black text-weight-bold text-center no-padding">공간 태그 추천</q-toolbar-title>
        <q-avatar @click="$router.push({ path: `/tag/taste`})">
          <img src="../../assets/image/space.png">
        </q-avatar>
    </q-toolbar>
  </q-header>
</template>

<script>
export default {
  name: 'TagHeader',
  data() {
    return {
      tagName: this.$route.params.tagname
    }
  },
  methods: {
    goBack() {
      window.history.back()
    },
  },
}
</script>

<style>

</style>