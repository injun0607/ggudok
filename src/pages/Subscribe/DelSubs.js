import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useLocation, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css';
// component import
import ErrorItem from '../../components/ErrorItem';
// redux import
import { setMySubsRank, setMySubsOtherRank, editSubscribe, deleteSubscribe } from '../../redux/actions/subscribeActions';

const DelSubs = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const subsId = location.state.subsId;
  const mySubsRank = useSelector(state => state.subscribe.mySubsRank);
  const mySubsOtherRank = useSelector(state => state.subscribe.mySubsOtherRank);
  const [selectedRankLevel, setSelectedRankLevel] = useState('');
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);

  
  // ************************** 구독중 요금제 fetch ***************************
  const fetchSubsData = async () => {
    try {
      const response = await axios.get(`/subs/buy_cancel/${subsId}`);
      const data = response.data;
      if(data.haveRank !== null){
        const updateOtherRank = [...data.subsRanks];
        const updateMyRank = data.subsRanks.filter((rank) => rank.rankLevel === data.haveRank);
        const indexOfMyRank = data.subsRanks.findIndex((rank) => rank.rankLevel === data.haveRank);
        updateOtherRank.splice(indexOfMyRank, 1);
        dispatch(setMySubsRank(...updateMyRank));
        dispatch(setMySubsOtherRank(updateOtherRank));
        setIsResult(true);
      } else {
        dispatch(setMySubsRank([]));
        dispatch(setMySubsOtherRank([]));
        setIsResult(false);
        alert('구독중인 서비스가 아닙니다.');
        navigate(-1);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchSubsData();
  }, []);

  const handleEdit = (e) => {
    e.preventDefault();
    const userData = {
      subsId,
      selectedRankLevel,
    };
    dispatch(editSubscribe(userData, navigate));
  };
  const handleDelete = (e) => {
    e.preventDefault();
    const userData = {
      subsId,
    };
    dispatch(deleteSubscribe(userData, navigate));
  };

  return (
    !IsLoading && <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h2>구독 변경/해지</h2>
        </div>
        {IsResult ?
        <>
          <div className={style.subscribeCur}>
            <div className={style.left}>
              <h2 className={style.name}>{mySubsRank.rankName}</h2>
              <p className={style.detail}>
                {
                  mySubsRank.content.map((cont, contIndex) => (
                    <p className={style.detail} key={contIndex}>{cont}</p>
                  ))
                }
              </p>
            </div>
            <div className={style.right}>
              <p className={style.price}>
              월 <span className={style.point}>{mySubsRank.price}</span>원
              </p>
            </div>
          </div>

          <form className={`${style.form} ${style.subscribe}`}>
            {mySubsOtherRank.map((itemRank, index) => (
              <div className={style.box} key={index}>
                <div className={style.radioWrap}>
                  <input type='radio' name='subscribe' value={itemRank.rankLevel} className='radtioInput' onChange={(e) => { setSelectedRankLevel(e.target.value); }} />
                </div>
                <div className={style.left}>
                  <h2 className={style.name}>{itemRank.rankName}</h2>
                    {
                      itemRank.content.map((cont, contIndex) => (
                        <p className={style.detail} key={contIndex}>{cont}</p>
                      ))
                    }
                </div>
                <div className={style.right}>
                  <p className={style.price}>
                  월 <span className={style.point}>{itemRank.price}</span>원
                  </p>
                </div>
              </div>
            ))}
            <div className={`mt_40 ${style.doublebutton}`}>
              <button type='button' className='btn btn_normal' onClick={ handleDelete }>해지하기</button>
              <button type='submit' className='btn btn_full' onClick={ handleEdit }>변경하기</button>
            </div>
          </form>
        </>
        : <ErrorItem message="구독중인 서비스가 아닙니다." />}
      </div>
    </section>
  );
};

export default DelSubs;
