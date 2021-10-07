<template>
  <div>
    <q-btn
      color="primary"
      rounded
      dense
      size="0.7rem"
      label="메뉴 보기"
      @click="icon = true"
      style="padding-left: 5px; padding-right: 6px"
    />

    <q-dialog v-model="icon">
      <q-card style="min-width: 240px">
        <q-card-section class="row items-center q-pb-none">
          <div class="text-h6">메뉴({{ cafeMenu.length }})</div>
          <q-space />
          <q-btn icon="close" flat round dense v-close-popup />
        </q-card-section>

        <q-card-section>
          <q-item v-for="menu in cafeMenu" :key="menu">
            <q-item-section>
              <q-item-label>{{ menu.menuName }}</q-item-label>
              <q-item-label>{{ menu.menuPrice }}원</q-item-label>
              <q-separator />
            </q-item-section>
          </q-item>
        </q-card-section>
      </q-card>
    </q-dialog>
  </div>
</template>

<script>
import { ref } from "vue";
import { cafeMenu } from "../../api/cafe";

export default {
  data() {
    return {
      cafeMenu: [],
    };
  },
  setup() {
    return {
      icon: ref(false),
    };
  },
  methods: {
    async menuItem() {
      try {
        const cafeId = this.$route.params.id;
        const { data } = await cafeMenu(cafeId);
        // console.log(data)
        this.cafeMenu = data.result;
      } catch (error) {
        console.error(error);
      }
    },
  },
  created() {
    this.menuItem();
  },
};
</script>

<style></style>
