import { SET_ADMIN } from './ActionTypes';

export default function setIsAdminAction(isAdmin) {
    return {
        type: SET_ADMIN,
        payload: {
            isAdmin: isAdmin
        }
    }
}