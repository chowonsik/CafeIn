import { api } from '../boot/axios'

// 회원가입 API
// function registerUser(userData) {
//   return api.post('/api/user/signup', userData)
// }
  
// 로그인 API
function loginUser(userData) {
  return api.post('/api/users/signin', userData)
}

export {
//   registerUser,
  loginUser,
}