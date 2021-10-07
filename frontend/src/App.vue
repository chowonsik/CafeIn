<template>
  <router-view />
</template>
<script>
import { defineComponent } from 'vue';
import { createNamespacedHelpers } from 'vuex'
const { mapState, mapActions } = createNamespacedHelpers("kakaomap")

export default defineComponent({
  name: 'App',
  computed: {
    ...mapState(['latitude', 'longitude'])
  },
  mounted() {
    this.geoFind()
  },
  methods: {
    ...mapActions(['geoFind'])
  }
})
</script>

<style>
body{ font-family: 'Noto Sans KR', 'sans-serif'; }
</style>