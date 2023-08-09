// component import
import Eventbanner from '../components/Eventbanner';
// css import
import style from '../styles/Item.module.css';

const Event = () => {

  return (
    <div className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'>
          <h4>이벤트</h4>
        </div>

        <div className='section'>
          <Eventbanner />
        </div>
      </div>
    </div>
  );
};

export default Event;
