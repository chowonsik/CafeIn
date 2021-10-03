export function geoFind(state, payload) {
  // get position
  state.latitude = payload.latitude;
  state.longitude = payload.longitude;
  // navigator.geolocation.getCurrentPosition(
  //   (pos) => {
  //     state.latitude = pos.coords.latitude;
  //     state.longitude = pos.coords.longitude;
  //   },
  //   (err) => {
  //     console.log(err.message);
  //   }
  // );
}
