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
    eventName,
    eventEng,
    eventImage,
    isEventNameval,
    isEventEngval,
  } = eventData;
  
  if(!isEventNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else if(!isEventEngval){ alert('한 글자 이상의 영문만 입력해주세요.')
  } else {
    try{
      const response = await axios.post('/admin/event/register', {
        eventName: eventName,
        eventEng: eventEng,
        eventImage: eventImage,
      });
      if (response.status === 200) {
        dispatch({ type: 'CREATE_EVENT' });
        alert(`신규 카테고리 등록이 완료되었습니다.`)
        navigate('/Admin/Event');
      } else {
        alert(`신규 카테고리 등록을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
      }
    } catch (error) {
      console.log('Error Create New Event :', error);
      alert(`${error}`)
    }
  }
}
export const editEvent = (eventData, navigate) => async(dispatch) => {
  const {
    eventId,
    eventName,
    eventEng,
    eventImage,
    isEventNameval,
    isEventEngval,
  } = eventData;
  
  if(!isEventNameval){ alert('한 글자 이상의 한글만 입력해주세요.')
  } else if(!isEventEngval){ alert('한 글자 이상의 영문만 입력해주세요.')
  } else {
    try{
      const response = await axios.post(`/admin/event/update/${eventId}`, {
        eventId: eventId,
        eventName: eventName,
        eventEng: eventEng,
        eventImage: eventImage,
      });
      if (response.status === 200) {
        dispatch({ type: 'EDIT_EVENT_SUCCESS' });
        alert(`카테고리 수정이 완료되었습니다.`)
        navigate('/Admin/Event');
      } else {
        dispatch({ type: 'EDIT_EVENT_FAILURE' });
        alert(`카테고리 수정을 실패하였습니다. 다시 작성해주시기 바랍니다.`)
      }
    } catch (error) {
      dispatch({ type: 'EDIT_EVENT_FAILURE' });
      console.log('Error Edit New Event :', error);
      alert(`${error}`)
    }
  }
}
export const setValidEventName = (isEventNameval) => {
  return {
    type: 'SET_VALID_EVENTNAME',
    payload: isEventNameval,
  };
};
export const setEventName = (eventName) => {
  return {
    type: 'SET_EVENTNAME',
    payload: eventName,
  };
};
export const setValidEventEng = (isEventEngval) => {
  return {
    type: 'SET_VALID_EVENTENG',
    payload: isEventEngval,
  };
};
export const setEventEng = (eventEng) => {
  return {
    type: 'SET_EVENTENG',
    payload: eventEng,
  };
};
export const setEventImage = (eventImage) => {
  return {
    type: 'SET_EVENTIMAGE',
    payload: eventImage,
  };
};
export const deleteEvent = (eventData) => async (dispatch) => {
  const { eventId } = eventData;
  try {
    const response = await axios.post(`/admin/event/delete`, { eventId : eventId });
    if (response.status === 200) {
      dispatch({type: 'DELETE_EVENT_SUCCESS'});
      alert(`카테고리 삭제가 완료되었습니다.`);
      window.location.reload();
    } else {
      dispatch({ type: 'DELETE_EVENT_FAILURE' });
      alert(`카테고리 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error Deleting Event :', error);
    dispatch({ type: 'DELETE_EVENT_FAILURE' });
    alert(`카테고리 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};