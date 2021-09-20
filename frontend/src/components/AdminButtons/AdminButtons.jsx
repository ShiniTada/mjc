import React, { useState } from 'react';
import { Button, ButtonGroup } from 'react-bootstrap';
import { DeleteCertificate } from '../modals/DeleteCertificate';
import { CreateCertificate } from '../modals/CreateCertificate';


function AdminButtons({ certificate }) {
    const [editVisable, setEditVisable] = useState(false)
    const [deleteVisable, setDeleteVisable] = useState(false)

    return (
        <ButtonGroup aria-label="Basic example">
            <Button
                style={{ background: "orange" }}
                variant="secondary"
                onClick={() => setEditVisable(true)}
            >
                Edit
            </Button>
            <Button
                style={{ background: "red" }}
                variant="secondary"
                onClick={() => setDeleteVisable(true)}
            >
                Delete
            </Button>
            {editVisable && <CreateCertificate show={editVisable} onHide={() => setEditVisable(false)} certificate={certificate} />}
            {deleteVisable && <DeleteCertificate show={deleteVisable} onHide={() => setDeleteVisable(false)} certificateId={certificate.id} />}
        </ButtonGroup>
    )
}

export default AdminButtons;