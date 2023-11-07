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
import { fetchItemsSuccess, setSelectedCategory, filterItems, pagingItems, deleteItem } from '../../redux/actions/admin/adminItemsActions';
import { fetchCategorySuccess } from '../../redux/actions/admin/adminCategoryActions';

const ITEMS_PER_PAGE = 10;
const NO_IMAGE_URL = '/images/common/noimg.png';

const Items = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const items = useSelector(state => state.adminItems.items);
  const filteredItems = useSelector(state => state.adminItems.filteredItems);
  const selectedCategory = useSelector(state => state.adminItems.selectedCategory);
  const pagedItems = useSelector(state => state.adminItems.pagedItems);
  const categories = useSelector(state => state.adminCategory.categories);
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);
  
  const [page, setPage] = useState(1);
  const [startIndex, setStartIndex] = useState(0);
  const endIndex = startIndex + ITEMS_PER_PAGE;
  
  // ************************** 기본 items fetch ***************************
  const fetchItemsData = async () => {
    try {
      const response = await axios.get(`/admin/subs`);
      const responseCategory = await axios.get(`/admin/category`);
      const data = response.data.subsList;
      const dataCategory = responseCategory.data.categoryList;

      if(data !== 0){
        dispatch(fetchItemsSuccess(data));
        dispatch(fetchCategorySuccess(dataCategory));

        // 페이지 아이템 계산
        const pagedItems = data.slice(startIndex, startIndex + ITEMS_PER_PAGE);
        dispatch(pagingItems(pagedItems))

        setStartIndex(0);
        setPage(1);
      } else {
        dispatch(fetchItemsSuccess([]));
        dispatch(fetchCategorySuccess([]));
        dispatch(pagingItems([]))
      }

    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchItemsData();
  }, []);

  // 결과 유무
  useEffect(() => {
    if (!IsLoading) {
      setIsResult(selectedCategory ? filteredItems?.length > 0 : items?.length > 0);
    }
  }, [IsLoading, items, filteredItems, selectedCategory]);

  // 페이지 변경 핸들러
  const handlePageChange = (pageNumber) => {
    setPage(pageNumber);
  };
  useEffect(() => {
    const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
    setStartIndex(newStartIndex);
  }, [page]);
  
  // ****************************** 필터 버튼 핸들러 *******************************
  // 선택된 카테고리에 기반한 아이템 필터링
  useEffect(() => {
    let updatedItems = items;
    if (selectedCategory) {
      updatedItems = items.filter(item => item.categoryName === selectedCategory);
    }
    dispatch(filterItems(updatedItems));

    // 페이지 아이템 계산
    if(updatedItems.length < ITEMS_PER_PAGE){
      setStartIndex(0);
      setPage(1);
    }
    const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
    setStartIndex(newStartIndex);

    const pagedItems = updatedItems.slice(newStartIndex, newStartIndex + ITEMS_PER_PAGE);
    dispatch(pagingItems(pagedItems));
  }, [items, selectedCategory, page]);

  // 페이지 변경 처리
  useEffect(() => {
    const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
    setStartIndex(newStartIndex);
  }, [page]);


  // 수정
  const handleEditItem = (subsId) => {
    navigate(`/Admin/ItemsEdit/${subsId}`);
  }
  // 삭제
  const handleDeleteItem = async(e, subsId, subsName) => {
    const itemData = { subsId };
    e.preventDefault();
    const confirm = window.confirm(`${subsName} 구독서비스를 삭제하시겠습니까?`);
    if(confirm){ dispatch(deleteItem(itemData)); }
    else {}
  }
  // 신규 구독서비스 등록
  const handleCreateItem = () => {
    navigate('/Admin/ItemsCreate');
  }

  return (
    !IsLoading && <div className='webwidth'>
      <div className='cont_tit_m'>
        <h2>구독서비스 목록</h2>
      </div>
      <section className={style.tableWrap}>
        <button type='button' className='btn_s btn_full02' onClick={ handleCreateItem }>신규 구독서비스 등록</button>
        {IsResult ? 
          <>
            <div>
              <select
                id="categoryFilter"
                className={style.select}
                onChange={(e) => dispatch(setSelectedCategory(e.target.value))}
                value={selectedCategory}
              >
                <option value="">전체</option>
                {categories.map((category) => (
                  <option key={category.categoryId} value={category.categoryName}>
                    {category.categoryName}
                  </option>
                ))}
              </select>
            </div>
            <div className={style.tbScroll}>
              <table className={style.table}>
                <colgroup>
                  <col width={'10%'} />
                  <col width={'*'} />
                  <col width={'*'} />
                  <col width={'10%'} />
                  <col width={'250px'} />
                </colgroup>
                <thead>
                  <tr>
                    <th>대표이미지</th>
                    <th>구독서비스명</th>
                    <th>태그</th>
                    <th>카테고리</th>
                    <th>관리</th>
                  </tr>
                </thead>
                <tbody>
                  {pagedItems.map((item, index) => (
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
                      <td className={style.tdBtn}>
                        {/* <button type='button' className='btn_xs btn_full03' onClick={(e) => handleEventItem(e, item.subsId, item.subsName) }>이벤트 등록</button> */}
                        <button type='button' className='btn_xs btn_normal' onClick={() => handleEditItem(item.subsId) }>수정</button>
                        <button type='button' className='btn_xs btn_full' onClick={(e) => handleDeleteItem(e, item.subsId, item.subsName) }>삭제</button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
            <Paging
              handlePageChange={handlePageChange}
              page={page}
              count={selectedCategory ? filteredItems.length : items.length}
              ITEMS_PER_PAGE={ITEMS_PER_PAGE}
            />
          </>
        : <ErrorItem message="등록된 구독서비스가 없습니다." />}
      </section>
    </div>
  )
}

export default Items;