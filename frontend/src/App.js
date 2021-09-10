import React from "react";

import { NavBar } from './components/Navbar';
import { Login } from './components/Login';
import { Footer } from './components/Footer';
import { CertificatesList } from './components/certificateList/CertificatesList';
import { CertificateCard } from "./components/certificateCard/CertificateCard";
import { SearchField } from "./components/SearchField";
import './App.css';
import store from './store/store';

import { BrowserRouter, Route, Switch } from 'react-router-dom';
import { connect } from 'react-redux';



function App() {

    return (
        <BrowserRouter>
            <NavBar />
            <Switch>
                <Route path="/login" component={Login}></Route>

                <CertificatesList />

                <Route path="/certificate" component={CertificateCard}></Route>


            </Switch>
            <Footer />
        </BrowserRouter >
    );
}

export default App;
    /*connect(
state => ({}),
dispatch => ({})
)(App);*/