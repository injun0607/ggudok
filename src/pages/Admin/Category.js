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
import { fetchCategorySuccess, pagingCategory, deleteCategory } from '../../redux/actions/admin/adminCategoryActions';

const ITEMS_PER_PAGE = 6;
const NO_IMAGE_URL = '/images/common/noimg.png';

const Category = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const categories = useSelector(state => state.adminCategory.categories);
  const pagedCategories = useSelector(state => state.adminCategory.pagedCategories);
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);
  
  const [page, setPage] = useState(1);
  const [startIndex, setStartIndex] = useState(0);
  const endIndex = startIndex + ITEMS_PER_PAGE;
  
  // ************************** 기본 categories fetch ***************************
  const fetchCategoryData = async () => {
    try {
      const response = await axios.get(`/admin/category`);
      const data = response.data.categoryList;
      console.log('data : ', data)

      if(data !== 0){
        dispatch(fetchCategorySuccess(data));

        // 페이지 리뷰 계산
        const pagedCategories = data.slice(startIndex, startIndex + ITEMS_PER_PAGE);
        dispatch(pagingCategory(pagedCategories))
        setStartIndex(0);
        setPage(1);
      } else {
        dispatch(fetchCategorySuccess([]));
        dispatch(pagingCategory([]))
      }

    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchCategoryData();
  }, []);

  // 결과 유무
  useEffect(() => {
    setIsResult(categories.length > 0);
  }, [dispatch, categories]);

  // 페이지 리뷰 슬라이스
  useEffect(() => {
    const pagedCategories = categories.slice(startIndex, startIndex + ITEMS_PER_PAGE);
    dispatch(pagingCategory(pagedCategories))
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
  const handleEditCategory = (categoryId) => {
    navigate(`/Admin/CategoryEdit`, { state: categoryId });
  }
  // 삭제
  const handleDeleteCategory = async(e, categoryId) => {
    const categoryData = { categoryId };
    e.preventDefault();
    const confirm = window.confirm('카테고리를 삭제하시겠습니까?');
    if(confirm){ dispatch(deleteCategory(categoryData)); }
    else {}
  }

  return (
    !IsLoading && <div className='webwidth'>
      <div className='cont_tit_m'>
        <h2>카테고리 목록</h2>
      </div>
      <section className={style.tableWrap}>
        <Link to="/Admin/CategoryCreate" className='btn_full02 btn_s'>신규 카테고리 등록</Link>
        {IsResult && categories && categories.length > 0 ? 
          <>
          <div className={style.tbScroll}>
          <table className={style.table}>
            <colgroup>
              <col width={'*'} />
              <col width={'15%'} />
              <col width={'250px'} />
            </colgroup>
            <thead>
              <tr>
                <th>카테고리명</th>
                <th>아이콘</th>
                <th>관리</th>
              </tr>
            </thead>
            <tbody>

              {pagedCategories.map((category, index) => (
                <tr key={index}>
                  <td>{category.categoryName}</td>
                  <td className={style.tdIcon}>
                    <img src={`/images/icons/${category.categoryEng}.svg` || NO_IMAGE_URL} alt={category.categoryName} />
                  </td>
                  <td className={style.tdBtn}>
                    <button type='button' className='btn_xs btn_normal' onClick={() => handleEditCategory(category.categoryId) }>수정</button>
                    <button type='button' className='btn_xs btn_full' onClick={(e) => handleDeleteCategory(e, category.categoryId) }>삭제</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
          <Paging handlePageChange={handlePageChange} page={page} count={categories.length} ITEMS_PER_PAGE={ITEMS_PER_PAGE} />
          </>
        : <ErrorItem message="등록 된 카테고리가 없습니다." />}
      </section>
    </div>
  )
}

export default Category;