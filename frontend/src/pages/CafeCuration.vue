<template>
  <q-header reveal bordered class="bg-white text-white">
    <q-toolbar>
      <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
      <q-toolbar-title class="text-black text-weight-bold text-center no-padding" style="marginRight: 1.2rem">카페 큐레이션</q-toolbar-title>
    </q-toolbar>
  </q-header>

  <q-item-label header class="text-h6 text-center text-weight-bold text-primary" style="marginTop: 1rem">코코님의 취향에 맞는 카페 큐레이션</q-item-label>
  

  <q-item-label caption class="text-center" style="marginTop: 1rem">코코님의 관심 카페를 기반으로 추천한 카페입니다.</q-item-label>
  <swiper :effect="'coverflow'" :grabCursor="true" :centeredSlides="true" :slidesPerView="'auto'" :coverflowEffect='{
    "rotate": 50,
    "stretch": 0,
    "depth": 100,
    "modifier": 1,
    "slideShadows": true
    }' :pagination="true" class="mySwiper"
  >
    <swiper-slide v-for="cafe in cafeList" :key="cafe.cafeName">
      <q-img :src="cafe.img" :ratio="1" />
        <q-card class="my-card" flat >
          <q-card-section class="row justify-center">
            <q-banner rounded class="bg-primary text-white text-center" style="paddingTop: 0; paddingBottom: 0">
              {{ cafe.tag }}
            </q-banner>
          </q-card-section>

          <q-card-section class="text-center">
              <div class="text-h6 text-bold">{{ cafe.cafeName }}</div>
            <div class="text-subtitle2">{{ cutAddress(cafe.address) }}</div>
          </q-card-section>
          <q-card-section class="text-center" style="paddingTop: 0">
            <q-icon name="favorite" class="text-negative" /><span style="marginRight: 0.5rem">{{ cafe.liked }}</span>
            <q-icon name="star" class="text-yellow"/><span style="marginRight: 0.5rem">{{ cafe.rating }}</span>
            <q-icon name="edit_note" class="text-primary" /><span style="marginRight: 0.5rem">{{ cafe.reviewCount }}</span>
          </q-card-section>

          <q-separator />
        </q-card>
    </swiper-slide>
  </swiper>
</template>

<script>
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from 'swiper/vue';

// Import Swiper styles
import 'swiper/css';

import "swiper/css/effect-coverflow"
import "swiper/css/pagination"


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
      cafeList: [
        {
          cafeName: "이삼옥",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20210627_85%2F1624720943230FQr0F_JPEG%2Fupload_99e3125638d9cc296ffb1102f76ec872.jpg",
          address: "인천 부평구 부평대로32번길 32-1 2,3층",
          openingHours: "매일 12:00 ~ 22:00",
          reviewCount: 20,
          rating: 3.5,
          liked: 25,
          tag: "#맛있는 #심플한 #원두"
        },
        {
          cafeName: "론트커피",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDEwMjVfMTM1%2FMDAxNjAzNjI5NTEzMTA3._fK5KzbPHfljHA7rWoBSA-iAOS4qGx8RmnkjV0t46c4g.lRBWU96FIow_RjECDqs4e3AZU_96-g5Wzhr_fXHjjG4g.JPEG.ckdmlwls%2F20201025%25A3%25DF190844.jpg",
          address: "인천 부평구 부평대로38번길 3 1,2층",
          openingHours: "매일 12:00 ~ 22:00",
          reviewCount: 15,
          rating: 4.2,
          liked: 25,
          tag: "#맛있는 #모던 #자리"
        },
        {
          cafeName: "그날의온도",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA2MzBfMTU5%2FMDAxNjI1MDE2Njc4MTMx.zv6y8_KdLR9fYH36NUyx6OyNP_HI_Jnm4QJKVnCtu_Yg.X8E1-Il-6lbjvIj57553aowwLTf5TzE4XRmhMsKcyP0g.JPEG.spi15%2FKakaoTalk_20210630_093805276_24.jpg",
          address: "인천 부평구 부평문화로 72 2층",
          openingHours: "토,일 10:00 ~ 23:30",
          reviewCount: 22,
          rating: 3.2,
          liked: 25,
          tag: "#고소 #인스타 #원두"
        },
        {
          cafeName: "혜리별관",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20210812_163%2F1628725927268BsJd0_JPEG%2Fupload_59673beb6f0bac3f4892b07dde91ccd9.jpg",
          address: "인천 부평구 부평대로38번길 19-1 1층",
          openingHours: "화~일 13:00 ~ 21:30",
          reviewCount: 30,
          rating: 3.5,
          liked: 25,
          tag: "#예쁜 #테이블 #소파"
        },
        {
          cafeName: "녹턴 커피 로스터스",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20210905_216%2F1630806743080jydIp_JPEG%2Fupload_a8d1132fa3378eb5d6ee572d7d829d74.jpeg",
          address: "인천 부평구 부평문화로71번길 19 1층",
          openingHours: "매일 12:00 - 22:00",
          reviewCount: 19,
          rating: 5,
          liked: 25,
          tag: "#모던 #인스타 #맛있는"
        },
        {
          cafeName: "할리스 부평로데오점",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDA1MTNfODkg%2FMDAxNTg5MzY0NzU4NDIw.t2vIwWmscTPjVRkxWqvOPyw-BDAQwUsyEc7t8e3zUTkg.B0Jv6JwHJh1m7I_NShCvJngVQ2EwxbXDNjTE-hSEbpUg.JPEG.apeach0908%2FIMG_4911.jpg",
          address: "인천 부평구 시장로 17-1 인일메트로 빌딩 1~3층",
          openingHours: "매일 00:00 ~ 24:00",
          reviewCount: 17,
          rating: 4.3,
          liked: 25,
          tag: "#자리 #테이블 #콘센트"
        },
        {
          cafeName: "303호",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20210428_37%2F1619539595557uiYph_JPEG%2Fupload_42d7ffd7f190832570f8332f340ecfda.jpeg",
          address: "인천 부평구 부평대로38번길 31 303호",
          openingHours: "화~토 12:00 ~ 23:00",
          reviewCount: 28,
          rating: 4.6,
          liked: 25,
          tag: "#산미 #분위기 #인스타"
        },
      ]
        
    };
  },
  methods: {
    goBack() {
      window.history.back()
    },
    cutAddress(cafeAddress) {
      const strArray = cafeAddress.split(' ')
      return strArray[0] + ' ' + strArray[1]

    }
  },
  
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