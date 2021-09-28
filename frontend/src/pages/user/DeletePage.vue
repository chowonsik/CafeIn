<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
        <q-toolbar-title class="text-black text-weight-bold text-center no-padding">회원탈퇴</q-toolbar-title>
      </q-toolbar>
    </q-header>
    <q-card flat bordered class="q-pt-md">
      <q-card-section>
        <div class="text-h6 text-weight-bold">회원탈퇴 안내</div>
      </q-card-section>

      <q-card-section class="q-pt-none">
        회원 탈퇴 시 해당 계정의 개인정보는 모두 삭제됩니다.<br>
        이후 복구는 불가능 합니다.
      </q-card-section>

      <q-space />

      <q-card-section>
        서비스 탈퇴 후 재가입은 불가능 합니다.
      </q-card-section>
    </q-card>
    <q-footer class="bg-white">
      <div class="text-black q-pa-sm flex row items-center">
        <q-icon name="warning" size="sm" class="text-red q-px-sm" />
        <div class="col">회원탈퇴를 원하시면 탈퇴 버튼을 눌러주세요.</div>
      </div>
      <q-separator />
      <q-btn flat class="full-width text-weight-bold" size="lg" color="red" label="회원탈퇴" />
    </q-footer>
  </div>
</template>

<script>
export default {
  name: 'DeletePage',
  methods: {
    goBack() {
      window.history.back()
    },
  }
}
</script>

<style>

</style>