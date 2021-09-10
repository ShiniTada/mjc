import React, { useState } from 'react';
import { InputGroup, FormControl, Form, Button, Row } from 'react-bootstrap';
//import { createTag } from '../../http/certificatesAPI';
import axios from "axios";

const URL = 'http://localhost:8080/certificates';

function SearchField() {

    return (
        <InputGroup style={{ width: '500px' }}>

            <FormControl
                type="text"
                placeholder="Search"
                aria-label="Input group example"
                aria-describedby="btnGroupAddon"
            />
            <InputGroup.Text id="btnGroupAddon">Go!</InputGroup.Text>
            <br />
        </InputGroup>


    );
}

export default SearchField;