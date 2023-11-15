import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation } from 'swiper/modules';
// component import
import Bannerslider from '../components/Slider';
import ErrorItem from '../components/ErrorItem';
import Loading from '../components/Loading';
// css import
import style from '../styles/Home.module.css';
import 'swiper/css';
import 'swiper/css/navigation';
// redux import
import { setRecomBasic, setRecomCustom } from '../redux/actions/itemActions';
import { fetchCategory } from '../redux/actions/categoryActions';
import { setCookie, getCookie, removeCookie } from '../redux/actions/cookieActions';

const NO_IMAGE_URL = '/images/common/noimg.png';
const NO_ICON_URL = '/images/common/logo_grey.png';

const Home = ({isCheckingLogin}) => {
  let dispatch = useDispatch();
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
  const categories = useSelector(state => state.category.categories);

  // const items = useSelector(state => state.item.items);
  const recommendBasic = useSelector(state => state.item.recommendBasic);
  const recommendCustomized = useSelector(state => state.item.recommendCustomized);

  const [ageTag, setAgeTag] = useState('');
  const [genderTag, setGenderTag] = useState('');

  const [IsResult, setIsResult] = useState(false);
  const [IsLoading, setIsLoading] = useState(true);

  // ************************** 추천 아이템 / 헤더 fetch ***************************
  const fetchHomeData = async () => {
    try {
      const response = await axios.get(`/home`);
      const data = response.data;
      if(data !== 0){
        dispatch(setRecomBasic(data.recommendBasic))
        dispatch(fetchCategory(data.categoryList))
        if(data.recommendCustomized.length){
          dispatch(setRecomCustom(data.recommendCustomized))
        } else {
          dispatch(setRecomCustom(data.defaultSubs))
        }
        if(isLoggedIn){
          setAgeTag(data.ageTag)
          setGenderTag(data.genderTag)
        }
      } else {
        dispatch(setRecomBasic([]));
        dispatch(setRecomCustom([]))
				dispatch(fetchCategory([]))
      }
    } catch (error) {
      console.error('Error fetching item:', error);
      alert(`서비스를 가져오던 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    if(!isCheckingLogin){
      fetchHomeData();
    }
  }, [isCheckingLogin, isLoggedIn])
  
  // 결과 유무
  useEffect(() => {
    setIsResult(recommendBasic.length > 0 && recommendCustomized.length > 0);
  }, [dispatch, recommendBasic, isLoggedIn]);


  return (
    !IsLoading && <section className={style.home}>
      <div className='webwidth'>
        <section className={style.bannerslider}>
          <Bannerslider />
        </section>
        <section className={style.section}>
          <h2 className={style.tit}>전체 카테고리</h2>
          <nav className={`${style.allcategory} ${style.box}`}>
            {
              categories &&
              (categories.map((category, index) =>
              <Link
                to={`/Category/${category.categoryEng}`}
                className={style.category}
                key={index}
              >
                <img src={category.categoryImage || NO_ICON_URL} alt={category.categoryName} />
                <p>{category.categoryName}</p>
              </Link>
              ))
            }
          </nav>
        </section>
        { IsResult ?
          <section className={style.section}>
          <h2 className={style.tit}>알고리즘 저격! <span className='sub_clr'>맞춤 서비스</span></h2>
          <Swiper
            cssMode={true}
            navigation={true}
            pagination={false}
            spaceBetween={24}
            slidesPerView={2}
            modules={[Navigation]}
            className="item-list_carousel"
            breakpoints={{
              800: {
                slidesPerView: 4,
              },
            }}
          >
            {
              recommendCustomized.map((item, index) => 
                <SwiperSlide key={index} className='item-list-box'>
                  <Link to={`/subs/detail/${item.id}`}>
                    <div className='img'>
                      <img src={item.image || NO_IMAGE_URL} alt={item.name} />
                    </div>
                    <div className='txt'>
                      <h3>{item.name}</h3>
                      <div className='tag'>
                        {
                          item.tags.map((tag, tagindex) => (
                            <p key={tagindex}>{tag.tagName}</p>
                          ))
                        }
                      </div>
                    </div>
                  </Link>
                </SwiperSlide>
              )
            }
          </Swiper>
          </section>
          : <ErrorItem message="해당하는 구독서비스가 없습니다." />}
        { IsResult ?
          <section className={style.section}>
            {
              isLoggedIn ?
              <h2 className={style.tit}>나와 같은 <span className='main_clr'>{ageTag} {genderTag}</span>이 가장 많이 구독한 서비스</h2> :
              <h2 className={style.tit}>지금 가장 인기있는 서비스</h2>
            }
            <Swiper
              cssMode={true}
              navigation={true}
              pagination={false}
              spaceBetween={24}
              slidesPerView={2}
              modules={[Navigation]}
              className="item-list_carousel"
              breakpoints={{
                800: {
                  slidesPerView: 4,
                },
              }}
            >
              {
                recommendBasic.map((item, index) => 
                <SwiperSlide key={index} className='item-list-box'>
                  <Link to={`/subs/detail/${item.id}`}>
                    <div className='img'>
                      <img src={item.image || NO_IMAGE_URL} alt={item.name} />
                    </div>
                    <div className='txt'>
                      <h3>{item.name}</h3>
                      <div className='tag'>
                      {
                        item.tags.map((tag, tagindex) => (
                          <p key={tagindex}>{tag.tagName}</p>
                        ))
                      }
                    </div>
                    </div>
                  </Link>
                </SwiperSlide>
                )
              }
            </Swiper>
          </section>
          : <ErrorItem message="해당하는 구독서비스가 없습니다." />}
      </div>
    </section>
  )
}

export default Home;