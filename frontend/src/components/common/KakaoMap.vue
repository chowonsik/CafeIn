<template>
  <div style="position: relative">
    <div id="map" style="width: 100%; height: 350px; z-index: 1"></div>
    <q-btn
      round
      color="primary"
      icon="my_location"
      @click="myLocation"
      style="position: absolute; bottom: 0.5rem; right: 0.5rem; z-index: 2"
    />
    <q-btn @click="getCafe">버튼</q-btn>
  </div>
</template>

<script>
import { createNamespacedHelpers } from "vuex";
const { mapState, mapActions, mapMutations } =
  createNamespacedHelpers("kakaomap");
import { nearCafeSearch } from "../../api/cafe";

export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      markers: [],
      infowindows: [],
      isOpenList: [],
      marker: null,
      nearCafes: [],
    };
  },
  created() {
    this.geoFind();
    this.getCafe();
  },
  computed: {
    ...mapState(["latitude", "longitude"]),
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=1582a2def7ba0fb35b9d01fe07ea8c10";
      document.head.appendChild(script);
    }
  },
  methods: {
    ...mapActions(["geoFind"]),
    ...mapMutations(["initMap"]),

    initMap() {
      this.geoFind();
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.latitude, this.longitude),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);

      //내 위치, 마커 이미지 설정
      var imageSrc = require("../../assets/image/location.png"), // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(26, 26), // 마커이미지의 크기입니다
        imageOption = { offset: new kakao.maps.Point(13, 13) }, // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
        iwPosition = new kakao.maps.LatLng(this.latitude, this.longitude); //인포윈도우 표시 위치입니다
      //마커 이미지 생성
      var markerImage = new kakao.maps.MarkerImage(
        imageSrc,
        imageSize,
        imageOption
      );
      // 마커를 생성합니다
      this.marker = new kakao.maps.Marker({
        position: iwPosition,
        image: markerImage,
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      this.marker.setMap(this.map);

      // this.infowindow = new kakao.maps.InfoWindow({
      //   map: this.map, // 인포윈도우가 표시될 지도
      //   position: iwPosition,
      //   content: iwContent,
      //   removable: iwRemoveable,
      // });

      // this.infowindow.open(this.map, this.marker);
      this.makeCafeMarker();
    },
    myLocation() {
      this.geoFind();

      // if (this.infowindow && this.infowindow.getMap()) {
      //   //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
      //   this.map.setCenter(this.infowindow.getPosition());
      //   return;
      // }

      //내 위치, 마커, 마커 이미지 설정
      var imageSrc = require("../../assets/image/location.png"), // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(26, 26), // 마커이미지의 크기입니다
        imageOption = { offset: new kakao.maps.Point(13, 13) }, // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
        iwPosition = new kakao.maps.LatLng(this.latitude, this.longitude); //인포윈도우 표시 위치입니다
      // iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
      //마커 이미지 생성
      var markerImage = new kakao.maps.MarkerImage(
        imageSrc,
        imageSize,
        imageOption
      );
      // 마커를 생성합니다
      this.marker = new kakao.maps.Marker({
        position: iwPosition,
        image: markerImage,
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      this.marker.setMap(this.map);

      // this.infowindow = new kakao.maps.InfoWindow({
      //   map: this.map, // 인포윈도우가 표시될 지도
      //   position: iwPosition,
      //   // content: iwContent,
      //   // removable: iwRemoveable,
      // });

      // this.infowindow.open(this.map, this.marker);

      this.map.setCenter(iwPosition);
    },
    // 카페 위치 이미지
    makeCafeMarker() {
      this.markers = [];
      this.isOpenList = [];
      this.infowindows = [];

      //마커 이미지 설정
      var imageSrc = require("../../assets/image/cafeLocation.png"), // 마커이미지의 주소입니다
        imageSize = new kakao.maps.Size(45, 45), // 마커이미지의 크기입니다
        imageOption = { offset: new kakao.maps.Point(22.5, 45) }; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

      for (let i = 0; i < this.nearCafes.length; i++) {
        //마커 설정
        var markerPosition = new kakao.maps.LatLng(
            this.nearCafes[i].cafeLatitude,
            this.nearCafes[i].cafeLongitude
          ), //마커 표시 위치입니다
          //인포윈도우 설정
          iwContent = `<span class="info-title">${this.nearCafes[i].cafeName}</span>`, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
          //마커 이미지 생성
          markerImage = new kakao.maps.MarkerImage(
            imageSrc,
            imageSize,
            imageOption
          );

        // 마커를 생성합니다
        let marker = new kakao.maps.Marker({
          position: markerPosition,
          image: markerImage,
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(this.map);

        //생성된 마커를 리스트에 기억해둡니다
        this.markers.push(marker);
        this.isOpenList.push(false);
        // console.log("markers", this.markers);

        let infowindow = new kakao.maps.InfoWindow({
          map: this.map,
          content: iwContent,
        });

        // 생성된 인포윈도우를 기억해둡니다
        this.infowindows.push(infowindow);

        //인포윈도우 css 강제 변경
        var infoTitle = document.querySelectorAll(".info-title");
        infoTitle.forEach(function (e) {
          var w = e.offsetWidth + 10;
          var ml = w / 2;
          e.parentElement.style.top = "82px";
          e.parentElement.style.left = "50%";
          e.parentElement.style.marginLeft = -ml + "px";
          e.parentElement.style.width = w + "px";
          e.parentElement.previousSibling.style.display = "none";
          e.parentElement.parentElement.style.border = "0px";
          e.parentElement.parentElement.style.background = "unset";
        });

        var map = this.map;
        var markers = this.markers;
        var isOpenList = this.isOpenList;
        var infowindows = this.infowindows;
        // 마커에 마우스오버 이벤트를 등록합니다
        kakao.maps.event.addListener(
          markers[i],
          "click",
          (function (i) {
            return function () {
              if (isOpenList[i]) infowindows[i].close();
              else infowindows[i].open(map, marker);
              isOpenList[i] = !isOpenList[i];
              console.log(isOpenList[i]);
            };
          })(i)
        );
      }
    },
    async getCafe() {
      try {
        const latitude = this.latitude;
        const longitude = this.longitude;
        const { data } = await nearCafeSearch(latitude, longitude);
        this.nearCafes = data.result;
        console.log(data.result);
        this.makeCafeMarker();
        this.map.setCenter(new kakao.maps.LatLng(latitude, longitude));
      } catch (error) {
        console.log(error);
      }
    },
  },
};
</script>

<style>
.info-title {
  display: block;
  background: #50627f;
  color: #fff;
  text-align: center;
  height: 24px;
  line-height: 22px;
  border-radius: 4px;
  padding: 0px 10px;
}
</style>
