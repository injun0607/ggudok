import React from 'react'
// component import
import Eventbanner from '../components/Eventbanner';
// css import
import style from '../styles/Item.module.css';

const Event = () => {

  return (
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h2>이벤트</h2>
        </div>

        <section className='section'>
          <Eventbanner />
        </section>
      </div>
    </section>
  );
};

export default Event;
