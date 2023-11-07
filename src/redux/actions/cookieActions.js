import Cookies from 'js-cookie';

// 쿠키에 값 저장
export const setCookie = (name, value, option) => {
  return Cookies.set(name, value, { ...option });
};
// 쿠키에 있는 값을 꺼냄
export const getCookie = (name) => {
  return Cookies.get(name);
};
// 쿠키 지움
export const removeCookie = (name) => {
  return Cookies.remove(name);
};
