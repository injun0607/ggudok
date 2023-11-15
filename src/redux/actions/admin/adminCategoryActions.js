import axios from 'axios';
export const fetchCategorySuccess = (categories) => {
  return {
    type: 'FETCH_CATEGORY_SUCCESS',
    payload: categories,
  };
}
export const pagingCategory = (pagedCategories) => {
  return {
    type: 'PAGING_CATEGORY',
    payload: pagedCategories,
  };
};
export const createCategory = (categoryData, navigate) => async(dispatch) => {
  const {
    categoryName,
    categoryEng,
    categoryImage,
    isCategoryNameval,
    isCategoryEngval,
  } = categoryData;
  
  if(!isCategoryNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else if(!isCategoryEngval){ alert('한 글자 이상의 영문만 입력해주세요.')
  } else {
    try{
      const response = await axios.post('/admin/category/register', {
        categoryName: categoryName,
        categoryEng: categoryEng,
        categoryImage: categoryImage,
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
export const editCategory = (categoryData, navigate) => async(dispatch) => {
  const {
    categoryId,
    categoryName,
    categoryEng,
    categoryImage,
    isCategoryNameval,
    isCategoryEngval,
  } = categoryData;
  
  if(!isCategoryNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else if(!isCategoryEngval){ alert('한 글자 이상의 영문만 입력해주세요.')
  } else {
    try{
      const response = await axios.post(`/admin/category/update/${categoryId}`, {
        categoryId: categoryId,
        categoryName: categoryName,
        categoryEng: categoryEng,
        categoryImage: categoryImage,
      });
      if (response.status === 200) {
        dispatch({ type: 'EDIT_CATEGORY_SUCCESS' });
        alert(`카테고리 수정이 완료되었습니다.`)
        navigate('/Admin/Category');
      } else {
        dispatch({ type: 'EDIT_CATEGORY_FAILURE' });
        alert(`카테고리 수정을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
      }
    } catch (error) {
      dispatch({ type: 'EDIT_CATEGORY_FAILURE' });
      console.log('Error Edit New Category :', error);
      alert(`${error}`)
    }
  }
}
export const setValidCategoryName = (isCategoryNameval) => {
  return {
    type: 'SET_VALID_CATEGORYNAME',
    payload: isCategoryNameval,
  };
};
export const setCategoryName = (categoryName) => {
  return {
    type: 'SET_CATEGORYNAME',
    payload: categoryName,
  };
};
export const setValidCategoryEng = (isCategoryEngval) => {
  return {
    type: 'SET_VALID_CATEGORYENG',
    payload: isCategoryEngval,
  };
};
export const setCategoryEng = (categoryEng) => {
  return {
    type: 'SET_CATEGORYENG',
    payload: categoryEng,
  };
};
export const setCategoryImage = (categoryImage) => {
  return {
    type: 'SET_CATEGORYIMAGE',
    payload: categoryImage,
  };
};
export const deleteCategory = (categoryData) => async (dispatch) => {
  const { categoryId } = categoryData;
  try {
    const response = await axios.post(`/admin/category/delete`, { categoryId : categoryId });
    if (response.status === 200) {
      dispatch({type: 'DELETE_CATEGORY_SUCCESS'});
      alert(`카테고리 삭제가 완료되었습니다.`);
      window.location.reload();
    } else {
      dispatch({ type: 'DELETE_CATEGORY_FAILURE' });
      alert(`카테고리 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error Deleting Category :', error);
    dispatch({ type: 'DELETE_CATEGORY_FAILURE' });
    alert(`카테고리 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};