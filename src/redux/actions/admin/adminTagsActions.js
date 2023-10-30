import axios from 'axios';
export const fetchTagSuccess = (tags) => {
  return {
    type: 'FETCH_TAG_SUCCESS',
    payload: tags,
  };
}
export const pagingTag = (pagedTags) => {
  return {
    type: 'PAGING_TAG',
    payload: pagedTags,
  };
};
export const createTag = (userData, navigate) => async(dispatch) => {
  const {
    tagName,
    isTagNameval,
  } = userData;
  
  if(!isTagNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else {
    try{
      const response = await axios.post('/admin/register', {
        tagName: tagName,
      });
      if (response.status === 200) {
        dispatch({ type: 'CREATE_TAG' });
        alert(`신규 태그 등록이 완료되었습니다.`)
        navigate('/Admin/Tag');
      } else {
        alert(`신규 태그 등록을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
      }
    } catch (error) {
      console.log('Error Create New Tag :', error);
      alert(`${error}`)
    }
  }
}
export const editTag = (userData, navigate) => async(dispatch) => {
  const {
    tagName,
    isTagNameval,
  } = userData;
  
  if(!isTagNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else {
    try{
      const response = await axios.post('/admin/register', {
        tagName: tagName,
      });
      if (response.status === 200) {
        dispatch({ type: 'EDIT_TAG_SUCCESS' });
        alert(`태그 수정이 완료되었습니다.`)
        navigate('/Admin/Tag');
      } else {
        dispatch({ type: 'EDIT_TAG_FAILURE' });
        alert(`태그 수정을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
      }
    } catch (error) {
      dispatch({ type: 'EDIT_TAG_FAILURE' });
      console.log('Error Create New Tag :', error);
      alert(`${error}`)
    }
  }
}
export const setValidTagName = (isTagNameval) => {
  return {
    type: 'SET_VALID_TAGNAME',
    payload: {
      isTagNameval,
    }
  };
};
export const setTagName = (tagName) => {
  return {
    type: 'SET_TAGNAME',
    payload: {
      tagName,
    }
  };
};
export const deleteTag = (tagData) => async (dispatch) => {
  const { tagId } = tagData;
  try {
    const response = await axios.post(`/admin/tag/delete`, { tagId : tagId });
    if (response.status === 200) {
      dispatch({type: 'DELETE_TAG_SUCCESS'});
      alert(`태그 삭제가 완료되었습니다.`);
      window.location.reload();
    } else {
      dispatch({ type: 'DELETE_TAG_FAILURE' });
      alert(`태그 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error Deleting Tag :', error);
    dispatch({ type: 'DELETE_TAG_FAILURE' });
    alert(`태그 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};