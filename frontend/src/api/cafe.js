import { api } from "../boot/axios";

// 카페 상세 조회 API
function cafeDetail(cafeId) {
  return api.get(`/api/cafes/${cafeId}`)
}

// 영업시간 조회 API
function cafeBhour(cafeId) {
  return api.get(`/api/bhours?cafeId=${cafeId}`)
}

// 카페메뉴 조회 API
function cafeMenu(cafeId) {
  return api.get(`/api/menus?cafeId=${cafeId}`)
}

export { cafeDetail, cafeBhour, cafeMenu }