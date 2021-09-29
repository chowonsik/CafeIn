<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">마이페이지</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <q-card flat bordered class="q-pa-md q-my-sm bg-primary text-white">
      <q-card-section class="row justify-between">
        <div class="row items-center">
          <q-avatar color="white" text-color="black" icon="coffee" />
          <div class="text-h5 text-weight-bold q-px-md col">{{ nickname }}</div>
        </div>
        <div class="row items-center">
          <EditUserDialog />
        </div>
      </q-card-section>
    </q-card>
    <q-separator class="q-my-sm" color="grey-4" size="5px" />
    <q-toolbar class="q-my-sm">
      <q-toolbar-title class="text-black text-weight-bold no-padding">최근 조회한 카페</q-toolbar-title>
      <q-icon size="sm" color="black" name="arrow_forward_ios" />
    </q-toolbar>
    <RecentlyCafeItem />
    <q-separator class="q-my-sm" color="grey-4" size="5px" />
    <q-toolbar>
      <q-toolbar-title class="text-black text-weight-bold no-padding">내 리뷰</q-toolbar-title>
      <q-icon size="sm" color="black" name="arrow_forward_ios" />
    </q-toolbar>
    <q-separator class="q-my-sm" color="grey-4" size="5px" />
    <q-toolbar @click="icon = true">
      <q-toolbar-title class="text-black text-weight-bold no-padding">가입 약관</q-toolbar-title>
      <q-icon size="sm" color="black" name="arrow_forward_ios" />
    </q-toolbar>
    <q-dialog v-model="icon">
      <TermCard />
    </q-dialog>
    <q-separator class="q-my-sm" color="grey-4" size="5px" />
    <q-toolbar @click="goUserDelete()">
      <q-toolbar-title class="text-black text-weight-bold no-padding">회원 탈퇴</q-toolbar-title>
      <q-icon size="sm" color="black" name="arrow_forward_ios" />
    </q-toolbar>
    <q-separator class="q-my-sm" color="grey-4" size="5px" />
  </div>
</template>

<script>
import { ref } from 'vue'
import TermCard from '../../components/user/TermCard.vue'
import RecentlyCafeItem from '../../components/cafe/RecentlyCafeItem.vue'
import EditUserDialog from '../../components/user/EditUserDialog.vue'
export default {
  name: 'ProfilePage',
  components: {
    RecentlyCafeItem,
    TermCard,
    EditUserDialog,
  },
  setup() {
    return {
      icon: ref(false),
      edit: ref(false),
    }
  },
  data () {
    return {
      nickname: '김싸피',
    }
  },
  methods: {
    goBack() {
      window.history.back()
    },
    goUserDelete() {
      this.$router.push({path:'/users/delete'}).catch(()=>{})
    }
  }
}
</script>

<style>

</style>