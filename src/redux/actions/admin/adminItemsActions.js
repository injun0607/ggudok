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
  const {
    subsName,
    categoryId,
    tagList,
    subsImage,
    subsRankList,
    isItemNameval,
  } = itemData;
  
  if(!isItemNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else if(!categoryId){ alert('카테고리를 선택하세요.')
} else if(!subsRankList){ alert('요금제 기본값을 입력하세요.')
  } else {
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
}
export const editItem = (itemData, navigate) => async(dispatch) => {
  const {
    itemId,
    itemName,
    itemEng,
    itemImage,
    isItemNameval,
    isItemEngval,
  } = itemData;
  
  if(!isItemNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else if(!isItemEngval){ alert('한 글자 이상의 영문만 입력해주세요.')
  } else {
    try{
      const response = await axios.post(`/admin/item/update/${itemId}`, {
        itemId: itemId,
        itemName: itemName,
        itemEng: itemEng,
        itemImage: itemImage,
      });
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
}
export const setValidItemName = (isItemNameval) => {
  return {
    type: 'SET_VALID_ITEMNAME',
    payload: isItemNameval,
  };
};
export const setItemName = (itemName) => {
  return {
    type: 'SET_ITEMNAME',
    payload: itemName,
  };
};
export const setValidItemEng = (isItemEngval) => {
  return {
    type: 'SET_VALID_ITEMENG',
    payload: isItemEngval,
  };
};
export const setItemEng = (itemEng) => {
  return {
    type: 'SET_ITEMENG',
    payload: itemEng,
  };
};
export const setItemImage = (itemImage) => {
  return {
    type: 'SET_ITEMIMAGE',
    payload: itemImage,
  };
};
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