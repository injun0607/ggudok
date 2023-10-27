import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { hover } from '@testing-library/user-event/dist/hover';
import { Pie } from 'react-chartjs-2';
// css import
import style from '../styles/Item.module.css';
// component import
import Error from '../components/Error';
import ErrorLogin from '../components/ErrorLogin';
import Loading from '../components/Loading';
import Paging from '../components/Paging';
// redux import
import { likeItemSuccess, likeItemFailure, fetchitemDetailSuccess, setLoginModal } from '../redux/actions/itemActions';
import { setReview, pagingReview, setMyItemReviewRating, setMyItemReviewContents, setMyItemReview, setReviewModal, } from '../redux/actions/reviewActions';

ChartJS.register(ArcElement, Tooltip, Legend);

const ITEMS_PER_PAGE = 5;
const NO_IMAGE_URL = '/images/common/noimg.png';


const ItemDetail = () => {
  let dispatch = useDispatch();
  const navigate = useNavigate();
  const itemDetail = useSelector(state => state.item.itemDetail);
  const reviews = useSelector(state => state.review.reviews);
  const myItemReviewRating = useSelector(state => state.review.myItemReviewRating);
  const myItemReviewContents = useSelector(state => state.review.myItemReviewContents);
  const similarItems = useSelector(state => state.item.similarItems);
  const reviewModal = useSelector(state => state.review.reviewModal);
  const loginModal = useSelector(state => state.item.loginModal);
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
  const [IsResult, setIsResult] = useState(false);
  const [IsLoading, setIsLoading] = useState(true);
  
  const { subsId } = useParams();
  
  const [itemStarAvg, setItemStarAvg] = useState([]);
  const [itemDefaultRank, setItemDefaultRank] = useState({});
  const [itemOtherRanks, setItemOtherRanks] = useState([]);
  const [ratingMap, setRatingMap] = useState({});
  
  const [IsLiked, setIsLiked] = useState(false);
  const [IsSubed, setIsSubed] = useState(false);
  
  const pagedReviews = useSelector(state => state.review.pagedReviews);
  const [page, setPage] = useState(1);
  const [startIndex, setStartIndex] = useState(0);
  const endIndex = startIndex + ITEMS_PER_PAGE;

  // ************************** 기본 아이템 fetch ***************************
  const fetchItemDetailData = async () => {
    try {
      const response = await axios.get(`/subs/detail/${subsId}`);
      const data = response.data;

      dispatch(fetchitemDetailSuccess(data));
      
      // 아이템 회원등급 Default와 나머지 분리
      const itemRanks = data.itemDetail.ranks;
      setItemDefaultRank(itemRanks.find(obj => obj.rankLevel === "DEFAULT"));
      setItemOtherRanks(itemRanks.filter(obj => obj.rankLevel !== "DEFAULT"));
      
      if(Object.keys(data.memberInfo).length !== 0){
        setIsLiked(data.memberInfo.subsLike);
        setIsSubed(data.memberInfo.subsHave);
      }
      if(data.itemDetail.reviews.length !== 0){
        dispatch(setReview(data.itemDetail.reviews));

        // 페이지 리뷰 계산
        const pagedReview = data.itemDetail.reviews.slice(startIndex, startIndex + ITEMS_PER_PAGE);
        dispatch(pagingReview(pagedReview))
        setStartIndex(0);
        setPage(1);

        // 아이템 평균별점
        const itemRatingAvg = data.itemDetail.ratingAvg;
        const newItemStarAvg = [];
        for(let i = 0; i < 5; i++){
          newItemStarAvg.push(i < itemRatingAvg);
        }
        setItemStarAvg(newItemStarAvg);
      } else {
        dispatch(setReview([]));
        dispatch(pagingReview([]))
      }
      if (isLoggedIn && data.memberInfo?.review) {
        dispatch(setMyItemReviewRating(data.memberInfo.review.rating));
        dispatch(setMyItemReviewContents(data.memberInfo.review.contents));
      }
    } catch (error) {
      console.log('Error fetching item:', error);
      alert(`서비스를 가져오던 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchItemDetailData();
  }, [dispatch, reviewModal, subsId])

  // 결과 유무
  useEffect(() => {
    if(Object.keys(itemDetail).length !== 0){setIsResult(true);}
    else{setIsResult(false);}
  }, [dispatch, itemDetail])

  // 페이지 리뷰 슬라이스
  useEffect(() => {
    const pagedReviews = reviews.slice(startIndex, startIndex + ITEMS_PER_PAGE);
    dispatch(pagingReview(pagedReviews))
  }, [dispatch, reviews, startIndex, page])

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber) => {
    setPage(pageNumber);
  };
  useEffect(() => {
    const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
    setStartIndex(newStartIndex);
  }, [page]);

  // 각 리뷰의 평균별점 업데이트
  useEffect(() => {
    const newRatingMap = reviews.map((review) => {
      const reviewRating = review.rating;
      const newReviewStar = Array(5).fill(false).map((_, index) => index < reviewRating);
      return newReviewStar;
    });
    
    setRatingMap(newRatingMap);
  }, [reviews])

  // 찜하기
  const handleLikedItem = async () => {
    if (isLoggedIn) {
      const itemId = { subsId };
      try {
        let endpoint = '/subs/like'; // 기본 엔드포인트 설정
        if (IsLiked) {
          endpoint = '/subs/dislike'; // 이미 좋아요한 경우 해제 엔드포인트로 변경
        }
  
        const response = await axios.post(`${endpoint}/${subsId}`, { subsId });
        if (response.status === 200) {
          setIsLiked(!IsLiked);
          dispatch(likeItemSuccess());
          fetchItemDetailData();
        } else {
          dispatch(likeItemFailure());
  
          alert(`관심서비스 ${IsLiked ? '해제' : '등록'} 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
        }
      } catch (error) {
        console.log(`Error ${IsLiked ? 'dislike' : 'like'} item:`, error);
        dispatch(likeItemFailure());
        alert(`관심서비스 ${IsLiked ? '해제' : '등록'} 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
      }
    }
  };

  // 모달팝업
  const handleReviewModal = () => { dispatch(setReviewModal()) }
  const handleLoginModal = () => { dispatch(setLoginModal()) }

  // 구독(해지)버튼
  const handleSubsItem = () => {
    if(!isLoggedIn){
      handleLoginModal();
    }
    else{
      const DataSubsId = { subsId };
      if (!IsSubed) {
        navigate(`/Subscribe/AddSubs`, { state: DataSubsId });
      } else {
        navigate(`/Subscribe/DelSubs`, { state: DataSubsId });
      }
    }
  }

  return (
    <>
    { !isLoggedIn && (loginModal ? <><ErrorLogin /> <div className='modalBg' onClick={ handleLoginModal }></div></> : null)}
    { reviewModal ? <><ReviewModal myItemReviewContents={myItemReviewContents} myItemReviewRating={myItemReviewRating} subsId={subsId} /> <div className='modalBg' onClick={ handleReviewModal }></div></> : null}
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        {!IsLoading ? (
        IsResult ? <div className={style.detailwrap}>
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
                    { itemStarAvg.length === 0 ?
                      <>
                        <span className={`material-icons ${style.starInActive}`}>star</span>
                        <span className={`material-icons ${style.starInActive}`}>star</span>
                        <span className={`material-icons ${style.starInActive}`}>star</span>
                        <span className={`material-icons ${style.starInActive}`}>star</span>
                        <span className={`material-icons ${style.starInActive}`}>star</span>
                      </>
                      :
                      itemStarAvg.map((starAvg, starindex) => (
                        starAvg ? <span key={starindex} className={`material-icons ${style.starActive}`}>star</span> : <span key={starindex} className="material-icons">star</span>
                      ))
                    }
                  </div>
                  <p className='txt_ex'>{reviews.length}명의 이용자가 평가했어요.</p>
                </div>
              </div>
              <div className={style.btnArea}>
                {isLoggedIn && <button type='button' className={style.likebtn} onClick={ handleLikedItem }>
                  {IsLiked ? <span className={`material-icons ${style.likeActive}`}>favorite</span> : <span className="material-icons">favorite_border</span>}
                </button> }
                {isLoggedIn && <button type='button' className={style.reviewbtn}  onClick={ () => { IsSubed ? handleReviewModal() : alert('구독중인 서비스만 리뷰를 작성할 수 있습니다.') } }>
                  <span className="material-icons">edit</span>
                </button>}
                <button type='button' className={style.subsbtn} onClick={ handleSubsItem }>
                  {!IsSubed ? '구독하기' : '구독 변경/해지'}
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
                <>
                <div className={style.ratings}>
                  { pagedReviews.map((review, index) => (
                      <article className={style.rating} key={index}>
                        <div className={style.userImg}>
                          <div className={style.circle}>
                            <img src={review.memberImage} alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
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
                <Paging handlePageChange={handlePageChange} page={page} count={reviews.length} ITEMS_PER_PAGE={ITEMS_PER_PAGE} />
                </>
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

        </div> : <Error message="존재하지 않는 구독서비스입니다." />) : <Loading />}

        
        <section className={style.section}>
          <div className='cont_tit_m'>
            <h2>비슷한 구독상품</h2>
          </div>
          <div className='item-list'>
            {similarItems.slice(0,4).map((item, index) => (
              <Link to={`/subs/detail/${item.id}`} key={index}className='item-list-box'>
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
            ))}
          </div>
        </section>
      </div>
    </section>
    </>
  );
}

const ReviewModal = ({myItemReviewRating, myItemReviewContents, subsId}) => {
  const dispatch = useDispatch();
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);

  // 리뷰모달팝업
  const handleReviewModal = () => { dispatch(setReviewModal()) }

  // 리뷰작성
  const handleWriteReview = async(e) => {
    e.preventDefault();
    if(isLoggedIn){
      if(myItemReviewContents.length === 0){
        alert('내용을 작성하세요.');
      } else if(myItemReviewRating === 0 || myItemReviewRating === undefined){
        alert('별점을 1점 이상 작성해주세요.');
      } else {
        const reviewData = {
          subsId: subsId,
          rating: myItemReviewRating,
          contents: myItemReviewContents,
        };

        dispatch(setMyItemReview(reviewData));
      }
    } else {alert('로그인 후 작성하세요.') }
  };
  // 기존 별점 가져오기
  const [editStar, setEditStar] = useState(
    new Array(5).fill(false).map((_, index) => index < myItemReviewRating)
  );
  // 별점 수정
  const handleEditStar = editstarIndex => {
    let clickStates = [...editStar];
    for (let i = 0; i < 5; i++) {
      clickStates[i] = i <= editstarIndex ? true : false;
    }
    setEditStar(clickStates);
    // 클릭된 별점 인덱스를 평점으로 변환해 전달
    const rating = editstarIndex + 1;
    dispatch(setMyItemReviewRating(rating));
  };
  let resultEditStar = editStar.filter(Boolean).length;

  return (
    <form className='modal_conts' onSubmit={ handleWriteReview }>
      <div className='modal_tit'>
        <h2>리뷰 작성하기</h2>
      </div>
      <div className={style.modalCont}>
        <div className={style.starrating}>
          <div className={style.star}>
            {[1, 2, 3, 4, 5].map((rating, starindex) => (
              <span
                key={starindex}
                className={`material-icons ${style.editStar} ${
                  rating <= resultEditStar ? style.starActive : ''
                }`}
                onClick={() => handleEditStar(rating - 1)}
                onMouseEnter={() => handleEditStar(rating - 1)}
              >
                star
              </span>
            ))}
          </div>
        </div>
        <div className={style.reviewEdit}>
          <textarea
            type='text' name='content' className={style.editArea}
            value={myItemReviewContents}
            onChange={(e) => dispatch(setMyItemReviewContents(e.target.value))}
          />
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