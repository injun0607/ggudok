import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
// css import
import style from '../../styles/Item.module.css';
// component import
import ErrorItem from '../../components/ErrorItem';
import Loading from '../../components/Loading';
// redux import
import { fetchLikedItemsSuccess } from '../../redux/actions/userActions';

const ITEMS_PER_PAGE = 12;
const NO_IMAGE_URL = '/images/common/noimg.png';

const MyLike = ({ isCheckingLogin }) => {
  let dispatch = useDispatch();
  const likedItems = useSelector(state => state.user.likedItems);
  const [slicedItems, setSlicedItems] = useState([]);
  const [IsResult, setIsResult] = useState(false);
  const [IsLoading, setIsLoading] = useState(true);

  // ************************** 기본 아이템 fetch ***************************
  const fetchLikedItemData = async () => {
    try {
      const response = await axios.get(`/member/favor_subs`);
      const data = response.data.items;

      dispatch(fetchLikedItemsSuccess(data));
      // setSlicedItems([...data]);
    } catch (error) {
      console.error('Error fetching item:', error);
      alert(`서비스를 가져오던 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchLikedItemData();
  }, [dispatch]);
  
  // 결과 유무
  useEffect(() => {
    setIsResult(likedItems.length > 0);
  }, [dispatch, likedItems, isCheckingLogin]);

  return (
    !IsLoading ? (IsResult ?
    <section className={style.section}>
      <div className={style.itemlist}>
        {likedItems.map((item, index) => (
          <Link to={`/subs/detail/${item.id}`} key={index} className={style.item}>
            <div className={style.img}>
              <img src={`${item.image}`} alt={item.name} onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
            </div>
            <div className={style.txt}>
              <h3>{item.name}</h3>
              <div className={style.tag}>
                {
                  item.tags.map((tag, tagindex) => (
                    <p key={tagindex}>{tag.tagName}</p>
                  ))
                }
              </div>
            </div>
          </Link>
        ))}
      </div>
    </section>
    : <ErrorItem />) : <Loading />
  )
}

export default MyLike;