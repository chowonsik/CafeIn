<template>
  <div>
    <q-header reveal bordered class="bg-white text-white">
      <q-toolbar>
        <q-icon
          size="sm"
          color="black"
          name="arrow_back_ios"
          @click="goBack()"
        />
        <q-toolbar-title
          class="text-black text-weight-bold text-center no-padding"
          style="marginright: 1.2rem"
          >카페 정보</q-toolbar-title
        >
      </q-toolbar>
    </q-header>
    <div>
      <q-img
        :src="cafeInfo.cafeImgUrl"
        :ratio="4 / 3"
        style="max-height: 300px"
      >
        <template v-slot:error>
          <q-img :src="coffeeImg" />
        </template>
      </q-img>
    </div>
    <div class="q-py-none">
      <q-card class="my-card" flat>
        <q-card-section class="q-mx-md">
          <div class="row justify-between">
            <div class="text-h5 text-bold">{{ cafeInfo.cafeName }}</div>
            <CafeMenuDialog />
          </div>
          <div class="text-subtitle2">{{ cafeInfo.cafeAddress }}</div>
        </q-card-section>
        <q-card-section class="q-py-xs q-mx-md">
          <!-- 북마크 개수 -->
          <q-icon name="favorite" class="text-negative" /><span
            style="margin-left: 3px"
            >{{ cafeInfo.bookmarkCnt }}</span
          >
          <!-- 평점 평균 -->
          <q-icon
            name="star"
            class="text-yellow"
            style="margin-left: 7px"
          /><span style="margin-left: 3px">{{
            parseFloat(cafeInfo.cafeAvgScore).toFixed(1)
          }}</span>
          <!-- 리뷰 개수 -->
          <q-icon
            name="edit_note"
            class="text-primary"
            style="margin-left: 7px"
          /><span>{{ cafeInfo.reviewCnt }}</span>
        </q-card-section>

        <q-separator />

        <q-card-section class="q-py-xs q-mx-md">
          <div class="text-h6 text-bold">매장소개</div>
          <q-item-label caption style="padding: 3px; margin-bottom: 5px"
            >전화번호 : {{ cafeInfo.cafeTel }}</q-item-label
          >
          <div class="text-h6 text-bold" style="margintop: 1rem">영업시간</div>
          <div style="padding: 3px">
            <q-item-label v-if="bhourEtc" caption>{{ bhourEtc }}</q-item-label>
            <q-item-label v-else caption
              >오전 {{ bhourInfo.bhourStartTime }} ~ 오후
              {{ bhourInfo.bhourEndTime }}</q-item-label
            >
          </div>
        </q-card-section>

        <q-separator />
      </q-card>
    </div>
    <div class="row justify-center">
      <div v-if="cafeInfo.reviewCnt >= 20">
        <WordCloud />
      </div>
      <div
        v-else
        color="primary"
        class="text-subtitle2 text-bold"
        style="padding: 20px; color: gray"
      >
        리뷰가 쌓이면 워드클라우드가 보여요!
      </div>
    </div>
    <q-separator />
    <div class="q-pa-md">
      <q-card class="my-card" flat>
        <q-card-section style="padding-top: 0px; padding-bottom: 0px">
          <div class="text-h6 text-bold">리뷰</div>
        </q-card-section>
        <div
          v-if="cafeInfo.reviewCnt == 0"
          color="primary"
          class="text-subtitle2 text-bold text-center"
          style="padding: 20px; color: gray"
        >
          작성된 리뷰가 없어요.
        </div>

        <q-infinite-scroll @load="onLoad" :offset="250">
          <q-list v-for="(review, index) in items" :key="index">
            <q-item style="min-height: 45px">
              <!-- 별점 -->
              <q-rating
                class="star"
                v-model="review.reviewScore"
                size="1em"
                color="orange"
                icon="star_border"
                icon-selected="star"
                readonly
              />
              <!-- 날짜 -->
              <q-item-label caption class="date">{{
                dateTime(review.reviewCreatedAt)
              }}</q-item-label>
            </q-item>
            <!-- 확장되지 않았을 때 보일 리뷰 내용 -->
            <q-item v-show="review.expanded">
              <q-item-label v-if="review.reviewContent.length < 100">
                {{ review.reviewContent }}
              </q-item-label>
              <q-item-label v-else :lines="3">
                {{ review.reviewContent.substr(0, 100) }}
              </q-item-label>
            </q-item>

            <!-- 확장 됐을 때 보일 리뷰 내용 -->
            <q-item v-show="!review.expanded">
              <q-item-label>{{ review.reviewContent }}</q-item-label>
            </q-item>
            <!-- 확장 버튼 -->
            <q-item
              v-show="review.reviewContent.length >= 100"
              style="padding-bottom: 0px; padding-top: 0px"
            >
              <q-btn
                color="grey"
                round
                flat
                dense
                :label="review.expanded ? '자세히 보기' : '간략히'"
                @click="review.expanded = !review.expanded"
              />
            </q-item>
          </q-list>
          <template v-slot:loading>
            <div class="row justify-center q-my-md">
              <q-spinner-puff color="deep-orange" size="40px" />
              <!-- <q-spinner-dots color="primary" size="40px" /> -->
            </div>
          </template>
        </q-infinite-scroll>
      </q-card>
    </div>

    <q-footer reveal bordered class="bg-white text-grey-8">
      <q-toolbar>
        <q-toolbar-title class="row justify-between items-center">
          <q-btn
            v-if="bookmarked"
            style="marginleft: 1rem"
            flat
            registerBookmark
            @click="deleteBookmark()"
          >
            <span class="material-icons" style="font-size: 2rem; color: #ff6666"
              >favorite</span
            >
          </q-btn>
          <q-btn
            v-else
            style="marginleft: 1rem"
            flat
            @click="registerBookmark()"
          >
            <span class="material-icons" style="font-size: 2rem"
              >favorite_border</span
            >
          </q-btn>
          <div style="marginright: 1rem">
            <ReviewDialog />
          </div>
        </q-toolbar-title>
      </q-toolbar>
    </q-footer>
  </div>
