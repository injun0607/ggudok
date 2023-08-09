import { Link, useNavigate } from 'react-router-dom';

const Error = () => {
  let navigate = useNavigate();

	return (
		<div className='webwidth'>
			<div className='errorpage'>
			<span className='material-icons'>warning</span>
				<p>저런! 존재하지 않는 페이지입니다.</p>
				<Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
			</div>
		</div>
	)
}

export default Error;