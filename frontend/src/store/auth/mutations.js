export function setToken(state, accessToken) {
  state.accessToken = accessToken
}

export function setUserId (state, userId) {
  state.userId = userId
}

export function clearToken(state) {
  state.accessToken = ""
}

export function clearUserId (state) {
  state.userId = ""
}
