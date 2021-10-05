import { getMyReview } from "../../api/review";
import { getUserFromCookie } from "../../utils/cookies";

export async function myReview({ commit }) {
  try {
    const userId = getUserFromCookie();
    const { data } = await getMyReview(userId);
    commit("myReview", data.result);
  } catch (error) {
    console.log(error);
  }
}
