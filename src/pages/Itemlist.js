import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Item.module.css';
// component import
import Filteraside from '../components/Filteraside';
import ErrorItem from '../components/ErrorItem';
// redux import
import { setNoResult } from '../redux/actions/itemActions';

const ITEMS_PER_PAGE = 15;
const NO_IMAGE_URL = '/images/common/noimg.png';

const Itemlist = ({ category }) => {
  let dispatch = useDispatch()

  const items = useSelector(state => state.item.items);
  const filteredItems = items.filter(item => item.category === category);
  const noResult = useSelector(state => state.item.noResult);

  // 페이지네이션 구현
  const [currentPage, setCurrentPage] = useState(1);

  const totalPages = Math.ceil(filteredItems.length / ITEMS_PER_PAGE);

  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const endIndex = startIndex + ITEMS_PER_PAGE;
  const slicedItems = filteredItems.slice(startIndex, endIndex);

  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
    }
  };


  // 존재하지 않는 아이템 검사
  useEffect(() => {
    console.log(filteredItems.length)
    if (filteredItems.length === 0) { dispatch(setNoResult(false)) }
    else { dispatch(setNoResult(true)) }
  }, [dispatch, filteredItems])

  return (
    <section className={style.pagewrap}>
      <div className='webwidth'>
        <div className='page_tit page_tit-side'>
          <h2>{category}</h2>
        </div>
        <div className={style.categorysection}>
          <Filteraside />
          <section className={style.right}>
            <div className={style.section}>
              <div className={style.itemlist}>
                {noResult ? slicedItems.map((item, index) => (
                  <Link to={`/ItemDetail/${item.id}`} key={index} className={style.item}>
                    {/* <div className={style.item}> */}
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
                    {/* </div> */}
                  </Link>
                )) : <ErrorItem />}
              </div>
            </div>

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
          </section>
        </div>
      </div>
    </section>
  );
};

export default Itemlist;
