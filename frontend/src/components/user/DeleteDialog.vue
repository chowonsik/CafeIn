<template>
  <div>
    <q-btn flat class="full-width text-weight-bold" size="lg" color="red" label="회원탈퇴" @click="confirm = true" />
    <q-dialog v-model="confirm" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <div class="q-ml-sm text-h6">정말로 탈퇴하시겠습니까?</div>
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
  name: "DeleteDialog",
  setup () {
    return {
      confirm: ref(false),
    }
  }
}
</script>

<style>

</style>