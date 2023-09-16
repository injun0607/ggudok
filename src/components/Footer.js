import React from 'react'
/* css import */
import style from '../styles/Footer.module.css';


const Footer = () => {
  return (
    <footer>
			<div className={style.footer}>
				<div className='webwidth'>
          Icons by Orion Icon Library - https://orioniconlibrary.com<br />
          Copyright© 2023 Alham. ALL RIGHT RESERVED.
        </div>
			</div>
    </footer>
  )
}

export default Footer;