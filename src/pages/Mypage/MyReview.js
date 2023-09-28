import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../../styles/Mypage.module.css'
// component import
import ErrorItem from '../../components/ErrorItem';
// redux import
import { setIsLoading, setIsResult } from '../../redux/actions/itemActions';
import { setReview } from '../../redux/actions/reviewActions';

const NO_IMAGE_URL = '/images/common/noimg.png';

const MyReview = ({isCheckingLogin}) => {
  let dispatch = useDispatch()
  const reviews = useSelector(state => state.review.reviews);
  const IsResult = useSelector(state => state.item.IsResult);
  const IsLoading = useSelector(state => state.item.IsLoading);
  
  // ************************** 기본 reviews fetch ***************************
  const fetchMyReviewData = async () => {
    try {
      const response = await axios.get(`/member/reviews`);
      const data = response.data.reviews;

      dispatch(setReview(data));
      dispatch(setIsResult(true));
    } catch (error) {
      console.error('Error fetching data:', error);
      dispatch(setIsResult(false));
    } finally {
      dispatch(setIsLoading(false));
    }
  };
  useEffect(() => {
    fetchMyReviewData();
  }, [isCheckingLogin]);
  
  // 각 리뷰의 평균별점
  let reviewRatingMap = {};
  for(let i = 0; i < reviews.length; i++){
    const reviewIndex = i;
    const reviewRating = reviews[i].rating;
    const newReviewStar = [];
    for(let i = 0; i < 5; i++){
      newReviewStar.push(i < reviewRating);
    }
    reviewRatingMap[reviewIndex] = newReviewStar;
  }

  return (
    !IsLoading && <section className={`${style.section} ${style.ratingwrap}`}>
      <div className={style.ratings}>
        {IsResult ? reviews.map((review, index) => (
          <ReviewItem key={index} review={review} />
        )) : <ErrorItem />}
      </div>
    </section>
  )
}

function ReviewItem({ review }) {
  const dispatch = useDispatch();

  // 리뷰수정
  const [isediting, setIsEditing] = useState(false);
  const [editStar, setEditStar] = useState([false, false, false, false, false]);
  const handleIsEditing = function(){ setIsEditing(!isediting) }
  const handleEditStar = index => {
    let clickStates = [...editStar];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= index ? true : false;
    }
    setEditStar(clickStates);
  };
  let resultEditStar = editStar.filter(Boolean).length;

  useEffect(() => {
    return () => { setEditStar(review.star); };
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [dispatch, isediting])

  return (
    <article className={style.rating}>
      <div className={style.service}>
        <div className={style.left}>
          <div className={style.circle}>
            <img src='../images/common/' alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
          </div>
          <h3 className={style.subsName}>{review.itemsubsName}</h3>
        </div>
        <div className={style.right}>
          <button className={style.edit} type='button' onClick={ handleIsEditing }>{isediting ? '취소' : '수정'}</button>
          <button className={style.delete} type='button'>삭제</button>
        </div>
      </div>
      <div className={style.txt}>
        <div className={style.starrating}>
          <div className={style.star}>
          { isediting ?
            editStar.map((EditActive, editstarIndex) => (
              <span
                key={editstarIndex}
                className={`material-icons ${style.editStar} ${EditActive ? style.starActive : ''}`}
                onClick={() => handleEditStar(editstarIndex)}
                onMouseEnter={() => handleEditStar(editstarIndex)}
              >
                star
              </span>
            ))
            : 
            review.rating.map((reviewRating, starindex) => (
              reviewRating ? <span key={starindex} className={`material-icons ${style.starActive}`}>star</span> : <span key={starindex} className="material-icons">star</span>
            ))
          }
          {/* { isediting ?
            editStar.map((EditActive, editstarIndex) => (
              <span
                key={editstarIndex}
                className={`material-icons ${style.editStar} ${EditActive ? style.starActive : ''}`}
                onClick={() => handleEditStar(editstarIndex)}
                onMouseEnter={() => handleEditStar(editstarIndex)}
              >
                star
              </span>
            ))
          : review.star.map((isActive, starIndex) => (
            <span key={starIndex} className={`material-icons ${isActive ? style.starActive : ''}`}>
              star
            </span>
          )) } */}
          </div>
        </div>
        {isediting ?
        <div className={style.reviewEdit}>
          <textarea type='text' subsName='content' className={style.editArea} />
          <button type='submit' className={style.editBtn}>저장</button>
        </div>
        : <p className={style.review}>{review.contents}</p>}
      </div>
    </article>
  )
}


export default MyReview;