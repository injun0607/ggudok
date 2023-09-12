/* eslint-disable no-unused-vars */
import React, { useState, useEffect, useRef } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, Link } from 'react-router-dom';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';
// css import
import style from '../styles/Item.module.css';
// component import
import Error from '../components/Error';
// redux import
import { setLikedItem, } from '../redux/actions/itemActions';
import { setReviewModal, } from '../redux/actions/reviewActions';
import { hover } from '@testing-library/user-event/dist/hover';

ChartJS.register(ArcElement, Tooltip, Legend);

const ITEMS_PER_PAGE = 5;
const NO_IMAGE_URL = '/images/common/noimg.png';


const ItemDetail = () => {
  let dispatch = useDispatch()
  const items = useSelector(state => state.item.items);
  const likeditems = useSelector(state => state.item.likeditems);
  const reviewModal = useSelector(state => state.review.reviewModal);
  const reviews = useSelector(state => state.review.reviews);

  const [isLikedOn, setIsLikedOn] = useState(false);
  
  const { itemId } = useParams();
  
  // 해당 아이템의 리뷰 filter
  const filteredReviews = reviews.filter(review => review.itemid === itemId );
  
  // 리뷰 페이지네이션 구현
  const [currentPage, setCurrentPage] = useState(1);
  
  const totalPages = Math.ceil(filteredReviews.length / ITEMS_PER_PAGE);
  
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const endIndex = startIndex + ITEMS_PER_PAGE;
  const slicedReviews = filteredReviews.slice(startIndex, endIndex);

  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
    }
  };

  // 관련상품
  const item = items.find(item => item.id === itemId);
  const similarItems = items.slice(0,4).filter(itemall => item.category === itemall.category);

  // 찜하기
  useEffect(() => {
    const likedItem = likeditems.find(likeditem => likeditem.id === itemId);
    if (likedItem === undefined) {
      setIsLikedOn(false)
    } else { setIsLikedOn(true) }
  }, [dispatch, isLikedOn, likeditems, items, itemId]);

  const handleLikedItem = () => { dispatch(setLikedItem(item)) }

  // 리뷰모달팝업
  const handleReviewModal = () => { dispatch(setReviewModal()) }

  // item이 undefined인 경우 error 페이지
  if (!item) { return ( <Error /> ); }

  return (
    <>
    { reviewModal ? <><ReviewModal /> <div className='modalBg' onClick={ handleReviewModal }></div></> : null}
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className={style.detailwrap}>
          <section className={style.left}>
            <div className={style.fixed}>
              <div className={style.box}>
                <h2 className={style.name}>{item.name}</h2>
                <div className={style.tag}>
                  <p>{item.category}</p>
                  <p>{item.tag}</p>
                </div>
                <div className={style.price}>
                  <p className={style.subprice}>
                    프리미엄 <span className={style.point}>월 12,900 원</span>
                  </p>
                  <p className={style.mainprice}>
                    스탠다드 <span className={style.point}>월 8,900 원</span>
                  </p>
                </div>
                <div className={style.starrating}>
                  <div className={style.star}>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className="material-icons">star</span>
                  </div>
                  <p className='txt_ex'>{filteredReviews.length}명의 사람들이 평가했어요.</p>
                </div>
              </div>
              <div className={style.btnArea}>
                <button type='button' className={style.likebtn} onClick={ handleLikedItem }>
                  {isLikedOn ? <span className={`material-icons ${style.likeActive}`}>favorite</span> : <span className="material-icons">favorite_border</span>}
                </button>
                <button type='button' className={style.reviewbtn} onClick={ handleReviewModal }>
                  <span className="material-icons">edit</span>
                </button>
              </div>
            </div>
          </section>
          <section className={style.right}>
            <article className={`${style.cont} ${style.detailLg}`}>
              <div className={style.img}>
                <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
              </div>
            </article>
            <article className={style.cont}>
              <p>
              스마트비디오는 혁신적인 OTT(Over-The-Top) 구독 서비스 기업으로, 최신 영화, 드라마, TV 프로그램, 애니메이션 등 다양한 콘텐츠를 제공하여 고객들에게 풍부한 시청 경험을 선사합니다.<br />우리는 사용자들의 다양한 취향과 관심사를 반영하여 개인 맞춤형 추천 알고리즘을 통해 최적의 콘텐츠를 제공하며, 접근성과 편리성을 추구하여 멀티 플랫폼에서 사용 가능한 통합된 서비스를 제공합니다. 고객의 편의를 최우선으로 생각하여 다양한 결제 옵션과 유연한 요금제를 제공하며, 탁월한 고객 서비스 품질로 항상 사용자들의 만족을 위해 노력하고 있습니다.
              </p>
            </article>
            <article className={`${style.cont} ${style.ratingwrap}`}>
              <div className={style.tit}>
                <h3>구독자 한줄평</h3>
              </div>
              {filteredReviews.length > 0 ? <div className={style.ratings}>
                {slicedReviews.map((slicedReview, index) => (
                  <article className={style.rating} key={index}>
                  <div className={style.userImg}>
                    <div className={style.circle}>
                      <img src='../images/common/' alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                    </div>
                  </div>
                  <div className={style.txt}>
                    <h4 className={style.name}>{slicedReview.username}</h4>
                    <div className={style.starrating}>
                      <div className={style.star}>
                        { slicedReview.star.map((isActive, starIndex) => (
                        <span key={starIndex} className={`material-icons ${isActive ? style.starActive : ''}`}>
                          star
                        </span>
                      )) }
                      </div>
                    </div>
                    <p className={style.review}>{slicedReview.contents}</p>
                  </div>
                </article>
                ))}
              </div>
              : <div className='txt_grey txt_center'>등록된 리뷰가 없습니다.</div> }
              { filteredReviews.length > 5 ? <div className='pagination-wrap'>
                <div className='pagination'>
                  <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
                  <span className='material-icons'>chevron_left</span>
                  </button>
                  <span>{currentPage}</span> / <span>{totalPages}</span>
                  <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}>
                  <span className='material-icons'>chevron_right</span>
                  </button>
                </div>
              </div> : null }
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

        </div>

        
        <section className={style.section}>
          <div className='cont_tit_m'>
            <h2>비슷한 구독상품</h2>
          </div>
          <div className={style.itemlist}>
            {similarItems.map((item, index) => (
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
            ))}
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
            { editStar.map((EditActive, editstarIndex) => (
              <span
                key={editstarIndex}
                className={`material-icons ${style.editStar} ${EditActive ? style.starActive : ''}`}
                onClick={() => handleEditStar(editstarIndex)}
                onMouseEnter={() => handleEditStar(editstarIndex)}
              >
                star
              </span>
            )) }
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
        '#FDBF50',
        '#80C3FC',
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