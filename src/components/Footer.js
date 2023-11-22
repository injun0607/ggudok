import React from 'react'
/* css import */
import style from '../styles/Footer.module.css';


const Footer = () => {
  return (
    <footer>
			<div className={style.footer}>
				<div className='webwidth'>
          Icons by Orion Icon Library - https://orioniconlibrary.com&nbsp;&nbsp;&nbsp;<span className='txt_grey02 txt_light'>|</span>&nbsp;&nbsp;&nbsp;Images by Unsplash - https://unsplash.com/ko<br />
          Copyright© 2023 Team Alham. ALL RIGHT RESERVED.
        </div>
			</div>
    </footer>
  )
}

export default Footer;