</template>

<script>
import moment from "moment";
import ReviewDialog from "../components/cafe/ReviewDialog.vue";
import CafeMenuDialog from "../components/cafe/CafeMenuDialog.vue";
import WordCloud from "../components/cafe/WordCloud.vue";
import { cafeDetail, cafeBhour, bookmark, cancelBookmark } from "../api/cafe";
import { ref } from "vue";
import { api } from "../boot/axios";
import { useRoute } from "vue-router";
import coffeeImg from "../assets/image/coffee.png"
import { createNamespacedHelpers } from 'vuex'
const { mapState } = createNamespacedHelpers("auth")


export default {
  name: "CafeDetail",

  components: {
    ReviewDialog,
    CafeMenuDialog,
    WordCloud,
  },
  data() {
    return {
      bookmarked: null,
      cafeInfo: [],
      bhourInfo: [],
      bhourEtc: "",
      reviews: [],
      bookmarkCount: 0,
      page: 1,
      list: [],
      coffeeImg: coffeeImg,
    }
  },
  computed: {
    ...mapState(['accessToken'])
  },
  setup() {
    const items = ref([]);
    const cafeId = useRoute().params.id;

    return {
      items,
      onLoad(index, done) {
        setTimeout(() => {
          api
            .get(
              `/api/reviews?cafeId=${cafeId}&search=&size=10&page=${index}`
            )
            .then(({ data }) => {
              for (let i = 0; i < data.result.length; i++) {
                if (data.result[i].reviewContent !== "") {
                  data.result[i].expanded = "false";
                  items.value.push(data.result[i]);
                }
              }
              return data.result;
            })
            .then((response) => {
              if (response.length === 0) {
                done(true);
              } else {
                done(false);
              }
            });
          // done()
        }, 500);
      },
    };
  },
  methods: {
    goBack() {
      window.history.back();
    },
    dateTime(value) {
      return moment(value).format("YYYY-MM-DD");
    },
    async cafeItem() {
      try {
        const cafeId = this.$route.params.id;
        const { data } = await cafeDetail(cafeId);
        // console.log(data);
        this.cafeInfo = data.result;
        this.bookmarkCount = data.result.bookmarkCnt;
        this.bookmarked = data.result.isBookMark;
        // console.log(this.bookmarked)
      } catch (error) {
        console.error(error);
      }
    },
    async bhourItem() {
      try {
        const cafeId = this.$route.params.id;
        const { data } = await cafeBhour(cafeId);
        // console.log(data.result)
        if (data.result.length > 0) {
          this.bhourInfo = data.result[0];
        } else {
          this.bhourEtc = "영업 시간 준비중입니다.";
        }
        // console.log("영업시간", this.bhourInfo)
        // console.log("영업시간", this.bhourEtc)
      } catch (error) {
        console.error(error);
      }
    },
    async registerBookmark() {
      if (this.accessToken === "") {
        alert("로그인이 필요한 페이지입니다.")
        this.$router.push("/users/login")
      } else {
        try {
          const cafeId = {
            cafeId: this.$route.params.id,
          };
          const { data } = await bookmark(cafeId);
          if (data.isSuccess === true) {
            this.bookmarked = 1;
            this.bookmarkCount += 1;
          }
          // console.log(data);
        } catch (error) {
          console.log(error);
        }
      }
    },
    async deleteBookmark() {
      if (this.accessToken === "") {
        alert("로그인이 필요한 페이지입니다.")
        this.$router.push("/users/login")
      } else {
        try {
          const cafeId = this.$route.params.id;
          const { data } = await cancelBookmark(cafeId);
          if (data.isSuccess === true) {
            this.bookmarked = 0;
            this.bookmarkCount -= 1;
          }
          // console.log(data);
        } catch (error) {
          console.log(error);
        }
        
      }
    },
    setCookie(cookie_name, value, days) {
      var exdate = new Date();
      exdate.setDate(exdate.getDate() + days);

      var cookie_value =
        escape(value) +
        (days == null ? "" : "; expires=" + exdate.toUTCString());
      document.cookie = cookie_name + "=" + cookie_value;
    },
    getCookie(cookie_name) {
      var x, y;
      var val = document.cookie.split(";");

      for (var i = 0; i < val.length; i++) {
        x = val[i].substr(0, val[i].indexOf("="));
        y = val[i].substr(val[i].indexOf("=") + 1);
        x = x.replace(/^\s+|\s+$/g, ""); // 앞과 뒤의 공백 제거하기
        if (x == cookie_name) {
          return unescape(y); // unescape로 디코딩 후 값 리턴
        }
      }
    },
    addCookie(id) {
      var items = this.getCookie("recentCafe"); // 이미 저장된 값을 쿠키에서 가져오기
      var maxItemNum = 5; // 최대 저장 가능한 아이템개수
      var expire = 7; // 쿠키값을 저장할 기간
      if (items) {
        var itemArray = items.split(",");
        if (itemArray.indexOf(id) != -1) {
          // 이미 존재하는 경우 종료
          console.log("Already exists.");
        } else {
          // 새로운 값 저장 및 최대 개수 유지하기
          itemArray.unshift(id);
          if (itemArray.length > maxItemNum) itemArray.length = 5;
          items = itemArray.join(",");
          this.setCookie("recentCafe", items, expire);
        }
      } else {
        // 신규 id값 저장하기
        this.setCookie("recentCafe", id, expire);
      }
    },
  },
  created() {
    this.cafeItem();
    this.bhourItem();
    this.addCookie(this.$route.params.id);
  },
};
</script>

<style scoped>
.star {
  align-self: end;
  margin-bottom: 1.6px;
}
.date {
  align-self: end;
  margin-left: 5px;
}
</style>
