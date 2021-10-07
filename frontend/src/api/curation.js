import { api } from "../boot/axios";

// 추천 API
function tagCafe(latitude, longtitude, tag) {
  return api.post(
    `/api/cafes/curation?type=1&latitude=${latitude}&longitude=${longtitude}&category=${tag}&distance=10&size=10&page=1`
  );
}

// 큐레이션 유저 찜 기반 조회 API
function getCuration(latitude, longtitude) {
  return api.get(
    `/api/cafes/curation?type=2&latitude=${latitude}&longitude=${longtitude}&distance=10&size=10&page=1`
  );
}

export { tagCafe, getCuration };
