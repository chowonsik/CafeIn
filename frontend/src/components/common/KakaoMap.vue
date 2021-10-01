<template>
  <div>
    <div id="map" style="width:1000px;height:350px;"></div>
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
        center: new kakao.maps.LatLng(37.56013, 126.99058), // 지도의 중심좌표
        level: 5, // 지도의 확대 레벨
      };
      this.map = new kakao.maps.Map(container, options);
    },
  }
}
</script>

<style>

</style>