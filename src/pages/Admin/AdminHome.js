import React, { useEffect } from 'react';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { Pie } from 'react-chartjs-2';
// css import
import style from '../../styles/Admin/AdminLayout.module.css';
//redux import
import { useDispatch } from 'react-redux';
import { setAdminLayout } from '../../redux/actions/adminLayoutActions';

ChartJS.register(ArcElement, Tooltip, Legend);

const AdminHome = () => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(setAdminLayout(true)); // Admin 내에서는 isadminLayout을 true로 설정
    return () => {
      dispatch(setAdminLayout(false)); // 컴포넌트 언마운트 시에는 다시 false로 설정
    };
  }, [dispatch]);

  return (
    <section className={style.home}>
      <article className={style.dashbox}>
        <div className={style.dashtit}>
          <h2>구독통계</h2>
        </div>
        <div className={style.dashcont}>
          <Pie data={dataAge} />
        </div>
      </article>
      <article className={style.dashbox}>
        <div className={style.dashtit}>
          <h2>구독통계</h2>
        </div>
        <div className={style.dashcont}>
          <Pie data={dataAge} />
        </div>
      </article>
      <article className={style.dashbox}>
        <div className={style.dashtit}>
          <h2>구독통계</h2>
        </div>
        <div className={style.dashcont}>
          <Pie data={dataAge} />
        </div>
      </article>
      <article className={style.dashbox}>
        <div className={style.dashtit}>
          <h2>구독통계</h2>
        </div>
        <div className={style.dashcont}>
          <Pie data={dataAge} />
        </div>
      </article>
      <article className={style.dashbox}>
        <div className={style.dashtit}>
          <h2>구독통계</h2>
        </div>
        <div className={style.dashcont}>
          <Pie data={dataAge} />
        </div>
      </article>
      <article className={style.dashbox}>
        <div className={style.dashtit}>
          <h2>구독통계</h2>
        </div>
        <div className={style.dashcont}>
          <Pie data={dataAge} />
        </div>
      </article>
      <article className={style.dashbox}>
        <div className={style.dashtit}>
          <h2>구독통계</h2>
        </div>
        <div className={style.dashcont}>
          <Pie data={dataAge} />
        </div>
      </article>
      <article className={style.dashbox}>
        <div className={style.dashtit}>
          <h2>구독통계</h2>
        </div>
        <div className={style.dashcont}>
          <Pie data={dataAge} />
        </div>
      </article>
    </section>
  )
}

export const dataAge = {
  labels: ['20대 미만', '20대', '30대', '40대', '50대', '60대 이상'],
  datasets: [
    {
      label: '구독자 수',
      data: [12, 19, 3, 5, 2, 3],
      backgroundColor: [
        '#FF724C',
        '#FF8E6F',
        '#FFA194',
        '#FFBDB9',
        '#FFD3CD',
        '#FFEAE6',
        '#FF533C',
        '#FF2A00',
        '#CC1E00',
        '#990F00',
      ],
      borderColor: [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
        'rgba(255, 206, 86, 1)',
        'rgba(75, 192, 192, 1)',
        'rgba(153, 102, 255, 1)',
        'rgba(255, 159, 64, 1)',
      ],
      borderWidth: 0,
    },
  ],
};
export const dataGender = {
  labels: ['여성', '남성'],
  datasets: [
    {
      label: '구독자 수',
      data: [12, 19],
      backgroundColor: [
        '#FDBF50',
        '#80C3FC',
        // '#FFCA7A',
        // '#FFD699',
        // '#FFE3B8',
        // '#FFEDD2',
        // '#FFF6EB',
        // '#FDAB50',
        // '#FEEDC3',
        // '#FBCF75',
        // '#F8BB25',
      ],
      borderColor: [
        'rgba(255, 99, 132, 1)',
        'rgba(54, 162, 235, 1)',
      ],
      borderWidth: 0,
    },
  ],
};

export default AdminHome;