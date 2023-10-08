import React from 'react'
import { useDispatch } from 'react-redux';
// css import
import style from '../styles/Item.module.css'
// redux import
import { setQuitModal } from '../redux/actions/userActions';

const Quit = () => {
  const dispatch = useDispatch();

  // 회원탈퇴모달팝업
  const handleQuitModal = () => { dispatch(setQuitModal()) }

  const handleQuit = async(e) => {
    e.preventDefault();
    
  }
  return (
    <section className='modal_conts'>
      <div className='modal_tit'>
			<span className='material-icons'>sentiment_dissatisfied</span>
        <h2>정말 탈퇴하시겠습니까?</h2>
      </div>
      <div className={style.modalCont}>
        <div className={style.doublebutton}>
          <button className='btn_s btn_normal' type='button' onClick={ handleQuit }>탈퇴하기</button>
				  <button className='btn_s btn_full' onClick={ handleQuitModal }>닫기</button>
        </div>
      </div>
    </section>
  )
}

export default Quit;