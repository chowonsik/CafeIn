<template>
  <swiper 
    :spaceBetween="30" 
    :centeredSlides="true" 
    :autoplay='{
      "delay": 2500,
      "disableOnInteraction": false
      }' 
    :pagination='{
      "clickable": true
      }' 
    class="mySwiper"
    style="height: 25vh"
  >
      <swiper-slide>
        <div class="gallbox">
          <q-img src="../../assets/image/cafeimage2.jpg"  />
          <p class="text-subtitle1">카페를 좋아하는 Cafe人</p>
        </div>
      </swiper-slide>
      <swiper-slide>
        <div class="gallbox">
          <q-img src="../../assets/image/cafeimage1.jpg"  />
          <p class="text-subtitle1">카페를 좋아하는 Cafe人</p>
        </div>
      </swiper-slide>
      <swiper-slide>
        <div class="gallbox">
          <q-img src="../../assets/image/cafeimage3.jpg"  />
          <p class="text-subtitle1">카페를 좋아하는 Cafe人</p>
        </div>
      </swiper-slide>
      <swiper-slide>
        <div class="gallbox">
          <q-img src="../../assets/image/cafeimage4.jpg"  />
          <p class="text-subtitle1">카페를 좋아하는 Cafe人</p>
        </div>
      </swiper-slide>
  </swiper>
</template>

<script>
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from 'swiper/vue';

// Import Swiper styles
import 'swiper/css';

import "swiper/css/pagination"
import "swiper/css/navigation"

// import Swiper core and required modules
import SwiperCore, {
  Autoplay,Pagination,Navigation
} from 'swiper';

// install Swiper modules
SwiperCore.use([Autoplay,Pagination,Navigation]);

export default {
  name: 'MainCarousel1',
  components: {
    Swiper,
    SwiperSlide,
  },
}
</script>

<style scoped>
.custom-caption {
  text-align: center;
  padding: 12px;
  color: white;
  background-color: rgba(0, 0, 0, .3);
}

.gallbox {
  position: relative;
  width: 100%;
  height: 100%; 
  overflow: hidden;
  }

.gallbox img {
  display: block; 
  height: 100%;
  }

.gallbox p {
  position: absolute; 
  /* bottom: 140px; */
  bottom: -16px; 
  left: 0; 
  width: 100%; 
  opacity: 0.8; 
  text-align: center;
  background-color: #eeeeee; 
  color: black; 
  font-size: 16px; 
  padding: 10px;
  }

</style>