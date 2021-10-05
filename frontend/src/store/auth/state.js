import { getAuthFromCookie, getUserFromCookie } from "../../utils/cookies";

export default {
  accessToken: getAuthFromCookie() || "",
  userId: getUserFromCookie() || "",
}