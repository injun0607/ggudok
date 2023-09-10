import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../styles/Compare.module.css';
// redux import
import { setTwoDepthItems, setSelectedOneDepth, setCompareItem1, setCompareItem2 } from '../redux/actions/compareActions';

const NO_IMAGE_URL = '/images/common/noimg.png';

const Compare = () => {
  const dispatch = useDispatch();

  const items = useSelector(state => state.item.items);
  const categories = useSelector(state => state.category.categories);
  const selectedOneDepth = useSelector(state => state.compare.selectedOneDepth);
  const twoDepthItems = useSelector(state => state.compare.twoDepthItems);
  let compareItem1 = useSelector(state => state.compare.compareItem1);
  let compareItem2 = useSelector(state => state.compare.compareItem2);

  useEffect(() => {
    const filteredItems = items.filter(item => item.category === selectedOneDepth);
    dispatch(setTwoDepthItems(filteredItems));
  }, [dispatch, items, selectedOneDepth]);

  const handleChangeOneDepth = (e) => {
    dispatch(setSelectedOneDepth(e.target.value));
  }

  const handleChangeCompareItem = (e, itemNumber) => {
    const selectedItemId = e.target.value;
    const selectedItem = items.find(item => item.id === selectedItemId);
    if (itemNumber === 1) {
      dispatch(setCompareItem1(selectedItem));
    } else if (itemNumber === 2) {
      dispatch(setCompareItem2(selectedItem));
    }
  };

  // 카테고리 선택하지 않으면 아이템 선택 비활성화
  const isOneDepthSelected = selectedOneDepth !== '';
  const isCompareItem1Selected = compareItem1 !== null;
  const isCompareItem2Selected = compareItem2 !== null;

  return (
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h2>구독서비스 간편비교하기</h2>
        </div>

        <section className={style.search}>
          <div className={style.onedepth}>
            <select className={style.select} onChange={ handleChangeOneDepth } value={selectedOneDepth}>
              <option value="">우선 카테고리를 선택하세요.</option>
              {
                categories.map((category, index) => 
                <option value={category.category} key={index}>{category.category}</option>
                )
              }
            </select>
          </div>
          <div className={style.twodepth}>
            <select className={`${style.select} ${style.select01}`} onChange={(e) => handleChangeCompareItem(e, 1)} value={compareItem1} disabled={!isOneDepthSelected}>
              <option value="">{isCompareItem1Selected ? `${compareItem1.name}` : '첫번째 구독서비스를 선택하세요.'}</option>
              {
                twoDepthItems.map((item, index) => 
                <option value={item.id} key={index}>{item.name}</option>
                )
              }
            </select>
            <select className={`${style.select} ${style.select02}`} onChange={(e) => handleChangeCompareItem(e, 2)} value={compareItem2} disabled={!isOneDepthSelected}>
              <option value="">{isCompareItem2Selected ? `${compareItem2.name}` : '두번째 구독서비스를 선택하세요.'}</option>
              {
                twoDepthItems.map((item, index) => 
                <option value={item.id} key={index}>{item.name}</option>
                )
              }
            </select>
          </div>
        </section>

        <section className={style.detail}>
          {/* {compareItem1 ? alert('on') : null } */}
          {compareItem1 ? (
            <div className={style.boxrow}>
              <div className={`${style.box} ${style.titbox}`}>
                <img src={`${compareItem1.image}`} alt={compareItem1.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                <h3>{compareItem1.name}</h3>
              </div>
              <div className={style.box}>
                <div className={style.category}>
                  <img src={`${compareItem1.icon}`} alt={compareItem1.category} />
                  <p>{compareItem1.category}</p>
                </div>
                <h3>카테고리</h3>
              </div>
              <div className={style.box}>
                <p className={style.tag}>{compareItem1.tag}</p>
                <h3>태그</h3>
              </div>
              <div className={style.box}>
                <div className={style.starrating}>
                  <div className={style.star}>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className="material-icons">star</span>
                  </div>
                </div>
                <h3>별점</h3>
              </div>
              <div className={style.box}>
                <p className={style.price}>
                  스탠다드 <span className={style.point}>월 8,900 원</span>
                </p>
                <h3>가격</h3>
              </div>
            </div>
            ) : (
            <div className={`${style.boxrow} ${style.boxrowerror}`}><p>구독서비스를 선택하세요!</p></div>
            )}
            
          {compareItem2 ? (
            <div className={style.boxrow}>
              <div className={`${style.box} ${style.titbox}`}>
                <img src={`${compareItem2.image}`} alt={compareItem2.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                <h3>{compareItem2.name}</h3>
              </div>
              <div className={style.box}>
                <div className={style.category}>
                  <img src={`${compareItem2.icon}`} alt={compareItem2.category} />
                  <p>{compareItem2.category}</p>
                </div>
                <h3>카테고리</h3>
              </div>
              <div className={style.box}>
                <p className={style.tag}>{compareItem2.tag}</p>
                <h3>태그</h3>
              </div>
              <div className={style.box}>
                <div className={style.starrating}>
                  <div className={style.star}>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className={`material-icons ${style.starActive}`}>star</span>
                    <span className="material-icons">star</span>
                  </div>
                </div>
                <h3>별점</h3>
              </div>
              <div className={style.box}>
                <p className={style.price}>
                  스탠다드 <span className={style.point}>월 8,900 원</span>
                </p>
                <h3>가격</h3>
              </div>
            </div>
            ) : (
            <div className={`${style.boxrow} ${style.boxrowerror}`}><p>구독서비스를 선택하세요!</p></div>
            )}
        </section>

      </div>
    </section>
  );
};

export default Compare;
