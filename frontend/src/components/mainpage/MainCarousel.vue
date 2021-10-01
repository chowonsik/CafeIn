<template>
  <q-carousel
      arrows
      animated
      v-model="slide"
      height="25vh"
    >
      <q-carousel-slide name="first" img-src="../../assets/image/cafeimage2.jpg">
        <div class="absolute-bottom custom-caption">
          <div class="text-h4">First stop</div>
          <div class="text-subtitle1">Popular Cafe</div>
        </div>
      </q-carousel-slide>
      <q-carousel-slide name="second" img-src="../../assets/image/cafeimage1.jpg">
        <div class="absolute-bottom custom-caption">
          <div class="text-h4">Second stop</div>
          <div class="text-subtitle1">Popular Cafe</div>
        </div>
      </q-carousel-slide>
      <q-carousel-slide name="third" img-src="../../assets/image/cafeimage3.jpg">
        <div class="absolute-bottom custom-caption">
          <div class="text-h4">Third stop</div>
          <div class="text-subtitle1">Famous Cafe</div>
        </div>
      </q-carousel-slide>
      <q-carousel-slide name="fourth" img-src="../../assets/image/cafeimage4.jpg">
        <div class="absolute-bottom custom-caption">
          <div class="text-h4">Fourth stop</div>
          <div class="text-subtitle1">Famous Cafe</div>
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
      slide: ref('first')
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