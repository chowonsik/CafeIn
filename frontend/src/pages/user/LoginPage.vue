<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">로그인</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <div style="margin-top: 150px">
      <div class="flex flex-center">
        <div class="q-gutter-y-md column" style="width: 80%">
          <div>
            <span>이메일</span>
            <q-input class="no-margin" outlined v-model="ph" placeholder="이메일 입력" :dense="dense" />
          </div>
          <div>
            <span>비밀번호</span>
            <q-input class="no-margin" outlined v-model="ph" placeholder="비밀번호 입력(영문, 숫자 조합)" :dense="dense" />
          </div>
          <q-btn color="primary" class="full-width" size="lg" style="margin: 50px" label="로그인" />
        </div>
      </div>
    </div>
    <q-separator style="margin-top: 50px" />
    <div class="flex justify-center" style="margin-top: 30px">
      <p>아이디 찾기  |</p>
      <p>비밀번호 찾기  |</p>
      <p>홈</p>
    </div>
    <div class="row justify-center" style="margin-top: 100px">
      <q-card flat bordered style="width: 90%">
        <q-card-section class="flex justify-around items-center">
          <div class="text-h6">아직 회원이 아니세요?</div>
          <div class="text-h6">회원가입</div>
        </q-card-section>
      </q-card>
    </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'LoginPage',
  setup () {
    return {
      ph: ref(''),
      dense: ref(false),
    }
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