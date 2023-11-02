import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Admin/AdminLayout.module.css';
// component import
import ErrorItem from '../../components/ErrorItem';
import Paging from '../../components/Paging';
// redux import
import { fetchEventSuccess, pagingEvent, deleteEvent } from '../../redux/actions/admin/adminEventsActions';

const ITEMS_PER_PAGE = 10;

const Events = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const events = useSelector(state => state.adminEvents.events);
  const pagedEvents = useSelector(state => state.adminEvents.pagedEvents);
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);
  
  const [page, setPage] = useState(1);
  const [startIndex, setStartIndex] = useState(0);
  const endIndex = startIndex + ITEMS_PER_PAGE;
  
  // ************************** 기본 events fetch ***************************
  const fetchEventData = async () => {
    try {
      const response = await axios.get(`/admin/event`);
      const data = response.data;

      if(data !== 0){
        dispatch(fetchEventSuccess(data));

        // 페이지 카테고리 계산
        const pagedEvents = data.slice(startIndex, startIndex + ITEMS_PER_PAGE);
        dispatch(pagingEvent(pagedEvents))
        setStartIndex(0);
        setPage(1);
      } else {
        dispatch(fetchEventSuccess([]));
        dispatch(pagingEvent([]))
      }

    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchEventData();
  }, []);

  // 결과 유무
  useEffect(() => {
    setIsResult(events.length > 0);
  }, [dispatch, events]);

  // 페이지 카테고리 슬라이스
  useEffect(() => {
    const pagedEvents = events.slice(startIndex, startIndex + ITEMS_PER_PAGE);
    dispatch(pagingEvent(pagedEvents))
  }, [dispatch, events, startIndex, page])

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber) => {
    setPage(pageNumber);
  };
  useEffect(() => {
    const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
    setStartIndex(newStartIndex);
  }, [page]);

  // 수정
  const handleEditEvent = (eventId) => {
    navigate(`/Admin/EventEdit/${eventId}`);
  }
  // 삭제
  const handleDeleteEvent = async(e, eventId) => {
    const eventData = { eventId };
    e.preventDefault();
    const confirm = window.confirm('카테고리를 삭제하시겠습니까?');
    if(confirm){ dispatch(deleteEvent(eventData)); }
    else {}
  }

  return (
    <div className='webwidth'>
      <div className='cont_tit_m'>
        <h2>이벤트 목록</h2>
      </div>
      <section className={style.tableWrap}>
        <Link to="/Admin/EventCreate" className='btn_full02 btn_s'>신규 이벤트 등록</Link>
        <div className={style.tbScroll}>
          <table className={style.table}>
            <colgroup>
              <col width={'*'} />
              <col width={'*'} />
              <col width={'*'} />
              <col width={'120px'} />
              <col width={'120px'} />
              <col width={'250px'} />
            </colgroup>
            <thead>
              <tr>
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