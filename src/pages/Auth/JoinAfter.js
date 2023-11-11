import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
// redux import
import { setCookie, getCookie, removeCookie } from '../../redux/actions/cookieActions';
import { joinAfter, setAge, setGender, setMemberName } from '../../redux/actions/userActions';

const JoinAfter = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const memberName = useSelector(state => state.user.memberName);
  const gender = useSelector(state => state.user.gender);
  const age = useSelector(state => state.user.age);
  const [access, setAccess] = useState('');
  const [IsResult, setIsResult] = useState(false);
  const [IsLoading, setIsLoading] = useState(true);

  
  // ************************ access token fetch *************************
  const fetchAccessToken = async () => {
    try {
      const urlParams = new URLSearchParams(window.location.search);
      const access = urlParams.get('access');
      const refresh = urlParams.get('refresh');
      if(access){
        // Access Token을 쿠키에 저장
        setCookie('access', access, { path: '/' });
        setCookie('refresh', refresh, { path: '/' });
        setAccess(access);
        setIsResult(true);
      } else {
        setIsResult(false);
      }
    } catch (error) {
      console.error('Error fetching Access Token:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchAccessToken();
  }, []);

  // 나이 검사
  const handleChangeAge = (newAge) => {
    // 나이 유효성 검사
    const regAge = /^[0-2]+$/;
    const isAgeReg = regAge.test(newAge);

    // 오류 메시지 설정
    if (!isAgeReg){
      return;
    }
  
    // Redux 상태 업데이트
    dispatch(setAge(newAge))
  }

  const handleJoinAfter = (e) => {
    e.preventDefault();
    if (!memberName) {
      alert('이름을 입력하세요.')
    } else if (!age) {
      alert('나이를 숫자만 입력하세요.')
    } else if (!gender) {
      alert('성별을 선택하세요.')
    } else {
      const userData = {
        access,
        memberName,
        gender,
        age,
      };
      dispatch(joinAfter(userData, navigate));
      
      dispatch(setMemberName(''));
      dispatch(setGender(''));
      dispatch(setAge(''));
      navigate('/Home');
    }
  };

  return (
    !IsLoading && <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>이제 한 단계만 남았어요!</h2></div>
        <div className={style.form}>
          <form onSubmit={ handleJoinAfter }>
            <div className={style.inputwrap}>
              <input type='text' name='memberName' autoComplete="off"
                placeholder='이름을 입력하세요.'
                value={memberName}
                onChange={(e) => { dispatch(setMemberName(e.target.value)) }} />
              <input type='text' name='age' autoComplete="off"
                  placeholder='나이를 숫자만 입력하세요.'
                  value={age}
                  onChange={(e) => { 
                    dispatch(setAge(e.target.value))
                    handleChangeAge(e.target.value);
                  }} />
              <select className={`${style.select}`} name='gender'
                value={gender}
                onChange={(e) => dispatch(setGender(e.target.value))}
              >
                <option value=''>성별을 선택하세요.</option>
                <option value='MAN'>남성</option>
                <option value='WOMAN'>여성</option>
              </select>
            </div>
            <button type='submit' className='btn btn_full'>회원가입</button>
          </form>
        </div>
      </div>
    </section>
  )
}

export default JoinAfter;