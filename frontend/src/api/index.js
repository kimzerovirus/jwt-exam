import axios from "axios";
import { getCookie } from "../utils/cookieUtil";

export const instance = axios.create({});

instance.interceptors.request.use(
  function (config) {
    // Do something before request is sent
    // console.log("request 요청 interceptor!!!!");
    // console.log(`${config.method} ${config.url}`);
    // console.log("### cookies token :" + VueCookies.get("access_token"));
    config.headers.common['Authorization'] = `Bearer ${getCookie('ac_token')}`;
    console.log('######auth: ', config.headers.common['Authorization']);
    return config;
  },
  // 요청중 에러시....
  function (error) {
    // Do something with request error
    return Promise.reject(error);
  },
);

// 응답이 온 직후 intercept
// instance.interceptors.response.use(
//   function (response) {
//     // console.log("response 받고나서 interceptor!!!!");
//     // console.log(response);
//     return response;
//   },
  // 요청 에러시....
//   async function (error) {
//     const errorAPI = error.config; // 에러가 난 axios config
//     // axios 에서 보낸 요청이 없으면 그냥 return (라우팅 같은것)
//     if (errorAPI === undefined) return;
//     // status 코드가 200 번대 이외는 error
//     if (error.response.status === 401 && errorAPI.retry === undefined) {
//       errorAPI.retry = true; // retry를 true 로 만들어 주어 또 에러가 났을 때 무한 루프를 돌지 않게
//       // refreshToken() 에서 true 를 return 하면 refreshToken 에 문제가 발생한것
//       // 따라서 다시 요청 안해줘도 됨
//       let refreshError = await refreshToken(); // access token 재발급 함수
//       if (refreshError) {
//         return;
//       }
//       // 만일 refreshToken 도 문제가 없으면
//       //에러난 api 헤더 교채해서 재요청 (access 쿠키 세팅은 refreshToken에서 함)
//       errorAPI.headers['Authorization'] = VueCookies.get('access_token');
//       return await axios(errorAPI); // 에러 config 가지고 다시 메소드 재요청
//     }
//     // 재발급 함수를 거치고도 에러가 나오면 refresh token 도 문제가 있는것 함수 취소
//     return Promise.reject(error);
//   },
// );

// async function refreshToken() {
//   const body = {
//     accessToken: VueCookies.get('access_token'),
//     refreshToken: VueCookies.get('refresh_token'),
//   };
//   // refreshToken 이 없는체로 들어오면 로그인을 안하거나
//   // 프론트에 토큰기한이 만료되어 사라진것
//   if (body.refreshToken == null) {
//     return true;
//   }
//   console.log('body', body);

//   const reissueInstance = axios.create({
//     timeout: 1000,
//   });
//   const token = reissueInstance
//     .post('/api/auth/reissue', body)
//     .then(function (response) {
//       console.log('리 이슈 후에 리스폰스 : ', response);
//       console.log('만료된 토큰', VueCookies.get('access_token'));
//       let access = response.data.accessToken; // 새로받은 access 토큰
//       VueCookies.set('access_token', access); // 쿠키에 다시 세팅
//       console.log('새로받은 토큰', access);
//     })
//     .catch(function (error) {
//       if (error.response.data.message == 'REFRESH 토큰 만료 오류') {
//         // alert('로그인 기간이 만료되었습니다. 다시 로그인해 주세요');

//         VueCookies.keys().forEach(cookie => VueCookies.remove(cookie));
//         router.push('/login');
//         Promise.reject(error);
//         return true;
//       } else {
//         console.log(error);
//       }
//       return error;
//     });
//   return token;
// }