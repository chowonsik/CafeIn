<template>
  <div>
    <q-list padding>
      <q-item style="marginBottom: 1rem" v-ripple v-for="nearCafe in nearCafes" :key="nearCafe.cafeName">
        <q-item-section avatar top>
          <q-avatar rounded size="80px">
            <img :src="cafe.img" >
          </q-avatar>
        </q-item-section>
        <q-item-section>
          <q-item-label overline style="marginBottom: 0.3rem; fontSize: 0.9rem; fontWeight: bold">{{nearCafe.cafeName}}</q-item-label>
          <div class="flex">
            <div style="width: 1.2rem">
              <q-item-label caption>{{nearCafe.rating}}</q-item-label>
            </div>
            <q-rating
              v-model="nearCafe.rating"
              size="1em"
              color="primary"
              icon="star_border"
              icon-selected="star"
              icon-half="star_half"
              readonly
            />
            <q-item-label caption>({{nearCafe.reviewCount}})</q-item-label>
          </div>
          <q-item-label caption>{{nearCafe.address}}</q-item-label>
          <q-item-label caption>{{nearCafe.openingHours}}</q-item-label>
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
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers("kakaomap")
import { api } from "../../boot/axios";

export default {
  name: "NearCafeItem",
  data() {
    return {
      nearCafes: [],
    }
  },
  methods: {
    ...mapState(['latitude', 'longitude']),
    async getCafeItem() {
      try {
        const { data } = await api.get(`/api/cafes?latitude=${this.latitude}&longitude=${this.longitude}&search=&size=10&page=1`)
        this.nearCafes = data.result
        console.log(data.result)
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