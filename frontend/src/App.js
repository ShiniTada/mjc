import React from "react";
import { NavBar } from './components/Navbar';
import { Login } from './components/Login';
import { Footer } from './components/Footer';
import { CertificatesList } from './components/certificateList/CertificatesList';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import './App.css';


function App() {
    return (
        <BrowserRouter>
            <NavBar />
            <Switch>
                {window.localStorage.setItem('page', Number(1))}
                <Route path="/login" component={Login}></Route>
                <Route path="/" component={CertificatesList}></Route>
                <Route path="/certificates" component={CertificatesList}></Route>
            </Switch>
            <Footer />
        </BrowserRouter >
    );
}

export default App;
