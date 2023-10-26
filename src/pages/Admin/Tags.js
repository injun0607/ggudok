import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
// css import
import style from '../../styles/Admin/AdminLayout.module.css';
//redux import
import { useDispatch } from 'react-redux';

const Tags = () => {
  const dispatch = useDispatch();

  return (
    <div className='webwidth'>
      <div className='cont_tit_m'>
        <h2>태그 목록</h2>
      </div>
      <section className={style.tableWrap}>
        <Link to="/Admin/CategoryCreate" className='btn_full02 btn_s'>신규 태그 등록</Link>
        <div className={style.tbScroll}>
          <table className={style.table}>
            <colgroup>
              <col width={'80px'} />
              <col width={'*'} />
              <col width={'250px'} />
            </colgroup>
            <thead>
              <tr>
                <th>번호</th>
                <th>태그명</th>
                <th>관리</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>20대</td>
                <td className={style.tdBtn}>
                  <button type='button' className='btn_xs btn_normal'>수정</button>
                  <button type='button' className='btn_xs btn_full'>삭제</button>
                </td>
              </tr>
              <tr>
                <td>2</td>
                <td>30대</td>
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

export default Tags;