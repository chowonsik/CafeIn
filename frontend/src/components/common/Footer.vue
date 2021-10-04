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
          <q-avatar color="primary" size="xl" text-color="white" icon="search" />
        </div>
        <div class="col-gutter-none">
          <div class="text-center">
            <q-avatar text-color="grey" size="xl" icon="favorite" />
          </div>
          <p class="no-margin text-center" style="font-size: 12px">찜</p>
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
export default {
  name: 'Footer',
  methods: {
    goHome() {
      this.$router.push({path:'/'}).catch(()=>{})
    },
    goNear() {
      this.$router.push({path:'/nearcafe'}).catch(()=>{})
    },
    goProfile() {
      this.$router.push({path:'/profile'}).catch(()=>{})
    }
  }
}
</script>

<style>

</style>