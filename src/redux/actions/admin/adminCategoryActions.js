import axios from 'axios';

export const createCategory = (userData, navigate) => async(dispatch) => {
  const {
    categoryName,
    categoryIcon,
    isCategoryNameval,
  } = userData;
  
  if(!isCategoryNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else {
    try{
      const response = await axios.post('/admin/register', {
        categoryName: categoryName,
        categoryIcon: categoryIcon,
      });
      if (response.status === 200) {
        dispatch({ type: 'CREATE_CATEGORY' });
        alert(`신규 카테고리 등록이 완료되었습니다.`)
        navigate('/Admin/Category');
      } else {
        alert(`신규 카테고리 등록을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
      }
    } catch (error) {
      console.log('Error Create New Category :', error);
      alert(`${error}`)
    }
  }
}

export const editCategory = (userData, navigate) => async(dispatch) => {
  const {
    categoryName,
    categoryIcon,
    isCategoryNameval,
  } = userData;
  
  if(!isCategoryNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else {
    try{
      const response = await axios.post('/admin/register', {
        categoryName: categoryName,
        categoryIcon: categoryIcon,
      });
      if (response.status === 200) {
        dispatch({ type: 'EDIT_CATEGORY' });
        alert(`카테고리 수정이 완료되었습니다.`)
        navigate('/Admin/Category');
      } else {
        alert(`카테고리 수정을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
      }
    } catch (error) {
      console.log('Error Create New Category :', error);
      alert(`${error}`)
    }
  }
}
export const setValidCategoryName = (isCategoryNameval) => {
  return {
    type: 'SET_VALID_CATEGORYNAME',
    payload: {
      isCategoryNameval,
    }
  };
};
export const setCategoryName = (categoryName) => {
  return {
    type: 'SET_CATEGORYNAME',
    payload: {
      categoryName,
    }
  };
};