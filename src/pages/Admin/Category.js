import React, { useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Admin/AdminLayout.module.css';
//redux import
import { useDispatch } from 'react-redux';

const Category = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  // 수정
  const handleEditCategory = () => {
    navigate(`/Subscribe/AddSubs`, { state: '' });
  }

  return (
    <div className='webwidth'>
      <div className='cont_tit_m'>
        <h2>카테고리 목록</h2>
      </div>
      <section className={style.tableWrap}>
        <Link to="/Admin/CategoryCreate" className='btn_full02 btn_s'>신규 카테고리 등록</Link>
        <div className={style.tbScroll}>
          <table className={style.table}>
            <colgroup>
              <col width={'80px'} />
              <col width={'15%'} />
              <col width={'*'} />
              <col width={'250px'} />
            </colgroup>
            <thead>
              <tr>
                <th>번호</th>
                <th>아이콘</th>
                <th>카테고리명</th>
                <th>관리</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td className={style.tdIcon}><img src='/images/icons/ott.svg' alt='영상' /></td>
                <td>영상</td>
                <td className={style.tdBtn}>
                  <button type='button' className='btn_xs btn_normal' onClick={ handleEditCategory }>수정</button>
                  <button type='button' className='btn_xs btn_full'>삭제</button>
                </td>
              </tr>
              <tr>
                <td>1</td>
                <td className={style.tdIcon}><img src='/images/icons/ott.svg' alt='영상' /></td>
                <td>영상</td>
                <td className={style.tdBtn}>
                  <button type='button' className='btn_xs btn_normal' onClick={ handleEditCategory }>수정</button>
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

export default Category;