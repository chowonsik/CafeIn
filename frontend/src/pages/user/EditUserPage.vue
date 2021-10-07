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
                <q-input class="no-margin no-padding" outlined v-model="v$.nickname.$model" :error="v$.nickname.$invalid" placeholder="닉네임 입력" clearable autocapitalize="off" />
                <span
                  v-for="error of v$.nickname.$errors"
                  :key="error.$uid"
                  class="text-red"
                >
                {{ error.$message }}
                </span>
              </div>
              <div class="q-mt-lg">
                <span>변경할 비밀번호</span>
                <q-input class="no-margin no-padding" type="password" outlined v-model="v$.password.$model" :error="v$.password.$invalid" placeholder="변경할 비밀번호 입력(영문 혹은 숫자)" clearable autocapitalize="off" />
                <span
                  v-for="error of v$.password.$errors"
                  :key="error.$uid"
                  class="text-red"
                >
                {{ error.$message }}
                </span>
              </div>
              <div class="q-mt-lg">
                <span>변경할 비밀번호 확인</span>
                <q-input class="no-margin no-padding" type="password" outlined v-model="v$.passwordConfirm.$model" :error="v$.passwordConfirm.$invalid" placeholder="변경할 비밀번호 확인(영문 혹은 숫자)" clearable autocapitalize="off" />
                <span
                  v-for="error of v$.passwordConfirm.$errors"
                  :key="error.$uid"
                  class="text-red"
                >
                {{ error.$message }}
                </span>
              </div>
              <q-btn :disabled="v$.$invalid" color="primary" type="submit" class="full-width q-ma-lg" size="lg" label="프로필 수정" />
            </div>
          </div>
        </q-form>
      </q-card>
    </div>

  </div>
</template>

<script>
import { profileUser, editUser } from '../../api/auth'
import useVuelidate from '@vuelidate/core'
import { required, minLength, maxLength, sameAs, helpers } from '@vuelidate/validators'
export default {
  name: "EditUserPage",
  setup() {
    return {
      v$: useVuelidate()
    }
  },
  data() {
    return {
      nickname: '',
      oldNickname: '',
      password: '',
      passwordConfirm: '',
    }
  },
  validations() {
    return {
      nickname: {
        required: helpers.withMessage('닉네임은 필수 항목입니다.', required),
        minLength: helpers.withMessage('닉네임은 2~10사이 입니다.', minLength(2)),
        maxLength: helpers.withMessage('닉네임은 2~10사이 입니다.', maxLength(10)),
        $autoDirty: true, $lazy: true
      },
      password: {
        // alphaNum,
        minLength: helpers.withMessage('비밀번호는 3~20사이 입니다.', minLength(3)),
        maxLength: helpers.withMessage('비밀번호는 3~20사이 입니다.', maxLength(20)),
        $autoDirty: true, $lazy: true
      },
      passwordConfirm: {
        sameAsPassword: helpers.withMessage('비밀번호와 비밀번호 확인이 다릅니다.', sameAs(this.password)),
        $autoDirty: true, $lazy: true
      },
    }
  },
  created() {
    this.getMyInfo()
  },
  methods: {
    async checkForm () {
      const isFormCorrect = await this.v$.$validate()
      // you can show some extra alert to the user or just leave the each field to show it's `$errors`.
      if (!isFormCorrect) return
      // actually submit form
      this.editMyInfo()
    },
    goBack() {
      window.history.back()
    },
    async getMyInfo() {
      try {
        const { data } = await profileUser()
        this.nickname = data.result.nickname
        this.oldNickname = data.result.nickname
      } catch (error) {
        console.error(error)
      }
    },
    async editMyInfo() {
      try {
        const userData = {
          nickname: this.nickname,
          password: this.password
        }
        if (this.oldNickname == this.nickname) {
          alert("닉네임을 변경해주세요.")
          this.password = ""
          this.passwordConfirm = ""
        } else {
          const { data } = await editUser(userData)
          alert(data.message)
          this.$router.push({path:'/profile'}).catch(()=>{})

        }
      } catch (error) {
        console.log(error)
      }
      
    }
  }
}
</script>

<style>

</style>