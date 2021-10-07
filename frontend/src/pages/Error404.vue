<template>
  <div class="fullscreen bg-primary text-white text-center flex flex-center">
    <q-img
      src="../assets/image/background1.jpg"
      :ratio="1"
      style="height: 100vh"
    >
      <div class="absolute-full text-center column flex flex-center">
        <div style="font-size: 20vh">
          404
        </div>
        <div class="text-h2" style="opacity:.8">
          PAGE NOT FOUND
        </div>
        <q-btn
          class="q-mt-xl text-weight-bolder"
          color="white"
          text-color="primary"
          unelevated
          to="/"
          label="Go Home"
          no-caps
        />
      </div>
    </q-img>
  </div>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'Error404'
})
</script>
