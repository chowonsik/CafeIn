import state from "src/store/auth/state";

export function setInterceptors(api) {
  // 요청 인터셉터 추가
  api.interceptors.request.use(
    async function (config) {
      config.headers["X-ACCESS-TOKEN"] = state.accessToken
      return await config;
    },
    function (error) {
      return Promise.reject(error);
    }
  );
      
  // 응답 인터셉터 추가
  api.interceptors.response.use(
    async function (response) {
      return await response;
    },
    function (error) {
      return Promise.reject(error);
    }
  );
  return api;
}