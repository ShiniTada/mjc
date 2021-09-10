import { CHANGE_PAGE, CREATE_CERTIFICATE, DELETE_CERTIFICATE, EDIT_CERTIFICATE, LOAD_CERTIFICATES } from '../actions/ActionTypes';

export default function Reducer(state, action) {
    switch (action.type) {
        case LOAD_CERTIFICATES:
            return { ...state, loadedCertificates: action.payload.loadedCertificates, dataLoaded: true };
        case CHANGE_PAGE:
            return { ...state, page: action.payload.page };

        case CREATE_CERTIFICATE:
            return state;
        case DELETE_CERTIFICATE:
            return state;
        case EDIT_CERTIFICATE:
            return state;
        default:
            return state
    }
}