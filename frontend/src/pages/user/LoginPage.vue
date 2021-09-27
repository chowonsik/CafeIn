<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">로그인</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <q-form @submit.prevent.stop="checkForm">
      <div class="flex flex-center" style="margin-top: 150px">
        <div class="q-gutter-y-md column" style="width: 80%">
          <div>
            <span>이메일</span>
            <q-input class="no-margin" ref="emailRef" type="email" outlined v-model="email" lazy-rules :rules="emailRules" placeholder="이메일 입력" :dense="dense" />
          </div>
          <div>
            <span>비밀번호</span>
            <q-input class="no-margin" ref="passwordRef" type="password" outlined v-model="password" lazy-rules :rules="passwordRules" placeholder="비밀번호 입력(영문, 숫자 조합)" :dense="dense" />
          </div>
          <q-btn color="primary" type="submit" class="full-width" size="lg" style="margin: 50px" label="로그인" />
        </div>
      </div>
    </q-form>
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
import { useQuasar } from 'quasar'
import { ref } from 'vue'
import { defineComponent } from 'vue';

export default defineComponent ({
  name: 'LoginPage',
  setup() {
    const $q = useQuasar()
    const email = ref(null)
    const emailRef = ref(null)
    const password = ref(null)
    const passwordRef = ref(null)
    const accept = ref(false)
    const emailPattern = /^(?=[a-zA-Z0-9@._%+-]{6,254}$)[a-zA-Z0-9._%+-]{1,64}@(?:[a-zA-Z0-9-]{1,63}\.){1,8}[a-zA-Z]{2,63}$/
    const passwordPattern = /^[a-zA-Z0-9]{8,20}$/
    
    return {
      dense: false,
      email,
      emailRef,
      password,
      passwordRef,
      emailRules: [
        val => (val !== null && val !== '') || '이메일은 필수 항목입니다.',
        val => (val && emailPattern.test(val)) || '이메일 형식이 옳지 않습니다.'
      ],
      passwordRules: [
        val => (val !== null && val !== '') || '비밀번호는 필수 항목입니다.',
        val => (val && passwordPattern.test(val)) || '비밀번호 형식이 옳지 않습니다.'
      ],
      accept,
      checkForm () {
        emailRef.value.validate()
        passwordRef.value.validate()
        if (emailRef.value.hasError || passwordRef.value.hasError) {
        }
        else if (accept.value !== true) {
          console.error('미완료')
        }
        else {
          console.log('완료')
        }
      }
    }
  },
  methods: {
    goBack() {
      window.history.back()
    },
  }
})
</script>

<style>

</style>