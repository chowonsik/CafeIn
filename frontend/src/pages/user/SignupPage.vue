<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">회원가입</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <q-form @submit.prevent.stop="checkForm">
      <div class="flex flex-center" style="margin-top: 50px">
        <div class="q-gutter-y-md column" style="width: 80%">
          <div>
            <span>이메일</span>
            <div class="row">
              <q-input class="no-margin no-padding col-10" outlined v-model="v$.email.$model" :error="v$.email.$invalid" placeholder="이메일 입력" clearable autocapitalize="off" />
              <q-btn class="col-2" color="primary" size="sm" label="인증" />
            </div>
            <span
              v-for="error of v$.email.$errors"
              :key="error.$uid"
              class="text-red"
            >
            {{ error.$message }}
            </span>
          </div>
          <div>
            <span>인증번호</span>
            <q-input class="no-margin no-padding" outlined v-model="v$.auth.$model" :error="v$.auth.$invalid" placeholder="인증번호 입력" clearable autocapitalize="off" />
            <span
              v-for="error of v$.auth.$errors"
              :key="error.$uid"
              class="text-red"
            >
            {{ error.$message }}
            </span>
          </div>

          <div>
            <span>닉네임</span>
            <q-input class="no-margin no-padding" outlined v-model="v$.nickname.$model" :error="v$.nickname.$invalid" placeholder="닉네임 입력" clearable autocapitalize="off" />
            <span
              v-for="error of v$.nickname.$errors"
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
          <div>
            <span>비밀번호 확인</span>
            <q-input class="no-margin no-padding" outlined v-model="v$.passwordConfirm.$model" :error="v$.passwordConfirm.$invalid" placeholder="비밀번호 확인(영문, 숫자 조합)" clearable autocapitalize="off" />
            <span
              v-for="error of v$.passwordConfirm.$errors"
              :key="error.$uid"
              class="text-red"
            >
            {{ error.$message }}
            </span>
          </div>
          <q-card flat bordered style="width: 90%">
            <q-card-section class="flex justify-around items-center">
              <q-checkbox v-model="v$.checkbox.$model" :error="v$.checkbox.$invalid" />
              <SignupDialog />
              <span
                v-for="error of v$.checkbox.$errors"
                :key="error.$uid"
                class="text-red"
              >
              {{ error.$message }}
              </span>
            </q-card-section>
          </q-card>
          <q-btn type="submit" color="primary" class="full-width" size="lg" label="동의하고 가입" />
        </div>
        <div class="q-my-md text-weight-bold">
          가입 필수 정보 및 약관을 모두 확인해주세요.
        </div>
      </div>
    </q-form>
  </div>
</template>

<script>
import useVuelidate from '@vuelidate/core'
import { required, email, helpers, minLength, maxLength, sameAs } from '@vuelidate/validators'
import SignupDialog from '../../components/user/SignupDialog.vue'
export default {
  name: 'SignupPage',
  components: {
    SignupDialog,
  },
  setup() {
    return {
      v$: useVuelidate()
    }
  },
  data() {
    return {
      email: "",
      auth: "",
      nickname: "",
      password: "",
      passwordConfirm: "",
      checkbox: false,
    }
  },
  validations() {
    return {
      email: {
        required: helpers.withMessage('이메일은 필수 항목입니다.', required), 
        email: helpers.withMessage('이메일 양식이 아닙니다.', email),
        $autoDirty: true, $lazy: true
      },
      auth: {
        required: helpers.withMessage('인증번호는 필수 항목입니다.', required),
        $autoDirty: true, $lazy: true
      },
      nickname: {
        required: helpers.withMessage('닉네임은 필수 항목입니다.', required),
        minLength: helpers.withMessage('닉네임은 2~10사이 입니다.', minLength(2)),
        maxLength: helpers.withMessage('닉네임은 2~10사이 입니다.', maxLength(10)),
        $autoDirty: true, $lazy: true
      },
      password: {
        required: helpers.withMessage('비밀번호는 필수 항목입니다.', required), 
        // alphaNum,
        minLength: helpers.withMessage('비밀번호는 3~20사이 입니다.', minLength(3)),
        maxLength: helpers.withMessage('비밀번호는 3~20사이 입니다.', maxLength(20)),
        $autoDirty: true, $lazy: true
      },
      passwordConfirm: {
        required: helpers.withMessage('비밀번호 확인은 필수 항목입니다.', required),
        sameAsPassword: helpers.withMessage('비밀번호와 비밀번호 확인이 다릅니다.', sameAs(this.password)),
        $autoDirty: true, $lazy: true
      },
      checkbox: {
        required: helpers.withMessage('약관에 동의 해주세요.', required),
        $autoDirty: true, $lazy: true
      }
    }
  },
  methods: {
    goBack() {
      window.history.back()
    },
    async checkForm () {
      const isFormCorrect = await this.v$.$validate()
      // you can show some extra alert to the user or just leave the each field to show it's `$errors`.
      if (!isFormCorrect) return
      // actually submit form
    },
  }
}
</script>

<style>

</style>