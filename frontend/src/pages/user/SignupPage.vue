<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">회원가입</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <q-form @submit.stop="checkForm">
      <div class="flex flex-center" style="margin-top: 50px">
        <div class="q-gutter-y-md column" style="width: 80%">
          <div>
            <span>이메일</span>
            <div class="row">
              <q-input class="no-margin col-10" outlined v-model="email" placeholder="이메일 입력" :dense="dense" />
              <q-btn class="col-2" color="primary" size="sm" label="인증" />
            </div>
          </div>
          <div>
            <span>인증번호</span>
            <q-input class="no-margin" outlined v-model="certification" placeholder="인증번호 입력" :dense="dense" />
          </div>
          <div>
            <span>닉네임</span>
            <q-input class="no-margin" outlined v-model="nickname" placeholder="닉네임 입력" :dense="dense" />
          </div>
          <div>
            <span>비밀번호</span>
            <q-input class="no-margin" outlined v-model="password" placeholder="비밀번호 입력(영문, 숫자 조합)" :dense="dense" />
          </div>
          <div>
            <span>비밀번호 확인</span>
            <q-input class="no-margin" outlined v-model="passwordConfirm" placeholder="비밀번호 확인(영문, 숫자 조합)" :dense="dense" />
          </div>
          <q-card flat bordered style="width: 90%">
            <q-card-section class="flex justify-around items-center">
              <q-checkbox v-model="checkbox" />
              <SignupDialog />
            </q-card-section>
          </q-card>
          <q-btn color="primary" class="full-width" size="lg" label="동의하고 가입" />
        </div>
        <div class="q-my-md text-weight-bold">
          가입 필수 정보 및 약관을 모두 확인해주세요.
        </div>
      </div>
    </q-form>
  </div>
</template>

<script>
import SignupDialog from '../../components/SignupDialog.vue'
export default {
  name: 'SignupPage',
  components: {
    SignupDialog,
  },
  data() {
    return {
      dense: false,
      email: "",
      certification: "",
      nickname: "",
      password: "",
      passwordConfirm: "",
      checkbox: false,
    }
  },
  methods: {
    goBack() {
      window.history.back()
    },
    checkForm() {
      
    }
  }
}
</script>

<style>

</style>