import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
//redux import
import { createEvent } from '../../redux/actions/admin/adminEventsActions';
import { fetchCategorySuccess } from '../../redux/actions/admin/adminCategoryActions';
import { fetchItemsSuccess, setSelectedCategory, filterItems } from '../../redux/actions/admin/adminItemsActions';

const EventCreate = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate(); 

  const items = useSelector(state => state.adminItems.items);
  const filteredItems = useSelector(state => state.adminItems.filteredItems);
  const categories = useSelector(state => state.adminCategory.categories);
  const selectedCategory = useSelector(state => state.adminItems.selectedCategory);

  const [eventSubsName, setEventSubsName] = useState(''); // 선택한 아이템의 이름
  const [subsId, setEventSubsId] = useState(''); // 선택한 아이템의 subsId
  const [eventInfoTag, setEventInfoTag] = useState(''); // 이벤트 태그 (ex. 40%할인)
  const [eventInfo, setEventInfo] = useState(''); // 이벤트 설명
  const [eventImage, setEventImage] = useState(''); // 이벤트 이미지
  const [eventDateStart, setEventDateStart] = useState([]); // 시작날짜
  const [eventDateEnd, setEventDateEnd] = useState([]); // 종료날짜
  const [isValid, setIsValid] = useState(true); // 활성화여부
  
  const [isEventInfoTagval, setEventInfoTagval] = useState(false);
  const [errorMessageInfoTag, setErrorMessageInfoTag] = useState('');
  const [isEventInfoval, setEventInfoval] = useState(false);
  const [errorMessageInfo, setErrorMessageInfo] = useState('');
  const [isEventDateStartval, setEventDateStartval] = useState(false);
  const [errorMessageDateStart, setErrorMessageDateStart] = useState('');
  const [isEventDateEndval, setEventDateEndval] = useState(false);
  const [errorMessageDateEnd, setErrorMessageDateEnd] = useState('');

  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);
  
  // ************************ 카테고리, 구독서비스 fetch *************************
  const fetchEventsData = async () => {
    try {
      const response = await axios.get(`/admin/event/register`);
      const dataSubs = response.data.subsList;
      const dataCategory = response.data.categoryList;

      if(dataSubs !== 0){
        dispatch(fetchItemsSuccess(dataSubs));
        dispatch(fetchCategorySuccess(dataCategory));
      } else {
        dispatch(fetchItemsSuccess([]));
        dispatch(fetchCategorySuccess([]));
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchEventsData();
  }, []);

  // ****************************** 필터 버튼 핸들러 *******************************
  // 선택된 카테고리에 기반한 아이템 필터링
  useEffect(() => {
    let updatedItems = items;
    if (selectedCategory) {
      updatedItems = items.filter(item => item.categoryName === selectedCategory);
    }
    dispatch(filterItems(updatedItems));
  }, [items, selectedCategory]);
  
  // 이벤트 태그
  const handleChangeEventInfoTag = (newEventInfo) => {
    const regEventInfoTag = /^.{1,}$/;
    const isEventInfoTagReg = regEventInfoTag.test(newEventInfo);
  
    if (!isEventInfoTagReg) {
      setErrorMessageInfoTag('한 글자 이상 입력해주세요.');
    } else {
      setErrorMessageInfoTag('');
    }
    
    setEventInfoTagval(isEventInfoTagReg);
  }
  // 이벤트 설명
  const handleChangeEventInfo = (newEventInfo) => {
    const regEventInfo = /^.{1,}$/;
    const isEventInfoReg = regEventInfo.test(newEventInfo);
  
    if (!isEventInfoReg) {
      setErrorMessageInfo('한 글자 이상 입력해주세요.');
    } else {
      setErrorMessageInfo('');
    }
    
    setEventInfoval(isEventInfoReg);
  }
  // 이벤트 날짜
  const handleChangeEventDateStart = (newEventDateStart) => {
    const regEventDateStart = /^\d{8}$/;
    const isEventDateStartReg = regEventDateStart.test(newEventDateStart);
  
    if (!isEventDateStartReg) {
      setErrorMessageDateStart('날짜 형식은 yyyymmdd로 입력해주세요.');
    } else {
      setErrorMessageDateStart('');
    }
    
    setEventDateStartval(isEventDateStartReg);
  }
  const handleChangeEventDateEnd = (newEventDateEnd) => {
    const regEventDateEnd = /^\d{8}$/;
    const isEventDateEndReg = regEventDateEnd.test(newEventDateEnd);
  
    if (!isEventDateEndReg) {
      setErrorMessageDateEnd('날짜 형식은 yyyymmdd로 입력해주세요.');
    } else if (eventDateStart && newEventDateEnd < eventDateStart) {
      setErrorMessageDateEnd('종료일은 시작일보다 빨라야 합니다.');
    } else {
      setErrorMessageDateEnd('');
    }
  
    setEventDateEndval(isEventDateEndReg);
  }

  // 이벤트 이미지
  const convertBlobURLToBlob = async (blobURL) => {
    try {
      const response = await fetch(blobURL);
      const blobData = await response.blob();
      return blobData;
    } catch (error) {
      console.error('Blob URL을 Blob 객체로 변환하는 중에 오류가 발생했습니다:', error);
      throw error;
    }
  };
  const handleImageUpload = async () => {
    try {
      if (eventImage) {
        const blobData = await convertBlobURLToBlob(eventImage);
        const formData = new FormData();
        formData.append('eventImage', blobData);
        
        const response = await axios({
          method: "POST",
          url: `/admin/event/register/image`,
          charset: 'utf-8',
          headers: {
            "Content-Type": "multipart/form-data",
          },
          data: formData
        })
        
        const imageUrl = response.data.imageUrl;
        setEventImage(imageUrl);
        
        return imageUrl;
      }
    } catch (error) {
      console.error('이미지 업로드 오류:', error);
    }
  };

  const handleCreate = async(e) => {
    e.preventDefault();

    if(!subsId) {
      alert('이벤트에 등록할 구독서비스를 선택해주세요.');
      return;
    } if(!isEventInfoTagval) {
      setErrorMessageInfoTag('한 글자 이상 입력해주세요.');
      alert('이벤트 태그를 한 글자 이상 입력해주세요.');
      return;
    } if(!isEventInfoval) {
      setErrorMessageInfo('한 글자 이상 입력해주세요.');
      alert('이벤트 설명을 한 글자 이상 입력해주세요.');
      return;
    } if(!isEventDateStartval) {
      setErrorMessageDateStart('날짜 형식은 yyyymmdd로 입력해주세요.');
      alert('날짜 형식을 yyyymmdd로 입력해주세요.');
      return;
    } if(!isEventDateEndval) {
      setErrorMessageInfo('종료일 형식은 yyyymmdd로 입력되어야 하며, 시작일보다 빨라야 합니다.');
      alert('종료일 형식을 yyyymmdd로 입력되어야 하며, 시작일보다 빨라야 합니다.');
      return;
    } else {
      try {
        const updatedImageUrl = await handleImageUpload();
    
        const eventData = {
          subsId,
          eventInfoTag,
          eventInfo,
          eventImage: updatedImageUrl,
          eventDateStart,
          eventDateEnd,
          isValid,
        };
        dispatch(createEvent(eventData, navigate));
      } catch (error) {
        console.error('Error handling Create:', error);
      }
    }
  };
  
  useEffect(() => {
    return () => {
      setEventSubsId('');
      setEventInfoTag('');
      setEventInfo('');
      setEventImage('');
      setEventDateStart('');
      setEventDateEnd('');
      setIsValid(true);
    }
  }, []);

  const NO_IMAGE_URL = '/images/common/noimg.png';

  return (
    !IsLoading && <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth'>
        <div className='cont_tit_m'>
          <h2>신규 이벤트 등록</h2>
        </div>
        
        <div className={`${style.form} mt_60`}>
            <form onSubmit={ handleCreate }>
              
              <div className={style.userImg}>
                <div className={`${style.circle} ${style.circleSmNr}`}>
                  <img src={eventImage || NO_IMAGE_URL} alt={eventSubsName} />
                </div>
                <input type="file" id="file" accept="image/*" className='inputFile'
                  onChange={(e) => {
                    const imageFile = e.target.files[0];
                    setEventImage(URL.createObjectURL(imageFile));
                  }}
                />
                <label htmlFor="file">이벤트 이미지 등록</label>
              </div>
              <div className={style.formSection}>
                <div className={style.tit}>
                  <h3>
                    구독서비스 선택
                  </h3>
                </div>
                <div className={`${style.cont} ${style.contTag}`}>
                  <select
                    id="categoryFilter"
                    className={style.select}
                    onChange={(e) => dispatch(setSelectedCategory(e.target.value))}
                    value={selectedCategory}
                  >
                    <option value="">카테고리 선택</option>
                    {categories.map((category) => (
                      <option key={category.categoryId} value={category.categoryName}>
                        {category.categoryName}
                      </option>
                    ))}
                  </select>
                  <select
                    id="itemFilter"
                    className={style.select}
                    onChange={(e) => {
                      setEventSubsId(e.target.value)
                    }}
                    value={subsId}
                  >
                    <option value="">구독서비스 선택</option>
                    {filteredItems.map((item) => (
                      <option key={item.subsId} value={item.subsId}>
                        {item.subsName}
                      </option>
                    ))}
                  </select>
                </div>
              </div>
              <div className={`${style.inputwrap} ${style.formSection}`}>
                <input
                  type='text' name='eventName'
                  placeholder='이벤트 태그를 입력하세요. (ex. 40% 할인)' 
                  value={eventInfoTag}
                  onChange={(e) => {
                    setEventInfoTag(e.target.value)
                    handleChangeEventInfoTag(e.target.value);
                  }} />
                {errorMessageInfoTag && <p style={{ color: 'red' }}>{errorMessageInfoTag}</p>}
                <input
                  type='text' name='eventEng'
                  placeholder='이벤트 설명을 입력하세요.' 
                  value={eventInfo}
                  onChange={(e) => {
                    setEventInfo(e.target.value)
                    handleChangeEventInfo(e.target.value);
                  }} />
                {errorMessageInfo && <p style={{ color: 'red' }}>{errorMessageInfo}</p>}
                <input
                  type='text' name='eventName'
                  placeholder='이벤트 시작날짜를 입력하세요. (ex. yyyymmdd)' 
                  value={eventDateStart}
                  
                  onChange={(e) => {
                    setEventDateStart(e.target.value)
                    handleChangeEventDateStart(e.target.value);
                  }} />
                {errorMessageDateStart && <p style={{ color: 'red' }}>{errorMessageDateStart}</p>}
                <input
                  type='text' name='eventName'
                  placeholder='이벤트 종료날짜를 입력하세요. (ex. yyyymmdd)' 
                  value={eventDateEnd}
                  
                  onChange={(e) => {
                    setEventDateEnd(e.target.value)
                    handleChangeEventDateEnd(e.target.value);
                  }} />
                {errorMessageDateEnd && <p style={{ color: 'red' }}>{errorMessageDateEnd}</p>}
              </div>
              <div className={style.formSection}>
                <div className={style.tit}>
                  <h3>
                    활성화
                  </h3>
                  <>
                    <input
                      type="checkbox"
                      id={style.toggle}
                      checked={isValid}
                      onChange={() => setIsValid(!isValid)}
                    />
                    <label htmlFor={style.toggle} className={style.toggleSwitch}>
                      <span className={style.toggleButton}></span>
                    </label>
                  </>
                </div>
              </div>
              <div className={style.doublebutton}>
                <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
                <button type='submit' className='btn btn_full'>등록하기</button>
              </div>
            </form>
          </div>
      </div>
    </section>
  )
}

export default EventCreate;