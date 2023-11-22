import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { Swiper, SwiperSlide } from 'swiper/react';
import { Navigation } from 'swiper/modules';
// css import
import 'swiper/css';
import 'swiper/css/navigation';
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
  const [textColor, setTextColor] = useState('white');
  
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

  // 이벤트 데이터가 변경될 때마다 호출되는 useEffect
  useEffect(() => {
    const imageElement = document.querySelector(".background-image");
  
    if (imageElement) {
      const handleImageLoad = function () {
        getAverageColor(imageElement);
      };
  
      // 이미지가 로딩되었을 때만 getAverageColor 호출
      imageElement.onload = handleImageLoad;
  
      // 컴포넌트 언마운트 시에 이벤트 리스너 제거
      return () => {
        imageElement.onload = null;
      };
    }
  }, [events]);
  

  // 이미지의 평균 색상을 계산하고 텍스트 색상을 업데이트하는 함수
  const getAverageColor = (imageElement) => {
    const canvas = document.createElement("canvas");
    const ctx = canvas.getContext("2d");
    const image = new Image();

    // 이미지 로딩 후 처리
    image.src = imageElement.src;
    image.onload = function () {
      canvas.width = image.width;
      canvas.height = image.height;
      ctx.drawImage(image, 0, 0, image.width, image.height);

      const imageData = ctx.getImageData(0, 0, image.width, image.height).data;

      let totalHue = 0;
      let totalSaturation = 0;
      let totalLightness = 0;

      // 이미지의 각 픽셀의 HSL 값을 더함
      for (let i = 0; i < imageData.length; i += 4) {
        const [hue, saturation, lightness] = rgbToHsl(
          imageData[i],
          imageData[i + 1],
          imageData[i + 2]
        );
        totalHue += hue;
        totalSaturation += saturation;
        totalLightness += lightness;
      }

      // HSL의 평균값 계산
      const averageHue = totalHue / (imageData.length / 4);
      const averageSaturation = totalSaturation / (imageData.length / 4);
      const averageLightness = totalLightness / (imageData.length / 4);

      // 이미지의 평균 밝기를 설정
      const averageBrightness = averageLightness * 100;

      // 밝기에 따라 텍스트 색상을 설정
      setTextColor(getContrastColor(averageBrightness));
    };
  };

  // RGB를 HSL로 변환하는 함수
  const rgbToHsl = (r, g, b) => {
    r /= 255;
    g /= 255;
    b /= 255;

    const max = Math.max(r, g, b);
    const min = Math.min(r, g, b);
    let h, s, l = (max + min) / 2;

    if (max === min) {
      h = s = 0;
    } else {
      const d = max - min;
      s = l > 0.5 ? d / (2 - max - min) : d / (max + min);

      switch (max) {
        case r:
          h = (g - b) / d + (g < b ? 6 : 0);
          break;
        case g:
          h = (b - r) / d + 2;
          break;
        case b:
          h = (r - g) / d + 4;
          break;
        default:
          break;
      }

      h /= 6;
    }

    return [h, s, l];
  };

  // 밝기에 따라 적절한 텍스트 색상을 반환하는 함수
  const getContrastColor = (brightness) => {
    const threshold = 50; // 이 값을 조절하여 적절한 밝기 수준을 선택
    return brightness > threshold ? "black" : "white";
  };


  return (
    !IsLoading ? (IsResult &&
    <section className="mainslider">
      <Swiper
          cssMode={true}
          navigation={true}
          pagination={false}
          spaceBetween={0}
          slidesPerView={1}
          modules={[Navigation]}
          className="item-list_carousel"
        >
      {
        events.map((event, index) => (
          <SwiperSlide key={index} className='bannerslider'>
            <Link to={`/subs/detail/${event.subsId}`}>
              <img src={event.image || NO_IMAGE_URL} style={{ width: '100%' }} alt={event.subsName} />
              <div className='tag'>
                <p>{event.categoryName}</p>
                <p>{event.infoTag}</p>
              </div>
              {/* <h3>{event.subsName}</h3>
              <h4>{event.info}</h4>
              <p>{event.startDate} - {event.endDate}</p> */}
              <h3 style={{ color: textColor }}>{event.subsName}</h3>
              <h4 style={{ color: textColor }}>{event.info}</h4>
              <p style={{ color: textColor }}>{event.startDate} - {event.endDate}</p>
            </Link>
          </SwiperSlide>
        ))
      }
      </Swiper>
    </section>
    ) : <Loading />
  )
}

export default Bannerslider;