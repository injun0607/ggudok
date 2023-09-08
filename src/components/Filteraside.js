import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Filter.module.css';
// redux import
import { setSelectedPrice, setSelectedRating, setSelectedTag, setHideMenu } from '../redux/actions/filterActions';

const Filteraside = () => {
  const dispatch = useDispatch();
  const selectedPrice = useSelector(state => state.filter.price);
  const selectedRating = useSelector(state => state.filter.rating);
  const selectedTag = useSelector(state => state.filter.tag);
  const hideMenuPrice = useSelector(state => state.filter.hideMenu.price);
  const hideMenuRating = useSelector(state => state.filter.hideMenu.rating);
  const hideMenuTag = useSelector(state => state.filter.hideMenu.tag);

  const handlehideMenu = (section) => {
    dispatch(setHideMenu(section));
  }

  const handlePriceChange = (e) => {
    const price = e.target.value;
    if (selectedPrice.includes(price)) {
      dispatch(setSelectedRating(selectedPrice.filter(r => r !== price)));
    } else {
      dispatch(setSelectedRating([...selectedPrice, price]));
    }
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

  // 리셋 버튼 핸들러
  const resetFilters = () => {
    setSelectedPrice('');
    setSelectedRating([]);
    setSelectedTag([]);
  };

  return (
    <aside className={style.left}>
      <div className={style.category}>
        <div className={style.sideBarTit}>
          <h2>필터</h2>
          <button onClick={ resetFilters } className={style.resetBtn}>
            <span className="material-icons">restart_alt</span>
            초기화
          </button>
        </div>
        <div className={style.sideBar}>
          <section className={style.filter}>
            <div className={style.tit}>
              <h3>가격</h3>
              <button className={`${style.hideMenuBtn} ${hideMenuPrice ? style.hideMenuBtnActive : ''}`} type='button' onClick={() => handlehideMenu('price')} data-section={'price'}>
                <span className='material-icons'>expand_more</span>
              </button>
            </div>
            <ul className={`${style.cont} ${hideMenuPrice ? style.hide : ''}`}>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='price' className='checkInput' id="priceRow" value="row" checked={selectedPrice.includes("priceRow")} onChange={handlePriceChange} />
                <label htmlFor="priceRow" className='checkbox-label'>
                    5,900원 미만
                </label>
              </li>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='price' className='checkInput' id="priceMedium" value="medium" checked={selectedPrice.includes("priceMedium")} onChange={handlePriceChange} />
                <label htmlFor="priceMedium" className='checkbox-label'>
                    5,900원 ~ 9,900원
                </label>
              </li>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='price' className='checkInput' id="priceHigh" value="high" checked={selectedPrice.includes("priceHigh")} onChange={handlePriceChange} />
                <label htmlFor="priceHigh" className='checkbox-label'>
                    10,000원 이상
                </label>
              </li>
            </ul>
          </section>
          <section className={style.filter}>
            <div className={style.tit}>
              <h3>평점</h3>
              <button className={`${style.hideMenuBtn} ${hideMenuRating ? style.hideMenuBtnActive : ''}`} type='button' onClick={() => handlehideMenu('rating')} data-section={'rating'}>
                <span className='material-icons'>expand_more</span>
              </button>
            </div>
            <ul className={`${style.cont} ${hideMenuRating ? style.hide : ''}`}>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='rating' className='checkInput' id="star1" value="star1" checked={selectedRating.includes("star1")} onChange={handleRatingChange} />
                <label htmlFor="priceRow" className='checkbox-label'>
                  <div className='starrating'>
                    <div className='starrating'>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons">star</span>
                      <span className="material-icons">star</span>
                      <span className="material-icons">star</span>
                      <span className="material-icons">star</span>
                    </div>
                  </div>
                </label>
              </li>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='rating' className='checkInput' id="star2" value="star2" checked={selectedRating.includes("star2")} onChange={handleRatingChange} />
                <label htmlFor="priceRow" className='checkbox-label'>
                  <div className='starrating'>
                    <div className='starrating'>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons">star</span>
                      <span className="material-icons">star</span>
                      <span className="material-icons">star</span>
                    </div>
                  </div>
                </label>
              </li>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='rating' className='checkInput' id="star3" value="star3" checked={selectedRating.includes("star3")} onChange={handleRatingChange} />
                <label htmlFor="priceRow" className='checkbox-label'>
                  <div className='starrating'>
                    <div className='starrating'>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons">star</span>
                      <span className="material-icons">star</span>
                    </div>
                  </div>
                </label>
              </li>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='rating' className='checkInput' id="star4" value="star4" checked={selectedRating.includes("star4")} onChange={handleRatingChange} />
                <label htmlFor="priceRow" className='checkbox-label'>
                  <div className='starrating'>
                    <div className='starrating'>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons">star</span>
                    </div>
                  </div>
                </label>
              </li>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='rating' className='checkInput' id="star5" value="star5" checked={selectedRating.includes("star5")} onChange={ handleRatingChange } />
                <label htmlFor="priceRow" className='checkbox-label'>
                  <div className='starrating'>
                    <div className='starrating'>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                      <span className="material-icons starActive">star</span>
                    </div>
                  </div>
                </label>
              </li>
            </ul>
          </section>
          <section className={style.filter}>
            <div className={style.tit}>
              <h3>태그</h3>
              <button className={`${style.hideMenuBtn} ${hideMenuTag ? style.hideMenuBtnActive : ''}`} type='button' onClick={() => handlehideMenu('tag')} data-section={'tag'}>
                <span className='material-icons'>expand_more</span>
              </button>
            </div>
            <ul className={`${style.cont} ${hideMenuTag ? style.hide : ''}`}>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='tag' className='checkInput' id="tag1" value="tag1" checked={selectedTag.includes("tag1")} onChange={ handleTagChange } />
                <label htmlFor="tag1" className='checkbox-label'>
                    20대
                </label>
              </li>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='tag' className='checkInput' id="tag2" value="tag2" checked={selectedTag.includes("tag2")} onChange={ handleTagChange } />
                <label htmlFor="tag2" className='checkbox-label'>
                    30대
                </label>
              </li>
            </ul>
          </section>
        </div>
      </div>
    </aside>
  )
}

export default Filteraside;