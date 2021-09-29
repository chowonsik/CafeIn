<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">내 리뷰</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <ReviewItem />
  </div>
</template>

<script>
import ReviewItem from '../components/cafe/ReviewItem.vue'
export default {
  name: 'MyReviewPage',
  components: {
    ReviewItem,
  },
  methods: {
    goBack() {
      window.history.back()
    },
  }
}
</script>

<style>

</style>