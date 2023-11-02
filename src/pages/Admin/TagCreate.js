import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
//redux import
import { createTag, setValidTagName, setTagName } from '../../redux/actions/admin/adminTagsActions';

const TagEdit = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate(); 

  const tagName = useSelector(state => state.adminTags.tagName);
  const isTagNameval = useSelector(state => state.adminTags.isTagNameval);
  const [errorMessage, setErrorMessage] = useState('');

  const handleChangeTagName = (newTagName) => {
    const regTagName = /^[가-힣]{1,}$/;
    const isTagNameReg = regTagName.test(newTagName);
  
    // 오류 메시지 설정
    if (!isTagNameReg) {
      setErrorMessage('한 글자 이상의 한글만 입력해주세요. (띄어쓰기 불가능)');
    } else {
      setErrorMessage('');
    }
    // Redux 상태 업데이트
    dispatch(setValidTagName(isTagNameReg));
  }
  const handleCreate = async(e) => {
    e.preventDefault();
    if(!isTagNameval){
      alert('한 글자 이상의 한글만 입력해주세요. (띄어쓰기 불가능)');
      setErrorMessage('한 글자 이상의 한글만 입력해주세요. (띄어쓰기 불가능)');
      return;
    } try {
      const tagData = {
        tagName,
      }
      dispatch(createTag(tagData, navigate));
    } catch (error) {
      console.error('Error handling Create:', error);
    }
  };
  
  useEffect(() => {
    return () => {
      dispatch(setTagName(''));
    }
  }, []);

  return (
    <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth'>
        <div className='cont_tit_m'>
          <h2>신규 태그 등록</h2>
        </div>
        
        <div className={`${style.form} mt_60`}>
            <form onSubmit={ handleCreate }>
              <div className={style.inputwrap}>
                <input
                  type='text' name='tagName' autoComplete="off"
                  placeholder='태그명을 입력하세요.' 
                  value={tagName}
                  onChange={(e) => {
                    dispatch(setTagName(e.target.value))
                    handleChangeTagName(e.target.value);
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

export default TagEdit;