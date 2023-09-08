import React from 'react';
// css import
import style from '../../styles/Mypage.module.css'

const MySubscribe = () => {

  return (
  <section className={`${style.section} ${style.subscribewrap}`}>
    <p className='txt_right main_clr02 mb_10'>* 한 달 기준 구독내역입니다.</p>
    <div className={style.overallprice}>
      <p className={style.md}>이번 달 총 요금은 <span className={style.lg}>38000</span>원입니다.</p>
      <img src='/images/icons/message.svg' alt='영상' />
    </div>
    <div className={style.subscribes}>
      <div className={style.tit}>
        <div className={style.name}>
          <img src='/images/icons/ott.svg' alt='영상' />
          <h3>영상</h3>
        </div>
        <div className={style.price}>
            <p className={style.sm}>총 요금</p>
            <p className={style.md}><span className={style.lg}>20,900</span>원</p>
        </div>
      </div>
      <div className={style.cont}>
        <article className={style.subscribe}>
          <h4>TVing 이용권</h4>
          <div className={style.tag}>
            <p>TVing</p>
            <p>5,900원</p>
          </div>
        </article>
        <article className={style.subscribe}>
          <h4>넷플릭스 베이직이용권</h4>
          <div className={style.tag}>
            <p>넷플릭스</p>
            <p>무료체험중</p>
          </div>
        </article>
      </div>
    </div>
    <div className={style.subscribes}>
      <div className={style.tit}>
        <div className={style.name}>
          <img src='/images/icons/food.svg' alt='식품' />
          <h3>식품</h3>
        </div>
        <div className={style.price}>
            <p className={style.sm}>총 요금</p>
            <p className={style.md}><span className={style.lg}>20,900</span>원</p>
        </div>
      </div>
      <div className={style.cont}>
        <article className={style.subscribe}>
          <h4>GS편의점 도시락 60%할인권</h4>
          <div className={style.tag}>
            <p>GS25</p>
            <p>9,900원</p>
          </div>
        </article>
        <article className={style.subscribe}>
          <h4>샐러드 24개</h4>
          <div className={style.tag}>
            <p>프레시풋</p>
            <p>29,900원</p>
          </div>
        </article>
        <article className={style.subscribe}>
          <h4>반찬 12팩</h4>
          <div className={style.tag}>
            <p>넷플릭스</p>
            <p>15,000원</p>
          </div>
        </article>
        <article className={style.subscribe}>
          <h4>전국 도넛매장 20% 할인권</h4>
          <div className={style.tag}>
            <p>크림드벨리</p>
            <p>3,000원</p>
          </div>
        </article>
        <article className={style.subscribe}>
          <h4>밀키트 5개</h4>
          <div className={style.tag}>
            <p>푸드샵</p>
            <p>10,000원</p>
          </div>
        </article>
      </div>
    </div>
    <div className={style.subscribes}>
      <div className={style.tit}>
        <div className={style.name}>
          <img src='/images/icons/alcohol.svg' alt='주류' />
          <h3>주류</h3>
        </div>
        <div className={style.price}>
            <p className={style.sm}>총 요금</p>
            <p className={style.md}><span className={style.lg}>20,900</span>원</p>
        </div>
      </div>
      <div className={style.cont}>
        <article className={style.subscribe}>
          <h4>국산 증류주 2병</h4>
          <div className={style.tag}>
            <p>GS25</p>
            <p>20,900원</p>
          </div>
        </article>
      </div>
    </div>
    <div className={style.subscribes}>
      <div className={style.tit}>
        <div className={style.name}>
          <img src='/images/icons/extra.svg' alt='패션잡화' />
          <h3>패션잡화</h3>
        </div>
        <div className={style.price}>
            <p className={style.sm}>총 요금</p>
            <p className={style.md}><span className={style.lg}>20,900</span>원</p>
        </div>
      </div>
      <div className={style.cont}>
        <article className={style.subscribe}>
          <h4>양말 5켤레</h4>
          <div className={style.tag}>
            <p>힙스테이션</p>
            <p>5,900원</p>
          </div>
        </article>
        <article className={style.subscribe}>
          <h4>셔츠 2벌</h4>
          <div className={style.tag}>
            <p>하모니스</p>
            <p>15,900원</p>
          </div>
        </article>
      </div>
    </div>
  </section>
  )
}

export default MySubscribe;