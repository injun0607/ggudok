import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Filter.module.css';
// redux import
import { setHideMenu } from '../redux/actions/filterActions';

const Filteraside = ({ resetFilters, handleSelectedPrice, handleSelectedRating, handleSelectedTag, filterTag, handleFilteredItems }) => {
  const dispatch = useDispatch();
  const selectedPrice = useSelector(state => state.filter.selectedPrice);
  const selectedRating = useSelector(state => state.filter.selectedRating);
  const selectedTag = useSelector(state => state.filter.selectedTag);
  const hideMenuPrice = useSelector(state => state.filter.hideMenu.price);
  const hideMenuRating = useSelector(state => state.filter.hideMenu.rating);
  const hideMenuTag = useSelector(state => state.filter.hideMenu.tag);

  // 필터 접기
  const handlehideMenu = (section) => {
    dispatch(setHideMenu(section));
  }

  // 화면 너비에 따라 필터 접기
  const [isResponsive, setIsResponsive] = useState(false);
  useEffect(() => {
    function handleResize() {
      setIsResponsive(window.innerWidth <= 640);
    }
    window.addEventListener('resize', handleResize);
    handleResize();
    return () => window.removeEventListener('resize', handleResize);
  }, []);
  useEffect(() => {
    if (isResponsive) {
      dispatch(setHideMenu('price', false));
      dispatch(setHideMenu('rating', false));
      dispatch(setHideMenu('tag', false));
    } else if (!isResponsive) {
      dispatch(setHideMenu('price', true));
      dispatch(setHideMenu('rating', true));
      dispatch(setHideMenu('tag', true));
    }
  }, [isResponsive]);

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
                <input type="checkbox" name='price' className='checkInput'
                  id="priceRow" value="priceRow"
                  onChange={(e) => {
                    handleSelectedPrice(e);
                  }}
                  checked={selectedPrice==='priceRow'} />
                <label htmlFor="priceRow" className='checkbox-label'>
                    5,900원 미만
                </label>
              </li>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='price' className='checkInput' 
                  id="priceMedium" value="priceMedium"
                  onChange={(e) => {
                    handleSelectedPrice(e);
                  }}
                  checked={selectedPrice==='priceMedium'} />
                <label htmlFor="priceMedium" className='checkbox-label'>
                    5,900원 ~ 9,900원
                </label>
              </li>
              <li className={style.checkInputWrap}>
                <input type="checkbox" name='price' className='checkInput' 
                  id="priceHigh" value="priceHigh"
                  onChange={(e) => {
                    handleSelectedPrice(e);
                  }}
                  checked={selectedPrice==='priceHigh'} />
                <label htmlFor="priceHigh" className='checkbox-label'>
                    9,900원 이상
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
                <input type="checkbox" name='rating' className='checkInput' id="star1" value="star1"
                onChange={(e) => {
                  handleSelectedRating(e);
                }}
                checked={selectedRating === 'star1'} />
                <label htmlFor="star1" className='checkbox-label'>
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
                <input type="checkbox" name='rating' className='checkInput' id="star2" value="star2"
                onChange={(e) => {
                  handleSelectedRating(e);
                }}
                checked={selectedRating === 'star2'} />
                <label htmlFor="star2" className='checkbox-label'>
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
                <input type="checkbox" name='rating' className='checkInput' id="star3" value="star3"
                onChange={(e) => {
                  handleSelectedRating(e);
                }}
                checked={selectedRating === 'star3'} />
                <label htmlFor="star3" className='checkbox-label'>
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
                <input type="checkbox" name='rating' className='checkInput' id="star4" value="star4"
                onChange={(e) => {
                  handleSelectedRating(e);
                }}
                checked={selectedRating === 'star4'} />
                <label htmlFor="star4" className='checkbox-label'>
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
                <input type="checkbox" name='rating' className='checkInput' id="star5" value="star5"
                onChange={(e) => {
                  handleSelectedRating(e);
                }}
                checked={selectedRating === 'star5'} />
                <label htmlFor="star5" className='checkbox-label'>
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
              <h3>
                태그
                <span className={style.filterMultiTxt}>* 중복선택가능</span>
              </h3>
              <button className={`${style.hideMenuBtn} ${hideMenuTag ? style.hideMenuBtnActive : ''}`} type='button'
              onClick={(e) => {
                handlehideMenu('tag');
              }}
              data-section={'tag'}>
                <span className='material-icons'>expand_more</span>
              </button>
            </div>
            <ul className={`${style.cont} ${style.contTag} ${hideMenuTag ? style.hide : ''}`}>
              {
                filterTag.map((filterTag, index) => (
                  <li className={style.checkInputWrap} key={index}>
                    <input type="checkbox" name='tag' className='checkInput' id={`tag${index}`} value={`${filterTag}`} onChange={ handleSelectedTag }
                    checked={selectedTag.includes(`${filterTag}`)} />
                    <label htmlFor={`tag${index}`} className='checkbox-label'>
                        {filterTag}
                    </label>
                  </li>
                ))
              }
            </ul>
          </section>
        </div>
      </div>
    </aside>
  )
}

export default Filteraside;