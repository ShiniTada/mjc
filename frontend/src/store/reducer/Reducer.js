import { SET_AUTH, CHANGE_PAGE, DELETE_CERTIFICATE, LOAD_CERTIFICATES, SET_TOTAL_NUMBER_ITEMS, CHANGE_SIZE, SET_ADMIN, SET_EMAIL_ACTION } from '../actions/ActionTypes';

export default function Reducer(state, action) {
    switch (action.type) {
        case LOAD_CERTIFICATES:
            return { ...state, loadedCertificates: action.payload.loadedCertificates, dataLoaded: true };
        case CHANGE_PAGE:
            localStorage.setItem('page', action.payload.page)
            return { ...state, page: action.payload.page };
        case SET_TOTAL_NUMBER_ITEMS:
            return { ...state, totalNumberItems: action.payload.totalNumberItems };
        case CHANGE_SIZE:
            return { ...state, size: action.payload.size };
        case SET_AUTH:
            return { ...state, isAuth: action.payload.isAuth };
        case SET_ADMIN:
            return { ...state, isAdmin: action.payload.isAdmin };
        case SET_EMAIL_ACTION:
            return { ...state, email: action.payload.email };
        case DELETE_CERTIFICATE:
            let updatedCertificates = state.loadedCertificates.filter(certificate => certificate.id !== action.payload.certificateId);
            return {
                ...state, loadedCertificates: updatedCertificates
            }
        default:
            return state;
    }
}