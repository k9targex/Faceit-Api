// RegisterForm.js
import React, { useState,useContext, useRef } from 'react';
import './RegisterForm.css';
import { FaUser, FaLock } from "react-icons/fa";
import { IoEarth } from "react-icons/io5";
import {  Link,useNavigate } from "react-router-dom"
import Cookies from 'js-cookie';
import axios from "axios";

const RegisterForm = () => {
  const navigate = useNavigate();
  
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [country, setCountry] = useState('');


  const handleSubmit = async (event) => {
    event.preventDefault(); 


        const data = {
            username: username,
            password: password,
            country: country
        };

        console.log(data.username);
        console.log(data.password);
        console.log(data.country);

  
      axios
      .post("http://localhost:8080/auth/signup", data)
      .then((response) => {
          console.log(response.data);
          navigate("/signin");
      })
      .catch((error) => {
          console.error("Error:", error);
      });
  };
  return (
    <div className='register-wrapper'>
      <form onSubmit={handleSubmit}>
        <h1>Register</h1>
        <div className="register-input-box">
        <input 
            type="text" 
            placeholder='Username' 
            value={username} 
            onChange={(event) => setUsername(event.target.value)} 
            required 
          />
          <FaUser className='icon' />
        </div>
        <div className="register-input-box">
        <input 
            type="password" 
            placeholder='Password' 
            value={password} 
            onChange={(event) => setPassword(event.target.value)} 
            required 
          />
          <FaLock className='icon' />
        </div>

        <div className="register-input-box">
          {/* Используем элемент select для выбора страны */}
          <select 
          value={country} 
           onChange={(event) => setCountry(event.target.value)} 
           required
           >
           
            <option value="" disabled>Choose your country</option> 
            <option value="Argentina">Argentina</option>
            <option value="Australia">Australia</option>
            <option value="Austria">Austria</option>
            <option value="Belarus">Belarus</option>
            <option value="Brazil">Brazil</option>
            <option value="Canada">Canada</option>
            <option value="China">China</option>
            <option value="Denmark">Denmark</option>
            <option value="Egypt">Egypt</option>
            <option value="Finland">Finland</option>
            <option value="France">France</option>
            <option value="Germany">Germany</option>
            <option value="Greece">Greece</option>
            <option value="India">India</option>
            <option value="Italy">Italy</option>
            <option value="Japan">Japan</option>
            <option value="Mexico">Mexico</option>
            <option value="Netherlands">Netherlands</option>
            <option value="Nigeria">Nigeria</option>
            <option value="Norway">Norway</option>
            <option value="Poland">Poland</option>
            <option value="Portugal">Portugal</option>
            <option value="Russia">Russia</option>
            <option value="South Africa">South Africa</option>
            <option value="South Korea">South Korea</option>
            <option value="Spain">Spain</option>
            <option value="Sweden">Sweden</option>
            <option value="Switzerland">Switzerland</option>
            <option value="Turkey">Turkey</option>
            <option value="UK">United Kingdom</option>
            <option value="USA">United States of America</option>
            <option value="Ukraine">Ukraine</option>
        </select>

          <IoEarth className='icon' />
        </div>


        <button type="submit">Register</button>

        <div className="register-link">
          <p> Already have an account? <Link to={"/signin"}> Login
          </Link> </p>
          
        </div>
      </form>
    </div>
  );
}

export default RegisterForm;
