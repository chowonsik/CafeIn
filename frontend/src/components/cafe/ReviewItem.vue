<template>
	<div>
		<div v-for="cafe in myReviews" :key="cafe.id">
			<q-card flat bordered>
      <q-card-section horizontal>
				<q-card-section class="col-4 flex flex-center no-padding q-mt-xs" @click="$router.push({ path: `/cafes/${cafe.cafeId}`})">
          <q-img
            class="rounded-borders"
            :src="cafe.cafeImgUrl"
						:ratio="1"
						style="height: 100px; width: 100px"
          />
        </q-card-section>
        <q-card-section class="q-pt-xs col-6">
          <div class="text-subtitle1 text-weight-bold q-mt-sm q-mb-xs">{{ cafe.cafeName }}</div>
            <q-rating
              v-model="cafe.reviewScore"
              size="1em"
              color="primary"
              icon="star_border"
              icon-selected="star"
              icon-half="star_half"
              readonly
            />
          <q-item-label>{{ dateTime(cafe.reviewCreatedAt) }}</q-item-label>
        </q-card-section>
				<q-card-section class="no-padding col-2">
					<ReviewListDialog @click="selectedMyReview(cafe)"/>
				</q-card-section>
					
      </q-card-section>
			<q-card-section class="q-ml-md q-mt-xs no-padding row items-center">
				<div>
					<q-chip outline color="primary" text-color="white" icon="coffee">
						리뷰
					</q-chip>
				</div>
				<div class="col-9">
					{{ cafe.reviewContent }}
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