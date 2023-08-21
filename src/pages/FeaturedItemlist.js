import React, { useState } from 'react';
import { useSelector } from 'react-redux';
// css import
import style from '../styles/Item.module.css';

const ITEMS_PER_PAGE = 10;
const NO_IMAGE_URL = '/images/common/noimg.png';

const FeaturedItemlist = ({ category }) => {
  const bestitems = useSelector(state => state.item.bestitems);

  // State variables for pagination
  const [currentPage, setCurrentPage] = useState(1);

  // Calculate the total number of pages
  const totalPages = Math.ceil(bestitems.length / ITEMS_PER_PAGE);

  // Slice data to display items for the current page
  const startIndex = (currentPage - 1) * ITEMS_PER_PAGE + 4;
  const endIndex = startIndex + ITEMS_PER_PAGE;
  const slicedItems = bestitems.slice(startIndex, endIndex);

  // Function to handle page change
  const handlePageChange = (newPage) => {
    if (newPage >= 1 && newPage <= totalPages) {
      setCurrentPage(newPage);
    }
  };


  return (
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h2>{category}</h2>
        </div>
        <section className='item-list'>
          {
            bestitems.slice(0, 4).map((item, index) => 
            <div className='item-list-box' key={index}>
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
            </div>
            )
          }
        </section>

        <section className={style.section}>
          <div className={style.itemlist}>
            {slicedItems.map((item, index) => (
              <div className={style.item} key={index}>
                <div className={style.img}>
                  <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                </div>
                <div className={style.txt}>
                  <h3>{item.name}</h3>
                  <div className={style.tag}>
                    <p>{item.category}</p>
                    <p>{item.tag}</p>
                  </div>
                </div>
              </div>
            ))}
          </div>
        </section>

        <div className='pagination-wrap'>
          <div className='pagination'>
            <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
            <span className='material-icons'>chevron_left</span>
            </button>
            <span>{currentPage}</span> / <span>{totalPages}</span>
            <button onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}>
            <span className='material-icons'>chevron_right</span>
            </button>
          </div>
        </div>
      </div>
    </section>
  );
};

export default FeaturedItemlist;
