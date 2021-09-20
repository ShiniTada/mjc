import { SET_EMAIL_ACTION } from './ActionTypes';

export default function setEmailAction(email) {
    return {
        type: SET_EMAIL_ACTION,
        payload: {
            email: email
        }
    }
}