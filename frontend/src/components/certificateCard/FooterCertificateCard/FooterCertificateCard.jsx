import React, { useState } from 'react';
import './footerCertificateCard.css';
import { Button, Card, Modal } from 'react-bootstrap';
import { DeleteCertificate } from '../../DeleteCertificate';

function FooterCertificateCard({ price }) {
    const [deleteVisable, setDeleteVisable] = useState(false);

    const deleteItem = () => {

    }

    return (
        <Card.Footer style={{
            display: 'flex'
            , justifyContent: 'space-around'
        }}>
            Price: {price}
            <Button variant='success'>
                Buy
            </Button>
            <Button
                style={{ background: "red" }}
                variant="secondary"
                onClick={() => setDeleteVisable(true)}
            >Delete
            </Button>
            <Modal
                show={deleteVisable}
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
                    Do you really want to delete certificate with id = {'id'}?
                </Modal.Body>
                <Modal.Footer>
                    <div className="d-block mx-auto">
                        <Button style={{ width: "80px", marginRight: "3px" }} variant="danger" onClick={deleteItem}>Yes</Button>
                        <Button style={{ width: "80px" }} variant="secondary" onClick={() => setDeleteVisable(false)}>Cancel</Button>
                    </div>
                </Modal.Footer>
            </Modal>
        </Card.Footer>
    );
}

export default FooterCertificateCard;