import { loginUser } from "../../api/auth"
import { saveAuthToCookie, saveUserToCookie } from "../../utils/cookies"

export async function LOGIN({ commit }, userData) {
  const { data } = await loginUser(userData)
  if (data.isSuccess == true) {
    commit('setToken', data.result.accessToken)
    commit('setUserId', data.result.userId)
    saveAuthToCookie(data.result.accessToken)
    saveUserToCookie(data.result.userId)
    console.log(data)
    alert(data.message)
    return data
  } else {
    alert(data.message)
  }
}
