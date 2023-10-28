import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Fade } from 'react-slideshow-image';
import 'react-slideshow-image/dist/styles.css'
// component import
import Loading from '../components/Loading';
// redux import
import { setEvent } from '../redux/actions/eventActions';

const NO_IMAGE_URL = '/images/common/noimg.png';

const Bannerslider = () => {
  let dispatch = useDispatch();
  const events = useSelector(state => state.event.events);
  const [IsResult, setIsResult] = useState(false);
  const [IsLoading, setIsLoading] = useState(true);
  
  // ************************** 기본 events fetch ***************************
  const fetchEventData = async () => {
    try {
      const response = await axios.get(`/event`);
      const data = response.data.eventSubs;

      if(data !== 0){
        dispatch(setEvent(data));
      } else {
        dispatch(setEvent([]));
      }

    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchEventData();
  }, [dispatch]);

  // 결과 유무
  useEffect(() => {
    setIsResult(events.length > 0);
  }, [dispatch, events]);

    return (
      !IsLoading ? (IsResult &&
      <section className="slide-container">
        <Fade>
        {
          events.map((event, index) => (
            <section key={index} className='bannerslider'>
              <img src={event.image || NO_IMAGE_URL} style={{ width: '100%' }} alt={event.subsName} />
              <div className='tag'>
                <p>{event.categoryName}</p>
                <p>{event.infoTag}</p>
              </div>
              <h3>{event.subsName}</h3>
              <h4>{event.info}</h4>
              <p>{event.startDate} - {event.endDate}</p>
            </section>
          ))
        }
        </Fade>
      </section>
      ) : <Loading />
    )
}

export default Bannerslider;