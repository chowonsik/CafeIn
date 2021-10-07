<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">검색</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <q-list padding>
      <q-infinite-scroll @load="onLoad" :offset="250">
      <q-item style="marginBottom: 1rem" v-ripple v-for="(cafe, index) in items" :key="index">
        <q-item-section avatar top @click="$router.push({ path: `/cafes/${cafe.cafeId}`})">
          <q-avatar rounded size="80px">
            <q-img :src="cafe.cafeImgUrl">
              <template v-slot:error>
                <q-img :src="coffeeImg" />
              </template>
            </q-img>
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
import { api } from '../boot/axios'
import state from "src/store/auth/state";
import { useRoute } from 'vue-router'
import mapState from "src/store/kakaomap/state";
import coffeeImg from "../assets/image/coffee.png"

export default {
  name: 'CafeSearch',
  data() {
    return {
      bookmarked: 1,
      coffeeImg: coffeeImg,
    }
  },
  methods: {
    goBack() {
      window.history.back()
    },
  },
  setup() {
    const route = useRoute()
    const cafeName = route.params.cafeName
    const items = ref([])
    const isBookmarked = ref([])
    const accessToken = state.accessToken
    const tagName = useRoute().params.tagname
    const latitude = mapState.latitude
    const longitude = mapState.longitude

    return {
      items,
      isBookmarked,
      onLoad (done) {
        setTimeout(() => {
          api
          .get(`/api/cafes?latitude=${latitude}&longitude=${longitude}&search=${cafeName}&size=10&page=1`, {
            headers: {
              "X-ACCESS-TOKEN": accessToken
            }
          })
          .then(({data}) => {
            // console.log(tagName)
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
        }, 2000)
      },
    }
  }
}
</script>

<style>

</style>