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
            <q-input class="no-margin no-padding" type="email" outlined v-model="v$.email.$model" :error="v$.email.$invalid" placeholder="이메일 입력" clearable autocapitalize="off" />
            <span
              v-for="error of v$.email.$errors"
              :key="error.$uid"
              class="text-red"
            >
            {{ error.$message }}
            </span>
          </div>
          <div>
            <span>비밀번호</span>
            <q-input class="no-margin no-padding" type="password" outlined v-model="v$.password.$model" :error="v$.password.$invalid" placeholder="비밀번호 입력(영문, 숫자 조합)" clearable autocapitalize="off" />
            <span
              v-for="error of v$.password.$errors"
              :key="error.$uid"
              class="text-red"
            >
            {{ error.$message }}
            </span>
          </div>
          <q-btn @click="loginForm()" color="primary" type="submit" class="full-width" size="lg" style="margin: 50px" label="로그인" />
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
      <q-card @click="goSignup()" flat bordered style="width: 90%">
        <q-card-section class="flex justify-around items-center">
          <div class="text-h6">아직 회원이 아니세요?</div>
          <div class="text-h6">회원가입</div>
        </q-card-section>
      </q-card>
    </div>
  </div>
</template>

<script>
import useVuelidate from '@vuelidate/core'
import { required, email, helpers, minLength, maxLength } from '@vuelidate/validators'
import { api } from '../../boot/axios'

export default {
  name: 'LoginPage',
  setup() {
    return {
      v$: useVuelidate()
    }
  },
  data() {
    return {
      email: '',
      password: '',
    }
  },
  validations() {
    return {
      email: {
        required: helpers.withMessage('이메일은 필수 항목입니다.', required), 
        email: helpers.withMessage('이메일 양식이 아닙니다.', email),
        $autoDirty: true, $lazy: true
      },
      password: {
        required: helpers.withMessage('비밀번호는 필수 항목입니다.', required), 
        // alphaNum,
        minLength: helpers.withMessage('비밀번호는 3~20사이 입니다.', minLength(3)),
        maxLength: helpers.withMessage('비밀번호는 3~20사이 입니다.', maxLength(20)),
        $autoDirty: true, $lazy: true
      }
    }
  },
  methods: {
    goBack() {
      window.history.back()
    },
    goSignup() {
      this.$router.push({path:'/users/signup'}).catch(()=>{})
    },
    async checkForm () {
      const isFormCorrect = await this.v$.$validate()
      // you can show some extra alert to the user or just leave the each field to show it's `$errors`.
      if (!isFormCorrect) return
      // actually submit form
    },
    async loginForm () {
      try {
        const userData = {
          email: this.email,
          password: this.password,
        }
        const { data } = await api.post('/api/users/signin', userData)
        console.log(data)
        alert(data.message)
      } catch (error) {
        console.error(error)
      }
    }    


  }
}
</script>

<style>

</style>