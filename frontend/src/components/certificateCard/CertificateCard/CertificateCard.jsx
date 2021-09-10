import React from 'react';
import './certificateCard.css';
import { HeaderCertificateCard } from '../HeaderCertificateCard';
import { BodyCertificateCard } from '../BodyCertificateCard';
import { FooterCertificateCard } from '../FooterCertificateCard';
import { Card } from 'react-bootstrap';

function CertificateCard({ item }) {
    return (
        <Card >
            <HeaderCertificateCard name={item.name} createDate={item.createDate} />
            <BodyCertificateCard description={item.description} durationInDays={item.durationInDays} tags={item.tags} />
            <FooterCertificateCard price={item.price} />
        </Card>
    );

}
export default CertificateCard;