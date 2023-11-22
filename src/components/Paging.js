import React from "react";
import Pagination from "react-js-pagination";

const Paging = ({ page, count, handlePageChange, ITEMS_PER_PAGE }) => {
  return (
    <div className='pagination-wrap'>
      <Pagination
        activePage={page}
        itemsCountPerPage={ITEMS_PER_PAGE}
        totalItemsCount={count}
        pageRangeDisplayed={5}
        prevPageText={"‹"}
        nextPageText={"›"}
        onChange={handlePageChange}
      />
    </div>
  );
};

export default Paging;
