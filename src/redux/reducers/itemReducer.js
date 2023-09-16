const initialState = {
  items: [
    // Category: 영상
  { id: '1', name: '스마트비디오', category: '영상', tag: ['20대', '30대', '여성'], icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb00.png' },
  { id: '2', name: '클립비젼', category: '영상', tag: '20대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb02.png' },
  { id: '3', name: '월드픽', category: '영상', tag: '30대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb03.png' },
  { id: '4', name: '무비스톤', category: '영상', tag: '30대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb00.png' },
  { id: '5', name: '뷰온', category: '영상', tag: '40대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb05.png' },
  { id: '6', name: '메디앙', category: '영상', tag: '40대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb00.png' },
  { id: '7', name: '플레이캐스트', category: '영상', tag: '50대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb07.png' },
  { id: '8', name: '포토랩', category: '영상', tag: '50대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb00.png' },
  { id: '9', name: '무한티비', category: '영상', tag: '60대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb04.png' },
  { id: '10', name: '스마트시네마', category: '영상', tag: '60대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb00.png' },
  { id: '11', name: '스마트비디오', category: '영상', tag: '20대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb01.png' },
  { id: '12', name: '클립비젼', category: '영상', tag: '20대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb00.png' },
  { id: '13', name: '월드픽', category: '영상', tag: '30대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb03.png' },
  { id: '14', name: '무비스톤', category: '영상', tag: '30대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb00.png' },
  { id: '15', name: '뷰온', category: '영상', tag: '40대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb06.png' },
  { id: '16', name: '메디앙', category: '영상', tag: '40대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb06.png' },
  { id: '17', name: '플레이캐스트', category: '영상', tag: '50대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb07.png' },
  { id: '18', name: '포토랩', category: '영상', tag: '50대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb03.png' },
  { id: '19', name: '무한티비', category: '영상', tag: '60대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb04.png' },
  { id: '20', name: '스마트시네마', category: '영상', tag: '60대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb05.png' },
  { id: '21', name: '스마트비디오', category: '영상', tag: '20대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb01.png' },
  { id: '22', name: '클립비젼', category: '영상', tag: '20대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb02.png' },
  { id: '23', name: '월드픽', category: '영상', tag: '30대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb03.png' },
  { id: '24', name: '무비스톤', category: '영상', tag: '30대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb04.png' },
  { id: '25', name: '뷰온', category: '영상', tag: '40대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb05.png' },
  { id: '26', name: '메디앙', category: '영상', tag: '40대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb06.png' },
  { id: '27', name: '플레이캐스트', category: '영상', tag: '50대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb07.png' },
  { id: '28', name: '포토랩', category: '영상', tag: '50대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb03.png' },
  { id: '29', name: '무한티비', category: '영상', tag: '60대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb04.png' },
  { id: '30', name: '스마트시네마', category: '영상', tag: '60대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb05.png' },

  // Category: 음악
  { id: '31', name: '멜로디데이', category: '음악', tag: '20대 여성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb06.png' },
  { id: '32', name: '뮤직파크', category: '음악', tag: '20대 남성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb07.png' },
  { id: '33', name: '소울플레이', category: '음악', tag: '30대 여성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb01.png' },
  { id: '34', name: '하모니스', category: '음악', tag: '30대 남성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb02.png' },
  { id: '35', name: '힙스테이션', category: '음악', tag: '40대 여성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb03.png' },
  { id: '36', name: '사운드메이트', category: '음악', tag: '40대 남성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb04.png' },
  { id: '37', name: '플레이쳐', category: '음악', tag: '50대 여성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb05.png' },
  { id: '38', name: '소울카페', category: '음악', tag: '50대 남성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb06.png' },
  { id: '39', name: '헤비볼륨', category: '음악', tag: '60대 여성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb07.png' },
  { id: '40', name: '올드멜로디', category: '음악', tag: '60대 남성', icon: '/images/icons/music.svg', image: '/images/thumbnails/thumb01.png' },

  // Category: 식품
  { id: '41', name: '푸드뱅크', category: '식품', tag: '20대 여성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb02.png' },
  { id: '42', name: '맛의달인', category: '식품', tag: '20대 남성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb03.png' },
  { id: '43', name: '크림드벨리', category: '식품', tag: '30대 여성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb04.png' },
  { id: '44', name: '프레시풋', category: '식품', tag: '30대 남성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb05.png' },
  { id: '45', name: '고메마켓', category: '식품', tag: '40대 여성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb06.png' },
  { id: '46', name: '푸드팜', category: '식품', tag: '40대 남성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb07.png' },
  { id: '47', name: '푸드샵', category: '식품', tag: '50대 여성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb01.png' },
  { id: '48', name: '헬시식품', category: '식품', tag: '50대 남성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb02.png' },
  { id: '49', name: '맛나랑', category: '식품', tag: '60대 여성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb03.png' },
  { id: '50', name: '알뜰식품', category: '식품', tag: '60대 남성', icon: '/images/icons/food.svg', image: '/images/thumbnails/thumb04.png' },

  // Category: 건강
  { id: '51', name: '헬스킹', category: '건강', tag: '20대 여성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb31.png' },
  { id: '52', name: '웰니스힐', category: '건강', tag: '20대 남성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb32.png' },
  { id: '53', name: '리커버업', category: '건강', tag: '30대 여성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb33.png' },
  { id: '54', name: '비타민케어', category: '건강', tag: '30대 남성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb34.png' },
  { id: '55', name: '라이프프리', category: '건강', tag: '40대 여성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb35.png' },
  { id: '56', name: '비건라이프', category: '건강', tag: '40대 남성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb36.png' },
  { id: '57', name: '스포츠클럽', category: '건강', tag: '50대 여성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb37.png' },
  { id: '58', name: '피트업', category: '건강', tag: '50대 남성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb38.png' },
  { id: '59', name: '알로하웰', category: '건강', tag: '60대 여성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb39.png' },
  { id: '60', name: '포인트웰', category: '건강', tag: '60대 남성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb40.png' },
  
  // Category: 음료
  { id: '61', name: '프레쉬브루', category: '음료', tag: '20대 여성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb41.png' },
  { id: '62', name: '슈퍼스무디', category: '음료', tag: '20대 남성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb42.png' },
  { id: '63', name: '탄산아트', category: '음료', tag: '30대 여성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb43.png' },
  { id: '64', name: '커피베이', category: '음료', tag: '30대 남성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb44.png' },
  { id: '65', name: '오가닉티', category: '음료', tag: '40대 여성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb45.png' },
  { id: '66', name: '프루티칵테일', category: '음료', tag: '40대 남성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb46.png' },
  { id: '67', name: '힐링티', category: '음료', tag: '50대 여성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb47.png' },
  { id: '68', name: '밀크오션', category: '음료', tag: '50대 남성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb48.png' },
  { id: '69', name: '프리미엄스무디', category: '음료', tag: '60대 여성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb49.png' },
  { id: '70', name: '빙그레이', category: '음료', tag: '60대 남성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb50.png' },

  //식물
  { id: '71', name: '우리식물', category: '식물', tag: '20대 여성', icon: '/images/icons/plant.svg', image: '/images/thumbnails/thumb41.png' },
  { id: '72', name: '그린그레스', category: '식물', tag: '30대 남성', icon: '/images/icons/plant.svg', image: '/images/thumbnails/thumb42.png' },
  ],
  bestitems: [
    { id: '61', name: '프레쉬브루', category: '음료', tag: '20대 여성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb41.png' },
    { id: '62', name: '슈퍼스무디', category: '음료', tag: '20대 남성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb42.png' },
    { id: '1', name: '오래오래', category: '반려동물', tag: '30대 여성', icon: '/images/icons/love.svg', image: '/images/thumbnails/thumb04.png' },
    { id: '1', name: '우리두', category: '음료', tag: '30대 남성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb07.png' },
    { id: '54', name: '비타민케어', category: '건강', tag: '30대 남성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb34.png' },
    { id: '1', name: '오래오래', category: '반려동물', tag: '30대 여성', icon: '/images/icons/love.svg', image: '/images/thumbnails/thumb04.png' },
    { id: '1', name: '우리두', category: '음료', tag: '30대 남성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb07.png' },
    { id: '55', name: '라이프프리', category: '건강', tag: '40대 여성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb35.png' },
    { id: '56', name: '비건라이프', category: '건강', tag: '40대 남성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb36.png' },
    { id: '57', name: '스포츠클럽', category: '건강', tag: '50대 여성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb37.png' },
    { id: '5', name: '뷰온', category: '영상', tag: '40대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb05.png' },
    { id: '6', name: '메디앙', category: '영상', tag: '40대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb00.png' },
    { id: '7', name: '플레이캐스트', category: '영상', tag: '50대 여성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb07.png' },
    { id: '8', name: '포토랩', category: '영상', tag: '50대 남성', icon: '/images/icons/ott.svg', image: '/images/thumbnails/thumb00.png' },
    { id: '54', name: '비타민케어', category: '건강', tag: '30대 남성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb34.png' },
    { id: '1', name: '오래오래', category: '반려동물', tag: '30대 여성', icon: '/images/icons/love.svg', image: '/images/thumbnails/thumb04.png' },
    { id: '1', name: '우리두', category: '음료', tag: '30대 남성', icon: '/images/icons/drink.svg', image: '/images/thumbnails/thumb07.png' },
    { id: '55', name: '라이프프리', category: '건강', tag: '40대 여성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb35.png' },
    { id: '56', name: '비건라이프', category: '건강', tag: '40대 남성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb36.png' },
    { id: '57', name: '스포츠클럽', category: '건강', tag: '50대 여성', icon: '/images/icons/health.svg', image: '/images/thumbnails/thumb37.png' },
  ],
  likeditems: [
  ],
  noResult: true,
};

const itemReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_LIKED_ITEM':
      const existingLikedItem = state.likeditems.find(
        (likeditem) => likeditem.id === action.payload.id
      );
      if (existingLikedItem) {
        const updatedLikedItem = state.likeditems.filter(
          (likeditem) => likeditem.id !== action.payload.id
        );
        return {
          ...state,
          likeditems: updatedLikedItem,
        };
      }
      else {
        return {
          ...state,
          likeditems: [...state.likeditems, action.payload],
        };
      };
    case 'SET_NORESULT':
      return {
        ...state,
        noResult: action.payload,
      };
    default:
      return state;
  }
};

export default itemReducer;
