import React, { useState } from 'react';
import './login.css';
import { Form, Button } from 'react-bootstrap';

function Login() {
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();

    return (
        <div className="login">
            <form className='form-login'>
                <h3>Log in</h3>
                <div className="form-group">
                    <label>Email</label><br />
                    <input type="email" className="form-control" placeholder="Enter email"


                    />
                </div>
                <div className="form-group">
                    <label>Password</label><br />
                    <input type="password" className="form-control" placeholder="Enter password" />
                </div>
                <div className="form-group">
                    <div className='error-message-login'>

                    </div>
                </div>
                <button type="submit" className="">Sign in</button>
            </form >
        </div>);
}

export default Login;