export function setToken(state, accessToken) {
  state.accessToken = accessToken
  console.log(accessToken)
}

export function setUserId (state, userId) {
  state.userId = userId
  console.log(userId)
}

export function clearToken(state) {
  state.accessToken = ""
  console.log(accessToken)
}

export function clearUserId (state) {
  state.userId = ""
  console.log(userId)
}
