import { CHANGE_PAGE } from './ActionTypes';

export default function changePageAction(page) {
    return {
        type: CHANGE_PAGE,
        payload: {
            page: page
        }
    }
}
