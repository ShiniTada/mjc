import { SET_TOTAL_NUMBER_ITEMS } from './ActionTypes';

export default function setTotalNumberItems(totalNumberItems) {
    return {
        type: SET_TOTAL_NUMBER_ITEMS,
        payload: {
            totalNumberItems: totalNumberItems
        }
    }
}