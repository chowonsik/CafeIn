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
                <span>변경할 비밀번호</span>
                <q-input type="password" outlined v-model="password" />
              </div>
              <div class="q-mt-lg">
                <span>변경할 비밀번호 확인</span>
                <q-input type="password" outlined v-model="passwordConfirm" />
              </div>
              <q-btn @click="editMyInfo()" color="primary" type="submit" class="full-width q-ma-lg" size="lg" label="프로필 수정" />
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
import { required, minLength, maxLength, sameAs } from '@vuelidate/validators'
export default {
  name: "EditUserPage",
  data() {
    return {
      nickname: '',
      password: '',
      passwordConfirm: '',
    }
  },
  created() {
    this.getMyInfo()
  },
  methods: {
    checkForm() {

    },
    goBack() {
      window.history.back()
    },
    async getMyInfo() {
      try {
        const { data } = await profileUser()
        this.nickname = data.result.nickname
      } catch (error) {
        console.log(error)
      }
    },
    async editMyInfo() {
      try {
        const userData = {
          nickname: this.nickname,
          password: this.password
        }
        const { data } = await editUser(userData)
        console.log(data)
      } catch (error) {
        console.log(error)
      }
      
    }
  }
}
</script>

<style>

</style>