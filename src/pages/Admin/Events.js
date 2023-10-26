import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
// css import
import style from '../../styles/Admin/AdminLayout.module.css';
//redux import
import { useDispatch } from 'react-redux';

const Events = () => {
  const dispatch = useDispatch();

  return (
    <div className='webwidth'>
      <div className='cont_tit_m'>
        <h2>이벤트 목록</h2>
      </div>
      <section className={style.tableWrap}>
        <Link to="/Admin/CategoryCreate" className='btn_full02 btn_s'>신규 이벤트 등록</Link>
        <div className={style.tbScroll}>
          <table className={style.table}>
            <colgroup>
              <col width={'80px'} />
              <col width={'*'} />
              <col width={'*'} />
              <col width={'*'} />
              <col width={'120px'} />
              <col width={'120px'} />
              <col width={'250px'} />
            </colgroup>
            <thead>
              <tr>
                <th>번호</th>
                <th>구독서비스명</th>
                <th>이벤트 태그</th>
                <th>이벤트 설명</th>
                <th>시작날짜</th>
                <th>종료날짜</th>
                <th>관리</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>스마트 비디오</td>
                <td>40% 할인</td>
                <td>지루한 날을 단번에 날려주는 스마트 비디오 OTT 서비스</td>
                <td>20231026</td>
                <td>20231026</td>
                <td className={style.tdBtn}>
                  <button type='button' className='btn_xs btn_normal'>수정</button>
                  <button type='button' className='btn_xs btn_full'>삭제</button>
                </td>
              </tr>
              <tr>
                <td>2</td>
                <td>스마트 비디오</td>
                <td>40% 할인</td>
                <td>지루한 날을 단번에 날려주는 스마트 비디오 OTT 서비스</td>
                <td>20231026</td>
                <td>20231026</td>
                <td className={style.tdBtn}>
                  <button type='button' className='btn_xs btn_normal'>수정</button>
                  <button type='button' className='btn_xs btn_full'>삭제</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </div>
  )
}

export default Events;