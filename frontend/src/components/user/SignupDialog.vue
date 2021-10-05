<template>
  <div>
    <q-btn flat class="text-body1" label="약관 및 정보수신 동의" @click="icon = true" />
    <q-dialog v-model="icon">
      <TermCard />
    </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'
import TermCard from '../../components/user/TermCard.vue'
export default {
  name: 'SignupDialog',
  components: {
    TermCard,
  },
  setup() {
    return {
      icon: ref(false),
    }
  }
}
</script>

<style>

</style>