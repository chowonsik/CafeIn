<template>
	<div>
		<div v-for="cafe in myReviews" :key="cafe.id">
			<q-card flat bordered>
        <q-card-section style="padding: 0" class="row justify-between">
          <q-rating
            v-model="cafe.reviewScore"
            size="1.5em"
            color="orange"
            icon="star_border"
            icon-selected="star"
            icon-half="star_half"
            readonly
            class="q-ml-md"
          />
          <ReviewListDialog @click="selectedMyReview(cafe)"/>
        </q-card-section>
        <q-card-section style="padding: 0">
          <div class="text-subtitle1 text-weight-bold q-ml-md">
            {{ cafe.cafeName }}
          </div>
        </q-card-section>

        <q-card-section class="row">
          <div class="col-4">
            <q-img
              class="rounded-borders"
              :src="cafe.cafeImgUrl"
              :ratio="1"
              style="height: 100px; width: 100px;"
            />
          </div>
          <div class="col-8">
          <!-- 확장되지 않았을 때 보일 리뷰 내용 -->
            <q-item v-show="cafe.expanded" class="no-padding">
              <q-item-label v-if="cafe.reviewContent.length < 100">
                {{ cafe.reviewContent }}
              </q-item-label>
              <q-item-label v-else :lines="3">
                {{ cafe.reviewContent.substr(0, 100) }}
              </q-item-label>
            </q-item>

            <!-- 확장 됐을 때 보일 리뷰 내용 -->
            <q-item v-show="!cafe.expanded" class="no-padding">
              <q-item-label>{{ cafe.reviewContent }}</q-item-label>
            </q-item>
            <!-- 확장 버튼 -->
            <q-item
              v-show="cafe.reviewContent.length >= 100"
              style="padding-bottom: 0px; padding-top: 0px"
              class="no-padding"
            >
              <q-btn
                color="grey"
                round
                flat
                dense
                :label="cafe.expanded ? '자세히 보기' : '간략히'"
                @click="cafe.expanded = !cafe.expanded"
              />
            </q-item>
            <div class="row justify-end">
              <q-item-label caption class="no-margin" style="marginTop: 1rem">{{ dateTime(cafe.reviewCreatedAt) }}</q-item-label>
            </div>
				</div>
        </q-card-section>
			</q-card>
      <q-separator />
		</div>
	</div>
</template>

<script>
import ReviewListDialog from './ReviewListDialog.vue'
import { createNamespacedHelpers } from 'vuex'
import moment from 'moment'
const { mapState, mapActions, mapMutations } = createNamespacedHelpers("review")

export default {
	name: 'ReviewItem',
	components: {
		ReviewListDialog,
	},
	data() {
		return {
		}
	},
  methods: {
    ...mapActions(['myReview']),
    ...mapMutations(['selectedMyReview']),
    dateTime(value) {
      return moment(value).format('YYYY-MM-DD')
    },
  },
  created() {
    this.myReview()
  },
  computed: {
    ...mapState(['myReviews'])
  }
}
</script>

<style>

</style>