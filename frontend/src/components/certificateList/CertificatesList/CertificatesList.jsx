import React, { useEffect, useState } from 'react';
import './certificatesList.css';
import { Container, Alert, InputGroup, FormControl, Button } from 'react-bootstrap';
import { CertificateCard } from '../../certificateCard/CertificateCard';
import { Paging } from '../Paging';
import { CountItems } from '../CountItems';
import { loadCertificatesRequest } from '../../../service/api';
import { connect } from 'react-redux';
import changePageAction from '../../../store/actions/ChangePage'
import loadCertificatesAction from '../../../store/actions/LoadCertificatesAction';
import setTotalNumberItems from '../../../store/actions/SetTotalNumberItems';
import changeSizeAction from '../../../store/actions/ChangeSizeAction';
import queryString from 'query-string';
import { useHistory } from 'react-router-dom';

function CertificatesList({ loadedCertificates, setLoadedCertificates, setTotalNumberItems, page, size, totalNumberItems, setSize, setPage }) {
    const history = useHistory();
    const [search, setSearch] = useState('');
    const [searchWord, setSearchWord] = useState(null);
    const [tags, setTags] = useState([]);
    const [errorMessage, setErrorMessage] = useState(null);
    const [showError, setShowError] = useState(false);
    const [isSearch, setIsSearch] = useState(false);


    function handleSearch(e) {
        e.preventDefault();
        setPage(1);
        setSize(10);
        setIsSearch(true);
        setSearch(e.target.value);
        let wordString = getSearchWord(search).toString();
        let tagsString = getTags(search).toString();
        loading(searchWord, tags);
        history.push({
            pathname: '/certificates',
            search: `?name=${wordString}&tags=${tagsString}`
        })
    }

    useEffect(() => {
        let params = queryString.parse(window.location.search);
        if (!isSearch && !Object.keys(params).length) {
            loading(null, null);
        } else {
            params.page && setPage(params.page)
            params.size && setSize(params.size)
            loading(params.name, params.tags);
        }
    });

    const getSearchWord = (search) => {
        let word = search.split('#')[0].trim();
        setSearchWord(word);
        return word;
    }

    const getTags = (text) => {
        let newTxt = text.split('(');
        let listTag = [];
        for (var i = 1; i < newTxt.length; i++) {
            let tag = newTxt[i].split(')')[0].trim();
            listTag.push(tag);
        }
        setTags(listTag);
        return listTag.join();
    }

    const loading = (word, tags) => {
        let tagsList = []
        { tags && tags.isArray && (tagsList = tags.join().toString()); }
        {
            tags && !tags.isArray && (tagsList = tags);
        }
        let searchParam = {
            params: {
                'page': page,
                'size': size,
                'name': word,
                'tags': tagsList
            }
        };
        loadCertificatesRequest(searchParam)
            .then(response => {
                setShowError(false);
                setLoadedCertificates(response.data.giftCertificateDtoList);
                setTotalNumberItems(response.data.totalNumberItems);
            })
            .catch(error => {
                setLoadedCertificates([]);
                setTotalNumberItems('');
                console.log(error.response);
                setShowError(true);
                setErrorMessage('The certificates not found!');
            });
    }

    return (
        <Container style={{ marginBottom: '100px' }}>
            <InputGroup style={{ width: '500px' }}>
                <FormControl
                    type="text"
                    placeholder="Search"
                    aria-label="Input group example"
                    aria-describedby="btnGroupAddon"
                    onChange={(e) => setSearch(e.target.value)}
                />
                <Button id="btnGroupAddon" onClick={handleSearch}>Go!</Button><br />
            </InputGroup>
            {loadedCertificates !== undefined && loadedCertificates.map(certificate => {
                return <CertificateCard key={certificate.id} item={certificate} />
            })}<br />
            {showError && <Alert variant='danger'>{errorMessage}</Alert>}
            {totalNumberItems > 10 && <CountItems />}
            {totalNumberItems > 10 && <Paging size={size} currentPage={page} count={totalNumberItems} />}
        </Container>
    );
}


function mapStateToProps(state) {
    const { page, loadedCertificates, size, totalNumberItems } = state;
    return {
        loadedCertificates: loadedCertificates,
        totalNumberItems: totalNumberItems,
        page: page,
        size: size
    }
}

function mapDispatchToProps(dispatch) {
    return {
        setLoadedCertificates: (loadedCertificates) => {
            dispatch(loadCertificatesAction(loadedCertificates))
        },
        setTotalNumberItems: (totalNumberItems) => {
            dispatch(setTotalNumberItems(totalNumberItems))
        },
        setPage: (page) => {
            dispatch(changePageAction(page))
        },
        setSize: (size) => {
            dispatch(changeSizeAction(size))
        }

    }
}

export default connect(mapStateToProps, mapDispatchToProps)(CertificatesList);