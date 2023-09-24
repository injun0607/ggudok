import React from 'react'
import { useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
// component import
import Bannerslider from '../components/Slider';
// css import
import style from '../styles/Home.module.css'
// redux import

const NO_IMAGE_URL = '/images/common/noimg.png';

const Home = () => {
  const categories = useSelector(state => state.category.categories);
  const items = useSelector(state => state.item.items);
  const favoritecategories = useSelector(state => state.category.favoritecategories);
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);

  return (
    <section className={style.home}>
      <div className='webwidth'>
        <section className={style.bannerslider}>
            <Bannerslider />
        </section>
        <section className={style.section}>
          <h2 className={style.tit}>전체 카테고리</h2>
          <nav className={`${style.allcategory} ${style.box}`}>
            {
              categories.map((category, index) =>
              <Link
                to={`/Category/${category.categoryEng}`}
                className={style.category}
                key={index}
            >
                <img src={`${category.icon}`} alt={category.category} />
                <p>{category.category}</p>
              </Link>
              )
            }
          </nav>
        </section>
        <section className={style.section}>
          <h2 className={style.tit}>알고리즘 저격! <span className='sub_clr'>맞춤 서비스</span></h2>
          <div className='item-list'>
            {
              items.slice(0, 4).map((item, index) => 
              <Link to={`/subs/detail/${item.id}`} key={index} className='item-list-box'>
              {/* <div className='item-list-box' key={index}> */}
                <div className='img'>
                  <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                </div>
                <div className='txt'>
                  <h3>{item.name}</h3>
                  <div className='tag'>
                    <p>{item.category}</p>
                    <p>{item.tag}</p>
                  </div>
                </div>
              {/* </div> */}
              </Link>
              )
            }
          </div>
        </section>
        <section className={style.section}>
          <h2 className={style.tit}>나와 같은 <span className='main_clr'>20대 남성</span>이 가장 많이 구독한 서비스</h2>
          <div className='item-list'>
            {
              items.slice(0, 4).map((item, index) => 
              <Link to={`/subs/detail/${item.id}`} key={index} className='item-list-box'>
                <div className='img'>
                  <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                </div>
                <div className='txt'>
                  <h3>{item.name}</h3>
                  <div className='tag'>
                    <p>{item.category}</p>
                    <p>{item.tag}</p>
                  </div>
                </div>
              </Link>
              )
            }
          </div>
        </section>
      </div>
    </section>
  )
}

export default Home;