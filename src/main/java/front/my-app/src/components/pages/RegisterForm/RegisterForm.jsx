// RegisterForm.js
import React, {useState, useEffect} from 'react';
import './RegisterForm.css';
import {FaUser, FaLock} from "react-icons/fa";
import {IoEarth} from "react-icons/io5";
import {Link, useNavigate,} from "react-router-dom"
import axios from "axios";
import {FaAsterisk} from "react-icons/fa6";

const RegisterForm = () => {
    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [country, setCountry] = useState('');
    const [isBlockVisible, setIsBlockVisible] = useState(false);
    const [is401Reg, setIs401Reg] = useState(false);

    const toggleBlockVisibility = () => {
        setIsBlockVisible(!isBlockVisible);
    };


    const handleClickReg = () => {
        setIs401Reg(false);
    };
    useEffect(() => {
        if (is401Reg)
            document.addEventListener('click', handleClickReg);
        return () => {
            if (is401Reg)
                document.removeEventListener('click', handleClickReg);
        };
    }, [is401Reg]);


    const handleSubmit = async (event) => {
        event.preventDefault();

        if (country === '') {

            return
        }
        const data = {
            username: username,
            password: password,
            country: country
        };

        axios
            .post("http://localhost:8080/auth/signup", data)
            .then((response) => {

                navigate("/signin");
            })
            .catch((error) => {
                if (error.response && error.response.status === 401) {
                    setIs401Reg(true);

                    console.log("click");
                } else {
                    console.error("Error:", error);
                }

            });
    };
    return (
        <div className='rg'>


            {is401Reg && (
                <div className='errorMessageRegister'>
                    <p>The username is already taken. Please choose another one.</p>
                </div>
            )}


           
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
                        <FaUser className='icon'/>
                    </div>
                    <div className="register-input-box">
                        <input
                            type="password"
                            placeholder='Password'
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            required
                        />
                        <FaLock className='icon'/>
                    </div>

                    <div className="register-input-box">
                        <button type="button" onClick={toggleBlockVisibility} className='showCountries'>
                            <p className='countryText'>

                                {!country && (
                                    <p> Choose the country</p>
                                )}

                                {country}

                            </p>
                        <FaAsterisk className='asterisk'/>

                            <IoEarth className='icon'/>
                        </button>

                    </div>

                    <button type="submit" className='register'>Register</button>

                    <div className="register-link">
                        <p> Already have an account? <Link to={"/signin"}> Login
                        </Link></p>

                    </div>
                </form>
            </div>
            {isBlockVisible && (
                <div className='countryList'>
                    <div className='boxToHideCountry'>
                        <ul>
                            {["Argentina", "Australia", "Austria", "Belarus", "Brazil", "Canada", "China", "Denmark", "Egypt", "Finland", "France", "Germany", "Greece", "India", "Italy", "Japan", "Mexico", "Netherlands", "Nigeria", "Norway", "Poland", "Portugal", "Russia", "South Africa", "South Korea", "Spain", "Sweden", "Switzerland", "Turkey", "United Kingdom", "USA", "Ukraine"].map((country, index) => (

                                <button type="button" className='countryFromList' onClick={() => {
                                    setCountry(country);
                                    toggleBlockVisibility();
                                }}>
                                    {country}
                                </button>

                            ))}

                        </ul>
                    </div>
                </div>
            )}
        </div>
    );
}

export default RegisterForm;
