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
import { fetchTagSuccess, pagingTag, deleteTag } from '../../redux/actions/admin/adminTagsActions';

const ITEMS_PER_PAGE = 10;
const NO_IMAGE_URL = '/images/common/noimg.png';

const Tags = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const tags = useSelector(state => state.adminTags.tags);
  const pagedTags = useSelector(state => state.adminTags.pagedTags);
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);
  
  const [page, setPage] = useState(1);
  const [startIndex, setStartIndex] = useState(0);
  const endIndex = startIndex + ITEMS_PER_PAGE;
  
  // ************************** 기본 tags fetch ***************************
  const fetchTagData = async () => {
    try {
      const response = await axios.get(`/admin/tag`);
      const data = response.data;
      console.log(data)
      if(data !== 0){
        dispatch(fetchTagSuccess(data));

        // 페이지 리뷰 계산
        const pagedTags = data.slice(startIndex, startIndex + ITEMS_PER_PAGE);
        dispatch(pagingTag(pagedTags))
        setStartIndex(0);
        setPage(1);
      } else {
        dispatch(fetchTagSuccess([]));
        dispatch(pagingTag([]))
      }

    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchTagData();
  }, []);

  // 결과 유무
  useEffect(() => {
    setIsResult(tags.length > 0);
  }, [dispatch, tags]);

  // 페이지 태그 슬라이스
  useEffect(() => {
    const pagedTags = tags.slice(startIndex, startIndex + ITEMS_PER_PAGE);
    dispatch(pagingTag(pagedTags))
  }, [dispatch, tags, startIndex, page])

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber) => {
    setPage(pageNumber);
  };
  useEffect(() => {
    const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
    setStartIndex(newStartIndex);
  }, [page]);

  // 삭제
  const handleDeleteTag = async(e, tagId) => {
    const tagData = { tagId };
    e.preventDefault();
    const confirm = window.confirm('태그를 삭제하시겠습니까?');
    if(confirm){ dispatch(deleteTag(tagData)); }
    else {}
  }

  return (
    !IsLoading && <div className='webwidth'>
      <div className='cont_tit_m'>
        <h2>태그 목록</h2>
      </div>
      <section className={style.tableWrap}>
        <Link to="/Admin/TagCreate" className='btn_full02 btn_s'>신규 태그 등록</Link>
        {IsResult && tags && tags.length > 0 ? 
          <>
          <div className={style.tbScroll}>
            <table className={style.table}>
              <colgroup>
                <col width={'*'} />
                <col width={'250px'} />
              </colgroup>
              <thead>
                <tr>
                  <th>태그명</th>
                  <th>관리</th>
                </tr>
              </thead>
              <tbody>
                {pagedTags.map((tag, index) => (
                <tr key={index}>
                  <td>{tag.tagName}</td>
                  <td className={style.tdBtn}>
                    <button type='button' className='btn_xs btn_full' onClick={(e) => handleDeleteTag(e, tag.tagId) }>삭제</button>
                  </td>
                </tr>
                ))}
              </tbody>
            </table>
          </div>
          <Paging handlePageChange={handlePageChange} page={page} count={tags.length} ITEMS_PER_PAGE={ITEMS_PER_PAGE} />
          </>
        : <ErrorItem message="등록된 태그가 없습니다." />}
      </section>
    </div>
  )
}

export default Tags;