import { getMyReview } from "../../api/review";
import { getUserFromCookie } from "../../utils/cookies";

export async function myReview({ commit }) {
  try {
    const userId = getUserFromCookie();
    const { data } = await getMyReview(userId);
    // console.log(userId);
    // console.log(data);
    commit("myReview", data.result);
  } catch (error) {
    console.error(error);
  }
}
