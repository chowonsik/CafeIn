<template>
  <div style="position: relative;">
    <div id="map" style="width: 100%; height:350px; z-index: 1"></div>
    <q-btn round color="primary" icon="my_location" 
      @click="myLocation" style="position: absolute; bottom: 0.5rem; right: 0.5rem; z-index: 2"
    />
    <q-btn @click="getCafe">버튼</q-btn>
  </div>
</template>

<script>
import { createNamespacedHelpers } from 'vuex'
const { mapState, mapActions, mapMutations } = createNamespacedHelpers("kakaomap")
import { api } from "../../boot/axios";

export default {
  name: "KakaoMap",
  data() {
    return {
      map: null,
      markers: [],
      infowindow: null,
      marker: null,
      markerPositions: [
        {
          name: '달빛카페',
          latlng: [36.35791659289339, 127.30811267515648],
        },
        {
          name: '카페보스턴',
          latlng: [36.35821830290163, 127.3071174943377],
        },
        {
          name: '카페드롭탑',
          latlng: [36.360541204137036, 127.30362819202823]
        },
        {
          name: '심쿵카페',
          latlng: [36.35752425687611, 127.30817520935133]
        },
        {
          name: '카페펜트리',
          latlng: [36.36060013967501, 127.30770314054023]
        }
      ],
      nearCafes: []
    }
  },
  created() {
    this.geoFind()
  },
  computed: {
    ...mapState(['latitude', 'longitude']),
  },
  beforeMount() {
    this.getCafe()
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
    ...mapActions(['geoFind']),
    ...mapMutations(['initMap']),

    initMap() {
      this.geoFind()
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(this.latitude, this.longitude),
        level: 5,
      };
      this.map = new kakao.maps.Map(container, options);

      var iwContent = '<div style="padding:5px;">내 위치</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(this.latitude, this.longitude), //인포윈도우 표시 위치입니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      // 마커를 생성합니다
      this.marker = new kakao.maps.Marker({
        position: iwPosition
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      this.marker.setMap(this.map);

      this.infowindow = new kakao.maps.InfoWindow({
        map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent,
        removable: iwRemoveable,
      });

      this.infowindow.open(this.map, this.marker)
      // this.makeCafeMarker()
    },
    myLocation() {
      this.geoFind()
  
      // if (this.infowindow && this.infowindow.getMap()) {
      //   //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
      //   this.map.setCenter(this.infowindow.getPosition());
      //   return;
      // }

      var iwContent = '<div style="padding:5px;">내 위치</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(this.latitude, this.longitude), //인포윈도우 표시 위치입니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      // 마커를 생성합니다
      this.marker = new kakao.maps.Marker({
        position: iwPosition
      });

      // 마커가 지도 위에 표시되도록 설정합니다
      this.marker.setMap(this.map);

      this.infowindow = new kakao.maps.InfoWindow({
        map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent,
        removable: iwRemoveable,
      });

      this.infowindow.open(this.map, this.marker)

      this.map.setCenter(iwPosition);
    },

    makeCafeMarker() {
      for (let i = 0; i < this.nearCafes.length; i++) {
        var iwContent = `<div style="padding:5px;">${this.nearCafes[i].cafeName}</div>`, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
          iwPosition = new kakao.maps.LatLng(this.nearCafes[i].cafeLatitude, this.nearCafes[i].cafeLongitude), //인포윈도우 표시 위치입니다
          iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

        // 마커를 생성합니다
        this.marker = new kakao.maps.Marker({
          position: iwPosition
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        this.marker.setMap(this.map)

        this.infowindow = new kakao.maps.InfoWindow({
          map: this.map, // 인포윈도우가 표시될 지도
          position: iwPosition,
          content: iwContent,
          removable: iwRemoveable,
        });
      }
    },
    async getCafe() {
      try {
        const response = await api.get(`/api/cafes?latitude=${this.latitude}&longitude=${this.longitude}&search=&size=10&page=1`)
        this.nearCafes = response.data.result
        console.log(response.data.result)
        this.makeCafeMarker()
        this.map.setCenter(new kakao.maps.LatLng(this.latitude, this.longitude))
      } catch (error) {
        console.log(error)
      }
    }
  },
}
</script>

<style>

</style>