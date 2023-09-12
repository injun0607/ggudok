import { useEffect } from "react";
import { useLocation } from "react-router-dom";

export default function Scrolltop() {
  const { pathname } = useLocation();

  useEffect(() => {
    // 페이지 이동 시 스크롤 위치를 맨 위로 이동
    window.scrollTo(0, 0);
    // 컴포넌트가 unmount될 때 스크롤 위치 초기화
    return () => {
      window.scrollTo(0, 0);
    };
  }, [pathname]);

  return null;
}