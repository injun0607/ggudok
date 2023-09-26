import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../../styles/Mypage.module.css'

const NO_IMAGE_URL = '/images/common/noimg.png';

const MyReview = () => {
  const navigate = useNavigate(); 

  const reviews = useSelector(state => state.review.reviews);

  return (
    <section className={`${style.section} ${style.ratingwrap}`}>
      <div className={style.ratings}>
        {reviews.map((review, index) => (
          <ReviewItem key={index} review={review} />
        ))}
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
          <h3 className={style.name}>{review.itemname}</h3>
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
          : review.star.map((isActive, starIndex) => (
            <span key={starIndex} className={`material-icons ${isActive ? style.starActive : ''}`}>
              star
            </span>
          )) }
          </div>
        </div>
        {isediting ?
        <div className={style.reviewEdit}>
          <textarea type='text' name='content' className={style.editArea} />
          <button type='submit' className={style.editBtn}>저장</button>
        </div>
        : <p className={style.review}>{review.contents}</p>}
      </div>
    </article>
  )
}


export default MyReview;