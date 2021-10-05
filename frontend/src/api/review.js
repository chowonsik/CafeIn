import { api } from "../boot/axios";

// 리뷰 작성 API
function createReview(ReviewData) {
  return api.post("/api/reviews", ReviewData);
}

// 카페 리뷰 조회 API
function getCafeReview(cafeId) {
  return api.get(
    `/api/reviews?cafeId=${cafeId}&userId=&search=&size=10&page=1&userId=`
  );
}

export { createReview, getCafeReview };
