<template>
  <q-header reveal bordered class="bg-white text-white">
    <q-toolbar>
      <q-icon size="sm" color="black" name="arrow_back_ios" @click="goBack()" />
      <q-toolbar-title class="text-black text-weight-bold text-center no-padding" style="marginRight: 1.2rem">태그 추천</q-toolbar-title>
    </q-toolbar>
  </q-header>
  <div>
    <q-img
        src="../assets/image/coffeeimage1.jpg"
        :ratio="16/9"
        style="max-height: 150px;"
    >
    <div class="absolute-full custom-caption">
      <div class="text-h5">카페 큐레이션</div>
      <div class="text-subtitle1">태그를 통해 카페 추천을 받으세요</div>
    </div>
    </q-img>
  </div>
  <div class="q-pa-md flex column">
    <h5 style="color: #FAF8E2; marginLeft: 1.2rem; marginTop: 0rem; marginBottom: 1.3rem">커피</h5>
    <div class="q-gutter-sm flex justify-around" style="marginBottom: 2.2rem">
      <q-btn
        v-for="tag in coffeeTags" :key="tag"
        rounded
        style="background: #FAF8E2"
        text-color="primary"
        size="sm"
      >
        <q-checkbox v-model="selection" :val="tag" :label="tag" color="primary" />
      </q-btn>
    </div>
      <h5 style="color: #FAF8E2; marginLeft: 1.2rem; marginTop: 0; marginBottom: 1.3rem">공부</h5>
    <div class="q-gutter-sm flex justify-around" style="marginBottom: 2.2rem">
      <q-btn
        v-for="tag in studyTags" :key="tag"
        rounded
        style="background: #FAF8E2"
        text-color="primary"
        size="sm"
        >
        <q-checkbox v-model="selection" size="0.5rem" :val="tag" :label="tag" color="primary" />
      </q-btn>
    </div>
      <h5 style="color: #FAF8E2; marginLeft: 1.2rem; marginTop: 0; marginBottom: 1.3rem">데이트</h5>
    <div class="q-gutter-sm flex justify-around" style="marginBottom: 2.2rem">
      <q-btn
        v-for="tag in dateTags" :key="tag"
        rounded
        style="background: #FAF8E2"
        text-color="primary"
        size="sm"
        >
        <q-checkbox v-model="selection" size="0.5rem" :val="tag" :label="tag" color="primary" />
      </q-btn>
    </div>
      <h5 style="color: #FAF8E2; marginLeft: 1.2rem; marginTop: 0; marginBottom: 1.3rem">포토스팟</h5>
    <div class="q-gutter-sm flex justify-around">
      <q-btn
        v-for="tag in photoTags" :key="tag"
        rounded
        style="background: #FAF8E2"
        text-color="primary"
        size="sm"
        >
        <q-checkbox v-model="selection" size="0.5rem" :val="tag" :label="tag" color="primary" />
      </q-btn>
    </div>

  </div>
  <div style="textAlign: center; marginTop: 0.7rem">
    <q-btn
      style="background: #FAF8E2"
      text-color="primary"
      size="1.4rem"
      label="추천받기"
    />
  </div>
</template>

<script>
import { ref } from 'vue'
import { defineComponent } from 'vue';

export default defineComponent({
  name: 'TagRecommend',
  methods: {
    goBack() {
      window.history.back()
    },
  },
  setup () {
    return {
      coffeeTags: ['맛있는', ' 원두 ', ' 산미 ', ' 고소 '],
      studyTags: ['조용한', '콘센트', '데이트', ' 커플 '],
      dateTags: ['분위기', ' 소파 ', '예쁜', '자리'],
      photoTags: ['레트로', ' 모던 ', '창고형', '인스타'],
      selection: ref([ ])
    }
  }
})
</script>

<style>
body {
  background-color: #104138;
}

.q-checkbox__inner {
  display: none;
}

</style>