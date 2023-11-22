import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
// css import
import style from '../../styles/Admin/AdminLayout.module.css';
//redux import
import { fetchCategorySuccess } from '../../redux/actions/admin/adminCategoryActions';
import { fetchItemsSuccess } from '../../redux/actions/admin/adminItemsActions';
import { fetchTagSuccess } from '../../redux/actions/admin/adminTagsActions';
import { fetchEventSuccess } from '../../redux/actions/admin/adminEventsActions';

const NO_IMAGE_URL = '/images/common/logo_grey.png';

const AdminHome = () => {
  const dispatch = useDispatch();
  const categories = useSelector(state => state.adminCategory.categories);
  const events = useSelector(state => state.adminEvents.events);
  const tags = useSelector(state => state.adminTags.tags);
  const items = useSelector(state => state.adminItems.items);

  const [IsLoading, setIsLoading] = useState(true);
  
  // ************************** 기본 전체 fetch ***************************
  const fetchData = async () => {
    try {
      const responseCategory = await axios.get(`/admin/category`);
      const responseItem = await axios.get(`/admin/subs`);
      const responseTag = await axios.get(`/admin/tag`);
      const responseEvent = await axios.get(`/admin/event`);
      const dataCategory = responseCategory.data.categoryList;
      const dataItem = responseItem.data.subsList;
      const dataTag = responseTag.data;
      const dataEvent = responseEvent.data;

      if(dataCategory !== 0){
        dispatch(fetchCategorySuccess(dataCategory));
      } else { dispatch(fetchCategorySuccess([])); }
      if(dataItem !== 0){
        dispatch(fetchItemsSuccess(dataItem));
      } else { dispatch(fetchItemsSuccess([])); }
      if(dataTag !== 0){
        dispatch(fetchTagSuccess(dataTag));
      } else { dispatch(fetchTagSuccess([])); }
      if(dataEvent !== 0){
        dispatch(fetchEventSuccess(dataEvent));
      } else { dispatch(fetchEventSuccess([])); }

    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchData();
  }, []);

  return (
    !IsLoading && <section className={style.dashboxWrap}>
      <article className={style.dashbox}>
        <Link to="/Admin/Category" className={style.dashtit}>
          <h2>카테고리 관리</h2>
          <button className={style.moreBtn}>
            <span className='material-icons'>chevron_right</span>
          </button>
        </Link>
        <div className={style.dashcont}>
          {categories.length > 0 ?
          <div className={`${style.tbScroll} ${style.tbSm}`}>
            <table className={style.table}>
              <colgroup>
                <col width={'*'} />
                <col width={'*'} />
                <col width={'100px'} />
              </colgroup>
              <thead>
                <tr>
                  <th>카테고리명</th>
                  <th>영문명</th>
                  <th>아이콘</th>
                </tr>
              </thead>
              <tbody>
                {categories.slice(0,4).map((category, index) => (
                  <tr key={index}>
                    <td>{category.categoryName}</td>
                    <td>{category.categoryEng}</td>
                    <td className={style.tdIcon}>
                      <img src={category.categoryImage || NO_IMAGE_URL} alt={category.categoryName} />
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          : <p className='txt_center txt_grey mt_30'>등록된 카테고리가 없습니다.</p>
          }
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
          {items.length > 0 ?
          <div className={`${style.tbScroll} ${style.tbSm}`}>
            <table className={style.table}>
              <colgroup>
                <col width={'100px'} />
                <col width={'*'} />
                <col width={'*'} />
                <col width={'10%'} />
              </colgroup>
              <thead>
                <tr>
                  <th>대표이미지</th>
                  <th>구독서비스명</th>
                  <th>태그</th>
                  <th>카테고리</th>
                </tr>
              </thead>
              <tbody>
                {items.slice(0,4).map((item, index) => (
                  <tr key={index}>
                    <td className={style.tdImg}>
                      <img src={item.subsImage || NO_IMAGE_URL} alt={item.subsName} />
                    </td>
                    <td>{item.subsName}</td>
                    <td>
                      <p>
                      {
                        item.tagList?.map((tag, tagindex) => (
                          <span key={tagindex}>
                            {tagindex > 0 && ", "} {tag.tagName}
                          </span>
                        ))
                      }
                      </p>
                    </td>
                    <td>{item.categoryName}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          : <p className='txt_center txt_grey mt_30'>등록된 구독서비스가 없습니다.</p>
          }
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
          {tags.length > 0 ?
          <div className={style.AdminBox}>
            {tags.map((tag, index) => (
              <div className={style.box} key={index}>
                <h3>{tag.tagName}</h3>
              </div>
            ))}
          </div>
          : <p className='txt_center txt_grey mt_30'>등록된 태그가 없습니다.</p>
          }
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
          {events.length > 0 ?
          <div className={`${style.tbScroll} ${style.tbSm}`}>
            <table className={style.table}>
              <colgroup>
                <col width={'100px'} />
                <col width={'*'} />
                <col width={'*'} />
                <col width={'*'} />
                <col width={'120px'} />
                <col width={'120px'} />
              </colgroup>
              <thead>
                <tr>
                  <th>이미지</th>
                  <th>구독서비스명</th>
                  <th>이벤트 태그</th>
                  <th>이벤트 설명</th>
                  <th>시작날짜</th>
                  <th>종료날짜</th>
                </tr>
              </thead>
              <tbody>
                {events.slice(0,4).map((event, index) => (
                  <tr key={index}>
                    <td className={style.tdImg}>
                      <img src={event.eventImage || NO_IMAGE_URL} alt={event.subsName} />
                    </td>
                    <td>{event.subsName}</td>
                    <td>{event.infoTag}</td>
                    <td>{event.info}</td>
                    <td>{event.startDate}</td>
                    <td>{event.endDate}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          : <p className='txt_center txt_grey mt_30'>등록된 이벤트가 없습니다.</p>
          }
        </div>
      </article>
    </section>
  )
}

export default AdminHome;