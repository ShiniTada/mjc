import axios from 'axios'

const API_URL_CERTIFICATES = 'http://localhost:8080/certificates';
const API_URL_TAGS = 'http://localhost:8080/tags';
const API_URL_AUTH_LOGIN = 'http://localhost:8080/auth/login';
const API_URL_AUTH_USERS = 'http://localhost:8080/users';
const API_URL_AUTH_ORDERS = 'http://localhost:8080/orders';

export function loadCertificatesRequest(searchParam) {
    return axios.get(API_URL_CERTIFICATES, searchParam);
}



