import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch } from 'react-redux';
//redux import
import { fetchCategorySuccess } from '../../redux/actions/admin/adminCategoryActions';

const NO_IMAGE_URL = '/images/common/logo_grey.png';

const AdminReviewInit = () => {
  const dispatch = useDispatch();
 
  // ************************** 추천 아이템 / 헤더 fetch ***************************
  const fetchReviewData = async () => {
    try {
      const response = await axios.post(`/reviewInit`);
      const data = response.data;
    } catch (error) {
      console.error('Error fetching item:', error);
      alert(`리뷰 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  };
  useEffect(() => {
    fetchReviewData();
  }, [dispatch]);
  return ( <></> )
}

export default AdminReviewInit;