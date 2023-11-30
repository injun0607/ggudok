import React from 'react'
/* css import */
import style from '../../styles/Admin/AdminFooter.module.css';


const AdminFooter = ({ InitReviewData }) => {
  return (
    <footer>
      <button className={style.secretBtn} onClick={ InitReviewData }>InitReviewData</button>
			<div className={style.footer}>
          Icons by Orion Icon Library - https://orioniconlibrary.com&nbsp;&nbsp;&nbsp;<span className='txt_grey02 txt_light'>|</span>&nbsp;&nbsp;&nbsp;Images by Unsplash - https://unsplash.com/ko<br />
          Copyright© 2023 Team Alham. ALL RIGHT RESERVED.
			</div>
    </footer>
  )
}

export default AdminFooter;