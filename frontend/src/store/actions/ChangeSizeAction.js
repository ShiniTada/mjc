import { CHANGE_SIZE } from './ActionTypes';

export default function changeSizeAction(size) {
    return {
        type: CHANGE_SIZE,
        payload: {
            size: size
        }
    }
}