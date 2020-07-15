import React, {useState} from 'react';
import LoginPage from './LoginPage';
import RegPage from './RegPage';

const LoginHandler = (props) => {

    const [registered, setRegistered] = useState(true);

    if(registered===true)
    return (
        <LoginPage onRegisterClick={()=>{setRegistered(false)}} onSubmit={props.onSubmit}/>
    );

    if(registered===false)
    return (
        <RegPage onSubmit={()=>{setRegistered(true)}}/>
    );
};

export default LoginHandler;