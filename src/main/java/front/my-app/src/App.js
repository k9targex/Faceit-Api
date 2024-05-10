// App.jsx
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
// import Header from "./components/header/Header";
import HomePage from "./components/pages/HomePage/HomePage";
import SignIn from "./components/pages/LoginForm/LoginForm";
import SignUp from "./components/pages/RegisterForm/RegisterForm";



class App extends React.Component {

  render() {
      return (
          <Router>
              <div className="App">
                  <Routes>
                      <Route path="/" element={<SignIn />} /> 
                      <Route path="/signin" element={<SignIn/>} />
                      <Route path="/signup" element={<SignUp/>} /> 
                      <Route path="/home" element={<HomePage/>} /> 

                  </Routes>
              </div>
          </Router>
      );
  }
}

export default App;
