import React, { useState } from "react";
import './navbar.css';
import { Navbar, Nav, Button } from "react-bootstrap";
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import setIsAuthAction from '../../store/actions/SetAuth';
import { CreateCertificate } from "../modals/CreateCertificate";

function NavBar({ isAuth, setIsAuth, isAdmin }) {
    const [editVisable, setEditVisable] = useState(false)

    const logout = () => {
        setIsAuth(false);
        window.localStorage.removeItem('email');
        window.localStorage.removeItem('token');
        window.localStorage.removeItem('admin');
        window.localStorage.removeItem('auth');
    }


    return (
        <Navbar fixed='top' bg="dark" variant="dark" style={{ margin: '0px' }}>
            <Navbar.Brand>Logo</Navbar.Brand>
            <Nav className="me-auto">
                {isAdmin &&
                    <Button
                        variant="primary"
                        onClick={() => setEditVisable(true)}>
                        Add new certificate
                    </Button>}
                {editVisable && <CreateCertificate show={editVisable} onHide={() => setEditVisable(false)} />}

            </Nav>

            <Nav className="ml-auto">
                {!isAuth && <Link style={{ color: "white" }} to="/login">Login</Link>}
                {isAuth && <Link style={{ color: "white" }} onClick={logout} to="/login">Logout</Link>}
            </Nav>
        </Navbar >

    );
}
function mapStateToProps(state) {
    return {
        isAuth: state.isAuth,
        isAdmin: state.isAdmin
    }
}
function mapDispatchToProps(dispatch) {
    return {
        setIsAuth: (isAuth) => {
            dispatch(setIsAuthAction(isAuth))
        }
    }
}


export default connect(mapStateToProps, mapDispatchToProps)(NavBar);