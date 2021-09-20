import React from 'react';
import { Button, Card } from 'react-bootstrap';
import { connect } from 'react-redux';
import AdminButtons from '../../AdminButtons/AdminButtons';


function FooterCertificateCard({ certificate, isAdmin, isAuth }) {

    return (
        <Card.Footer style={{
            display: 'flex'
            , justifyContent: 'space-around'
        }}>
            Price: {certificate.price}
            {isAuth && !isAdmin &&
                <Button variant='success'>
                    Buy
                </Button>}
            {isAdmin &&
                <AdminButtons certificate={certificate} />
            }
        </Card.Footer>
    );
}

function mapStateToProps(state) {
    const { isAdmin, isAuth } = state;
    return {
        isAdmin: isAdmin,
        isAuth: isAuth
    }
}

export default connect(mapStateToProps, null)(FooterCertificateCard);