<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">프로필 수정</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <div class="q-mt-xl">
      <q-card flat>
        <q-form @submit.prevent.stop="checkForm">
          <div class="flex flex-center">
            <div class="column" style="width: 80%">
              <div class="q-mt-lg">
                <span>닉네임</span>
                <q-input type="nickname" outlined v-model="nickname" />
              </div>
              <div class="q-mt-lg">
                <span>바꿀 비밀번호</span>
                <q-input type="password" outlined v-model="password" />
              </div>
              <div class="q-mt-lg">
                <span>바꿀 비밀번호 확인</span>
                <q-input type="password" outlined v-model="passwordConfirm" />
              </div>
              <q-btn color="primary" type="submit" class="full-width q-ma-lg" size="lg" label="프로필 수정" />
            </div>
          </div>
        </q-form>
      </q-card>
    </div>

  </div>
</template>

<script>
export default {
  name: "EditUserPage",
  data() {
    return {
      nickname: '',
      password: '',
      passwordConfirm: '',
    }
  },
  methods: {
    checkForm() {

    },
    goBack() {
      window.history.back()
    },
  }
}
</script>

<style>

</style>