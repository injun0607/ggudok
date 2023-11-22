import React from 'react';
import { useSelector } from 'react-redux';
import { Fade } from 'react-slideshow-image';
import 'react-slideshow-image/dist/styles.css'

const Bannerslider = () => {
  const events = useSelector(state => state.event.events);

    return (
      <section className="slide-container">
        <Fade>
        {events.map((event, index) => (
          <div key={index} className='bannerslider'>
            <img style={{ width: '100%' }} src={event.image} alt={event.name}/>
            <div className='tag'>
              <p>{event.category}</p>
              <p>{event.discount}</p>
            </div>
            <h3>{event.name}</h3>
            <h4>{event.copy}</h4>
            <p>{event.date}</p>
          </div>
        ))}
        </Fade>
      </section>
    )
}

export default Bannerslider;