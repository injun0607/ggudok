import React, {useState} from 'react';
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../styles/Auth.module.css'

const Contactus = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [submitName, setSubmitName] = useState('');
  const [submitEmail, setSubmitEmail] = useState('');
  const [title, setTitle] = useState('');
  const [contents, setContents] = useState('');

  const [IsNameVal, setIsNameVal] = useState(false);
  const [isEmailVal, setValidEmail] = useState(false);
  const [errorMessageName, setErrorMessageName] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  // 이름 유효성 검사
  const handleChangeName = (newName) => {
    const regName = /^[가-힣]{1,}$/;
    const isNameReg = regName.test(newName);

    if (!isNameReg){
      setIsNameVal(false);
    } else {
      setIsNameVal(true);
    }
  
    setSubmitName(newName);
  }
  // 이메일 유효성 검사
  const handleChangeEmail = (newEmail) => {
    const regEmail = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,}$/;
    const isEmailReg = regEmail.test(newEmail);
    if (!isEmailReg){
      setErrorMessage('올바른 이메일 주소를 입력하세요.');
    } else {
      setErrorMessage('');
    }
    setValidEmail(isEmailReg);
  }

  // 이메일 전송 함수
  const contact = async(submitData) => {
    try {
      const response = await axios.post('/contact_us', submitData);
      if (response.status === 200) {
        alert(`개발자에게 성공적으로 메일을 전송했습니다! 야호!`);
        window.location.reload();
      } else {
        alert(`메일 전송 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
      }
    } catch (error) {
      console.log('Error mailing :', error);
      alert(`메일 전송 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
    }
  }; 
  const handleContact = (e) => {
    e.preventDefault();
    const submitData = {
      submitName: submitName,
      submitEmail: submitEmail,
      title: title,
      contents: contents,
    };
    if (submitName === '') {
      alert('성함을 입력하세요.')
    } else if (submitEmail === '') {
      alert('이메일 주소를 입력하세요.')
    } else if (!isEmailVal) {
      alert('올바른 이메일 주소를 입력하세요.')
    } else if (title === '') {
      alert('제목을 입력하세요.')
    } else if (contents === '') {
      alert('내용을 입력하세요.')
    } else {
      contact(submitData);
    }
  };

  return (
    <section className={`${style.contactus} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>언제든지 열려 있어요</h2></div>
        <div className={style.form}>
          <form onSubmit={ handleContact }>
            <div className={style.inputwrap}>
              <div className={style.inputHalf}>
                <input type='text' name='submitName' placeholder='성함을 입력하세요.'
                  value={submitName}
                  onChange={(e) => {
                    setSubmitName(e.target.value)
                    handleChangeName(e.target.value);
                  }} />
                {errorMessageName && <p style={{ marginTop: '8px', color: 'red' }}>{errorMessageName}</p>}
              </div>
              <div className={style.inputHalf}>
                <input type='text' name='submitEmail' placeholder='이메일 주소를 입력하세요.'
                  value={submitEmail}
                  onChange={(e) => {
                    setSubmitEmail(e.target.value)
                    handleChangeEmail(e.target.value);
                  }} />
                {errorMessage && <p style={{ marginTop: '8px', color: 'red' }}>{errorMessage}</p>}
              </div>
              <input type='text' name='title' placeholder='제목을 입력하세요.'
                  value={title}
                  onChange={(e) => { setTitle(e.target.value) }} />
              <textarea type='text' name='contents' placeholder='내용을 입력하세요.'
                  value={contents}
                  onChange={(e) => { setContents(e.target.value) }} />
            </div>
            <div className={style.doublebutton}>
              <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
              <button type='submit' className='btn btn_full'>메일 보내기</button>
            </div>
          </form>
        </div>
      </div>
    </section>
  )
}

export default Contactus;