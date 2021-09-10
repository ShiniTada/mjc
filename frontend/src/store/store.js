import { createStore, applyMiddleware } from 'redux';
import Reducer from './reducer/Reducer';
import thunk from 'redux-thunk';

const initialState = [
    { loadedCertificates: [] },
    { page: '' },
    { size: '10' },
    { totalNumberItems: '' }];

const store = createStore(Reducer, initialState, applyMiddleware(thunk));

console.log(store.getState());

export default store;