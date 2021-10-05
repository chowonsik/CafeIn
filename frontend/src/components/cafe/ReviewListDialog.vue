<template>
  <div>
    <q-btn flat round icon="more_vert" @click="open"/>
    <q-dialog v-model="dialog" :position="position" maximized>
      <q-card class="edit-review">
        <q-card-actions vertical align="center">
          <EditReviewDialog />
          <DeleteReviewDialog />
        </q-card-actions>
        <q-separator />
        <q-card-actions>
          <q-btn class="full-width" v-close-popup="3" flat>취소하기</q-btn>
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'
import EditReviewDialog from './EditReviewDialog.vue'
import DeleteReviewDialog from './DeleteReviewDialog.vue'
export default {
  name: "ReviewListDialog",
  components: {
    EditReviewDialog,
    DeleteReviewDialog,
  },
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
