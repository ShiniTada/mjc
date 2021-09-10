import React, { useEffect, useState } from 'react';
import './certificatesList.css';
import { Container, Alert } from 'react-bootstrap';
import { CertificateCard } from '../../certificateCard/CertificateCard';
import { Paging } from '../Paging';
import { CountItems } from '../CountItems';
import { SearchField } from '../../SearchField';
import { loadCertificatesRequest } from '../../../service/api';
import { connect } from 'react-redux';
import changePageAction from '../../../store/actions/ChangePage'
import loadCertificatesAction from '../../../store/actions/LoadCertificatesAction';
import setTotalNumberItems from '../../../store/actions/SetTotalNumberItems';

function CertificatesList({ loadedCertificates, setLoadedCertificates, setTotalNumberItems, page, size, setPage, totalNumberItems }) {

    // const [loadedCertificates, setLoadedCertificates] = useState([]);
    //const [size, setSize] = useState(10);
    // const [totalNumberItems, setTotalNumberItems] = useState();
    //const [page, setPage] = useState(1);
    let namePart = '';
    let descriptionPart = '';
    let tags = [];
    let [errorMessage, setErrorMessage] = useState(null);
    //  const arrayLength = loadedCertificates.length;


    useEffect(() => {
        let searchParam = {
            params: {
                'page': page,
                'size': size,
                'namePart': namePart,
                'descriptionPart': descriptionPart,
                'tags': tags
            }
        };

        loadCertificatesRequest(searchParam).
            then(response => {
                setLoadedCertificates(response.data.giftCertificateDtoList);
                setTotalNumberItems(response.data.totalNumberItems);
                setErrorMessage(null);
            })
            .catch(error => {
                console.log(error);
                setErrorMessage(error.response.data.errorMessage);
            });

    });
    if (totalNumberItems != undefined) {
        totalNumberItems > 10 ? setPage(1) : setPage(0)
        /* if (loadedCertificates.length > 10) {
             setPage(1);
         }*/
    }

    return (
        <Container style={{ marginBottom: '100px' }}>
            {loadedCertificates != undefined && loadedCertificates.map(certificate => {
                return <CertificateCard key={certificate.id} item={certificate} />
            })}

            <CountItems />
            <Paging />
        </Container>
    );
}


function mapStateToProps(state) {
    return {
        loadedCertificates: state.loadedCertificates,
        totalNumberItems: state.totalNumberItems,
        page: state.page,
        size: state.size
    };
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
        }
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(CertificatesList);