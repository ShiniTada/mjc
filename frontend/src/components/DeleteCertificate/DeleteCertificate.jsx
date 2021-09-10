import React, { useState } from 'react';
import { Modal, Button } from 'react-bootstrap';

function DeleteCertificate({ certificateId }) {
    const [deleteVisable, setDeleteVisable] = useState(false);
    const [show, setShow] = useState(false);

    const deleteItem = () => {

    }

    return (
        <Modal
            show={show}
            onHide={() => setDeleteVisable(false)}
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
                    <Button style={{ width: "80px" }} variant="secondary" onClick={() => setDeleteVisable(false)}>Cancel</Button>
                </div>
            </Modal.Footer>
        </Modal>
    );
}

export default DeleteCertificate;