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
          <q-btn @click="goDelete()" flat label="예" color="red" v-close-popup />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'
import { deleteUser } from '../../api/auth'
import { deleteCookie } from "../../utils/cookies"
import { createNamespacedHelpers } from 'vuex'
const { mapMutations } = createNamespacedHelpers("auth")

export default {
  name: "DeleteDialog",
  setup () {
    return {
      confirm: ref(false),
    }  
  },
  methods: {
    ...mapMutations(['clearToken', 'clearUserId']),
    async goDelete() {
      try {
        const { data } = await deleteUser()
        // console.log(data)
        this.clearToken
        this.clearUserId
        deleteCookie('til_auth')
        deleteCookie('til_user')
        this.$router.push("/")
        alert(data.message)
      } catch(error) {
        console.error(error)
      }
    }
  }
}
</script>

<style>

</style>