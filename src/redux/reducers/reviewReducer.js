const initialState = {
  reviews: [
    { userid: '1', username:'달달이', itemid: '1', itemname: '스마트비디오', star: [true, false, false, false, false], contents: '스마트비디오는 혁신적인 OTT(Over-The-Top) 구독 서비스 기업으로, 최신 영화, 드라마, TV 프로그램, 애니메이션 등 다양한 콘텐츠를 제공하여 고객들에게 풍부한 시청 경험을 선사합니다..',},
    { userid: '2', username:'해변의 달달이', itemid: '1', itemname: '스마트비디오', star: [true, true, true, false, false], contents: '우리가 낳은 사랑은 버려진다 그래 이별은 너와 나의 책임이야 사랑 앞에 우린 죄인이야 울지마 바보야 나 정말 괜찮아',},
    { userid: '3', username:'징징이', itemid: '1', itemname: '스마트비디오', star: [true, true, true, false, false], contents: '멀티 플랫폼에서 사용 가능한 통합된 서비스를 제공합니다. 고객의 편의를 최우선으로 생각하여 다양한 결제 옵션과 유연한 요금제를 제공하며, 탁월한 고객 서비스 품질로 항상 사용자들의 만족을 위해 노력하고 있습니다.',},
    { userid: '4', username:'스폰지밥', itemid: '1', itemname: '스마트비디오', star: [true, false, false, false, false], contents: '스마트비디오 구독서비스는 현대의 엔터테인먼트 소비에 혁신적인 플랫폼 중 하나입니다.',},
    { userid: '5', username:'달달이', itemid: '1', itemname: '스마트비디오', star: [true, true, true, true, true], contents: '스마트비디오는 혁신적인 OTT(Over-The-Top) 구독 서비스 기업으로, 최신 영화, 드라마, TV 프로그램, 애니메이션 등 다양한 콘텐츠를 제공하여 고객들에게 풍부한 시청 경험을 선사합니다..',},
    { userid: '6', username:'해변의 달달이', itemid: '1', itemname: '스마트비디오', star: [true, true, true, false, false], contents: '우리가 낳은 사랑은 버려진다 그래 이별은 너와 나의 책임이야 사랑 앞에 우린 죄인이야 울지마 바보야 나 정말 괜찮아',},
    { userid: '7', username:'숑숑이', itemid: '1', itemname: '스마트비디오', star: [true, true, true, false, false], contents: '멀티 플랫폼에서 사용 가능한 통합된 서비스를 제공합니다. 고객의 편의를 최우선으로 생각하여 다양한 결제 옵션과 유연한 요금제를 제공하며, 탁월한 고객 서비스 품질로 항상 사용자들의 만족을 위해 노력하고 있습니다.',},
    { userid: '8', username:'밥밥디라라', itemid: '1', itemname: '스마트비디오', star: [true, true, false, false, false], contents: '스마트비디오 구독서비스는 현대의 엔터테인먼트 소비에 혁신적인 플랫폼 중 하나입니다.',},
    { userid: '1', username:'달달이', itemid: '2', itemname: '클립비젼', star: [true, false, false, false, false], contents: '스마트비디오는 혁신적인 OTT(Over-The-Top) 구독 서비스 기업으로, 최신 영화, 드라마, TV 프로그램, 애니메이션 등 다양한 콘텐츠를 제공하여 고객들에게 풍부한 시청 경험을 선사합니다..',},
  ],
  reviewModal: false,
};

const reviewReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_REVIEW':
      return {
        ...state,
        selectedOneDepth: action.payload,
      };
    case 'SET_REVIEWMODAL':
      return {
        ...state,
        reviewModal: !state.reviewModal,
      };
    default:
      return state;
  }
};


export default reviewReducer;
