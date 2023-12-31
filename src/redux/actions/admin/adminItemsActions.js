import axios from 'axios';
export const fetchItemsSuccess = (items) => {
  return {
    type: 'FETCH_ITEM_SUCCESS',
    payload: items,
  };
}
export const setSelectedCategory = (selectedCategory) => {
  return {
    type: 'SELECTED_CATEGORY',
    payload: selectedCategory,
  };
};
export const filterItems = (filteredItems) => {
  return {
    type: 'FILTER_ITEM',
    payload: filteredItems,
  };
};
export const pagingItems = (pagedItems) => {
  return {
    type: 'PAGING_ITEM',
    payload: pagedItems,
  };
};
export const createItem = (itemData, navigate) => async(dispatch) => {
  try{
    const response = await axios.post('/admin/subs/register', itemData);
    if (response.status === 200) {
      dispatch({ type: 'CREATE_ITEM' });
      alert(`신규 구독서비스 등록이 완료되었습니다.`)
      navigate('/Admin/Items');
    } else {
      alert(`신규 구독서비스 등록을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error Create New Items :', error);
    alert(`${error}`)
  }
}
export const editItem = (subsId, itemData, navigate) => async(dispatch) => {
  try{
    const response = await axios.post(`/admin/subs/update/${subsId}`, itemData);
    if (response.status === 200) {
      dispatch({ type: 'EDIT_ITEM_SUCCESS' });
      alert(`구독서비스 수정이 완료되었습니다.`)
      navigate('/Admin/Items');
    } else {
      dispatch({ type: 'EDIT_ITEM_FAILURE' });
      alert(`구독서비스 수정을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
    }
  } catch (error) {
    dispatch({ type: 'EDIT_ITEM_FAILURE' });
    console.log('Error Edit New Items :', error);
    alert(`${error}`)
  }
}
export const deleteItem = (subsId) => async (dispatch) => {
  try {
    const response = await axios.post(`/admin/subs/delete`, subsId);
    if (response.status === 200) {
      dispatch({type: 'DELETE_ITEM_SUCCESS'});
      alert(`구독서비스 삭제가 완료되었습니다.`);
      window.location.reload();
    } else {
      dispatch({ type: 'DELETE_ITEM_FAILURE' });
      alert(`구독서비스 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error Deleting Item :', error);
    dispatch({ type: 'DELETE_ITEM_FAILURE' });
    alert(`구독서비스 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};