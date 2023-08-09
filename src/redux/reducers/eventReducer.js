const initialState = {
  events: [
    { name: '프레쉬브루', copy: '매일 마시는 커피, 더 저렴하게 즐기기', image: '/images/slide/mainslide02.png', category: '음료', discount: '한달 간 무료 구독', date: '2023.08.01 - 2023.10.31' },
    { name: '우리식물', copy: '한 달 간 고생한 나를 위한 먼슬리 생화 구독', image: '/images/slide/mainslide03.png', category: '식물', discount: '40% 할인 프로모션 중', date: '2023.08.15 - 2023.11.01' },
    { name: '스마트시네마', copy: '언제 어디서나 최고의 화질로 무제한 감상', image: '/images/common/noimg.png', category: '식품', discount: '2주 무료 이용 시작하기', date: '2023.08.15 - 2023.09.15' },
    { name: '푸드샵', copy: '건강한 식사를 손쉽게, 샐러드 구독으로 더 즐겁게', image: '/images/slide/mainslide01.png', category: '식품', discount: '친구와 함께 1+1!', date: '2023.08.01 - 2023.09.31' },
    { name: '헤비볼륨', copy: '메탈은 더이상 구시대의 유물이 아니다', image: '/images/slide/mainslide03.png', category: '식물', discount: '할인이벤트 종료임박', date: '2023.07.01 - 2023.08.31' },
  ]
}

const eventReducer = (state = initialState, action) => {
  switch (action.type) {
    default:
      return state;
  }
}

export default eventReducer;