<template>
  <div class="q-pa-sm">
    <q-btn class="full-width" color="red" flat @click="confirm = true">삭제하기</q-btn>
    <q-dialog v-model="confirm" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <div class="q-ml-sm text-h6">정말로 리뷰를 삭제하시겠습니까?</div>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat label="아니요" color="primary" v-close-popup />
          <q-btn flat label="예" color="red" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: "DeleteReviewDialog",
  setup () {
    return {
      confirm: ref(false),
    }
  }
}
</script>

<style>

</style>