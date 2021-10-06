<template>
  <div>
    <MainCarousel />
    <div class="bg-grey-3">
      <div class="row justify-around font-center items-center q-pt-sm" style="height: 6rem">
        <div>
          <q-avatar>
            <img src="../assets/image/coffeecup.png">
          </q-avatar>
          <p class="no-margin text-center">맛</p>
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
          <p class="no-margin text-center">분위기</p>
        </div>
      </div>
      <div class="row justify-around font-center items-center" style="height: 6rem">
        <div>
          <q-avatar>
            <img src="../assets/image/camera.png">
          </q-avatar>
          <p class="no-margin text-center">뷰</p>
        </div>
        <div>
          <q-avatar>
            <img src="../assets/image/service.png">
          </q-avatar>
          <p class="no-margin text-center">서비스</p>
        </div>
        <div>
          <q-avatar>
            <img src="../assets/image/space.png">
          </q-avatar>
          <p class="no-margin text-center">공간</p>
        </div>
      </div>
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