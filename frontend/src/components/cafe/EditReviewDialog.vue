<template>
    <div class="q-pa-sm">
    <q-btn class="full-width" color="primary" flat @click="card = true">수정하기</q-btn>
    <q-dialog v-model="card">
      <q-card class="my-card" style="min-width: 300px">

        <q-card-section>
          <div class="col text-h6 text-bold">
            리뷰 수정
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
          <q-btn v-close-popup color="primary" rounded dense label="수정" />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'
export default {
  name: 'EditReviewDialog',
  setup () {
    return {
      card: ref(false),
      stars: ref(0),
      text: ref('')
    }
  }
}
</script>

<style>

</style>