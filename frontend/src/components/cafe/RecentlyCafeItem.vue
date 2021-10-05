<template>
  <div class="q-pa-md">
    <q-card class="my-card" flat >
      <q-card-section horizontal style="overflow: auto">
        <q-item v-for="cafe in recentCafeList" :key="cafe.cafeName">
          <q-card style="max-width:180px" @click="$router.push({ path: `/cafes/${cafe.cafeId}`})">
            <q-item-section>
              <q-img
                :src="cafe.cafeImgUrl"
                :ratio="1"
                spinner-color="primary"
                position
                style="height: 180px; width: 180px"
              />
            </q-item-section>
            <q-item-section>
              <div class="text-subtitle1 q-my-md text-weight-bold text-center">{{ cafe.cafeName }}</div>
            </q-item-section>
            <q-item-section>
              <div class="text-subtitle2 q-mb-md">{{ cafe.cafeAddress }}</div>
            </q-item-section>
          </q-card>
        </q-item>
      </q-card-section>
    </q-card>
  </div>
</template>

<script>
import { cafeDetail } from '../../api/cafe'

export default {
  name: 'RecentlyCafeItem',
  data() {
    return {
      recentCafeId: [],
      recentCafeList: [],
    }
  },
  methods: {
    getCookie(cookie_name) {
      var x, y;
      var val = document.cookie.split(';');

      for (var i = 0; i < val.length; i++) {
        x = val[i].substr(0, val[i].indexOf('='));
        y = val[i].substr(val[i].indexOf('=') + 1);
        x = x.replace(/^\s+|\s+$/g, ''); // 앞과 뒤의 공백 제거하기
        if (x == cookie_name) {
          this.recentCafeId = unescape(y).split(','); // unescape로 디코딩 후 값 리턴
        }
      }
    },
    async getRecentCafe() {
      for (let i = 0; i < this.recentCafeId.length; i++) {
        const { data } = await cafeDetail(this.recentCafeId[i])
        this.recentCafeList.push(data.result)
      }
    }
  },
  created() {
    this.getCookie('recentCafe')
    this.getRecentCafe()
  }
}
</script>

<style lang="sass" scoped>

</style>
