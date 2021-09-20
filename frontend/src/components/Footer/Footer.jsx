import React from 'react';
import './footer.css';
import { Navbar } from 'react-bootstrap';

function Footer() {

    return (
        <Navbar bg="dark" variant="dark" fixed="bottom" style={{ margin: '0px' }}>
            <Navbar.Brand className="m-auto" style={{ color: "grey", fontWeight: 700 }}> Minsk, 2021</Navbar.Brand>
        </Navbar>

    );
}
export default Footer;

