<template>
  <div>
    <q-btn size="md" round color="primary" icon="search" text-color="white" @click="open('top')" />

    <q-dialog v-model="dialog" :position="position">
      <q-card style="width: 350px">
        <q-card-section class="row items-center q-pa-none">
          <q-space />
          <q-btn icon="close" flat round dense v-close-popup />
        </q-card-section>


        <q-card-section class="row items-center justify-center no-wrap">
          <div style="width: 350px">
            <q-input
              class="row items-center"
              v-model="cafeName"
              filled
              placeholder="오늘의 카페 검색"
              v-on:keyup.enter="goSearch()"
            >
              <div class="col-gutter-none content-center row items-center" @click="goSearch()" >
                <q-avatar color="primary" size="lg" text-color="white" icon="search" />
              </div>
            </q-input>
          </div>
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref } from 'vue'
export default {
  name: 'SearchDialog',
  data() {
    return {
      cafeName: '',
    }
  },
  setup() {
    const dialog = ref(false)
    const position = ref('top')
    return {
      dialog,
      position,

      open(pos) {
        position.value = pos
        dialog.value = true
      }
    }
  },
  methods: {
    goSearch() {
      this.$router.push({path:`/search/${this.cafeName}`}).catch(()=> {});
    }
  }
}
</script>

<style>

</style>