<template>
  <div class="q-pa-md q-gutter-sm">
    <q-btn label="리뷰 작성" color="primary" @click="reviewButton()" />

    <q-dialog v-model="card">
      <q-card class="my-card" style="min-width: 300px">

        <q-card-section>
          <div class="col text-h6 text-bold">
            리뷰 작성
          </div>
          <div class="row" style="marginTop: 1rem">
            <q-rating v-model="stars" :max="5" size="2rem" color="primary" icon="star_border" icon-selected="star" style="margin: auto"/>
          </div>

        </q-card-section>

        <q-card-section class="q-pt-none" style="min-height: 150px">
        <q-input
          v-model="text"
          filled
          type="textarea"

        />
        </q-card-section>

        <q-separator />

        <q-card-actions align="right">
          <q-btn v-close-popup color="negative" rounded dense label="취소" />
          <q-btn @click="writeReview()" v-close-popup color="primary" rounded dense label="작성" />
        </q-card-actions>
      </q-card>
    </q-dialog>

    
  </div>
</template>

<script>
import { ref } from 'vue'
import { createReview } from '../../api/review'
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers("auth")

export default {
  setup () {
    return {
      card: ref(false),
      stars: ref(0),
      text: ref('')
    }
  },
  methods: {
    async writeReview() {
      try {
        const cafeId = this.$route.params.id
        const reviewData = {
          cafeId : cafeId,
          totalScore : this.stars,
          content : this.text
        }
        const { data } = await createReview(reviewData)
        // console.log(reviewData)
        alert(data.message)
        console.log(data)
        location.reload()

      } catch (error) {
        console.log(error)
      }
    },
    reviewButton() {
      if (this.accessToken === "") {
        alert("로그인이 필요한 페이지입니다.")
        this.$router.push("/users/login")
      } else {
        this.card = true
      }
    }
  },
  computed: {
    ...mapState(['accessToken'])
  },
}
</script>

<style>

</style>