/* eslint-disable no-unused-vars */
import React, { useState, useEffect } from 'react';
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


ChartJS.register(ArcElement, Tooltip, Legend);
const NO_IMAGE_URL = '/images/common/noimg.png';


const ItemDetail = () => {
  let dispatch = useDispatch()
  const categories = useSelector(state => state.category.categories);
  const items = useSelector(state => state.item.items);
  const likeditems = useSelector(state => state.item.likeditems);
  const [isLikedOn, setIsLikedOn] = useState(false);
  
  const { itemId } = useParams();
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

  // item이 undefined인 경우 error 페이지
  if (!item) {
    return (
      <Error />
    );
  }

  return (
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
                  <p className='txt_ex'><span>4</span>명의 사람들이 평가했어요.</p>
                </div>
              </div>
              <div className={style.btnArea}>
                <button type='button' className={style.likebtn} onClick={ handleLikedItem }>
                  {isLikedOn ? <span className={`material-icons ${style.starActive}`}>favorite</span> : <span className="material-icons">favorite_border</span>}
                </button>
                <button type='button' className={style.reviewbtn}>
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
              <div className={style.ratings}>
                <div className={style.rating}>
                  <div className={style.userImg}>
                    <div className={style.circle}>
                      <img src='../images/common/' alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                    </div>
                  </div>
                  <div className={style.txt}>
                    <h4 className={style.name}>휘성</h4>
                    <div className={style.starrating}>
                      <div className={style.star}>
                        <span className={`material-icons ${style.starActive}`}>star</span>
                        <span className={`material-icons ${style.starActive}`}>star</span>
                        <span className={`material-icons ${style.starActive}`}>star</span>
                        <span className={`material-icons ${style.starActive}`}>star</span>
                        <span className={`material-icons ${style.starActive}`}>star</span>
                      </div>
                    </div>
                    <p className={style.review}>우리가 낳은 사랑은 버려진다 그래 이별은 너와 나의 책임이야 사랑 앞에 우린 죄인이야 울지마 바보야 나 정말 괜찮아</p>
                  </div>
                </div>
                <div className={style.rating}>
                  <div className={style.userImg}>
                    <div className={style.circle}>
                      <img src='../images/common/' alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                    </div>
                  </div>
                  <div className={style.txt}>
                    <h4 className={style.name}>임창정</h4>
                    <div className={style.starrating}>
                      <div className={style.star}>
                        <span className={`material-icons ${style.starActive}`}>star</span>
                        <span className={`material-icons ${style.starActive}`}>star</span>
                        <span className={`material-icons ${style.starActive}`}>star</span>
                        <span className="material-icons">star</span>
                        <span className="material-icons">star</span>
                      </div>
                    </div>
                    <p className={style.review}>여보세요 나야 정말 미안해 이기적인 그때의 나에게 그대를 다시 불러오라고 미친 듯이 외쳤어</p>
                  </div>
                </div>
              </div>
              {/* <div className='pagination-wrap'>
                <div className='pagination'>
                  <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
                  <span className='material-icons'>chevron_left</span>
                  </button>
                  <span>{currentPage}</span> / <span>{totalPages}</span>
                  <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}>
                  <span className='material-icons'>chevron_right</span>
                  </button>
                </div>
              </div> */}
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
  );
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