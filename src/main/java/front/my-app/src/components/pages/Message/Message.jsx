import React from 'react';
import { Link } from 'react-router-dom';
import "./Message.css"
import { BiDoorOpen } from "react-icons/bi";
export const Message = () => {
  return (
    <div className='message-wrapper'>
      <div className='messageBox'>
      <p className='message'>Login please!</p>
      <Link to="/signup">
        <button className='buttonDoor'>
        <BiDoorOpen className='door' />
        </button>
      </Link>
      </div>
      
    </div>
  );
};
export default Message;