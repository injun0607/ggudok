import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate, useLocation } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
//redux import
import { createItem } from '../../redux/actions/admin/adminItemsActions';
import { fetchTagSuccess } from '../../redux/actions/admin/adminTagsActions';
import { fetchCategorySuccess } from '../../redux/actions/admin/adminCategoryActions';

const ItemsCreate = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate(); 
  const location = useLocation();

  const categories = useSelector(state => state.adminCategory.categories);
  const tags = useSelector(state => state.adminTags.tags);
  const [rankLevels, setRankLevels] = useState([]);

  const [itemName, setItemName] = useState('');
  const [isItemNameval, setItemNameval] = useState(false);
  const [itemCategory, setItemCategory] = useState('');
  const [itemTags, setItemTags] = useState([]);
  const [itemImage, setItemImage] = useState('');
  const [itemRanks, setItemRanks] = useState([
    {
      rankName: "",
      price: "",
      rankLevel: "",
      contentList: [{content: ""}],
    },
  ]);

  const [errorMessage, setErrorMessage] = useState('');
  const [errorMessageE, setErrorMessageE] = useState('');
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);
  
  // ************************** 카테고리, 태그 fetch ***************************
  const fetchItemsData = async () => {
    try {
      const response = await axios.get(`/admin/subs/register`);
      const data = response.data;

      if(data !== 0){
        dispatch(fetchTagSuccess(data.tagList));
        dispatch(fetchCategorySuccess(data.categoryList));
        setRankLevels(data.rankLevels);
      } else {
        dispatch(fetchTagSuccess([]));
        dispatch(fetchCategorySuccess([]));
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchItemsData();
  }, []);

  // 구독서비스명
  const handleChangeItemName = (newItemName) => {
    const regItemName = /^[가-힣]{1,}$/;
    const isItemNameReg = regItemName.test(newItemName);
  
    if (!isItemNameReg) {
      setErrorMessage('한 글자 이상의 한글만 입력해주세요.');
    } else {
      setErrorMessage('');
    }
    
    setItemNameval(isItemNameReg);
  }
  // 태그 선택
  const handleSelectedTag = (e) => {
    let updateItemTags = [];
    if (itemTags.includes(e.target.value)) {
      updateItemTags = itemTags.filter((tag) => tag !== e.target.value);
      if (updateItemTags.length === 0){ updateItemTags = [] }
    } else {
      updateItemTags = [...itemTags, e.target.value];
    }
    setItemTags(updateItemTags);
  };
  // 이미지 선택
  const convertBlobURLToBlob = async (blobURL) => {
    try {
      const response = await fetch(blobURL);
      const blobData = await response.blob();
      return blobData;
    } catch (error) {
      console.error('Blob URL을 Blob 객체로 변환하는 중에 오류가 발생했습니다:', error);
      throw error;
    }
  };
  const handleImageUpload = async () => {
    try {
      if (itemImage) {
        const blobData = await convertBlobURLToBlob(itemImage);
        const formData = new FormData();
        formData.append('subsImage', blobData);
        
        const response = await axios({
          method: "POST",
          url: `/admin/subs/register/image`,
          charset: 'utf-8',
          headers: {
            "Content-Type": "multipart/form-data",
          },
          data: formData
        })
        
        const imageUrl = response.data.imageUrl;
        setItemImage(imageUrl);
        
        return imageUrl;
      }
    } catch (error) {
      console.error('이미지 업로드 오류:', error);
    }
  };
  // 요금제
  const addRankBox = () => {
    const newRankBox = {
      rankName: "",
      price: "",
      rankLevel: "",
      contentList: [{ content: "" }],
    };
    setItemRanks([...itemRanks, newRankBox]);
  };
  const removeRankBox = (index) => {
    if (itemRanks.length > 1) {
      const updatedRanks = [...itemRanks];
      updatedRanks.splice(index, 1);
      setItemRanks(updatedRanks);
    }
  };
  const handleRankLevelChange = (rankLevel, index) => {
    const updatedRanks = [...itemRanks];
    updatedRanks[index].rankLevel = rankLevel;
    setItemRanks(updatedRanks);
  };
  const handleRankNameChange = (rankName, index) => {
    const updatedName = [...itemRanks];
    updatedName[index].rankName = rankName;
    setItemRanks(updatedName);
  };
  const handleRankPriceChange = (rankPrice, index) => {
    const updatedPrice = [...itemRanks];
    updatedPrice[index].price = rankPrice;
    setItemRanks(updatedPrice);
  };
  const addContent = (index) => {
    const updatedRanks = [...itemRanks];
    updatedRanks[index].contentList.push({ content: "" });
    setItemRanks(updatedRanks);
  };
  const removeContent = (rankIndex, contentIndex) => {
    const updatedRanks = [...itemRanks];
    updatedRanks[rankIndex].contentList.splice(contentIndex, 1);
    setItemRanks(updatedRanks);
  };
  const handleContentChange = (content, rankIndex, contentIndex) => {
    console.log("Before Update:", itemRanks);
    const updatedRanks = [...itemRanks];
    updatedRanks[rankIndex].contentList[contentIndex].content = content;
    setItemRanks(updatedRanks);
    console.log("After Update:", updatedRanks);
  };
  

  // 최종 form submit
  const handleCreate = async(e) => {
    e.preventDefault();
    if(!itemCategory) {
      setErrorMessageE('카테고리를 설정해주세요.');
      return;
    }
    try {
      const updatedImageUrl = await handleImageUpload();

      const tagList = tags
        .filter(tag => itemTags.includes(tag.tagName))
        .map(tag => ({ tagId: tag.tagId }));
  
      const category = categories.find(cat => cat.categoryName === itemCategory);
      if (!category) {
        console.error('Category not found:', itemCategory);
        return;
      }
      const categoryId = category.categoryId;
  
      const itemData = {
        subsName: itemName,
        categoryId: categoryId,
        tagList: tagList,
        subsImage: updatedImageUrl,
        subsRankList: itemRanks,
        isItemNameval,
      };
      console.log('itemData', itemData)
      console.log('subsRankList', itemRanks)
      dispatch(createItem(itemData, navigate));
    } catch (error) {
      console.error('Error handling Create:', error);
    }
  };

  const NO_IMAGE_URL = '/images/common/noimg.png';

  return (
    <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth'>
        <div className='cont_tit_m'>
          <h2>신규 카테고리 등록</h2>
        </div>
        
        <div className={`${style.form} mt_60`}>
            <form onSubmit={ handleCreate }>
              
              <div className={style.userImg}>
                <div className={`${style.circle} ${style.circleSm}`}>
                  <img src={itemImage || NO_IMAGE_URL} alt={itemName} />
                </div>
                <input type="file" id="file" accept="image/*" className='inputFile'
                  onChange={(e) => {
                    const imageFile = e.target.files[0];
                    setItemImage(URL.createObjectURL(imageFile));
                  }}
                />
                <label htmlFor="file">대표이미지 등록</label>
              </div>
              <div className={style.inputwrap}>
                <input
                  type='text' name='itemName' autoComplete="off"
                  placeholder='구독서비스명을 입력하세요.' 
                  value={itemName}
                  onChange={(e) => {
                    setItemName(e.target.value)
                    handleChangeItemName(e.target.value);
                  }} />
                {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
                <select
                  className={style.select}
                  onChange={(e) => setItemCategory(e.target.value)}
                  value={itemCategory}
                >
                  <option value="">카테고리 선택</option>
                    {categories.map((category) => (
                      <option key={category.categoryId} value={category.categoryName}>
                        {category.categoryName}
                      </option>
                    ))}
                </select>
                {errorMessageE && <p style={{ color: 'red' }}>{errorMessageE}</p>}
                <section className={style.formSection}>
                  <div className={style.tit}>
                    <h3>
                      태그
                      <span className={style.multiTxt}>* 중복선택가능</span>
                    </h3>
                  </div>
                  <ul className={`${style.cont} ${style.contTag}`}>
                    {
                      tags.map((tag, index) => (
                        <li className={style.checkInputWrap} key={index}>
                          <input type="checkbox" name='tag' className='checkInput' id={`tag${index}`} value={`${tag.tagName}`} onChange={ handleSelectedTag }
                          checked={itemTags.includes(`${tag.tagName}`)} />
                          <label htmlFor={`tag${index}`} className='checkbox-label'>
                            {tag.tagName}
                          </label>
                        </li>
                      ))
                    }
                  </ul>
                </section>
                <section className={style.formSection}>
                  <div className={style.tit}>
                    <h3>
                      요금제
                    </h3>
                    <button type='button' className='btn_xs btn_full02' onClick={ addRankBox }>추가</button>
                  </div>
                  
                  {
                    itemRanks.map((rank, index) => (
                    <div key={index} className={`${style.cont} ${style.nopadtop}`}>
                      {index > 0 && <button type='button' className={style.closeBtn} onClick={() => removeRankBox(index)}><span className='material-icons'>close</span></button>}
                      <div className={`${style.contInner} ${style.contTriple}`}>
                        <select
                          className={style.select}
                          onChange={(e) => handleRankLevelChange(e.target.value, index)}
                          value={rank.rankLevel}
                        >
                          <option value="">등급 선택</option>
                            {
                              index !== 0 ?
                              rankLevels.map((rankLevel, index) => (
                                <option key={index} value={rankLevel}>
                                  {rankLevel}
                                </option>
                              ))
                              : <option key={index} value={rankLevels[0]}>
                              {rankLevels[0]}
                            </option>
                            }
                        </select>
                        <input
                          type='text' name='itemRankName' autoComplete="off"
                          placeholder='요금제명을 입력하세요.' 
                          value={rank.rankName}
                          onChange={(e) => {
                            handleRankNameChange(e.target.value, index)
                          }} />
                        <input
                          type='text' name='itemRankPrice' autoComplete="off"
                          placeholder='가격을 숫자만 입력하세요.' 
                          value={rank.rankPrice}
                          onChange={(e) => {
                            handleRankPriceChange(e.target.value, index)
                          }} />
                      </div>
                      <div className={`mt_10 ${style.contInner}`}>
                        <div className={style.innerTit}>
                          <h4>
                            혜택
                          </h4>
                          <button type='button' className='btn_xs btn_normal_b_clr' onClick={() => addContent(index)}>추가</button>
                        </div>
                        {
                          rank.contentList.map((content, contentIndex) => (
                          <div className={style.innerCont} key={contentIndex}>
                            <input
                              type='text' name='itemRankName' autoComplete="off"
                              placeholder='상세한 혜택 내용을 입력하세요.' 
                              value={content.content}
                              onChange={(e) => handleContentChange(e.target.value, index, contentIndex)}
                              />
                            {contentIndex > 0 && <button type='button' className={style.closeBtn} onClick={() => removeContent(index, contentIndex)}><span className='material-icons'>close</span></button>}
                          </div>
                        ))}
                      </div>
                    </div>
                  ))}
                </section>
              </div>
              <div className={style.doublebutton}>
                <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
                <button type='submit' className='btn btn_full'>등록하기</button>
              </div>
            </form>
          </div>
      </div>
    </section>
  )
}

export default ItemsCreate;