import { useSelector } from 'react-redux';

const Eventbanner = () => {
  const events = useSelector(state => state.event.events);

  return (
    <>
      {events.map((event, index) => (
        <div key={index} className='bannerslider'>
          <img style={{ width: '100%' }} src={event.image} alt={event.name} />
          <div className='tag'>
            <p>{event.category}</p>
            <p>{event.discount}</p>
          </div>
          <h3>{event.name}</h3>
          <h4>{event.copy}</h4>
          <p>{event.date}</p>
        </div>
      ))}
    </>
  );
};

export default Eventbanner;
