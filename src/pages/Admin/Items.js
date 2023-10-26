import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
// css import
import style from '../../styles/Admin/AdminLayout.module.css';
//redux import
import { useDispatch } from 'react-redux';

const Items = () => {
  const dispatch = useDispatch();

  return (
    <div className='webwidth'>
      <div className='cont_tit_m'>
        <h2>구독서비스 목록</h2>
      </div>
      <section className={style.tableWrap}>
        <Link to="/Admin/CategoryCreate" className='btn_full02 btn_s'>신규 구독서비스 등록</Link>
        <div className={style.tbScroll}>
          <table className={style.table}>
            <colgroup>
              <col width={'80px'} />
              <col width={'*'} />
              <col width={'15%'} />
              <col width={'350px'} />
            </colgroup>
            <thead>
              <tr>
                <th>번호</th>
                <th>구독서비스명</th>
                <th>카테고리</th>
                <th>관리</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>스마트비디오</td>
                <td>영상</td>
                <td className={style.tdBtn}>
                  <button type='button' className='btn_xs btn_normal'>태그관리</button>
                  <button type='button' className='btn_xs btn_full03'>이벤트 등록</button>
                  <button type='button' className='btn_xs btn_full'>상세관리</button>
                </td>
              </tr>
              <tr>
                <td>2</td>
                <td>클립비젼</td>
                <td>음료</td>
                <td className={style.tdBtn}>
                  <button type='button' className='btn_xs btn_normal'>태그관리</button>
                  <button type='button' className='btn_xs btn_full03'>이벤트 등록</button>
                  <button type='button' className='btn_xs btn_full'>상세관리</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </section>
    </div>
  )
}

export default Items;