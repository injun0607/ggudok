import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, Link } from 'react-router-dom';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { hover } from '@testing-library/user-event/dist/hover';
import { Pie } from 'react-chartjs-2';
// css import
import style from '../styles/Item.module.css';
// component import
import Error from '../components/Error';
import Loading from '../components/Loading';
// redux import
import { setLikeItem, setIsLoading, setIsResult, fetchitemDetailSuccess } from '../redux/actions/itemActions';
import { setReview, setReviewModal, } from '../redux/actions/reviewActions';

ChartJS.register(ArcElement, Tooltip, Legend);

const ITEMS_PER_PAGE = 5;
const NO_IMAGE_URL = '/images/common/noimg.png';


const ItemDetail = () => {
  let dispatch = useDispatch();
  const itemDetail = useSelector(state => state.item.itemDetail);
  const reviews = useSelector(state => state.review.reviews);
  const similarItems = useSelector(state => state.item.similarItems);
  const likeditems = useSelector(state => state.item.likeditems);
  const reviewModal = useSelector(state => state.review.reviewModal);
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
  const IsResult = useSelector(state => state.item.IsResult);
  const IsLoading = useSelector(state => state.item.IsLoading);
  
  const { subsId } = useParams();

  const [isLikedOn, setIsLikedOn] = useState(false);
  const [IsPager, setIsPager] = useState(false);
  const [slicedItems, setSlicedItems] = useState([]);
  const [itemStarAvg, setItemStarAvg] = useState([]);
  const [itemDefaultRank, setItemDefaultRank] = useState({});
  const [itemOtherRanks, setItemOtherRanks] = useState([]);
  const [ratingMap, setRatingMap] = useState({});

  // ************************** 기본 아이템 fetch ***************************
  const fetchItemDetailData = async () => {
    try {
      const response = await axios.get(`/subs/detail/${subsId}`);
      const data = response.data;

      dispatch(fetchitemDetailSuccess(data));
      dispatch(setReview(data.itemDetail.reviews));

      console.log('1')
      // 아이템 평균별점
      const itemRatingAvg = data.itemDetail.ratingAvg;
      const newItemStarAvg = [];
      for(let i = 0; i < 5; i++){
        newItemStarAvg.push(i < itemRatingAvg);
      }
      setItemStarAvg(newItemStarAvg);
      // 아이템 회원등급 Default와 나머지 분리
      const itemRanks = data.itemDetail.ranks;
      setItemDefaultRank(itemRanks.find(obj => obj.rankLevel === "DEFAULT"));
      setItemOtherRanks(itemRanks.filter(obj => obj.rankLevel !== "DEFAULT"));

      dispatch(setIsResult(true));
      console.log('2')
    } catch (error) {
      dispatch(setIsResult(false));
    } finally {
      console.log('3')
      dispatch(setIsLoading(false));
    }
  };
  useEffect(() => {
    fetchItemDetailData();
  }, [])

  useEffect(() => {
    // 각 리뷰의 평균별점 업데이트
    const newRatingMap = reviews.map((review) => {
      const reviewRating = review.rating;
      const newReviewStar = Array(5).fill(false).map((_, index) => index < reviewRating);
      return newReviewStar;
    });
    
    setRatingMap(newRatingMap);
  }, [reviews])


  // 찜하기
  useEffect(() => {
    const likedItem = likeditems.find(likeditem => likeditem.subsId === subsId);
    if (likedItem === undefined) {
      setIsLikedOn(false)
    } else { setIsLikedOn(true) }
  }, [dispatch, isLikedOn, likeditems, itemDetail]);

  const handleLikedItem = async() => {
    const itemId = { subsId };
    dispatch(setLikeItem(itemId));
  };

  // 리뷰모달팝업
  const handleReviewModal = () => { dispatch(setReviewModal()) }

  return (
    <>
    { reviewModal ? <><ReviewModal /> <div className='modalBg' onClick={ handleReviewModal }></div></> : null}
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        {!IsLoading ? (
        Object.keys(itemDetail).length !== 0 ? <div className={style.detailwrap}>
          <section className={style.left}>
            <div className={style.fixed}>
              <div className={style.box}>
                <h2 className={style.name}>{itemDetail.name}</h2>
                <div className={style.tag}>
                  <p>{itemDetail.category}</p>
                  {
                    itemDetail.tags.map((tag, tagindex) => (
                      <p key={tagindex}>{tag.tagName}</p>
                    ))
                  }
                </div>
                <div className={style.price}>
                  {
                    itemOtherRanks.map((otherRank, otherRankindex) => (
                      <p className={style.subprice} key={otherRankindex}>
                      {otherRank.rankName} <span className={style.point}>월 {otherRank.price} 원</span>
                    </p>
                    ))
                  }
                  <p className={style.mainprice}>
                    {itemDefaultRank.rankName} <span className={style.point}>월 {itemDefaultRank.price} 원</span>
                  </p>
                </div>
                <div className={style.starrating}>
                  <div className={style.star}>
                    {
                      itemStarAvg.map((starAvg, starindex) => (
                        starAvg ? <span key={starindex} className={`material-icons ${style.starActive}`}>star</span> : <span key={starindex} className="material-icons">star</span>
                      ))
                    }
                  </div>
                  <p className='txt_ex'>{reviews.length}명의 사람들이 평가했어요.</p>
                </div>
              </div>
              <div className={style.btnArea}>
                {isLoggedIn && <button type='button' className={style.likebtn} onClick={ handleLikedItem }>
                  {isLikedOn ? <span className={`material-icons ${style.likeActive}`}>favorite</span> : <span className="material-icons">favorite_border</span>}
                </button> }
                <button type='button' className={style.reviewbtn} onClick={ handleReviewModal }>
                  <span className="material-icons">edit</span>
                </button>
              </div>
            </div>
          </section>
          <section className={style.right}>
            <article className={`${style.cont} ${style.detailLg}`}>
              <div className={style.img}>
                <img src={`${itemDetail.image}`} alt={itemDetail.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
              </div>
            </article>
            <article className={style.cont}>
              <p>{itemDetail.info}</p>
            </article>
            <article className={`${style.cont} ${style.ratingwrap}`}>
              <div className={style.tit}>
                <h3>구독자 한줄평</h3>
              </div>
              { ratingMap && ratingMap.length > 0 ? (
                <div className={style.ratings}>
                  { reviews.map((review, index) => (
                      <article className={style.rating} key={index}>
                        <div className={style.userImg}>
                          <div className={style.circle}>
                            <img src='../images/common/' alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                          </div>
                        </div>
                        <div className={style.txt}>
                          <h4 className={style.name}>{review.memberName}</h4>
                          <div className={style.starrating}>
                            <div className={style.star}>
                              { ratingMap[index].map((rating, starindex) => (
                                  rating ? <span key={starindex} className={`material-icons ${style.starActive}`}>star</span> : <span key={starindex} className="material-icons">star</span>
                                ))
                              }
                            </div>
                          </div>
                          <p className={style.review}>{review.contents}</p>
                        </div>
                      </article>
                    ))}
                </div>
              ) : (
                <div className='txt_grey txt_center'>등록된 리뷰가 없습니다.</div>
              )}
              {/* { reviews.length > 5 ? <div className='pagination-wrap'>
                <div className='pagination'>
                  <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
                  <span className='material-icons'>chevron_left</span>
                  </button>
                  <span>{currentPage}</span> / <span>{totalPages}</span>
                  <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}>
                  <span className='material-icons'>chevron_right</span>
                  </button>
                </div>
              </div> : null } */}
            </article>
            <article className={`${style.cont} ${style.chartwrap}`}>
              <div className={style.tit}>
                <h3>구독 유형 분석</h3>
              </div>
              <div className={style.chart}>
                <Pie data={dataAge} />
              </div>
              <div className={style.chart}>
                <Pie data={dataGender} />
              </div>
            </article>
          </section>

        </div> : <Error />) : <Loading />}

        
        <section className={style.section}>
          <div className='cont_tit_m'>
            <h2>비슷한 구독상품</h2>
          </div>
          <div className={style.itemlist}>
            {/* {similarItems.map((item, index) => (
              <Link to={`/subs/detail/${item.id}`} key={index} className={style.item}>
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
            ))} */}
          </div>
        </section>
      </div>
    </section>
    </>
  );
}

const ReviewModal = () => {
  const dispatch = useDispatch();
  const reviewModal = useSelector(state => state.review.reviewModal);

  // 리뷰모달팝업
  const handleReviewModal = () => { dispatch(setReviewModal()) }

  //별점 수정
  const [editStar, setEditStar] = useState([false, false, false, false, false]);
  const handleEditStar = editstarIndex => {
    let clickStates = [...editStar];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= editstarIndex ? true : false;
    }
    setEditStar(clickStates);
  };
  let resultEditStar = editStar.filter(Boolean).length;

  return (
    <form className='modal_conts'>
      <div className='modal_tit'>
        <h2>리뷰 작성하기</h2>
      </div>
      <div className={style.modalCont}>
        <div className={style.starrating}>
          <div className={style.star}>
            {/* { editStar.map((EditActive, editstarIndex) => (
              <span
                key={editstarIndex}
                className={`material-icons ${style.editStar} ${EditActive ? style.starActive : ''}`}
                onClick={() => handleEditStar(editstarIndex)}
                onMouseEnter={() => handleEditStar(editstarIndex)}
              >
                star
              </span>
            )) } */}
          </div>
        </div>
        <div className={style.reviewEdit}>
          <textarea type='text' name='content' className={style.editArea} />
        </div>
        <div className={style.doublebutton}>
          <Link className='btn_s btn_normal' onClick={ handleReviewModal }>닫기</Link>
          <button type='submit' className='btn_s btn_full'>저장</button>
        </div>
      </div>
    </form>
  )
}

export const dataAge = {
  labels: ['20대 미만', '20대', '30대', '40대', '50대', '60대 이상'],
  datasets: [
    {
      label: '구독자 수',
      data: [12, 19, 3, 5, 2, 3],
      backgroundColor: [
        '#FF724C',
        '#FF8E6F',
        '#FFA194',
        '#FFBDB9',
        '#FFD3CD',
        '#FFEAE6',
        '#FF533C',
        '#FF2A00',
        '#CC1E00',
        '#990F00',
      ],
      borderColor: [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)',
      ],
      borderWidth: 0,
    },
  ],
};
export const dataGender = {
  labels: ['여성', '남성'],
  datasets: [
    {
      label: '구독자 수',
      data: [12, 19],
      backgroundColor: [
        '#d4d9dd',
        '#858c94',
        // '#FFCA7A',
        // '#FFD699',
        // '#FFE3B8',
        // '#FFEDD2',
        // '#FFF6EB',
        // '#FDAB50',
        // '#FEEDC3',
        // '#FBCF75',
        // '#F8BB25',
      ],
      borderColor: [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
      ],
      borderWidth: 0,
    },
  ],
};

export default ItemDetail;