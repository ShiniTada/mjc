import { DELETE_CERTIFICATE } from './ActionTypes';

export default function deleteCertificateAction(certificateId) {
    return {
        type: DELETE_CERTIFICATE,
        payload: {
            certificateId: certificateId
        }
    }
}