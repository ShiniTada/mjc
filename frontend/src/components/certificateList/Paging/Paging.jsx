import React, { useState } from 'react';
import { Pagination } from "react-bootstrap";
import changePageAction from '../../../store/actions/ChangePage';
import changeSizeAction from '../../../store/actions/ChangeSizeAction';
import { connect } from 'react-redux';



function Paging({ size, page, count, setPage, setSize }) {

    const pageCount = Math.ceil(count / size);
    const firstPage = 1;
    const lastPage = pageCount;

    let pages = [];
    console.log(page);

    if (page > 3) {
        pages.push(page - 2);
        pages.push(page - 1);
        pages.push(page);
        if ((page + 1) <= pageCount) {
            pages.push(page + 1);
        }
        if ((page + 2) <= pageCount) {
            pages.push(page + 2);
        }
    } else {
        pages.push(1);
        pages.push(2);
        pages.push(3);
        pages.push(4);
        pages.push(5);
    }



    const handleChangePage = (e) => {
        setPage(e.target.value);
        // console.log(page)
    }

    if (pageCount === 0 && page === 0) {
        return;
    }

    return (
        <Pagination>
            <Pagination.First onClick={handleChangePage} />
            <Pagination.Prev />
            {pages.map(page => {
                <Pagination.Item key={page} onChange={handleChangePage}>{page}</Pagination.Item >
            })}

            <Pagination.Next />
            <Pagination.Last onClick={handleChangePage} />
        </Pagination>
    );
}

function mapStateToProps(state) {
    return {
        size: state.size,
        page: state.page,
        count: state.totalNumberItems
    }
}

function mapDispatchToProps(dispatch) {
    return {
        setPage: (page) => {
            dispatch(changePageAction(page))
        }
        /*, setSize: (size) => {
            dispatch(changeSizeAction(size))
        }*/
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Paging);