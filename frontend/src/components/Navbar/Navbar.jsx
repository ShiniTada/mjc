import React, { useState } from "react";
import './navbar.css';
import { Navbar, Nav, Button, Modal, Form, Alert } from "react-bootstrap";

function NavBar() {
    const [show, setShow] = useState(false);
    const [showError, setShowError] = useState(false);
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [price, setPrice] = useState(0);
    const [durationInDays, setDurationInDays] = useState('');
    const [errorMessage, setErrorMessage] = useState('');

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    const handleShowError = () => setShowError(false);

    const validateName = (e) => {
        let inputName = e.target.value;
        if (inputName.length > 6 && inputName.length < 30) {
            console.log(inputName);
            setName(inputName);
        } else {
            setShowError(true);
            setErrorMessage('Name have to be less than 6 and greater than 30 characters');
            window.setTimeout(function () { setShowError(false); }, 3000);
        }
    }
    const validateDescription = (e) => {
        let inputDescription = e.target.value;
        if (inputDescription.length > 6 && inputDescription.length < 30) {
            setDescription(inputDescription);
        } else {
            setShowError(true);
            setErrorMessage('Description have to be less than 12 and greater than 1000 characters');
            window.setTimeout(function () { setShowError(false); }, 3000);
        }
    }

    const validatePrice = (e) => {
        let inputPrice = e.target.value;
        if (inputPrice > 0 && isInt(parseInt(inputPrice)) || isFloat(parseFloat(inputPrice))) {
            setPrice(inputPrice);
        } else {
            setShowError(true);
            setErrorMessage('Price must be a number or float and be greater than 0');
            window.setTimeout(function () { setShowError(false); }, 3000);
        }
    }
    const validateDuration = (e) => {
        let inputDuration = parseInt(e.target.value);
        if ((inputDuration >= 0) && isInt(inputDuration)) {
            setDurationInDays(inputDuration);
        } else {
            setShowError(true);
            setErrorMessage('Duration must be a positive number');
            window.setTimeout(function () { setShowError(false); }, 3000);
        }
    }

    function isInt(n) {
        return (Number(n) === n) && (n % 1 === 0);
    }

    function isFloat(n) {
        return Number(n) === n && n % 1 !== 0;
    }


    return (
        <Navbar fixed='top' bg="dark" variant="dark">
            <Navbar.Brand>Logo</Navbar.Brand>
            <Nav className="me-auto">
                <Button variant="primary" onClick={handleShow}>
                    Add new certificate
                </Button>
                <Modal
                    show={show}
                    onHide={handleClose}
                    backdrop="static"
                    keyboard={false}
                >
                    <Modal.Header closeButton>
                        <Modal.Title>Add new certificate</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Alert variant='danger' show={showError} closeButton>
                            {errorMessage}
                        </Alert>
                        <Form>
                            <Form.Control type="text" onChange={validateName} placeholder="Name" /><br />
                            <Form.Control type="text" onChange={validateDescription} placeholder="Description" /><br />
                            <Form.Control type="text" onChange={validatePrice} placeholder="Price" /><br />
                            <Form.Control type="text" onChange={validateDuration} placeholder="Duration in days" />

                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleClose}>
                            Close
                        </Button>
                        <Button variant="primary">Save</Button>
                    </Modal.Footer>
                </Modal>
            </Nav>
            <Nav className="ml-auto">
                <Nav.Link style={{ color: "white" }}>Login</Nav.Link>
                <Nav.Link style={{ color: "white" }}>Sign up</Nav.Link>
            </Nav>
        </Navbar >

    );
}

export default NavBar;