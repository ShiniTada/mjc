import React from 'react';
import { Card } from 'react-bootstrap';

function BodyCertificateCard({ description, durationInDays, tags }) {
    let tagsList = [];
    for (let i = 0; i < tags.length; i++) {
        let tag;
        if (i !== (tags.length - 1)) {
            tag = tags[i].name + ', ';
        } else {
            tag = tags[i].name;
        }
        tagsList.push(<mark key={tags[i].id} style={{ background: '#b9fac4' }}>{tag}</mark>);
    }
    return (
        <Card.Body style={{ background: '#f2dff5', margin: '0 0 0 0' }}>
            <p>{description}</p>
            <p>Duration: {durationInDays}</p>
            <p>Tags:{tagsList}</p>

        </Card.Body>
    );
}

export default BodyCertificateCard;