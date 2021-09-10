import React from 'react';
import { Card } from 'react-bootstrap';
import './headerCertificateCard.css';

function HeaderCertificateCard({ name, createDate }) {

    return (
        <Card.Header style={{
            background: '#d4b4d9',
            display: 'flex', justifyContent: 'space-between'
        }}>
            <p>{name}  </p>
            <p>{createDate}</p>
        </Card.Header>
    );
}

export default HeaderCertificateCard;