export function geoFind(context) {
  navigator.geolocation.getCurrentPosition(
    (pos) => {
      let locationInfo = {
        latitude: pos.coords.latitude,
        longitude: pos.coords.longitude,
      };

      context.commit("geoFind", locationInfo);
    },
    (err) => {
      console.log(err.message);
    }
  );
}
