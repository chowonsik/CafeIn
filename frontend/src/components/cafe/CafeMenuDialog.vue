<template>
  <div>
    <q-btn color="primary" rounded dense size="0.7rem" label="메뉴 보기" @click="icon = true"/>

    <q-dialog v-model="icon">
      <q-card style="min-width: 240px">
        <q-card-section class="row items-center q-pb-none">
          <div class="text-h6">메뉴({{cafeMenu.length}})</div>
          <q-space />
          <q-btn icon="close" flat round dense v-close-popup />
        </q-card-section>

        <q-card-section>
          <q-item v-for="menu in cafeMenu" :key="menu">
            <q-item-section >
              <q-item-label>{{ menu.name }}</q-item-label>
              <q-item-label>{{ menu.price }}원</q-item-label>
              <q-separator />
            </q-item-section>
          </q-item>
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  data() {
    return {
      cafeMenu: [
        {name: "아메리카노", price: 4500},
        {name: "에스프레소", price: 3000},
        {name: "녹턴프레소", price: 3500},
        {name: "녹턴커피", price: 3500},
        {name: "카페라떼",price: 5000},

      ]
    }
  },
  setup () {
    return {
      icon: ref(false),
    }
  }
}
</script>

<style>

</style>