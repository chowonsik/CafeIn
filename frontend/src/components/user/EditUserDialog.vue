<template>
  <div>
    <q-btn @click="icon = true" rounded color="white" size="sm" text-color="black" label="프로필 수정" />
      <q-dialog v-model="icon">
        <q-card>
          <q-card-section class="row items-center q-pb-none">
            <div class="text-h6">Close icon</div>
            <q-space />
            <q-btn icon="close" flat round dense v-close-popup />
          </q-card-section>

          <q-form @submit.prevent.stop="checkForm">
            <div class="flex flex-center">
              <div class="column" style="width: 80%">
                <div class="q-mt-md">
                  <span>닉네임</span>
                  <q-input type="nickname" outlined v-model="nickname" />
                </div>
                <div class="q-mt-md">
                  <span>바꿀 비밀번호</span>
                  <q-input type="password" outlined v-model="password" />
                </div>
                <div class="q-mt-md">
                  <span>바꿀 비밀번호 확인</span>
                  <q-input type="password" outlined v-model="passwordConfirm" />
                </div>
                <q-btn color="primary" type="submit" class="full-width q-ma-md" size="lg" label="프로필 수정" />
              </div>
            </div>
          </q-form>
        </q-card>
      </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'
export default {
  name: 'EditUserDialog',
  setup() {
    return {
      icon: ref(false),
    }
  },
  data() {
    return {
      nickname: '',
      password: '',
      passwordConfirm: '',
    }
  },
  methods: {
    checkForm() {

    }
  }
}
</script>

<style>

</style>