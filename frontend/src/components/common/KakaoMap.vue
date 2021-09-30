<template>
  <div>
    <div id="map" style="width:1000px;height:500px;"></div>
  </div>
</template>

<script>
export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      markers: [],
    }
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
    initMap() {
      // 지도를 표시할 div
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 5, // 지도의 확대 레벨
      };
      this.map = new kakao.maps.Map(container, options);
    },
  }
}
</script>

<style>

</style>