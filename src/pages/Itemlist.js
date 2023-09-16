import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Item.module.css';
// component import
import Filteraside from '../components/Filteraside';
import ErrorItem from '../components/ErrorItem';
// redux import
import { setNoResult, fetchItemsSuccess } from '../redux/actions/itemActions';

const ITEMS_PER_PAGE = 15;
const NO_IMAGE_URL = '/images/common/noimg.png';

const Itemlist = ({ category, categoryEng }) => {
  let dispatch = useDispatch()

  const items = useSelector(state => state.item.items);
  const noResult = useSelector(state => state.item.noResult);

  // 아이템 fetch해오기
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/subs/${categoryEng}`);
        const data = response.data.items;
        dispatch(fetchItemsSuccess(data));
      } catch (error) {
        console.error('Error fetching data:', error);
        dispatch(setNoResult(false));
      }
    };

    fetchData();
  }, [dispatch, categoryEng]);

  // 페이지네이션 구현
  const [currentPage, setCurrentPage] = useState(1);
  const totalPages = Math.ceil(items.length / ITEMS_PER_PAGE);
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const endIndex = startIndex + ITEMS_PER_PAGE;
  const slicedItems = items.slice(startIndex, endIndex);
  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
    }
  };

  // 존재하지 않는 아이템 검사
  // useEffect(() => {
  //   if (items.length === 0) { dispatch(setNoResult(false)) }
  //   else { dispatch(setNoResult(true)) }
  // }, [dispatch, items])

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
                          {
                            item.tags.map((tag, tagindex) => (
                              <p key={tagindex}>{tag.tagName}</p>
                            ))
                          }
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
