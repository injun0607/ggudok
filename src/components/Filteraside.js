import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Filter.module.css';
// redux import
import { setSelectedPrice, setSelectedRating, setSelectedTag } from '../redux/actions/filterActions';

const Filteraside = () => {
  const dispatch = useDispatch();
  const selectedPrice = useSelector(state => state.filter.price);
  const selectedRating = useSelector(state => state.filter.rating);
  const selectedTag = useSelector(state => state.filter.tag);

  
  const handlePriceChange = (e) => {
    dispatch(setSelectedPrice(e.target.value));
  };

  const handleRatingChange = (e) => {
    const rating = e.target.value;
    if (selectedRating.includes(rating)) {
      dispatch(setSelectedRating(selectedRating.filter(r => r !== rating)));
    } else {
      dispatch(setSelectedRating([...selectedRating, rating]));
    }
  };

  const handleTagChange = (e) => {
    const tag = e.target.value;
    if (selectedTag.includes(tag)) {
      dispatch(setSelectedTag(selectedTag.filter(t => t !== tag)));
    } else {
      dispatch(setSelectedTag([...selectedTag, tag]));
    }
  };


  // 필터 적용 버튼 핸들러
  const applyFilters = () => {
    // 여기서 선택된 필터들을 사용하여 아이템을 필터링하고 결과를 보여줄 수 있습니다.
  };
  // 리셋 버튼 핸들러
  const resetFilters = () => {
    setSelectedPrice('');
    setSelectedRating([]);
    setSelectedTag([]);
    // 필터를 초기화하고 전체 아이템을 보여줄 수 있습니다.
  };

  return (
    <>
      <div className={style.filterSidebar}>
        <div className={style.filterSection}>
          <h2>가격</h2>
          <select value={selectedPrice} onChange={ handlePriceChange }>
            <option value="">전체</option>
            <option value="low">낮은 가격</option>
            <option value="medium">중간 가격</option>
            <option value="high">높은 가격</option>
          </select>
        </div>
        <div className={style.filterSection}>
          <h2>평점</h2>
          <div>
            <label>
              <input type="checkbox" value="1" checked={selectedRating.includes("1")} onChange={ handleRatingChange } />
              1점
            </label>
            <label>
              <input type="checkbox" value="2" checked={selectedRating.includes("2")} onChange={ handleRatingChange } />
              2점
            </label>
          </div>
        </div>
        <div className={style.filterSection}>
          <h2>태그</h2>
          <div>
            <label>
              <input type="checkbox" value="tag1" checked={selectedTag.includes("tag1")} onChange={ handleTagChange } />
              태그1
            </label>
            <label>
              <input type="checkbox" value="tag2" checked={selectedTag.includes("tag2")} onChange={ handleTagChange } />
              태그2
            </label>
          </div>
        </div>
        <div className={style.doublebutton}>
          <button onClick={ applyFilters } className="btn btn_full">검색</button>
          <button onClick={ resetFilters } className="btn btn_normal">초기화</button>
        </div>
      </div>
    </>
  )
}

export default Filteraside;