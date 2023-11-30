import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Compare.module.css';
// redux import
import { setIsLoading, setIsResult, } from '../redux/actions/itemActions';
import { setTwoDepthItems, setSelectedOneDepth, setCompareItem1, setCompareItem2 } from '../redux/actions/compareActions';

const NO_IMAGE_URL = '/images/common/noimg.png';
const NO_ICON_URL = '/images/common/logo_grey.png';

const Compare = () => {
  const dispatch = useDispatch();

  const categories = useSelector(state => state.category.categories);
  const selectedOneDepth = useSelector(state => state.compare.selectedOneDepth);
  const twoDepthItems = useSelector(state => state.compare.twoDepthItems);
  let compareItem1 = useSelector(state => state.compare.compareItem1);
  let compareItem2 = useSelector(state => state.compare.compareItem2);
  let [categoryIcon, setCategoryIcon] = useState('');
  const [item1StarAvg, setItem1StarAvg] = useState([]);
  const [item1DefaultRank, setItem1DefaultRank] = useState({});
  const [item1OtherRanks, setItem1OtherRanks] = useState([]);
  const [item2StarAvg, setItem2StarAvg] = useState([]);
  const [item2DefaultRank, setItem2DefaultRank] = useState({});
  const [item2OtherRanks, setItem2OtherRanks] = useState([]);

  // ************************** 기본 아이템 fetch ***************************
  const fetchCompareData = async () => {
    try {
      const selectedCategory = categories.filter(category => category.categoryEng === selectedOneDepth)[0];
      const selectedCategoryEng = selectedCategory.categoryEng;
      setCategoryIcon(selectedCategory.categoryImage);
      
      const response = await axios.get(`/subs/${selectedCategoryEng}`);
      const data = response.data.items;
      dispatch(setTwoDepthItems(data));

      dispatch(setIsResult(true));
    } catch (error) {
      console.error('Error fetching data:', error);
      dispatch(setIsResult(false));
    } finally {
      dispatch(setIsLoading(false));
    }
  };
  useEffect(() => {
    if (selectedOneDepth){
    fetchCompareData();}
  }, [selectedOneDepth]);

  const handleChangeOneDepth = (e) => {
    dispatch(setSelectedOneDepth(e.target.value));
    dispatch(setCompareItem1({}));
    dispatch(setCompareItem2({}));
  }

  const handleChangeCompareItem = (e, itemNumber) => {
    const selectedItemId = Number(e.target.value);
    const selectedItem = twoDepthItems.find(item => item.id === selectedItemId);
    if (itemNumber === 1) {
      dispatch(setCompareItem1(selectedItem));
    } else if (itemNumber === 2) {
      dispatch(setCompareItem2(selectedItem));
    }
  };

  // 카테고리 선택하지 않으면 아이템 선택 비활성화
  const isOneDepthSelected = selectedOneDepth !== '';
  const isCompareItem1Selected = Object.keys(compareItem1).length > 0;
  const isCompareItem2Selected = Object.keys(compareItem2).length > 0;  

  useEffect(() => {
    if(isCompareItem1Selected){
      // 아이템 평균별점
      const itemRatingAvg = compareItem1.ratingAvg;
      const newItemStarAvg = [];
      for(let i = 0; i < 5; i++){
        newItemStarAvg.push(i < itemRatingAvg);
      }
      setItem1StarAvg(newItemStarAvg);
      // 아이템 회원등급 Default와 나머지 분리
      const itemRanks = compareItem1.ranks;
      setItem1DefaultRank(itemRanks.find(obj => obj.rankLevel === "DEFAULT"));
      setItem1OtherRanks(itemRanks.filter(obj => obj.rankLevel !== "DEFAULT"));
    }
  }, [dispatch, compareItem1, setCompareItem1])

  useEffect(() => {
    if(isCompareItem2Selected){
      // 아이템 평균별점
      const itemRatingAvg = compareItem2.ratingAvg;
      const newItemStarAvg = [];
      for(let i = 0; i < 5; i++){
        newItemStarAvg.push(i < itemRatingAvg);
      }
      setItem2StarAvg(newItemStarAvg);
      // 아이템 회원등급 Default와 나머지 분리
      const itemRanks = compareItem2.ranks;
      setItem2DefaultRank(itemRanks.find(obj => obj.rankLevel === "DEFAULT"));
      setItem2OtherRanks(itemRanks.filter(obj => obj.rankLevel !== "DEFAULT"));
    }
  }, [dispatch, compareItem2, setCompareItem2])

  return (
    categories.length !==0 && <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h2>구독서비스 간편비교하기</h2>
        </div>

        <section className={style.search}>
          <div className={style.onedepth}>
            <select className={style.select} onChange={ handleChangeOneDepth } value={selectedOneDepth}>
              <option value="">우선 카테고리를 선택하세요.</option>
              {
                categories.map((category, categoryId) => 
                <option value={category.categoryEng} key={categoryId}>{category.categoryName}</option>
                )
              }
            </select>
          </div>
          <div className={style.twodepth}>
            <select className={`${style.select} ${style.select01}`} onChange={(e) => handleChangeCompareItem(e, 1)} value={compareItem1} disabled={!isOneDepthSelected}>
              <option value="">{isCompareItem1Selected ? `${compareItem1.name}` : '첫번째 구독서비스를 선택하세요.'}</option>
              {
                twoDepthItems
                .filter(item => item.id !== compareItem2.id)
                .map((item, index) => 
                <option value={item.id} key={index}>{item.name}</option>
                )
              }
            </select>
            <select className={`${style.select} ${style.select02}`} onChange={(e) => handleChangeCompareItem(e, 2)} value={compareItem2} disabled={!isOneDepthSelected}>
              <option value="">{isCompareItem2Selected ? `${compareItem2.name}` : '두번째 구독서비스를 선택하세요.'}</option>
              {
                twoDepthItems
                .filter(item => item.id !== compareItem1.id)
                .map((item, index) => 
                <option value={item.id} key={index}>{item.name}</option>
                )
              }
            </select>
          </div>
        </section>

        <section className={style.detail}>
          {/* {compareItem1 ? alert('on') : null } */}
          {isCompareItem1Selected ? (
            <div className={style.boxrow}>
              <div className={`${style.box} ${style.titbox}`}>
                <Link to={`/subs/detail/item/${compareItem1.id}`}>
                  <img src={compareItem1.image || NO_IMAGE_URL} alt={compareItem1.name} />
                  <h3>{compareItem1.name}</h3>
                </Link>
              </div>
              <div className={style.box}>
                <div className={style.category}>
							    <img src={categoryIcon || NO_ICON_URL} alt={selectedOneDepth} />
                  <p>{compareItem1.category}</p>
                </div>
                <h3>카테고리</h3>
              </div>
              <div className={style.box}>
                <div className={style.tag}>
                {
                  compareItem1.tags.map((tag, tagindex) => (
                    <p key={tagindex}>{tag.tagName}</p>
                  ))
                }
                </div>
                <h3>태그</h3>
              </div>
              <div className={style.box}>
                <div className={style.starrating}>
                  <div className={style.star}>
                    {
                      item1StarAvg.map((starAvg, starindex) => (
                        starAvg ? <span key={starindex} className={`material-icons ${style.starActive}`}>star</span> : <span key={starindex} className="material-icons">star</span>
                      ))
                    }
                  </div>
                </div>
                <h3>별점</h3>
              </div>
              <div className={style.box}>
                <div className={style.price}>
                  {
                    item1OtherRanks.map((otherRank, otherRankindex) => (
                      <p className={style.subprice} key={otherRankindex}>
                      {otherRank.rankName} <span className={style.point}>월 {otherRank.price} 원</span>
                    </p>
                    ))
                  }
                  <p className={style.mainprice}>
                    {item1DefaultRank.rankName} <span className={style.point}>월 {item1DefaultRank.price} 원</span>
                  </p>
                </div>
                <h3>요금제</h3>
              </div>
            </div>
            ) : (
            <div className={`${style.boxrow} ${style.boxrowerror}`}><p>구독서비스를 선택하세요!</p></div>
            )}
            
          {isCompareItem2Selected ? (
            <div className={style.boxrow}>
              <div className={`${style.box} ${style.titbox}`}>
                <Link to={`/subs/detail/item/${compareItem2.id}`}>
                  <img src={compareItem2.image || NO_IMAGE_URL} alt={compareItem2.name} />
                  <h3>{compareItem2.name}</h3>
                </Link>
              </div>
              <div className={style.box}>
                <div className={style.category}>
							    <img src={categoryIcon || NO_ICON_URL} alt={selectedOneDepth} />
                  <p>{compareItem2.category}</p>
                </div>
                <h3>카테고리</h3>
              </div>
              <div className={style.box}>
                <div className={style.tag}>
                {
                  compareItem2.tags.map((tag, tagindex) => (
                    <p key={tagindex}>{tag.tagName}</p>
                  ))
                }
                </div>
                <h3>태그</h3>
              </div>
              <div className={style.box}>
                <div className={style.starrating}>
                  <div className={style.star}>
                    {
                      item2StarAvg.map((starAvg, starindex) => (
                        starAvg ? <span key={starindex} className={`material-icons ${style.starActive}`}>star</span> : <span key={starindex} className="material-icons">star</span>
                      ))
                    }
                  </div>
                </div>
                <h3>별점</h3>
              </div>
              <div className={style.box}>
                <div className={style.price}>
                  {
                    item2OtherRanks.map((otherRank, otherRankindex) => (
                      <p className={style.subprice} key={otherRankindex}>
                      {otherRank.rankName} <span className={style.point}>월 {otherRank.price} 원</span>
                    </p>
                    ))
                  }
                  <p className={style.mainprice}>
                    {item2DefaultRank.rankName} <span className={style.point}>월 {item2DefaultRank.price} 원</span>
                  </p>
                </div>
                <h3>가격</h3>
              </div>
            </div>
            ) : (
            <div className={`${style.boxrow} ${style.boxrowerror}`}><p>구독서비스를 선택하세요!</p></div>
            )}
        </section>

      </div>
    </section>
  );
};

export default Compare;
