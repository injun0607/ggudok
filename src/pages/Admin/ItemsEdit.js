import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
//redux import
import { editItem, setValidItemName, setValidItemEng, setItemName, setItemEng, setItemImage } from '../../redux/actions/admin/adminItemsActions';

const ItemsEdit = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate(); 

  const { subsId } = useParams();

  const itemName = useSelector(state => state.adminItem.itemName);
  const itemEng = useSelector(state => state.adminItem.itemEng);
  const itemImage = useSelector(state => state.adminItem.itemImage);
  
  const isItemNameval = useSelector(state => state.adminItem.isItemNameval);
  const isItemEngval = useSelector(state => state.adminItem.isItemEngval);
  const [errorMessage, setErrorMessage] = useState('');
  const [errorMessageE, setErrorMessageE] = useState('');

  const [IsLoading, setIsLoading] = useState(true);

  // ************************** 기본 value fetch ***************************
  const fetchItemData = async () => {
    try {
      const response = await axios.get(`/admin/subs/update/${subsId}`);
      const data = response.data;
      console.log('editing data', data)
      dispatch(setItemName(data.subsName));
      dispatch(setItemEng(data.subsEng))
      dispatch(setItemImage(data.subsImage))
      dispatch(setValidItemName(true));
      dispatch(setValidItemEng(true));
  } catch (error) {
      console.error('Error fetching item data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchItemData();
    return () => {
      dispatch(setItemName(''));
      dispatch(setItemEng(''));
      dispatch(setItemImage(''));
    }
  }, []);

  const handleChangeItemName = (newItemName) => {
    const regItemName = /^[가-힣]{1,}$/;
    const isItemNameReg = regItemName.test(newItemName);
  
    // 오류 메시지 설정
    if (!isItemNameReg) {
      setErrorMessage('한 글자 이상의 한글만 입력해주세요.');
    } else {
      setErrorMessage('');
    }
    // Redux 상태 업데이트
    dispatch(setValidItemName(isItemNameReg));
  }
  const handleChangeItemEng = (newItemEng) => {
    const regItemEng = /^[a-z]{1,}$/;
    const isItemEngReg = regItemEng.test(newItemEng);
  
    // 오류 메시지 설정
    if (!isItemEngReg) {
      setErrorMessageE('한 글자 이상의 영문만 입력해주세요.');
    } else {
      setErrorMessageE('');
    }
    // Redux 상태 업데이트
    dispatch(setValidItemEng(isItemEngReg));
  }

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
      if (itemImage) {
        const blobData = await convertBlobURLToBlob(itemImage);
        const formData = new FormData();
        formData.append('itemImage', blobData);
        
        const response = await axios({
          method: "POST",
          url: `/admin/item/image`,
          charset: 'utf-8',
          headers: {
            "Content-Type": "multipart/form-data",
          },
          data: formData
        })
        
        const imageUrl = response.data.imageUrl;
        await dispatch(setItemImage(imageUrl));
        
        return imageUrl;
      }
    } catch (error) {
      console.error('이미지 업로드 오류:', error);
      throw error;
    }
  };

  const handleEdit = async (e) => {
    e.preventDefault();
    try {
      const updatedImageUrl = await handleImageUpload();
  
      const itemData = {
        subsId,
        itemName,
        itemEng,
        itemImage: updatedImageUrl,
        isItemNameval,
        isItemEngval,
      };
      console.log('itemData', itemData);
      dispatch(editItem(itemData, navigate));
    } catch (error) {
      console.error('Error handling edit:', error);
    }
  };
  

  const NO_IMAGE_URL = '/images/common/noimg.png';

  return (
    !IsLoading && <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth'>
        <div className='cont_tit_m'>
          <h2>구독서비스 수정</h2>
        </div>
        
        <div className={`${style.form} mt_60`}>
            <form onSubmit={ handleEdit }>
              
              <div className={style.userImg}>
                <div className={`${style.circle} ${style.circleSm}`}>
                  <img src={itemImage || NO_IMAGE_URL} alt={itemName} />
                </div>
                <input type="file" id="file" accept="image/*" className='inputFile'
                  onChange={(e) => {
                    const imageFile = e.target.files[0];
                    dispatch(setItemImage(URL.createObjectURL(imageFile)));
                  }}
                />
                <label htmlFor="file">카테고리 아이콘 등록</label>
              </div>
              <div className={style.inputwrap}>
                <input
                  type='text' name='itemName' autoComplete="off"
                  placeholder='카테고리명을 입력하세요.' 
                  value={itemName}
                  onChange={(e) => {
                    dispatch(setItemName(e.target.value))
                    handleChangeItemName(e.target.value);
                  }} />
                {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
                <input
                  type='text' name='itemEng' autoComplete="off"
                  placeholder='영문명을 입력하세요.' 
                  value={itemEng}
                  onChange={(e) => {
                    dispatch(setItemEng(e.target.value))
                    handleChangeItemEng(e.target.value);
                  }} />
                {errorMessageE && <p style={{ color: 'red' }}>{errorMessageE}</p>}
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

export default ItemsEdit;