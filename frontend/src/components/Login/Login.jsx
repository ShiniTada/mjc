import React, { useState } from 'react';
import './login.css';
import { Form, Button, Container, Alert } from 'react-bootstrap';
import { getToken } from '../../service/api';
import setIsAuthAction from '../../store/actions/SetAuth';
import { connect } from 'react-redux';
import { useHistory } from 'react-router-dom';
import jwt_decode from 'jwt-decode';
import setIsAdminAction from '../../store/actions/SetAdmin';
import setEmailAction from '../../store/actions/SetEmailAction';

function Login({ setIsAuth, setIsAdmin, setEmailAction }) {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);
    const [show, setShow] = useState(false);
    const history = useHistory();

    function handleSubmit(event) {
        event.preventDefault();
        getToken({
            email: email,
            password: password
        }).then(response => {
            window.localStorage.setItem('email', response.data.email);
            window.localStorage.setItem('token', response.data.token);
            if (response.status == 200) {
                setIsAuth(true);
                window.localStorage.setItem('auth', 'true');
                let details = jwt_decode(response.data.token);
                setEmailAction(details.sub);
                if (details.roles == 'ADMIN') {
                    window.localStorage.setItem('admin', 'true');
                    setIsAdmin(true);
                }
                history.push('/certificates');
            }
        }).catch((error) => {
            console.log(error)
            setShow(true);
            let message = error.response.data.errorMessage;
            setError(message);
        })
    }

    return (
        <Container style={{ marginBottom: '100px' }}>
            <Form>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    < Form.Label > Email address</Form.Label >
                    <Form.Control type="email" placeholder="Enter email" onChange={(e) => setEmail(e.target.value)} />
                </Form.Group >

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
                </Form.Group>

                {error == null && <div style={{ color: '#D3D3D3' }}>Check your password, it must be 3-200 characters and try again</div>}
                {show && <Alert variant='danger' style={{ display: 'flex', 'justifyContent': 'space-between' }}>{error}
                    <Button onClick={() => setShow(false)} variant="danger">
                        Close
                    </Button>
                </Alert>}

                <Button variant="primary" type="submit" onClick={handleSubmit}>
                    Submit
                </Button>
            </Form >
        </Container >
    )
}

function mapDispatchToProps(dispatch) {
    return {
        setIsAuth: (isAuth) => {
            dispatch(setIsAuthAction(isAuth))
        },
        setIsAdmin: (isAdmin) => {
            dispatch(setIsAdminAction(isAdmin))
        },
        setEmailAction: (email) => {
            dispatch(setEmailAction(email))
        }
    }
}

export default connect(null, mapDispatchToProps)(Login);