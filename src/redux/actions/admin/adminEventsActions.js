import axios from 'axios';
export const fetchEventSuccess = (events) => {
  return {
    type: 'FETCH_EVENT_SUCCESS',
    payload: events,
  };
}
export const pagingEvent = (pagedEvents) => {
  return {
    type: 'PAGING_EVENT',
    payload: pagedEvents,
  };
};
export const createEvent = (eventData, navigate) => async(dispatch) => {
  const {
    subsId,
    eventInfoTag,
    eventInfo,
    eventImage: updatedImageUrl,
    eventDateStart,
    eventDateEnd,
    isValid,
  } = eventData;
  
  try{
    const response = await axios.post('/admin/event/register', {
      subsId,
      infoTag: eventInfoTag,
      info: eventInfo,
      eventImage: updatedImageUrl,
      startDate: eventDateStart,
      endDate: eventDateEnd,
      valid: isValid,
    });
    if (response.status === 200) {
      dispatch({ type: 'CREATE_EVENT' });
      alert(`신규 이벤트 등록이 완료되었습니다.`)
      navigate('/Admin/Events');
    } else {
      alert(`신규 이벤트 등록을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error Create New Event :', error);
    alert(`${error}`)
  }
}
export const editEvent = (eventData, navigate) => async(dispatch) => {
  const {
    eventId,
    subsId,
    eventInfoTag,
    eventInfo,
    eventImage: updatedImageUrl,
    eventDateStart,
    eventDateEnd,
    isValid,
  } = eventData;
  
try{
    const response = await axios.post(`/admin/event/update`, {
      eventId,
      subsId,
      infoTag: eventInfoTag,
      info: eventInfo,
      eventImage: updatedImageUrl,
      startDate: eventDateStart,
      endDate: eventDateEnd,
      valid: isValid,
    });
    if (response.status === 200) {
      dispatch({ type: 'EDIT_EVENT_SUCCESS' });
      alert(`이벤트 수정이 완료되었습니다.`)
      navigate('/Admin/Events');
    } else {
      dispatch({ type: 'EDIT_EVENT_FAILURE' });
      alert(`이벤트 수정을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
    }
  } catch (error) {
    dispatch({ type: 'EDIT_EVENT_FAILURE' });
    console.log('Error Edit New Event :', error);
    alert(`${error}`)
  }
}
export const deleteEvent = (eventData) => async (dispatch) => {
  const { eventId } = eventData;
  try {
    const response = await axios.post(`/admin/event/delete`, { eventId : eventId });
    if (response.status === 200) {
      dispatch({type: 'DELETE_EVENT_SUCCESS'});
      alert(`이벤트 삭제가 완료되었습니다.`);
      window.location.reload();
    } else {
      dispatch({ type: 'DELETE_EVENT_FAILURE' });
      alert(`이벤트 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error Deleting Event :', error);
    dispatch({ type: 'DELETE_EVENT_FAILURE' });
    alert(`이벤트 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};