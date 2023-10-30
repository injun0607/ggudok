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
import { fetchTagSuccess, pagingTag, deleteTag } from '../../redux/actions/admin/adminTagActions';

const ITEMS_PER_PAGE = 6;
const NO_IMAGE_URL = '/images/common/noimg.png';

const Tags = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const categories = useSelector(state => state.adminTag.categories);
  const pagedCategories = useSelector(state => state.adminTag.pagedCategories);
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);
  
  const [page, setPage] = useState(1);
  const [startIndex, setStartIndex] = useState(0);
  const endIndex = startIndex + ITEMS_PER_PAGE;
  
  // ************************** 기본 categories fetch ***************************
  const fetchTagData = async () => {
    try {
      const response = await axios.get(`/admin/category`);
      const data = response.data.categoryList;
      console.log('data : ', data)

      if(data !== 0){
        dispatch(fetchTagSuccess(data));

        // 페이지 리뷰 계산
        const pagedCategories = data.slice(startIndex, startIndex + ITEMS_PER_PAGE);
        dispatch(pagingTag(pagedCategories))
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
    setIsResult(categories.length > 0);
  }, [dispatch, categories]);

  // 페이지 리뷰 슬라이스
  useEffect(() => {
    const pagedCategories = categories.slice(startIndex, startIndex + ITEMS_PER_PAGE);
    dispatch(pagingTag(pagedCategories))
  }, [dispatch, categories, startIndex, page])

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber) => {
    setPage(pageNumber);
  };
  useEffect(() => {
    const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
    setStartIndex(newStartIndex);
  }, [page]);

  // 수정
  const handleEditTag = (categoryId) => {
    navigate(`/Admin/TagEdit`, { state: categoryId });
  }
  // 삭제
  const handleDeleteTag = async(e, categoryId) => {
    const categoryData = { categoryId };
    e.preventDefault();
    const confirm = window.confirm('카테고리를 삭제하시겠습니까?');
    if(confirm){ dispatch(deleteTag(categoryData)); }
    else {}
  }

  return (
    <div className='webwidth'>
      <div className='cont_tit_m'>
        <h2>태그 목록</h2>
      </div>
      <section className={style.tableWrap}>
        <Link to="/Admin/TagCreate" className='btn_full02 btn_s'>신규 태그 등록</Link>
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
              <tr>
                <td>20대</td>
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