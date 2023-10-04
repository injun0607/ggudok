import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Item.module.css';
// component import
import ErrorItem from '../components/ErrorItem';
import Loading from '../components/Loading';
// redux import
import { fetchItemsSuccess } from '../redux/actions/itemActions';

const ITEMS_PER_PAGE = 12;
const NO_IMAGE_URL = '/images/common/noimg.png';

const FeaturedItemlist = () => {
  let dispatch = useDispatch();
  const items = useSelector(state => state.item.items);
  const [slicedItems, setSlicedItems] = useState([]);
  const [IsResult, setIsResult] = useState(false);
  const [IsLoading, setIsLoading] = useState(true);
  const [IsPager, setIsPager] = useState(false);

  const { category } = useParams();

  // ************************** 기본 아이템 fetch ***************************
  const fetchFeaturedItemData = async () => {
    try {
      const response = await axios.get(`/subs/${category}`);
      const data = response.data.items;

      dispatch(fetchItemsSuccess(data));
      setSlicedItems([...items]);
    } catch (error) {
      console.error('Error fetching item:', error);
      alert(`서비스를 가져오던 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchFeaturedItemData();
  }, [dispatch, category]);

  // 결과 유무
  useEffect(() => {
    setIsResult(items.length > 0);
  }, [items, category]);

  return (
    !IsLoading ? (IsResult ?
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h2>{category}</h2>
        </div>
        <section className='item-list mb_60'>
          {items.slice(0, 4).map((item, index) => 
            <Link to={`/subs/detail/${item.id}`} key={index} className='item-list-box'>
              <div className='rank'>
                <p className='rankNum'>{index + 1}</p>
              </div>
              <div className='img'>
                <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
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
            )}
        </section>

        <section className={style.section}>
          <div className={style.itemlist}>
            {items.map((item, index) => (
              <Link to={`/subs/detail/${item.id}`} key={index} className={style.item}>
                <div className={style.img}>
                  <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                </div>
                <div className={style.txt}>
                  <h3>{item.name}</h3>
                  <div className={style.tag}>
                    {
                      item.tags.map((tag, tagindex) => (
                        <p key={tagindex}>{tag.tagName}</p>
                      ))
                    }
                  </div>
                </div>
              </Link>
            ))}
          </div>
        </section>

      </div>
    </section>
    : <ErrorItem />) : <Loading />
  );
};

export default FeaturedItemlist;
