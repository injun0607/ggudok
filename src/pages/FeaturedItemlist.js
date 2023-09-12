import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Item.module.css';
// component import
import ErrorItem from '../components/ErrorItem';
// redux import
import { setNoResult } from '../redux/actions/itemActions';

const ITEMS_PER_PAGE = 12;
const NO_IMAGE_URL = '/images/common/noimg.png';

const FeaturedItemlist = ({ category }) => {
  let dispatch = useDispatch()

  const bestitems = useSelector(state => state.item.bestitems);
  const noResult = useSelector(state => state.item.noResult);

  
  // 아이템 개수 설정 및 페이지 이동
  const [currentPage, setCurrentPage] = useState(1);
  const totalPages = Math.ceil(bestitems.length / ITEMS_PER_PAGE);

  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE + 4;
  const endIndex = startIndex + ITEMS_PER_PAGE;
  const slicedItems = bestitems.slice(startIndex, endIndex);

  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
    }
  };

  // 존재하지 않는 아이템 검사
  useEffect(() => {
    if (bestitems.length === 0) { dispatch(setNoResult(false)) }
    else { dispatch(setNoResult(true)) }
  }, [dispatch, bestitems])

  return (
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h2>{category}</h2>
        </div>
        <section className='item-list mb_60'>
          {noResult ? bestitems.slice(0, 4).map((item, index) => 
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

        {noResult && <div className='pagination-wrap'>
          <div className='pagination'>
              <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
              <span className='material-icons'>chevron_left</span>
              </button>
              <span>{currentPage}</span> / <span>{totalPages}</span>
              <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}>
              <span className='material-icons'>chevron_right</span>
              </button>
            </div>
        </div>}
      </div>
    </section>
  );
};

export default FeaturedItemlist;
