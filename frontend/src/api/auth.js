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

// 프로필 조회 API
function profileUser(userData) {
  return api.get("/api/users/me", userData);
}

// 회원탈퇴 API
function deleteUser(userData) {
  return api.patch("/api/users/deactivate", userData)
}

export { registerUser, loginUser, emailUser, profileUser, deleteUser };
