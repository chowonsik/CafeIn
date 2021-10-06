<template>
  <div>
    <q-list padding>
      <q-infinite-scroll @load="onLoad" :offset="250">
      <q-item style="marginBottom: 1rem" v-ripple v-for="(cafe, index) in items" :key="index">
        <q-item-section avatar top @click="$router.push({ path: `/cafes/${cafe.cafeId}`})">
          <q-avatar rounded size="80px">
            <img :src="cafe.cafeImgUrl" >
          </q-avatar>
        </q-item-section>
        <q-item-section @click="$router.push({ path: `/cafes/${cafe.cafeId}`})">
          <q-item-label overline style="marginBottom: 0.3rem; fontSize: 0.9rem; fontWeight: bold">{{cafe.cafeName}}</q-item-label>
          <div class="flex">
            <div style="width: 1.2rem">
              <q-item-label caption>{{cafe.cafeAvgScore.toFixed(1)}}</q-item-label>
            </div>
            <q-rating
              v-model="cafe.cafeAvgScore"
              size="1em"
              color="primary"
              icon="star_border"
              icon-selected="star"
              icon-half="star_half"
              readonly
              style="marginLeft: 0.3rem"
            />
            <q-item-label caption>({{cafe.reviewCnt}})</q-item-label>
          </div>
          <q-item-label style="marginTop: 0.3rem" caption>{{cafe.cafeAddress}}</q-item-label>
          <q-item-label caption>{{cafe.cafeTel}}</q-item-label>
        
        </q-item-section>

      </q-item>
      <template v-slot:loading>
        <div class="row justify-center q-my-md">
          <q-spinner-dots color="primary" size="40px" />
        </div>
      </template>
      </q-infinite-scroll>
      
    </q-list>
  </div>
</template>

<script>
import { ref } from 'vue'
import axios from 'axios'
import state from "src/store/auth/state";
import { bookmark, cancelBookmark } from 'src/api/cafe';
import { useRoute } from 'vue-router'
import { createNamespacedHelpers } from 'vuex'
// const { mapState } = createNamespacedHelpers("kakaomap")
import mapState from "src/store/kakaomap/state";

export default {
  name: 'TagListPage',
  // computed: {
  //   ...mapState(['latitude', 'longitude'])
  // },
  data() {
    return {
      bookmarked: 1
    }
  },
  setup () {
    const items = ref([])
    const accessToken = state.accessToken
    const isBookmarked = ref([])
    const tagName = useRoute().params.tagname
    const latitude = mapState.latitude
    const longitude = mapState.longitude

    return {
      items,
      isBookmarked,
      onLoad (index, done) {
        setTimeout(() => {
          axios
          .get(`https://j5b204.p.ssafy.io/api/cafes/curation?type=1&latitude=${latitude}&longitude=${longitude}&category=${tagName}&distance=10&size=10&page=${index}`, {
            headers: {
              "X-ACCESS-TOKEN": accessToken
            }
          })
          .then(({data}) => {
            console.log(tagName)
            items.value.push(...data.result)
            isBookmarked.value.push()
            return data.result
          })
          .then(response => {
            if (response.length === 0 ) {
              done(true)
            } else {
              done(false)
            }
          })
          // done()
        }, 2000)
      },
    }
  },
}
</script>

<style>

</style>