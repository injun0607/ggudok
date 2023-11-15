import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Spinner from '../images/spinner.gif';

export const Background = styled.div`
  position: absolute;
  width: 100vw;
  height: 100vh;
  top: 0;
  left: 0;
  background: #ffffffb7;
  z-index: 999;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

export const LoadingText = styled.div`
  font: 1rem 'Noto Sans KR';
  text-align: center;
`;

export const Loading = () => {
  // 로딩 상태를 관리할 useState 훅을 사용합니다.
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    // 예시로 3초 후에 로딩을 완료한다고 가정합니다.
    const timer = setTimeout(() => {
      setIsLoading(false);
    }, 3000);

    // 컴포넌트가 언마운트될 때 타이머를 정리합니다.
    return () => clearTimeout(timer);
  }, []);

  return (
    <Background>
      <LoadingText>잠시만 기다려 주세요!</LoadingText>
      <img src={Spinner} alt="로딩중" width="5%" />
    </Background>
  );
};

export default Loading;