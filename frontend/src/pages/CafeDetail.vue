<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding" style="marginRight: 1.2rem">카페 정보</q-toolbar-title>
      </q-toolbar>
    </q-header>

    <div>
      <q-img
        :src="cafeInfo.cafeImgUrl" 
        :ratio="4/3"
        style="max-height: 300px;"
      />
    </div>
      <div class="q-pa-md">
      <q-card class="my-card" flat >

        <q-card-section>
          <div class="row justify-between">
            <div class="text-h5 text-bold">{{ cafeInfo.cafeName }}</div>
            <CafeMenuDialog />
          </div>
          <div class="text-subtitle2">{{ cafeInfo.cafeAddress }}</div>
        </q-card-section>
        <q-card-section style="paddingTop: 0">
          <q-icon name="favorite" class="text-negative" /><span style="marginRight: 0.5rem">{{ cafeInfo.bookmarkCnt }}</span>
          <q-icon name="star" class="text-yellow"/><span style="marginRight: 0.5rem">{{ cafeInfo.bookmarkCnt }}</span>
          <q-icon name="edit_note" class="text-primary" /><span style="marginRight: 0.5rem">{{ cafeInfo.reviewCnt }}</span>
        </q-card-section>

        <q-separator />

        <q-card-section>
          <div class="text-h6 text-bold">매장소개</div>
          <q-item-label caption>전화번호 : {{ cafeInfo.cafeTel }}</q-item-label>
          <div class="text-h6 text-bold" style="marginTop: 1rem">영업시간</div>
          <q-item-label caption>오전 {{ bhourInfo.bhourStartTime }} ~ 오후 {{ bhourInfo.bhourEndTime }}</q-item-label>
        </q-card-section>

        <q-separator />


      </q-card>
    </div>

    <div class="q-pa-md">
      <q-card class="my-card" flat >
        <q-card-section>
          <div class="text-h6 text-bold">리뷰 ({{ cafeInfo.reviewCnt }})</div>
        </q-card-section>
        
        <q-item v-for="review in reviews" :key="review.id">
          <q-item-section>
            <q-item-label>{{ review.reviewContent}}</q-item-label>
          </q-item-section>

          <q-item-section side top>
            <q-item-label caption>{{ dateTime(review.reviewCreatedAt) }}</q-item-label>
              <q-rating
                v-model="review.reviewScore"
                size="1em"
                color="yellow"
                icon="star_border"
                icon-selected="star"
                readonly
              />
          </q-item-section>
        </q-item>
      </q-card>
    </div>

    <q-footer reveal bordered class="bg-white text-grey-8">
      <q-toolbar>
        <q-toolbar-title class="row justify-between items-center">
            <q-btn v-if="bookmarked" style="marginLeft: 1rem" flat registerBookmark @click="deleteBookmark()">
              <span class="material-icons" style="font-size: 2rem; color: #FF6666">favorite</span>
            </q-btn>
            <q-btn v-else style="marginLeft: 1rem" flat @click="registerBookmark()">
              <span class="material-icons" style="font-size: 2rem;">favorite_border</span>
            </q-btn>
          <div style="marginRight: 1rem">
            <review-dialog />
          </div>
        </q-toolbar-title>
      </q-toolbar>
    </q-footer>
  </div>
</template>

<script>
import moment from 'moment'
import ReviewDialog from '../components/cafe/ReviewDialog.vue'
import CafeMenuDialog from '../components/cafe/CafeMenuDialog.vue'
import { cafeDetail, cafeBhour, bookmark, cancelBookmark } from '../api/cafe'
import { getCafeReview } from '../api/review'

export default {
  name: 'CafeDetail',
  components: {
    ReviewDialog,
    CafeMenuDialog
  },
  data() {
    return {
      bookmarked: null,
      cafeInfo: [],
      bhourInfo: [],
      reviews: [],
    }
  },
  methods: {
    goBack() {
      window.history.back()
    },
    bookmark() {
      this.bookmarked = !this.bookmarked
      console.log(this.bookmarked)
    },
    dateTime(value) {
      return moment(value).format('YYYY-MM-DD')
    },
    async cafeItem() {
      try {
        const cafeId = this.$route.params.id
        const { data } = await cafeDetail(cafeId)
        console.log(data)
        this.cafeInfo = data.result
        this.bookmarked = data.result.isBookMark
        console.log(this.bookmarked)
      } catch(error) {
        console.error(error)
      }
    },
    async bhourItem() {
      try {
        const cafeId = this.$route.params.id
        const { data } = await cafeBhour(cafeId)
        console.log(data)
        this.bhourInfo = data.result[0]
      } catch(error) {
        console.error(error)
      } 
    },
    async reviewItem() {
      try {
        const cafeId = this.$route.params.id
        const { data } = await getCafeReview(cafeId)
        console.log(data)
        this.reviews = data.result
      } catch(error) {
        console.error(error)
      }
    },
    async registerBookmark() {
      try {
        const cafeId = {
          cafeId: this.$route.params.id
        }
        const { data } = await bookmark(cafeId)
        if (data.isSuccess === true) {
          this.bookmarked = 1
        }
        console.log(data)
      } catch(error) {
        console.log(error)
      }
    },
    async deleteBookmark() {
      try {
        const cafeId = this.$route.params.id
        const { data } = await cancelBookmark(cafeId)
        if (data.isSuccess === true) {
          this.bookmarked = 0
        }
        console.log(data)
      } catch(error) {
        console.log(error)
      }
    }
  },
  created() {
    this.cafeItem()
    this.bhourItem()
    this.reviewItem()
  }
}
</script>

<style>

</style>