import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
// component import
import ErrorItem from '../../components/ErrorItem';
import Loading from '../../components/Loading';
// css import
import style from '../../styles/Mypage.module.css'
// redux import
import { setMySubscribe, setMyTotal } from '../../redux/actions/subscribeActions';

const MySubscribe = () => {
  let dispatch = useDispatch();
  const mySubscribe = useSelector(state => state.subscribe.mySubscribe);
  const myTotalAvg = useSelector(state => state.subscribe.myTotalAvg);
  const [IsResult, setIsResult] = useState(false);
  const [IsLoading, setIsLoading] = useState(true);
  
  // ************************** 기본 MySubscribe fetch ***************************
  const fetchSubsData = async () => {
    try {
      const response = await axios.get(`/member/have_subs`);
      const data = response.data.items;
      const total = response.data.totalAvg;
      console.log('data : ', data)

      if(data !== 0){
        dispatch(setMySubscribe(data));
        dispatch(setMyTotal(total));
      } else {
        dispatch(setMySubscribe([]));
        dispatch(setMyTotal(''));
      }

    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchSubsData();
  }, [dispatch]);

  // 결과 유무
  useEffect(() => {
    setIsResult(mySubscribe.length > 0);
  }, [dispatch, mySubscribe]);

  return (
    !IsLoading ? (IsResult ?
    <section className={`${style.section} ${style.subscribewrap}`}>
      <p className='txt_right main_clr02 mb_10'>* 한 달 기준 구독내역입니다.</p>
      <div className={style.overallprice}>
        <p className={style.md}>이번 달 총 요금은 <span className={style.lg}>{ myTotalAvg }</span>원입니다.</p>
        <img src='/images/icons/message.svg' alt='요금안내' />
      </div>
      {
        mySubscribe.map((category, categoryIndex) => (
        <div className={style.subscribes} key={categoryIndex}>
          <div className={style.tit}>
            <div className={style.name}>
              <img src={`/images/icons/${category.categoryEng}.svg`} alt={category.categoryName} />
              <h3>{category.categoryName}</h3>
            </div>
            <div className={style.price}>
                <p className={style.sm}>총 요금</p>
                <p className={style.md}><span className={style.lg}>{category.totalPrice}</span>원</p>
            </div>
          </div>
          <div className={style.cont}>
            {category.subsList.map((subs, subsIndex) => (
              <article className={style.subscribe} key={subsIndex}>
                {subs.content.map((cont, index) => (
                  <h4>{cont}</h4>
                ))}
                <div className={style.tag}>
                  <p>{subs.subsName}</p>
                  <p>{subs.price}원</p>
                </div>
              </article>
            ))}
          </div>
        </div>
      ))}
    </section>
        : <ErrorItem />) : <Loading />
  )
}
export default MySubscribe;