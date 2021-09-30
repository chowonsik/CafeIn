<template>
  <div>
    <q-btn flat round icon="more_vert" @click="open"/>
    <q-dialog v-model="dialog" :position="position" maximized>
      <q-card class="edit-review">
        <q-card-actions vertical align="center">
          <q-btn class="full-width" flat>수정하기</q-btn>
          <q-btn class="full-width" flat>삭제하기</q-btn>
          <q-btn class="full-width" flat>취소하기</q-btn>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'
export default {
  name: "EditReviewDialog",
  setup () {
    const dialog = ref(false)
    const position = ref('bottom')

    return {
      dialog,
      position,

      open () {
        dialog.value = true
      }
    }
  }
}
</script>

<style>
.edit-review {
  width: 100%;
  margin: 0;
  padding: 0;
}
</style>
