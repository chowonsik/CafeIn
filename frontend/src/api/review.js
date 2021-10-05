import { api } from "../boot/axios";

// 리뷰 작성 API
function createReview(ReviewData) {
  return api.post("/api/reviews", ReviewData)
}

export { createReview }