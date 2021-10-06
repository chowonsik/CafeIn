import { api } from "../boot/axios";

// 리뷰 작성 API
function createReview(ReviewData) {
  return api.post("/api/reviews", ReviewData);
}

// 카페 리뷰 조회 API
function getCafeReview(cafeId, pageNum) {
  return api.get(
    `/api/reviews?cafeId=${cafeId}&userId=&search=&size=10&page=${pageNum}&userId=`
  );
}

// 내 리뷰 조회 API
function getMyReview(userId) {
  return api.get(
    `/api/reviews?cafeId=&userId=${userId}&search=&size=10&page=1&cafeId=`
  );
}

// 리뷰 수정 API
function editReview(reviewId, reviewData) {
  return api.patch(`/api/reviews/${reviewId}`, reviewData);
}

// 리뷰 삭제 API
function deleteReview(reviewId) {
  return api.delete(`/api/reviews/${reviewId}`);
}
export { createReview, getCafeReview, getMyReview, editReview, deleteReview };
