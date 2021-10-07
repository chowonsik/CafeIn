<template>
  <div>
    <q-list padding>
      <div v-ripple v-for="nearCafe in nearCafes" :key="nearCafe.cafeName">
        <q-item :to="`/cafes/${nearCafe.cafeId}`">
        <q-item-section avatar top>
          <q-avatar rounded size="80px">
            <img :src="nearCafe.cafeImgUrl" >
          </q-avatar>
        </q-item-section>
        <q-item-section>
          <q-item-label overline style="marginBottom: 0.3rem; fontSize: 0.9rem; fontWeight: bold">{{nearCafe.cafeName}}</q-item-label>
          <div class="flex">
            <div style="width: 1.2rem">
              <q-item-label caption v-if="nearCafe.cafeAvgScore">{{(nearCafe.cafeAvgScore).toFixed(1)}}</q-item-label>
              <q-item-label caption v-else>0</q-item-label>
            </div>
            <q-rating
              v-model="nearCafe.cafeAvgScore"
              size="1em"
              color="primary"
              icon="star_border"
              icon-selected="star"
              icon-half="star_half"
              readonly
              style="marginLeft: 0.3rem"
            />
            <q-item-label caption>({{nearCafe.reviewCnt}})</q-item-label>
          </div>
          <q-item-label caption>{{nearCafe.cafeAddress}}</q-item-label>
          <q-item-label caption>{{nearCafe.cafeTel}}</q-item-label>
        </q-item-section>
        <q-item-section side top style="paddingLeft: 0">
          <!-- <q-btn flat round text-color="negative" icon="favorite" /> -->
          <q-btn
            v-if="nearCafe.isBookMark"
            style="marginleft: 1rem"
            flat
          >
            <span class="material-icons" style="font-size: 1.5rem; color: #ff6666"
              >favorite</span
            >
          </q-btn>
          <q-btn
            v-else
            style="marginleft: 1rem"
            flat
          >
            <span class="material-icons" style="font-size: 1.5rem"
              >favorite_border</span
            >
          </q-btn>
        </q-item-section>
        </q-item>
      </div>  
    </q-list>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers("kakaomap")
import { nearCafeSearch } from "../../api/cafe" 

export default {
  name: "NearCafeItem",
  data() {
    return {
      nearCafes: [],
    }
  },
  computed: {
    ...mapState(['latitude', 'longitude']),
  },
  methods: {
    async getCafeItem() {
      try {
        const latitude = this.latitude
        const longitude = this.longitude
        const { data } = await nearCafeSearch(latitude, longitude)
        this.nearCafes = data.result
        // console.log(data.result)
      } catch (error) {
        console.log(error)
      }
    }
  },
  created() {
    this.getCafeItem()
  }
}
</script>

<style>

</style>