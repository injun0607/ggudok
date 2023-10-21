import React from 'react';
import { useSelector } from 'react-redux';
import { Fade } from 'react-slideshow-image';
import 'react-slideshow-image/dist/styles.css'

// const fadeImages = [
//   {
//     url: '/images/slide/mainslide02.png',
//     caption: '매일 마시는 커피, 더 저렴하게 즐기기'
//   },
//   {
//     url: '/images/slide/mainslide03.png',
//     caption: '한 달 간 고생한 나를 위한 먼슬리 생화 구독'
//   },
//   {
//     url: '/images/slide/mainslide01.png',
//     caption: '건강한 식사를 손쉽게, 샐러드 구독으로 더 즐겁게'
//   },
// ];

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