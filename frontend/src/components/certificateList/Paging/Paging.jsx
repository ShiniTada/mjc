import React from 'react';
import { Pagination } from "react-bootstrap";
import changePageAction from '../../../store/actions/ChangePage';
import { useDispatch } from 'react-redux';
import { createPages } from '../../../utils/pagesCreator';


function Paging({ size, currentPage, count }) {

    const dispatch = useDispatch();

    const pagesCount = Math.ceil(count / size);
    const firstPage = Number(1);
    const lastPage = pagesCount;
    const pages = [];

    createPages(pages, pagesCount, currentPage);

    return (

        <Pagination >
            {(pagesCount > 1) &&
                <Pagination.First onClick={() => dispatch(changePageAction(firstPage))} />}
            {(pagesCount > 1) && pages.map((page) => (page == currentPage)
                ? <Pagination.Item className='active' key={page} onClick={() => dispatch(changePageAction(page))}>
                    {page}</Pagination.Item>
                : <Pagination.Item key={page} onClick={() => dispatch(changePageAction(page))}>
                    {page}</Pagination.Item>
            )}
            {(pagesCount > 1) &&
                <Pagination.Last onClick={() => dispatch(changePageAction(lastPage))} />}
        </Pagination >
    );
}

export default Paging;