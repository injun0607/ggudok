import axios from 'axios';

export const setIsLoading = (isLoading) => {
  return {
    type: 'SET_IS_LOADING',
    payload: isLoading,
  };
};

export const setRecomBasic = (data) => {
  return {
    type: 'SET_RECOM_BASIC',
    payload: data,
  };
};
export const setRecomCustom = (data) => {
  return {
    type: 'SET_RECOM_CUSTOM',
    payload: data,
  };
};
export const setBestItems = (data) => {
  return {
    type: 'SET_BEST_ITEMS',
    payload: data,
  };
};
export const fetchItemsSuccess = (data) => {
  return {
    type: 'FETCH_ITEMS_SUCCESS',
    payload: data,
  };
};

export const fetchitemDetailSuccess = (data) => {
  return {
    type: 'FETCH_ITEMDETAIL_SUCCESS',
    payload: data,
  };
};

export const filterItem = (filtereditem) => {
  return {
    type: 'FILTER_ITEM',
    payload: filtereditem,
  };
};

export const pagingItem = (pageditems) => {
  return {
    type: 'PAGING_ITEM',
    payload: pageditems,
  };
};

export const likeItemSuccess = () => {
  return {
    type: 'LIKE_ITEM_SUCCESS'
  };
};

export const likeItemFailure = () => {
  return {
    type: 'LIKE_ITEM_FAILURE'
  };
};

export const setIsResult = (isresult) => {
  return {
    type: 'SET_ISRESULT',
    payload: isresult,
  };
};

// 로그인 모달 제어
export const setLoginModal = (modal) => {
  return {
    type: 'SET_LOGINMODAL',
    payload: modal,
  };
};