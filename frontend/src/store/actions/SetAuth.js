import { SET_AUTH } from './ActionTypes';

export default function setIsAuthAction(isAuth) {
    return {
        type: SET_AUTH,
        payload: {
            isAuth: isAuth
        }
    }
}