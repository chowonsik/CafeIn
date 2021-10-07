<template>
  <q-footer reveal bordered class="bg-white text-grey-8">
    <q-toolbar>
      <q-toolbar-title class="row justify-around font-center items-center">
        <div class="col-gutter-none">
          <div class="text-center" @click="goHome()">
            <q-avatar text-color="grey" size="xl" icon="home" />
          </div>
          <p class="no-margin text-center" style="font-size: 12px">홈</p>
        </div>
        <div class="col-gutter-none" @click="goNear()">
          <div class="text-center">
            <q-avatar text-color="grey" size="xl" icon="explore" />
          </div>
          <p class="no-margin text-center" style="font-size: 12px">내주변</p>
        </div>
        <div class="col-gutter-none content-center">
          <SearchDialog />
        </div>
        <div class="col-gutter-none" @click="goTag()">
          <div class="text-center">
            <q-avatar text-color="primary" size="xl" icon="favorite" />
          </div>
          <p class="no-margin text-center word-color" style="font-size: 12px">찜</p>
        </div>
        <div class="col-gutter-none" @click="goProfile()">
          <div class="text-center">
            <q-avatar text-color="grey" size="xl" icon="person" />
          </div>
          <p class="no-margin text-center" style="font-size: 12px">MY</p>
        </div>
      </q-toolbar-title>
    </q-toolbar>
  </q-footer>
</template>

<script>
import SearchDialog from '../mainpage/SearchDialog.vue'
export default {
  name: 'TagFooter',
  components: {
    SearchDialog
  },
  methods: {
    goHome() {
      this.$router.push({path:'/'}).catch(()=>{})
    },
    goNear() {
      this.$router.push({path:'/nearcafe'}).catch(()=>{})
    },
    goProfile() {
      this.$router.push({path:'/profile'}).catch(()=>{})
    },
    goTag() {
      this.$router.push({path:'/mycafe'}).catch(()=>{})
    }
  }
}
</script>

<style>
.word-color {
  color: #104138;;
}
</style>