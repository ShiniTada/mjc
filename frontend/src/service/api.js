import axios from 'axios'

const API_URL_CERTIFICATES = 'http://localhost:8080/certificates';
const API_URL_AUTH_LOGIN = 'http://localhost:8080/auth/login';


export function loadCertificatesRequest(searchParam) {
    return axios.get(API_URL_CERTIFICATES, searchParam);
}

export function getToken(requestToken) {
    return axios.post(API_URL_AUTH_LOGIN, requestToken);
}

export function createCertificateRequest(certificateDto) {
    let config = {
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        }
    }
    return axios.post(API_URL_CERTIFICATES, certificateDto, config);
}

export function deleteCertificate(certificateId) {
    let config = {
        headers: {
            'Authorization': 'Bearer ' + window.localStorage.getItem('token')
        }
    }
    return axios.delete(API_URL_CERTIFICATES + '/' + certificateId, config);
}

export function updateCertificate(updateCertificate, certificateId) {
    let config = {
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    }

    return axios.put(API_URL_CERTIFICATES + '/' + certificateId, updateCertificate, config)
}



