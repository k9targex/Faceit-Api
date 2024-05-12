// App.jsx
import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
// import Header from "./components/header/Header";
import HomePage from "./components/pages/HomePage/HomePage";
import SignIn from "./components/pages/LoginForm/LoginForm";
import SignUp from "./components/pages/RegisterForm/RegisterForm";
import StatsPage from "./components/pages/StatsPage/StatsPage";
import Message from "./components/pages/Message/Message";


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
                      <Route path="/stats" element={<StatsPage/>} /> 
                      <Route path="/message" element={<Message/>} /> 


                  </Routes>
              </div>
          </Router>
      );
  }
}

export default App;
