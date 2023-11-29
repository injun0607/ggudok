import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../../styles/Mypage.module.css'
// component import
import ErrorItem from '../../components/ErrorItem';
import Paging from '../../components/Paging';
// redux import
import { setMyReview, pagingMyReview, editMyReview, editMyReviewRating, editMyReviewContents, deleteMyReview } from '../../redux/actions/reviewActions';

const ITEMS_PER_PAGE = 5;
const NO_IMAGE_URL = '/images/common/noimg.png';

const MyReview = ({isCheckingLogin}) => {
  let dispatch = useDispatch()
  const reviews = useSelector(state => state.review.myReviews);
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);
  const [ratingMap, setRatingMap] = useState({});
  
  const pagedMyReviews = useSelector(state => state.review.pagedMyReviews);
  const [page, setPage] = useState(1);
  const [startIndex, setStartIndex] = useState(0);
  const endIndex = startIndex + ITEMS_PER_PAGE;
  
  // ************************** 기본 reviews fetch ***************************
  const fetchMyReviewData = async () => {
    try {
      const response = await axios.get(`/member/reviews`);
      const data = response.data.reviews;

      if(data !== 0){
        dispatch(setMyReview(data));

        // 페이지 리뷰 계산
        const pagedReview = data.slice(startIndex, startIndex + ITEMS_PER_PAGE);
        dispatch(pagingMyReview(pagedReview))
        setStartIndex(0);
        setPage(1);
      } else {
        dispatch(setMyReview([]));
        dispatch(pagingMyReview([]))
      }

      // 각 리뷰의 평균별점 업데이트
      if(data.length !== 0){
        const newRatingMap = data.map((review) => {
          const reviewRating = review.rating;
          const newReviewStar = Array(5).fill(false).map((_, index) => index < reviewRating);
          return newReviewStar;
        });
        setRatingMap(newRatingMap);
      }

    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchMyReviewData();
  }, [dispatch]);

  // 결과 유무
  useEffect(() => {
    setIsResult(reviews.length > 0);
  }, [dispatch, reviews]);

  // 페이지 리뷰 슬라이스
  useEffect(() => {
    const pagedReviews = reviews.slice(startIndex, startIndex + ITEMS_PER_PAGE);
    dispatch(pagingMyReview(pagedReviews))
  }, [dispatch, reviews, startIndex, page])

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber) => {
    setPage(pageNumber);
  };
  useEffect(() => {
    const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
    setStartIndex(newStartIndex);
  }, [page]);

  return (
    !IsLoading && <section className={`${style.section} ${style.ratingwrap}`}>
      <div className={style.ratings}>
        {IsResult && reviews.length > 0 ? 
          <>
          {pagedMyReviews.map((review, index) => (
            <ReviewItem key={index} review={review} ratingMap={ratingMap} index={index} />
          ))}
          <Paging handlePageChange={handlePageChange} page={page} count={reviews.length} ITEMS_PER_PAGE={ITEMS_PER_PAGE} />
          </>
        : <ErrorItem message="작성한 리뷰가 없습니다." />}
      </div>
    </section>
  )
}

function ReviewItem({ review, ratingMap, index }) {
  const dispatch = useDispatch();
  const subsId = review.subsId;
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
  
  // 리뷰수정
  const [isediting, setIsEditing] = useState(false);
  const handleIsEditing = function(){ setIsEditing(!isediting) }
  const editReviewRating = useSelector(state => state.review.editReviewRating);
  const editReviewContents = useSelector(state => state.review.editReviewContents);

  const handleEditReview = async(e) => {
    e.preventDefault();
    if(isLoggedIn){
      if(editReviewContents.length === 0){
        alert('내용을 작성하세요.');
      } else if(editReviewRating === 0 || editReviewRating === undefined){
        alert('별점을 1점 이상 작성해주세요.');
      } else {
        const reviewData = {
          subsId: subsId,
          rating: editReviewRating,
          contents: editReviewContents,
        };

        dispatch(editMyReview(reviewData));
      }
    } else {alert('로그인 후 작성하세요.') }
  };
  const handleDeleteReview = async(e) => {
    const itemId = { subsId };
    e.preventDefault();
    if(isLoggedIn){
      const confirm = window.confirm('리뷰를 삭제하시겠습니까?');
      if(confirm){ dispatch(deleteMyReview(itemId)); }
      else {}
    }
  }
  // 기존 별점 가져오기
  const [editStar, setEditStar] = useState(
    new Array(5).fill(false).map((_, index) => index < editReviewRating)
  );
  const handleEditStar = editstarIndex => {
    let clickStates = [...editStar];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= editstarIndex ? true : false;
    }
    setEditStar(clickStates);
    // 클릭된 별점 인덱스를 평점으로 변환해 전달
    const rating = editstarIndex + 1;
    dispatch(editMyReviewRating(rating));
  };
  let resultEditStar = editStar.filter(Boolean).length;

  return (
    <form className={style.rating} onSubmit={ handleEditReview }>
      <div className={style.service}>
        <div className={style.left}>
          <div className={style.circle}>
            <img src={review.subsImage || NO_IMAGE_URL} alt={review.subsName} />
          </div>
          <h3 className={style.name}>{review.subsName}</h3>
        </div>
        <div className={style.right}>
          <button className={style.edit} type='button' onClick={ handleIsEditing }>{isediting ? '취소' : '수정'}</button>
          <button className={style.delete} type='button' onClick={ handleDeleteReview }>삭제</button>
        </div>
      </div>
      <div className={style.txt}>
        <div className={style.starrating}>
          <div className={style.star}>
            { isediting ?
              [1, 2, 3, 4, 5].map((rating, starindex) => (
                <span key={starindex}
                  className={`material-icons ${style.editStar} ${
                    rating <= resultEditStar ? style.starActive : ''
                  }`}
                  onClick={() => handleEditStar(rating - 1)}
                  onMouseEnter={() => handleEditStar(rating - 1)}
                >
                  star
                </span>
              ))
              : 
              ratingMap[index].map((rating, starindex) => (
                rating ? <span key={starindex} className={`material-icons ${style.starActive}`}>star</span> : <span key={starindex} className="material-icons">star</span>
              ))
            }
          </div>
        </div>
        {isediting ?
        <div className={style.reviewEdit}>
          <textarea
            type='text' name='content' className={style.editArea}
            value={editReviewContents}
            onChange={(e) => dispatch(editMyReviewContents(e.target.value))}
          />
          <button type='submit' className={style.editBtn}>저장</button>
        </div>
        : <p className={style.review}>{review.contents}</p>}
      </div>
    </form>
  )
}


export default MyReview;