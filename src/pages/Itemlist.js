import React, { useEffect, useState, useMemo } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Item.module.css';
// component import
import Filteraside from '../components/Filteraside';
import ErrorItem from '../components/ErrorItem';
import Paging from '../components/Paging';
// redux import
import { fetchItemsSuccess, filterItem, pagingItem } from '../redux/actions/itemActions';
import { setSelectedPrice, setSelectedRating, setSelectedTag } from '../redux/actions/filterActions';

const ITEMS_PER_PAGE = 12;
const NO_IMAGE_URL = '/images/common/noimg.png';

const Itemlist = ({ category, categoryEng }) => {
  let dispatch = useDispatch()
  const selectedPrice = useSelector(state => state.filter.selectedPrice);
  const selectedRating = useSelector(state => state.filter.selectedRating);
  const selectedTag = useSelector(state => state.filter.selectedTag);
  const items = useSelector(state => state.item.items);
  const filtereditems = useSelector(state => state.item.filtereditems);
  const pageditems = useSelector(state => state.item.pageditems);
  const [IsResult, setIsResult] = useState(false);
  const [IsLoading, setIsLoading] = useState(true);
  const [filterTag, setFilterTag] = useState([]);
  const [page, setPage] = useState(1);
  const [startIndex, setStartIndex] = useState(0);
  const endIndex = startIndex + ITEMS_PER_PAGE;
  
  // ************************** 기본 아이템 fetch ***************************
  const fetchItemListData = async () => {
    try {
      const response = await axios.get(`/subs/${categoryEng}`);
      const data = response.data.items;

      // 아이템 데이터를 받아온 후에 filterTag을 계산하고 상태를 업데이트
      if(response.data.items.length !== 0){
        const tagsAll = data.map(item => item.tags.map(tag => tag.tagName)).flat();
        setFilterTag(tagsAll.reduce((ac, v) => ac.includes(v) ? ac : [...ac, v], []));
        dispatch(fetchItemsSuccess(data));

        setStartIndex(0);
        setPage(1);
      } else {
        dispatch(fetchItemsSuccess([]));
        dispatch(filterItem([]));
        dispatch(pagingItem([]));
      }
    } catch (error) {
      console.error('Error fetching data:', error);
      alert(`서비스를 가져오던 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    } finally {
      setIsLoading(false);
      
    }
  };
  useEffect(() => {
    fetchItemListData();
  }, [dispatch, categoryEng]);

// 페이지 첫 진입 시 필터 초기화
useEffect(() => {
  dispatch(setSelectedPrice(null));
  dispatch(setSelectedRating(null));
  dispatch(setSelectedTag([]));
}, []);

// 결과 유무
useEffect(() => {
  setIsResult(items.length > 0 || filtereditems.length > 0);
}, [items, filtereditems, categoryEng]);

// 페이지 변경 핸들러
const handlePageChange = (pageNumber) => {
  setPage(pageNumber);
};
useEffect(() => {
  const newStartIndex = (page - 1) * ITEMS_PER_PAGE;
  setStartIndex(newStartIndex);
}, [page]);


  // ****************************** 필터 버튼 핸들러 *******************************
  // 리셋 버튼 핸들러
  const resetFilters = () => {
    window.location.reload();
  };

  // 가격 체크박스 핸들러
  const handleSelectedPrice = (e) => {
    // 선택한 필터 reducer에 업데이트
    let updateSelectedPrice = null;
    if (selectedPrice === e.target.value) {
      updateSelectedPrice = null;
    } else {
      updateSelectedPrice = e.target.value;
    }
    dispatch(setSelectedPrice(updateSelectedPrice));
  };
  // 별점 체크박스 핸들러
  const handleSelectedRating = (e) => {
    // 선택한 필터 reducer에 업데이트
    let updateSelectedRating = null;
    if (selectedRating === e.target.value) {
      updateSelectedRating = null;
    } else {
      updateSelectedRating = e.target.value;
    }
    dispatch(setSelectedRating(updateSelectedRating));
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
  };

  // 필터적용 핸들러
  const handleFilteredItems = useMemo(() => {
    let updateditems = items;
    
    if(selectedPrice !== null){
      updateditems = updateditems.filter(item => {
        if (selectedPrice === 'priceRow') {
          return item.ranks.some(rank => rank.price < 5900);
        } else if (selectedPrice === 'priceMedium') {
          return item.ranks.some(rank => 5900 <= rank.price && rank.price <= 9900);
        } else {
          return item.ranks.some(rank => rank.price > 9900);
        }
      });
    };
    if(selectedRating !== null){
      let selectedRatingAvg = parseInt(selectedRating.substr(-1));
      updateditems = updateditems.filter(item => item.ratingAvg === selectedRatingAvg );
    };
    if(selectedTag.length !== 0){
      updateditems = updateditems.filter(item => 
        selectedTag.every(selectedtag => 
          item.tags.some(tag => tag.tagName === selectedtag)
        )
      );
    };
    // 필터조작 후 1페이지로 이동
    if (selectedPrice !== null || selectedRating !== null || selectedTag.length !== 0) {
      setPage(1);
    }

    setIsResult(updateditems.length > 0);

    if(updateditems.length < 13){
      setStartIndex(0);
      setPage(1);
    }

    // 페이지 아이템 계산
    const pagedItems = updateditems.slice(startIndex, startIndex + ITEMS_PER_PAGE);

    // Redux 상태 업데이트
    dispatch(filterItem(updateditems));
    dispatch(pagingItem(pagedItems));

    return updateditems;
  }, [items, selectedPrice, selectedRating, selectedTag, startIndex, page]);

  useEffect(() => {
    if (!IsLoading) {
      if (selectedPrice || selectedRating || selectedTag.length > 0) {
        setIsResult(filtereditems.length > 0);
      }
    }
  }, [IsLoading, filtereditems, items, selectedPrice, selectedRating, selectedTag]);


  return (
    <section className={style.pagewrap}>
      <div className='webwidth'>
        <div className='page_tit page_tit-side'>
          <h2>{category}</h2>
        </div>
        <div className={style.categorysection}>
          <Filteraside resetFilters={resetFilters} handleSelectedPrice={handleSelectedPrice} handleSelectedRating={handleSelectedRating} handleSelectedTag={handleSelectedTag} filterTag={filterTag} handleFilteredItems={handleFilteredItems} />
          <section className={style.right}>
            <div className={style.section}>
              <div className={style.itemlist}>
                {IsResult ? pageditems.map((item, index) => (
                  <Link to={`/subs/detail/item/${item.id}`} key={index} className={style.item}>
                    <div className={style.img}>
                      <img src={item.image || NO_IMAGE_URL} alt={item.name} />
                    </div>
                    <div className={style.txt}>
                      <h3>{item.name}</h3>
                      <div className={style.tag}>
                        {
                          item.tags.map((tag, tagindex) => (
                            <p key={tagindex}>{tag.tagName}</p>
                          ))
                        }
                      </div>
                    </div>
                  </Link>
                )) : <ErrorItem message="이런! 해당하는 구독서비스가 없습니다." />}
              </div>
            </div>

            <Paging handlePageChange={handlePageChange} page={page} count={selectedPrice || selectedRating || selectedTag.length > 0 ? filtereditems.length : items.length} ITEMS_PER_PAGE={ITEMS_PER_PAGE} />
          </section>
        </div>
      </div>
    </section>
  );
};

export default Itemlist;
