import React, { useEffect } from 'react';
import { Link } from 'react-router-dom';
// css import
import style from '../../styles/Admin/AdminLayout.module.css';
//redux import
import { useDispatch } from 'react-redux';

const AdminHome = () => {
  const dispatch = useDispatch();

  return (
    <section className={style.dashboxWrap}>
      <article className={style.dashbox}>
        <Link to="/Admin/Category" className={style.dashtit}>
          <h2>카테고리 관리</h2>
          <button className={style.moreBtn}>
            <span className='material-icons'>chevron_right</span>
          </button>
        </Link>
        <div className={style.dashcont}>
          <div className={`${style.tbScroll} ${style.tbSm}`}>
            <table className={style.table}>
              <colgroup>
                <col width={'*'} />
                <col width={'*'} />
                <col width={'15%'} />
              </colgroup>
              <thead>
                <tr>
                  <th>카테고리명</th>
                  <th>영문명</th>
                  <th>아이콘</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>영상</td>
                  <td>Health</td>
                  <td className={style.tdIcon}><img src='/images/icons/ott.svg' alt='영상' /></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </article>
      <article className={style.dashbox}>
        <Link to="/Admin/Items" className={style.dashtit}>
          <h2>구독서비스 관리</h2>
          <button className={style.moreBtn}>
            <span className='material-icons'>chevron_right</span>
          </button>
        </Link>
        <div className={style.dashcont}>
          <div className={`${style.tbScroll} ${style.tbSm}`}>
            <table className={style.table}>
              <colgroup>
                <col width={'*'} />
                <col width={'15%'} />
              </colgroup>
              <thead>
                <tr>
                  <th>구독서비스명</th>
                  <th>카테고리</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>스마트비디오</td>
                  <td>영상</td>
                </tr>
                <tr>
                  <td>클립비젼</td>
                  <td>음료</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </article>
      <article className={style.dashbox}>
        <Link to="/Admin/Tags" className={style.dashtit}>
          <h2>태그 관리</h2>
          <button className={style.moreBtn}>
            <span className='material-icons'>chevron_right</span>
          </button>
        </Link>
        <div className={style.dashcont}>
          <div className={`${style.tbScroll} ${style.tbSm}`}>
            <table className={style.table}>
              <colgroup>
                <col width={'*'} />
              </colgroup>
              <thead>
                <tr>
                  <th>태그명</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>20대</td>
                </tr>
                <tr>
                  <td>30대</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </article>
      <article className={style.dashbox}>
        <Link to="/Admin/Events" className={style.dashtit}>
          <h2>이벤트 관리</h2>
          <button className={style.moreBtn}>
            <span className='material-icons'>chevron_right</span>
          </button>
        </Link>
        <div className={style.dashcont}>
          <div className={`${style.tbScroll} ${style.tbSm}`}>
            <table className={style.table}>
              <colgroup>
                <col width={'*'} />
                <col width={'*'} />
                <col width={'*'} />
                <col width={'120px'} />
                <col width={'120px'} />
              </colgroup>
              <thead>
                <tr>
                  <th>구독서비스명</th>
                  <th>이벤트 태그</th>
                  <th>이벤트 설명</th>
                  <th>시작날짜</th>
                  <th>종료날짜</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>스마트 비디오</td>
                  <td>40% 할인</td>
                  <td>지루한 날을 단번에 날려주는 스마트 비디오 OTT 서비스</td>
                  <td>20231026</td>
                  <td>20231026</td>
                </tr>
                <tr>
                  <td>스마트 비디오</td>
                  <td>40% 할인</td>
                  <td>지루한 날을 단번에 날려주는 스마트 비디오 OTT 서비스</td>
                  <td>20231026</td>
                  <td>20231026</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </article>
    </section>
  )
}

export default AdminHome;