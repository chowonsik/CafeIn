<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">내 주변</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <div>
      <KakaoMap />
    </div>
    <div>
      <NearCafeItem />
    </div>
  </div>
</template>

<script>
import KakaoMap from '../components/common/KakaoMap.vue'
import NearCafeItem from '../components/cafe/NearCafeItem.vue'
export default {
  name: "NearCafe",
  components: {
    KakaoMap,
    NearCafeItem,
  },
  methods: {
    goBack() {
      window.history.back()
    },
  }
}
</script>

<style>

</style>