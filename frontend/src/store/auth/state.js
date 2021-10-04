import { getAuthFromCookie, getUserFromCookie } from "../../utils/cookies";

export default function () {
  return {
    accessToken: getAuthFromCookie() || "",
    userId: getUserFromCookie() || "",
  };
}