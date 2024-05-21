import React, { useState,useEffect } from 'react';
import './LoginForm.css';
import { FaUser, FaLock } from "react-icons/fa";
import Cookies from 'js-cookie';
import axios from "axios";
import {  Link,useNavigate } from "react-router-dom"
const getUser = "http://localhost:8080/users/getUserByName";

const LoginForm = () => {
  const navigate = useNavigate();
  
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [is401Log, setIs401Log] = useState(false);

  const handleClickLog = (event) => {
      setIs401Log(false); 
  };

  useEffect(() => { 
    if(is401Log)
      document.addEventListener('click', handleClickLog);
  return () => {
      if(is401Log)
    document.removeEventListener('click', handleClickLog);
  };


  }, [is401Log]);

  const handleSubmit = async (event) => {
    event.preventDefault(); 


        const data = {
            username: username,
            password: password
        };
        axios
        .post("http://localhost:8080/auth/signin", data)
        .then((response) => { 
            const token = response.data;  
            localStorage.setItem('token', token);
          
            axios
                .get(getUser, {
                  params: {
                    username: data.username
                },
           
                headers: {
                    Authorization: `Bearer ${token}`
                }
            })
            .then((response) => {
              
                  const nicknames = response.data.favoritePlayers.map(player => player.nickname);
                  const nicknamesString = JSON.stringify(nicknames);
                    Cookies.set('token', token);
                    Cookies.set('id', response.data.id);
                    Cookies.set('username', response.data.username);
                    Cookies.set('favoritePlayers', nicknamesString);
                    navigate("/home");
                    window.location.reload();
                })
        })
          .catch((error) => {
            if (error.response && error.response.status === 401) {
             
              setIs401Log(true);
            }
            else{
              console.error("Error:", error);
            }
        });


  };

  return (
    <body className='lg'>
        {is401Log && (
        <div className='errorMessageLogin'>
          <p>Incorrect login or password.</p>
        </div>
      )}
    <div className='login-wrapper '>
      <form onSubmit={handleSubmit}>
        <h1>Login</h1>
        <div className="login-input-box">
          <input 
            type="text" 
            placeholder='Username' 
            value={username} 
            onChange={(event) => setUsername(event.target.value)} 
            required 
          />
          <FaUser className='icon' />
        </div>
        <div className="login-input-box">
          <input 
            type="password" 
            placeholder='Password' 
            value={password} 
            onChange={(event) => setPassword(event.target.value)} 
            required 
          />
          <FaLock className='icon' />
        </div>

        <button type="submit">Login</button>

        <div className="register-link">
          <p> Don't have an account?    <Link to={"/signup"}> Register
          </Link></p>
       
        </div>
      </form>
    </div>
    </body>
  );
}

export default LoginForm;
