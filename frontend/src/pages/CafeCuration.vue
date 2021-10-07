<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding" style="marginRight: 1.2rem">카페 큐레이션</q-toolbar-title>
      </q-toolbar>
    </q-header>

    <q-item-label header class="text-h6 text-center text-weight-bold text-primary" style="marginTop: 1rem">{{ nickname }}님 취향에 맞는 카페 큐레이션</q-item-label>
    

    <q-item-label caption class="text-center" style="marginTop: 1rem">{{ nickname }}님의 관심 카페를 기반으로 추천한 카페입니다.</q-item-label>
    <swiper :effect="'coverflow'" :grabCursor="true" :centeredSlides="true" :slidesPerView="'auto'" :coverflowEffect='{
      "rotate": 50,
      "stretch": 0,
      "depth": 100,
      "modifier": 1,
      "slideShadows": true
      }' :pagination="true" class="mySwiper"
    >
      <swiper-slide v-for="cafe in curationList" :key="cafe.cafeName" @click="$router.push({ path: `/cafes/${cafe.cafeId}`})">
        <q-img :src="cafe.cafeImgUrl" :ratio="1" />
          <q-card class="my-card" flat >
            <q-card-section class="row justify-center">
              <q-banner rounded class="bg-primary text-white text-center" style="paddingTop: 0; paddingBottom: 0">
                {{ parseFloat(cafe.cafeDistance).toFixed(1) }} km거리에 있습니다.
              </q-banner>
            </q-card-section>

            <q-card-section class="text-center">
                <div class="text-h6 text-bold">{{ cafe.cafeName }}</div>
              <div class="text-subtitle2">{{ cutAddress(cafe.cafeAddress) }}</div>
            </q-card-section>
            <q-card-section class="text-center" style="paddingTop: 0">
              <q-icon name="favorite" class="text-negative" /><span style="marginRight: 0.5rem">{{ cafe.bookmarkCnt }}</span>
              <q-icon name="star" class="text-yellow"/><span style="marginRight: 0.5rem">{{ cafe.cafeAvgScore.toFixed(1) }}</span>
              <q-icon name="edit_note" class="text-primary" /><span style="marginRight: 0.5rem">{{ cafe.reviewCnt }}</span>
            </q-card-section>

            <q-separator />
          </q-card>
      </swiper-slide>
    </swiper>
  </div>
</template>

<script>
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from 'swiper/vue';

// Import Swiper styles
import 'swiper/css';

import "swiper/css/effect-coverflow"
import "swiper/css/pagination"

import { getCuration } from '../api/curation'
import { createNamespacedHelpers } from 'vuex'
import { profileUser } from '../api/auth'
const { mapState, mapActions } = createNamespacedHelpers("kakaomap")


// import Swiper core and required modules
import SwiperCore, {
  EffectCoverflow,Pagination
} from 'swiper';

// install Swiper modules
SwiperCore.use([EffectCoverflow,Pagination]);


export default {
  name: 'CafeCuration',
  components: {
    Swiper,
    SwiperSlide,
  },
  data() {
    return {
      curationList: [],
      nickname: '',
    };
  },
  methods: {
    ...mapActions(['geoFind']),
    goBack() {
      window.history.back()
    },
    cutAddress(cafeAddress) {
      const strArray = cafeAddress.split(' ')
      return strArray[0] + ' ' + strArray[1]
    },
    async getCurationCafe() {
      try {
        const { data } = await getCuration(this.latitude, this.longitude)
        this.curationList = data.result
      } catch (error) {
        console.error(error)
      }
    },
    async getProfile() {
      try {
        const { data } = await profileUser()
        // console.log(data)
        this.nickname = data.result.nickname
      } catch(error) {
        console.error(error)
      }
    }
  },
  computed: {
    ...mapState(['latitude', 'longitude'])
  },

  created() {
    this.getCurationCafe()
    this.getProfile()
  }
  
}
</script>

<style scoped>
#app { height: 100% }
html,
body {
  position: relative;
  height: 100%;
}

body {
  background: #eee;
  font-family: Helvetica Neue, Helvetica, Arial, sans-serif;
  font-size: 14px;
  color: #000;
  margin: 0;
  padding: 0;
}

.swiper {
  width: 100%;
  height: 500px;
  padding-top: 0.5rem;
  padding-bottom: 50px;
}

.swiper-slide {
  background-position: center;
  background-size: cover;
  width: 250px;
  height: 250px;
}

.swiper-slide img {
  display: block;
  width: 100%;
}

</style>