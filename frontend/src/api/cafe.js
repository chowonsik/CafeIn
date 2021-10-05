import { api } from "../boot/axios";

// 카페 상세 조회 API
function cafeDetail(cafeId) {
  return api.get(`/api/cafes/${cafeId}`);
}

// 영업시간 조회 API
function cafeBhour(cafeId) {
  return api.get(`/api/bhours?cafeId=${cafeId}`);
}

// 카페메뉴 조회 API
function cafeMenu(cafeId) {
  return api.get(`/api/menus?cafeId=${cafeId}`);
}

// 카페 찜 등록 API
function bookmark(cafeId) {
  return api.post(`/api/bookmarks`, cafeId);
}

// 카페 찜 취소 API
function cancelBookmark(cafeId) {
  return api.delete(`/api/bookmarks?cafeId=${cafeId}`);
}

export { cafeDetail, cafeBhour, cafeMenu, bookmark, cancelBookmark };
