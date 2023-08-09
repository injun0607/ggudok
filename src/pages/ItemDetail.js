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

ChartJS.register(ArcElement, Tooltip, Legend);
const NO_IMAGE_URL = '/images/common/noimg.png';


const ItemDetail = () => {
  const categories = useSelector(state => state.category.categories);
  let dispatch = useDispatch()
  const items = useSelector(state => state.item.items);
  
  const { itemId } = useParams();
  const item = items.find(item => item.id === itemId);
  const similarItems = items.slice(0,5).filter(itemall => item.category === itemall.category);

  // item이 undefined인 경우 예외 처리
  if (!item) {
    return (
      <Error />
    );
  }

  return (
    <div className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className={style.detailsection}>

          <div className={style.left}>
            <div className={style.detailLg}>
              <div className={style.img}>
                <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
              </div>
            </div>
            <div className={style.detailSm}>
              <div className={style.cont}>
                <p>
                스마트비디오는 혁신적인 OTT(Over-The-Top) 구독 서비스 기업으로, 최신 영화, 드라마, TV 프로그램, 애니메이션 등 다양한 콘텐츠를 제공하여 고객들에게 풍부한 시청 경험을 선사합니다.<br />우리는 사용자들의 다양한 취향과 관심사를 반영하여 개인 맞춤형 추천 알고리즘을 통해 최적의 콘텐츠를 제공하며, 접근성과 편리성을 추구하여 멀티 플랫폼에서 사용 가능한 통합된 서비스를 제공합니다. 고객의 편의를 최우선으로 생각하여 다양한 결제 옵션과 유연한 요금제를 제공하며, 탁월한 고객 서비스 품질로 항상 사용자들의 만족을 위해 노력하고 있습니다.
                </p>
              </div>
            </div>
            <div className={style.chartwrap}>
              <div className={style.tit}>
                <h5>구독 유형 분석</h5>
              </div>
              <div className={style.chart}>
                <Pie data={dataAge} />
              </div>
              <div className={style.chart}>
                <Pie data={dataGender} />
              </div>
            </div>
          </div>

          <div className={style.right}>
            <div className={style.fixed}>
              <div className={style.box}>
                <h2 className={style.name}>{item.name}</h2>
                <div className={style.tag}>
                  <p>{item.category}</p>
                  <p>{item.tag}</p>
                </div>
                <div className={style.price}>
                  <p>한 달 <span className={style.point}>40,000 원</span></p>
                </div>
                <div className={style.starrating}>
                  <div className={style.star}>
                    <span className="material-icons">star</span>
                    <span className="material-icons">star</span>
                    <span className="material-icons">star</span>
                    <span className="material-icons">star</span>
                    <span className="material-icons">star</span>
                  </div>
                  <p className='txt_ex'><span>4</span>명의 사람들이 평가했어요.</p>
                </div>
              </div>
            </div>
          </div>

        </div>

        
        <div className={style.section}>
          <div className='cont_tit_m'>
            <h5>비슷한 구독상품</h5>
          </div>
          <div className={style.itemlist}>
            {similarItems.map((item, index) => (
              <Link to={`/ItemDetail/${item.id}`} key={index} className={style.item}>
                {/* <div className={style.item}> */}
                  <div className={style.img}>
                    <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                  </div>
                  <div className={style.txt}>
                    <h4>{item.name}</h4>
                    <div className={style.tag}>
                      <p>{item.category}</p>
                      <p>{item.tag}</p>
                    </div>
                  </div>
                {/* </div> */}
              </Link>
            ))}
          </div>
        </div>
      </div>
    </div>
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