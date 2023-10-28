import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
//redux import
import { editCategory, setValidCategoryName, setCategoryName } from '../../redux/actions/admin/adminCategoryActions';

const CategoryEdit = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const isCategoryNameval = useSelector(state => state.user.isCategoryNameval);

  const [categoryIcon, setCategoryIcon] = useState(null);
  const categoryName = useSelector(state => state.user.categoryName);
  const IsLoading = useSelector(state => state.user.IsLoading);
  
  const [errorMessage, setErrorMessage] = useState('');

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

  const handleImageUpload = async () => {
    try {
      if (categoryIcon) {
        const formData = new FormData();
        formData.append('categoryIcon', categoryIcon);
        console.log(categoryIcon)
  
        // 서버로 이미지 업로드 요청
        const response = await axios.post('/upload-pcategoryIcon', formData);
        // 업로드 성공 시 서버에서 이미지 URL을 받아옴
        const imageUrl = response.data.imageUrl;
        // 이미지 URL을 저장
        setCategoryIcon(imageUrl);
  
        console.log('이미지 업로드 성공');
      }
    } catch (error) {
      console.error('이미지 업로드 오류:', error);
    }
  };

  const handleCreate = async(e) => {
    e.preventDefault();
    await handleImageUpload();
    const userData = {
      categoryName,
      categoryIcon,
      isCategoryNameval,
    };
    dispatch(editCategory(userData, navigate));
  };

  const NO_IMAGE_URL = '/images/common/noimg.png';

  return (
    <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth'>
        <div className='cont_tit_nm'>
          <h2>카테고리 수정</h2>
        </div>
        
        <div className={`${style.form} mt_60`}>
            <form onSubmit={ handleCreate }>
              
              <div className={style.userImg}>
                <div className={`${style.circle} ${style.circleSm}`}>
                  <img src={categoryIcon || NO_IMAGE_URL} alt={categoryName} />
                </div>
                <input type="file" id="file" accept="image/*" className='inputFile'
                  onChange={(e) => {
                    const imageFile = e.target.files[0];
                    setCategoryIcon(URL.createObjectURL(imageFile));
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