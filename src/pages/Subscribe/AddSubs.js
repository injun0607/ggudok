import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate, useLocation } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css';
// redux import
import { addSubscribe } from '../../redux/actions/subscribeActions';

const AddSubs = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  const subsId = location.state.subsId;
  const [itemRanks, setItemRanks] = useState([
    {
      rankName: "",
      price: 0,
      rankLevel: "",
      contentList: [{content: ""}],
    },
  ]);
  const [selectedRankLevel, setSelectedRankLevel] = useState('');
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);

  
  // ************************** 카테고리, 태그 fetch ***************************
  const fetchItemsData = async () => {
    try {
      const response = await axios.get(`/subs/subs_rank/${subsId}`);
      const data = response.data;

      if(data !== 0){
        setItemRanks(data);
      } else {
        setItemRanks([]);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchItemsData();
  }, []);

  const handleAdd = (e) => {
    e.preventDefault();
    const userData = {
      subsId,
      selectedRankLevel,
    };
    dispatch(addSubscribe(userData, navigate));
  };

  return (
    !IsLoading && <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h2>구독하기</h2>
        </div>

        <form className={`${style.form} ${style.subscribe}`} onSubmit={ handleAdd }>
          {itemRanks.map((itemRank, index) => (
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
            <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
            <button type='submit' className='btn btn_full'>구독하기</button>
          </div>
        </form>
      </div>
    </section>
  );
};

export default AddSubs;
