import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../../styles/Item.module.css';
// component import
import ErrorItem from '../../components/ErrorItem';
import Loading from '../../components/Loading';
import Paging from '../../components/Paging';
// redux import
import { pagingItem } from '../../redux/actions/itemActions';
import { fetchLikedItemsSuccess } from '../../redux/actions/userActions';

const ITEMS_PER_PAGE = 12;
const NO_IMAGE_URL = '/images/common/noimg.png';

const MyLike = ({ isCheckingLogin }) => {
  let dispatch = useDispatch();
  const likedItems = useSelector(state => state.user.likedItems);
  const [IsResult, setIsResult] = useState(false);
  const [IsLoading, setIsLoading] = useState(true);

  const pageditems = useSelector(state => state.item.pageditems);
  const [page, setPage] = useState(1);
  const [startIndex, setStartIndex] = useState(0);
  const endIndex = startIndex + ITEMS_PER_PAGE;

  // ************************** 기본 아이템 fetch ***************************
  const fetchLikedItemData = async () => {
    try {
      const response = await axios.get(`/member/favor_subs`);
      const data = response.data.items;

      if(data !== 0){
        dispatch(fetchLikedItemsSuccess(data));

        // 페이지 리뷰 계산
        const pagedReview = data.slice(startIndex, startIndex + ITEMS_PER_PAGE);
        dispatch(pagingItem(pagedReview))
        setStartIndex(0);
        setPage(1);
      } else {
        dispatch(fetchLikedItemsSuccess([]));
        dispatch(pagingItem([]))
      }
      
    } catch (error) {
      console.error('Error fetching item:', error);
      alert(`서비스를 가져오던 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchLikedItemData();
  }, [dispatch]);
  
  // 결과 유무
  useEffect(() => {
    setIsResult(likedItems.length > 0);
  }, [dispatch, likedItems, isCheckingLogin]);

  // 페이지 리뷰 슬라이스
  useEffect(() => {
    const pagedReviews = likedItems.slice(startIndex, startIndex + ITEMS_PER_PAGE);
    dispatch(pagingItem(pagedReviews))
  }, [dispatch, likedItems, startIndex, page])

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber) => {
    setPage(pageNumber);
  };
  useEffect(() => {
    const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
    setStartIndex(newStartIndex);
  }, [page]);

  return (
    !IsLoading ? (IsResult ?
    <>
    <section className={style.section}>
      <div className={style.itemlist}>
        {likedItems.map((item, index) => (
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
    <Paging handlePageChange={handlePageChange} page={page} count={likedItems.length} ITEMS_PER_PAGE={ITEMS_PER_PAGE} />
    </>
    : <ErrorItem />) : <Loading />
  )
}

export default MyLike;