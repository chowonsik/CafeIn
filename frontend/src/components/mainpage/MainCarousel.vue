<template>
  <q-carousel
      arrows
      animated
      infinite
      :autoplay="autoplay"
      @mouseenter="autoplay = false"
      @mouseleave="autoplay = true"
      v-model="slide"
      height="25vh"
    >
      <q-carousel-slide :name="1" img-src="../../assets/image/cafeimage2.jpg">
        <div class="absolute-bottom custom-caption">
          <div class="text-subtitle1">카페를 좋아하는 Cafe人</div>
        </div>
      </q-carousel-slide>
      <q-carousel-slide :name="2" img-src="../../assets/image/cafeimage1.jpg">
        <div class="absolute-bottom custom-caption">
          <div class="text-subtitle1">카페를 좋아하는 Cafe人</div>
        </div>
      </q-carousel-slide>
      <q-carousel-slide :name="3" img-src="../../assets/image/cafeimage3.jpg">
        <div class="absolute-bottom custom-caption">
          <div class="text-subtitle1">카페를 좋아하는 Cafe人</div>
        </div>
      </q-carousel-slide>
      <q-carousel-slide :name="4" img-src="../../assets/image/cafeimage4.jpg">
        <div class="absolute-bottom custom-caption">
          <div class="text-subtitle1">카페를 좋아하는 Cafe人</div>
        </div>
      </q-carousel-slide>
    </q-carousel>
</template>

<script>
import { ref } from 'vue'
export default {
  name: 'MainCarousel',
  setup () {
    return {
      slide: ref('first'),
      autoplay: ref(true)
    }
  }

}
</script>

<style lang="sass" scoped>
.custom-caption
  text-align: center
  padding: 12px
  color: white
  background-color: rgba(0, 0, 0, .3)
</style>