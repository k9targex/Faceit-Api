import React, { useState,useContext, useRef } from 'react';
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

  const handleSubmit = async (event) => {
    event.preventDefault(); 


        const data = {
            username: username,
            password: password
        };

        console.log(data.username);
        console.log(data.password);
    try{
        axios
        .post("http://localhost:8080/auth/signin", data)
        .then((response) => { 
            const token = response.data;  
            console.log(token);
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
                    Cookies.set('token', token);
                    Cookies.set('id', response.data.id);
                    Cookies.set('username', response.data.username);
                    navigate("/home");
                    window.location.reload();
                })
        })
        .catch((error) => {
            console.error("Error:", error);
        });

    }  catch (error) {
      console.error('Error:', error);
    }
  };

  return (
    <div className='login-wrapper'>
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
  );
}

export default LoginForm;
