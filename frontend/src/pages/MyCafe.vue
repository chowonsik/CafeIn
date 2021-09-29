<template>
  <div>
    <q-list padding>

      <q-item style="marginBottom: 1rem" v-ripple v-for="cafe in cafeList" :key="cafe.cafeName">

        <q-item-section avatar top>
          <q-avatar rounded size="80px">
            <img :src="cafe.img" >
          </q-avatar>
        </q-item-section>
        <q-item-section>
          <q-item-label overline style="marginBottom: 0.3rem; fontSize: 0.9rem; fontWeight: bold">{{cafe.cafeName}}</q-item-label>
          <div class="flex">
            <div style="width: 1.2rem">
              <q-item-label caption>{{cafe.rating}}</q-item-label>
            </div>
            <q-rating
              v-model="cafe.rating"
              size="1em"
              color="primary"
              icon="star_border"
              icon-selected="star"
              icon-half="star_half"
              readonly
            />
            <q-item-label caption>({{cafe.reviewCount}})</q-item-label>
          </div>
          <q-item-label caption>{{cafe.address}}</q-item-label>
          <q-item-label caption>{{cafe.openingHours}}</q-item-label>

          <q-item-label caption>매장 내 식사 · 테이크아웃 · 배달여부</q-item-label>
        
        </q-item-section>
        <q-item-section side top>
          <q-btn flat round text-color="negative" icon="favorite" />
        </q-item-section>

      </q-item>

      

      

      
    </q-list>
  </div>
</template>

<script>
export default {
  name: 'MyCafe',
  data() {
    return {
      cafeList: [
        {
          cafeName: "이삼옥",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20210627_85%2F1624720943230FQr0F_JPEG%2Fupload_99e3125638d9cc296ffb1102f76ec872.jpg",
          address: "인천 부평구 부평대로32번길 32-1 2,3층",
          openingHours: "매일 12:00 ~ 22:00",
          reviewCount: 20,
          rating: 3.5
        },
        {
          cafeName: "론트커피",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDEwMjVfMTM1%2FMDAxNjAzNjI5NTEzMTA3._fK5KzbPHfljHA7rWoBSA-iAOS4qGx8RmnkjV0t46c4g.lRBWU96FIow_RjECDqs4e3AZU_96-g5Wzhr_fXHjjG4g.JPEG.ckdmlwls%2F20201025%25A3%25DF190844.jpg",
          address: "인천 부평구 부평대로38번길 3 1,2층",
          openingHours: "매일 12:00 ~ 22:00",
          reviewCount: 15,
          rating: 4.2
        },
        {
          cafeName: "그날의온도",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA2MzBfMTU5%2FMDAxNjI1MDE2Njc4MTMx.zv6y8_KdLR9fYH36NUyx6OyNP_HI_Jnm4QJKVnCtu_Yg.X8E1-Il-6lbjvIj57553aowwLTf5TzE4XRmhMsKcyP0g.JPEG.spi15%2FKakaoTalk_20210630_093805276_24.jpg",
          address: "인천 부평구 부평문화로 72 2층",
          openingHours: "토,일 10:00 ~ 23:30",
          reviewCount: 22,
          rating: 3.2
        },
        {
          cafeName: "혜리별관",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20210812_163%2F1628725927268BsJd0_JPEG%2Fupload_59673beb6f0bac3f4892b07dde91ccd9.jpg",
          address: "인천 부평구 부평대로38번길 19-1 1층",
          openingHours: "화~일 13:00 ~ 21:30",
          reviewCount: 30,
          rating: 3.5
        },
        {
          cafeName: "녹턴 커피 로스터스",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20210905_216%2F1630806743080jydIp_JPEG%2Fupload_a8d1132fa3378eb5d6ee572d7d829d74.jpeg",
          address: "인천 부평구 부평문화로71번길 19 1층",
          openingHours: "매일 12:00 - 22:00",
          reviewCount: 19,
          rating: 5
        },
        {
          cafeName: "할리스 부평로데오점",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMDA1MTNfODkg%2FMDAxNTg5MzY0NzU4NDIw.t2vIwWmscTPjVRkxWqvOPyw-BDAQwUsyEc7t8e3zUTkg.B0Jv6JwHJh1m7I_NShCvJngVQ2EwxbXDNjTE-hSEbpUg.JPEG.apeach0908%2FIMG_4911.jpg",
          address: "인천 부평구 시장로 17-1 인일메트로 빌딩 1~3층",
          openingHours: "매일 00:00 ~ 24:00",
          reviewCount: 17,
          rating: 4.3
        },
        {
          cafeName: "303호",
          img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20210428_37%2F1619539595557uiYph_JPEG%2Fupload_42d7ffd7f190832570f8332f340ecfda.jpeg",
          address: "인천 부평구 부평대로38번길 31 303호",
          openingHours: "화~토 12:00 ~ 23:00",
          reviewCount: 28,
          rating: 4.6
        },
      ]
    }
  }

}
</script>

<style>

</style>