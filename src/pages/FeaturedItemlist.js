import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Item.module.css';
// component import
import ErrorItem from '../components/ErrorItem';
// redux import
import { setIsLoading, setIsResult, fetchItemsSuccess } from '../redux/actions/itemActions';

const ITEMS_PER_PAGE = 12;
const NO_IMAGE_URL = '/images/common/noimg.png';

const FeaturedItemlist = ({ category }) => {
  let dispatch = useDispatch();
  const items = useSelector(state => state.item.items);
  const IsResult = useSelector(state => state.item.IsResult);
  const IsLoading = useSelector(state => state.item.IsLoading);
  const [IsPager, setIsPager] = useState(false);
  const [slicedItems, setSlicedItems] = useState([]);

  // ************************** 기본 아이템 fetch ***************************
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/subs/${category}`);
        const data = response.data.items;

        dispatch(fetchItemsSuccess(data));
        setSlicedItems([...items]);
        dispatch(setIsLoading(false));
        dispatch(setIsResult(true));
      } catch (error) {
        console.error('Error fetching data:', error);
        dispatch(setIsResult(false));
      }
    };
    fetchData();
  }, [category]);

  return (
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h2>{category}</h2>
        </div>
        <section className='item-list mb_60'>
          {IsResult ? slicedItems.slice(0, 4).map((item, index) => 
            <Link to={`/ItemDetail/${item.id}`} key={index} className='item-list-box'>
              <div className='rank'>
                <p className='rankNum'>{index + 1}</p>
              </div>
              <div className='img'>
                <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
              </div>
              <div className='txt'>
                <h3>{item.name}</h3>
                <div className='tag'>
                  <p>{item.category}</p>
                  <p>{item.tag}</p>
                </div>
              </div>
            </Link>
            ) : <ErrorItem />}
        </section>

        <section className={style.section}>
          <div className={style.itemlist}>
            {slicedItems.map((item, index) => (
              <Link to={`/ItemDetail/${item.id}`} key={index} className={style.item}>
                <div className={style.img}>
                  <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                </div>
                <div className={style.txt}>
                  <h3>{item.name}</h3>
                  <div className={style.tag}>
                    <p>{item.category}</p>
                    <p>{item.tag}</p>
                  </div>
                </div>
              </Link>
            ))}
          </div>
        </section>

        {/* {IsPager && <div className='pagination-wrap'>
          <div className='pagination'>
              <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
              <span className='material-icons'>chevron_left</span>
              </button>
              <span>{currentPage}</span> / <span>{totalPages}</span>
              <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}>
              <span className='material-icons'>chevron_right</span>
              </button>
            </div>
        </div>} */}
      </div>
    </section>
  );
};

export default FeaturedItemlist;
