<template>
  <div>
    <main-carousel />
    <div>
      <div class="row justify-around font-center items-center" style="height: 6rem">
        <div>
          <q-avatar>
            <img src="../assets/image/coffeecup.png">
          </q-avatar>
          <p class="no-margin text-center">커피</p>
        </div>
        <div>
          <q-avatar>
            <img src="../assets/image/openbook.png">
          </q-avatar>
          <p class="no-margin text-center">공부</p>
        </div>
        <div>
          <q-avatar>
            <img src="../assets/image/love.png">
          </q-avatar>
          <p class="no-margin text-center">데이트</p>
        </div>
        <div>
          <q-avatar>
            <img src="../assets/image/camera.png">
          </q-avatar>
          <p class="no-margin text-center">사진</p>
        </div>
      </div>
    </div>
    <div style="margin-bottom: 0.5rem">
      <q-img
          src="../assets/image/cafeimage5.jpg"
          :ratio="16/9"
          style="max-height: 220px;"
      >
      <div class="absolute-full custom-caption">
        <div class="text-h5">카페 큐레이션</div>
        <div class="text-subtitle1">태그를 통해 카페 추천을 받으세요</div>
      </div>
      </q-img>
    </div>
    <div>
      <q-img
        src="../assets/image/coffeeimage1.jpg"
        :ratio="16/9"
        style="max-height: 220px;"
      >
      <div class="absolute-full custom-caption">
        <div class="text-h4">나에게 맞는</div>
        <div class="text-h4">카페 추천</div>
      </div>
      </q-img>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';
import MainCarousel from '../components/mainpage/MainCarousel.vue'
import { createNamespacedHelpers } from 'vuex'
const { mapState, mapActions } = createNamespacedHelpers("kakaomap")

export default defineComponent({
  name: 'PageIndex',
  components: {
    MainCarousel
  },
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

<style lang="sass" scoped>
.custom-caption
  padding: 12px
  color: white
  background-color: rgba(0, 0, 0, .3)
</style>