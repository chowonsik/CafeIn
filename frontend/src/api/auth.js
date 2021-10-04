import { api } from "../boot/axios";

// 회원가입 API
function registerUser(userData) {
  return api.post("/api/users/signup", userData);
}

// 로그인 API
function loginUser(userData) {
  return api.post("/api/users/signin", userData);
}

// 이메일 인증번호전송 API
function emailUser(userData) {
  return api.post("/api/users/email", userData);
}

export { registerUser, loginUser, emailUser };
