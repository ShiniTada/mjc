import React from 'react'
import { Modal, Button } from 'react-bootstrap'
import { connect, useDispatch } from 'react-redux'
import { deleteCertificate, loadCertificatesRequest } from '../../../service/api'
import loadCertificatesAction from '../../../store/actions/LoadCertificatesAction'


function DeleteCertificate({ show, onHide, certificateId, page, size }) {
    const dispatch = useDispatch();

    const deleteItem = (e) => {
        e.preventDefault();
        deleteCertificate(certificateId).then(() => {
            loadCertificatesRequest({
                params: {
                    'page': page,
                    'size': size
                }
            }).then(response => {
                dispatch(loadCertificatesAction(response.data.giftCertificateDtoList))
            })
        })
        onHide()
    }

    return (
        <Modal
            show={show}
            onHide={onHide}
            backdrop="static"
            keyboard={false}
        >
            <Modal.Header style={{
                background: "#d3d3d340",
                height: "50px",
                color: "var(--bs-gray-500)"
            }}>
                <Modal.Title style={{ fontSize: "18px" }}>Delete confirmation</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                Do you really want to delete certificate with id = {certificateId}?
            </Modal.Body>
            <Modal.Footer>
                <div className="d-block mx-auto">
                    <Button style={{ width: "80px", marginRight: "3px" }} variant="danger" onClick={deleteItem}>Yes</Button>
                    <Button style={{ width: "80px" }} variant="secondary" onClick={onHide}>Cancel</Button>
                </div>
            </Modal.Footer>
        </Modal >
    )
}

function mapStateToProps(state) {
    const { page, size } = state
    return {
        page: page,
        size: size

    }
}

export default connect(mapStateToProps, null)(DeleteCertificate)