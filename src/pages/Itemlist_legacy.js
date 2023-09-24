import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Item.module.css';
// component import
import Filteraside from '../components/Filteraside';
import ErrorItem from '../components/ErrorItem';
// redux import
import { setIsLoading, setIsResult, fetchItemsSuccess, filterItem } from '../redux/actions/itemActions';
import { setSelectedPrice, setSelectedRating, setSelectedTag } from '../redux/actions/filterActions';

const ITEMS_PER_PAGE = 15;
const NO_IMAGE_URL = '/images/common/noimg.png';

const Itemlist = ({ category, categoryEng }) => {
  let dispatch = useDispatch()
  const selectedPrice = useSelector(state => state.filter.selectedPrice);
  const selectedRating = useSelector(state => state.filter.selectedRating);
  const selectedTag = useSelector(state => state.filter.selectedTag);
  const items = useSelector(state => state.item.items);
  const filtereditems = useSelector(state => state.item.filtereditems);
  const IsResult = useSelector(state => state.item.IsResult);
  const IsLoading = useSelector(state => state.item.IsLoading);
  const [filterTag, setFilterTag] = useState([]);

  // ************************** 기본 아이템 fetch ***************************
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/subs/${categoryEng}`);
        const data = response.data.items;

        // 아이템 데이터를 받아온 후에 filterTag을 계산하고 상태를 업데이트
        const tagsAll = data.map(item => item.tags.map(tag => tag.tagName)).flat();
        setFilterTag(tagsAll.reduce((ac, v) => ac.includes(v) ? ac : [...ac, v], []));
        dispatch(fetchItemsSuccess(data));
        dispatch(setIsLoading(false));
        dispatch(setIsResult(true));
      } catch (error) {
        console.error('Error fetching data:', error);
        dispatch(setIsResult(false));
      }
    };
    fetchData();
  }, [dispatch, categoryEng]);

  // ************************ 필터 및 기본 아이템 페이저 **********************
  const [IsPager, setIsPager] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [slicedItems, setSlicedItems] = useState(items.slice(0, ITEMS_PER_PAGE));
  const totalPages = Math.ceil(items.length / ITEMS_PER_PAGE);
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
  const endIndex = startIndex + ITEMS_PER_PAGE;
  useEffect(() => {
    if (IsLoading) return;
    if((selectedPrice.length !== 0 || selectedRating.length !== 0 || selectedTag.length !== 0) && filtereditems.length !== 0){ // 필터 O + 아이템 O
      const newSlicedItems = filtereditems.slice(startIndex, endIndex);
      console.log(`필터 O + 아이템 O`)
      console.log(filtereditems)
      if(filtereditems.length > ITEMS_PER_PAGE){
        setIsPager(true);
      }
      setSlicedItems(newSlicedItems);
      dispatch(setIsResult(true));
      return;
    } else if ((selectedPrice.length !== 0 || selectedRating.length !== 0 || selectedTag.length !== 0) && filtereditems.length === 0){ // 필터 O + 아이템 X
      console.log(`필터 O + 아이템 X`)
      dispatch(setIsResult(false));
      return;
    } else { // 필터 X + 아이템 X || 필터 X + 아이템 O
      if(items.length === 0){
        console.log(items)
        console.log(`필터 X + 아이템 X`)
        dispatch(setIsResult(false));
        return;
      }
      console.log(`필터 X + 아이템 O`)
      const newSlicedItems = items.slice(startIndex, endIndex);
      setSlicedItems(newSlicedItems);
      dispatch(setIsResult(true));
      if(filtereditems.length > ITEMS_PER_PAGE){
        setIsPager(true);
      }
    }
  }, [IsLoading, selectedPrice, selectedRating, selectedTag]);
  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
    }
  };
  
  // ****************************** 필터 버튼 핸들러 *******************************
  // 리셋 버튼 핸들러
  const resetFilters = () => {
    window.location.reload();
  };
  // 체크박스 핸들러 전역변수 설정
  let updatedFilteredItems = [];
  let isFilteredItems = items;

  // 가격 체크박스 핸들러
  const handleSelectedPrice = (e) => {
    // 선택한 필터 reducer에 업데이트
    let updateSelectedPrice = [];
    if (selectedPrice.includes(e.target.value)) {
      updateSelectedPrice = selectedPrice.filter((price) => price !== e.target.value);
    } else {
      updateSelectedPrice = [...selectedPrice, e.target.value];
    }
    dispatch(setSelectedPrice(updateSelectedPrice));
    // items배열을 필터링하여 filteredItems를 업데이트
    // isFilteredItems는 기본적으로 items이나,
    // 이미 filtereditems배열이 있을경우 isFilteredItems는 filtereditems
    if(filtereditems.length !== 0){isFilteredItems = filtereditems }
    if(updateSelectedPrice.includes('priceRow')){
      updatedFilteredItems = isFilteredItems.filter(item => 
        item.ranks.some(rank => rank.price < 5900)
      );
    } else if(updateSelectedPrice.includes('priceMedium')){
      updatedFilteredItems = isFilteredItems.filter(item => 
        item.ranks.some(rank => 5900 <= rank.price && rank.price <= 9900)
      );
    } else if(updateSelectedPrice.includes('priceHigh')){
      updatedFilteredItems = isFilteredItems.filter(item => 
        item.ranks.some(rank => rank.price > 9900)
      );
    }
    console.log(updatedFilteredItems)
    // 지금 문제가 되는거 :
    // <다른 필터를 체크하지 않은 상태> 체크 후 체크해제 -> 의도대로 됨
    // <다른 필터를 체크 한 상태> 체크 후 체크해제 -> 빈 배열로 업데이트 됨
    if((selectedPrice.length !== 0 || selectedRating.length !== 0 || selectedTag.length !== 0) && updatedFilteredItems.length === 0){ 
      // 체크해제하려는 속성을 가진 아이템을 기존(isFilteredItems) 배열에서 삭제
    }
    // 필터링된 아이템 목록 업데이트
    dispatch(filterItem(updatedFilteredItems));
  };

  // 별점 체크박스 핸들러
  const handleSelectedRating = (e) => {
    // 선택한 필터 reducer에 업데이트
    let updateSelectedRating = [];
    let selectedRatingAvg = null;
    if (selectedRating.includes(e.target.value)) {
      updateSelectedRating = selectedRating.filter((rating) => rating !== e.target.value);
    } else {
      updateSelectedRating = [...selectedRating, e.target.value];
      selectedRatingAvg = Number(updateSelectedRating[0].substr(-1));
    }
    dispatch(setSelectedRating(updateSelectedRating));
    // items 배열을 필터링하여 filteredItems를 업데이트
    if(filtereditems.length !== 0){ isFilteredItems = filtereditems }
    updatedFilteredItems = isFilteredItems.filter(item => item.ratingAvg === selectedRatingAvg );
    // 필터링된 아이템 목록 업데이트
    dispatch(filterItem(updatedFilteredItems));
  };
  
  // 태그 체크박스 핸들러
  const handleSelectedTag = (e) => {
    // 선택한 필터 reducer에 업데이트
    let updateSelectedTag = [];
    if (selectedTag.includes(e.target.value)) {
      updateSelectedTag = selectedTag.filter((tag) => tag !== e.target.value);
      if (updateSelectedTag.length === 0){ updateSelectedTag = [] }
    } else {
      updateSelectedTag = [...selectedTag, e.target.value];
    }
    dispatch(setSelectedTag(updateSelectedTag));
    // items 배열을 필터링하여 filteredItems를 업데이트
    if(filtereditems.length !== 0){ isFilteredItems = filtereditems }
    updatedFilteredItems = isFilteredItems.filter(item => 
      updateSelectedTag.every(selectedTag => 
        item.tags.some(tag => tag.tagName === selectedTag)
      )
    );
    // 필터링된 아이템 목록 업데이트
    dispatch(filterItem(updatedFilteredItems));
  };

  // 아이템이 있는지 검사
  // useEffect(()=>{
  //   if(slicedItems.length === 0){ dispatch(setIsResult(false)); }
  //   else { dispatch(setIsResult(true)); }
  // }, [selectedPrice, selectedRating, selectedTag])

  return (
    <section className={style.pagewrap}>
      <div className='webwidth'>
        <div className='page_tit page_tit-side'>
          <h2>{category}</h2>
        </div>
        <div className={style.categorysection}>
          <Filteraside resetFilters={resetFilters} handleSelectedPrice={handleSelectedPrice} handleSelectedRating={handleSelectedRating} handleSelectedTag={handleSelectedTag} filterTag={filterTag} />
          <section className={style.right}>
            <div className={style.section}>
              <div className={style.itemlist}>
                {IsResult ? slicedItems.map((item, index) => (
                  <Link to={`/subs/detail/${item.id}`} key={index} className={style.item}>
                    <div className={style.img}>
                      <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                    </div>
                    <div className={style.txt}>
                      <h3>{item.name}</h3>
                      <div className={style.tag}>
                        {/* <p>{item.category}</p> */}
                        {
                          item.tags.map((tag, tagindex) => (
                            <p key={tagindex}>{tag.tagName}</p>
                          ))
                        }
                      </div>
                    </div>
                  </Link>
                )) : <ErrorItem />}
              </div>
            </div>

            {IsPager && <div className='pagination-wrap'>
              <div className='pagination'>
                  <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
                  <span className='material-icons'>chevron_left</span>
                  </button>
                  <span>{currentPage}</span> / <span>{totalPages}</span>
                  <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}>
                  <span className='material-icons'>chevron_right</span>
                  </button>
                </div>
            </div>}
          </section>
        </div>
      </div>
    </section>
  );
};

export default Itemlist;
