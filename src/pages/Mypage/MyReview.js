import React from 'react';
// css import
import style from '../../styles/Mypage.module.css'

const NO_IMAGE_URL = '/images/common/noimg.png';

const MyReview = () => {

  return (
  <section className={`${style.section} ${style.ratingwrap}`}>
    <div className={style.ratings}>
      <div className={style.rating}>
        <div className={style.service}>
          <div className={style.left}>
            <div className={style.circle}>
              <img src='../images/common/' alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
            </div>
            <h3 className={style.name}>넷플릭스</h3>
          </div>
          <div className={style.right}>
            <button className={style.edit} type='button'>수정</button>
            <button className={style.delete} type='button'>삭제</button>
          </div>
        </div>
        <div className={style.txt}>
          <div className={style.starrating}>
            <div className={style.star}>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
            </div>
          </div>
          <p className={style.review}>우리가 낳은 사랑은 버려진다 그래 이별은 너와 나의 책임이야 사랑 앞에 우린 죄인이야 울지마 바보야 나 정말 괜찮아</p>
        </div>
      </div>
      <article className={style.rating}>
        <div className={style.service}>
          <div className={style.left}>
            <div className={style.circle}>
              <img src='../images/common/' alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
            </div>
            <h3 className={style.name}>넷플릭스</h3>
          </div>
          <div className={style.right}>
            <button className={style.edit} type='button'>수정</button>
            <button className={style.delete} type='button'>삭제</button>
          </div>
        </div>
        <div className={style.txt}>
          <div className={style.starrating}>
            <div className={style.star}>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
            </div>
          </div>
          <p className={style.review}>넷플릭스 구독서비스는 현대의 엔터테인먼트 소비에 혁신적인 플랫폼 중 하나입니다.<br /><br />1. 다양한 장르와 양질의 콘텐츠가 풍부하게 제공되어 시청자에게 다양한 선택권을 제공합니다.<br />2. 오리지널 콘텐츠의 퀄리티와 다양성은 정말 놀랍습니다.<br />3. 사용자 인터페이스가 직관적이고 사용하기 쉽습니다.<br />4. 다양한 장치에서 스트리밍이 가능하며, 오프라인 시청도 지원해 편리합니다.<br />5. 자동 재생 기능으로 연속해서 에피소드를 시청할 때 편리합니다.<br />6. 개별 프로필을 설정하여 가족 구성원 각자의 관심사에 맞춰 추천 콘텐츠를 받을 수 있습니다.<br />7. 콘텐츠가 자주 업데이트되어 신규 콘텐츠를 기대할 수 있습니다.<br />8. 광고 없이 끊임없는 스트리밍을 즐길 수 있습니다.<br />9. 해지가 쉬우며 유연한 요금제를 제공해 가입자에게 다양한 선택권을 제공합니다.<br />10.넷플릭스는 시청자들에게 풍요로운 엔터테인먼트 경험을 제공하여 많은 사람들이 그 매력에 빠져들고 있습니다.</p>
        </div>
      </article>
      <article className={style.rating}>
        <div className={style.service}>
          <div className={style.left}>
            <div className={style.circle}>
              <img src='../images/common/' alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
            </div>
            <h3 className={style.name}>넷플릭스</h3>
          </div>
          <div className={style.right}>
            <button className={style.edit} type='button'>수정</button>
            <button className={style.delete} type='button'>삭제</button>
          </div>
        </div>
        <div className={style.txt}>
          <div className={style.starrating}>
            <div className={style.star}>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
            </div>
          </div>
          <p className={style.review}>우리가 낳은 사랑은 버려진다 그래 이별은 너와 나의 책임이야 사랑 앞에 우린 죄인이야 울지마 바보야 나 정말 괜찮아</p>
        </div>
      </article>
      <article className={style.rating}>
        <div className={style.service}>
          <div className={style.left}>
            <div className={style.circle}>
              <img src='../images/common/' alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
            </div>
            <h3 className={style.name}>넷플릭스</h3>
          </div>
          <div className={style.right}>
            <button className={style.edit} type='button'>수정</button>
            <button className={style.delete} type='button'>삭제</button>
          </div>
        </div>
        <div className={style.txt}>
          <div className={style.starrating}>
            <div className={style.star}>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
              <span className={`material-icons ${style.starActive}`}>star</span>
            </div>
          </div>
          <p className={style.review}>우리가 낳은 사랑은 버려진다 그래 이별은 너와 나의 책임이야 사랑 앞에 우린 죄인이야 울지마 바보야 나 정말 괜찮아</p>
        </div>
      </article>
    </div>
  </section>
  )
}

export default MyReview;