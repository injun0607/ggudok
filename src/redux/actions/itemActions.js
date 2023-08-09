export const categoryToEng = (category) => {
  // 결제 정보를 받아와서 결제 성공 액션 객체를 반환
  return {
    type: 'CATEGORY_TO_ENG',
    payload: {
      category,
    }
  };
};
