<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding" style="marginRight: 1.2rem">카페 리뷰</q-toolbar-title>
      </q-toolbar>
    </q-header>

    <div class="q-pa-md">
      <q-card class="my-card" flat >
        <q-card-section>
          <div class="text-h6 text-bold">리뷰 ({{ cafeInfo.reviewCount }})</div>
        </q-card-section>

        <q-item v-for="review in cafeInfo.reviews" :key="review.created_at">
          <q-item-section>
            <q-item-label>{{ review.content }}</q-item-label>
          </q-item-section>

          <q-item-section side top>
            <q-item-label caption>{{ review.created_at }}</q-item-label>
              <q-rating
                v-model="review.rating"
                size="1em"
                color="primary"
                icon="star_border"
                icon-selected="star"
                readonly
              />
          </q-item-section>
        </q-item>
      </q-card>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'CafeReview',
  data() {
    return {
      cafeInfo: {
        cafeName: "녹턴 커피 로스터스",
        img: "https://search.pstatic.net/common/?autoRotate=true&quality=95&type=w750&src=https%3A%2F%2Fmyplace-phinf.pstatic.net%2F20210905_216%2F1630806743080jydIp_JPEG%2Fupload_a8d1132fa3378eb5d6ee572d7d829d74.jpeg",
        address: "인천 부평구 부평문화로71번길 19 1층",
        openingHours: "매일 12:00 - 22:00",
        liked: 35,
        rating: 4.3,
        reviewCount: 5,
        reviews: [
          {
            content: "라떼가 진짜 맛있음",
            rating: 4,
            created_at: "2021-09-20",
          },
          {
            content: "카페 너무 예뻐요. 데이트하기 좋음.",
            rating: 5,
            created_at: "2021-09-01",
          },
          {
            content: "커피 너무 맛있어요",
            rating: 4,
            created_at: "2021-08-02",
          },
          {
            content: "자리가 별로 없습니다.",
            rating: 3,
            created_at: "2021-07-25",
          },
          {
            content: "사람이 너무 많아서 별로였어요.",
            rating: 3,
            created_at: "2021-07-15",
          },
        ]
      }
    }
  },
  methods: {
    goBack() {
      window.history.back()
    },
  },
})
</script>

<style>

</style>