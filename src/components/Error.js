import React from 'react'
import { Link, useNavigate } from 'react-router-dom';

const Error = ({ message }) => {
  let navigate = useNavigate();

	return (
		<div className='webwidth'>
			<div className='errorpage'>
			<span className='material-icons'>warning</span>
				<p>{ message }</p>
				<Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
			</div>
		</div>
	)
}

export default Error;