export function myReview(state, payload) {
  state.myReviews = payload;
}

export function selectedMyReview(state, payload) {
  state.selectedReview = payload;
  console.log(state.selectedReview);
}
