import { createStore, applyMiddleware } from 'redux';
import Reducer from './reducer/Reducer';
import thunk from 'redux-thunk';

const initialState = {
    loadedCertificates: [],
    page: localStorage.getItem('page'),
    size: 10,
    totalNumberItems: '',
    isAuth: window.localStorage.getItem('auth') === 'true',
    isAdmin: window.localStorage.getItem('admin') === 'true',
    email: ''
};

const store = createStore(Reducer, initialState, applyMiddleware(thunk));

export default store;