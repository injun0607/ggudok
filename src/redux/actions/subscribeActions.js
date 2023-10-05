import axios from 'axios';

// *************************** 구독하기 ****************************
export const addSubscribe = (userData, navigate) => async (dispatch) => {
  const { subsId, rankLevel } = userData;
  try {
    const response = await axios.post('/subs/buy', {
      subsId,
      rankLevel: rankLevel,
    });
    if (response.status === 200) {
      dispatch({ type: 'ADD_SUBS_SUCCESS', payload: { subsId } });
      navigate('/Mypage/MySubscribe');
      alert(`구독이 완료되었습니다.`);
    } else {
      dispatch({ type: 'ADD_SUBS_FAILURE' });
      alert(`구독 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
    }
  } catch (error) {
    console.log('Error adding subs :', error);
    dispatch({ type: 'ADD_SUBS_FAILURE' });
    alert(`구독 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
  }
};
// *************************** 구독 변경 ****************************
export const editSubscribe = (userData, navigate) => async (dispatch) => {
  const { subsId, rankLevel } = userData;
  try {
    const response = await axios.post('/subs/buy', {
      subsId: subsId,
      rankLevel: rankLevel,
    });
    if (response.status === 200) {
      dispatch({ type: 'EDIT_SUBS_SUCCESS', payload: { subsId } });
      navigate('/Mypage/MySubscribe');
      alert(`구독서비스가 변경되었습니다.`);
    } else {
      dispatch({ type: 'EDIT_SUBS_FAILURE' });
      alert(`구독 변경 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
    }
  } catch (error) {
    console.log('Error editing subs :', error);
    dispatch({ type: 'ADD_SUBS_FAILURE' });
    alert(`구독 변경 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
  }
};
// *************************** 구독 해지 ****************************
export const deleteSubscribe = (itemId, navigate) => async (dispatch) => {
  const { subsId } = itemId;
  try {
    const response = await axios.post('/subs/buy_cancel', { subsId });
    if (response.status === 200) {
      dispatch({ type: 'DELETE_SUBS_SUCCESS', payload: { subsId } });
      navigate('/Mypage/MySubscribe');
      alert(`구독이 해지되었습니다.`);
    } else {
      dispatch({ type: 'DELETE_SUBS_FAILURE' });
      alert(`구독 해지 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
    }
  } catch (error) {
    console.log('subsId : ', subsId);
    console.log('Error deleting subs :', error);
    dispatch({ type: 'DELETE_SUBS_FAILURE' });
    alert(`구독 해지 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`);
  }
};