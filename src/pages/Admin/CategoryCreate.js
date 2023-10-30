import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { useParams, Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
//redux import
import { createCategory, setValidCategoryName, setValidCategoryEng, setCategoryName, setCategoryEng, setCategoryImage } from '../../redux/actions/admin/adminCategoryActions';

const CategoryEdit = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate(); 

  const categoryName = useSelector(state => state.adminCategory.categoryName);
  const categoryEng = useSelector(state => state.adminCategory.categoryEng);
  const categoryImage = useSelector(state => state.adminCategory.categoryImage);
  
  const isCategoryNameval = useSelector(state => state.adminCategory.isCategoryNameval);
  const isCategoryEngval = useSelector(state => state.adminCategory.isCategoryEngval);
  const [errorMessage, setErrorMessage] = useState('');
  const [errorMessageE, setErrorMessageE] = useState('');

  const handleChangeCategoryName = (newCategoryName) => {
    const regCategoryName = /^[가-힣]{1,}$/;
    const isCategoryNameReg = regCategoryName.test(newCategoryName);
  
    // 오류 메시지 설정
    if (!isCategoryNameReg) {
      setErrorMessage('한 글자 이상의 한글만 입력해주세요.');
    } else {
      setErrorMessage('');
    }
    // Redux 상태 업데이트
    dispatch(setValidCategoryName(isCategoryNameReg));
  }
  const handleChangeCategoryEng = (newCategoryEng) => {
    const regCategoryEng = /^[a-z]{1,}$/;
    const isCategoryEngReg = regCategoryEng.test(newCategoryEng);
  
    // 오류 메시지 설정
    if (!isCategoryEngReg) {
      setErrorMessageE('한 글자 이상의 영문만 입력해주세요.');
    } else {
      setErrorMessageE('');
    }
    // Redux 상태 업데이트
    dispatch(setValidCategoryEng(isCategoryEngReg));
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
      if (categoryImage) {
        const blobData = await convertBlobURLToBlob(categoryImage);
        const formData = new FormData();
        formData.append('categoryImage', blobData);
        
        const response = await axios({
          method: "POST",
          url: `/admin/category/image`,
          charset: 'utf-8',
          headers: {
            "Content-Type": "multipart/form-data",
          },
          data: formData
        })
        
        const imageUrl = response.data.imageUrl;
        dispatch(setCategoryImage(imageUrl));
        
        return imageUrl;
      }
    } catch (error) {
      console.error('이미지 업로드 오류:', error);
    }
  };

  const handleCreate = async(e) => {
    e.preventDefault();
    try {
      const updatedImageUrl = await handleImageUpload();
  
      const categoryData = {
        categoryName,
        categoryEng,
        categoryImage: updatedImageUrl,
        isCategoryNameval,
        isCategoryEngval,
      };
      console.log('categoryData', categoryData)
      dispatch(createCategory(categoryData, navigate));
  } catch (error) {
    console.error('Error handling Create:', error);
  }
};
  
  useEffect(() => {
    return () => {
      dispatch(setCategoryName(''));
      dispatch(setCategoryEng(''));
      dispatch(setCategoryImage(''));
    }
  }, []);

  const NO_IMAGE_URL = '/images/common/noimg.png';

  return (
    <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth'>
        <div className='cont_tit_m'>
          <h2>신규 카테고리 등록</h2>
        </div>
        
        <div className={`${style.form} mt_60`}>
            <form onSubmit={ handleCreate }>
              
              <div className={style.userImg}>
                <div className={`${style.circle} ${style.circleSm}`}>
                  <img src={categoryImage || NO_IMAGE_URL} alt={categoryName} />
                </div>
                <input type="file" id="file" accept="image/*" className='inputFile'
                  onChange={(e) => {
                    const imageFile = e.target.files[0];
                    dispatch(setCategoryImage(URL.createObjectURL(imageFile)));
                  }}
                />
                <label htmlFor="file">카테고리 아이콘 등록</label>
              </div>
              <div className={style.inputwrap}>
                <input
                  type='text' name='categoryName' autoComplete="off"
                  placeholder='카테고리명을 입력하세요.' 
                  value={categoryName}
                  onChange={(e) => {
                    dispatch(setCategoryName(e.target.value))
                    handleChangeCategoryName(e.target.value);
                  }} />
                {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
                <input
                  type='text' name='categoryEng' autoComplete="off"
                  placeholder='영문명을 입력하세요.' 
                  value={categoryEng}
                  onChange={(e) => {
                    dispatch(setCategoryEng(e.target.value))
                    handleChangeCategoryEng(e.target.value);
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

export default CategoryEdit;