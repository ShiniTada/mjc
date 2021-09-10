import { LOAD_CERTIFICATES } from './ActionTypes';

export default function loadCertificatesAction(data) {

    return {
        type: LOAD_CERTIFICATES,
        payload: {
            loadedCertificates: data
        }
    }
}