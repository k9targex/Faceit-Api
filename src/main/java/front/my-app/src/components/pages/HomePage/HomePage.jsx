import React, { useState,useContext, useRef,useEffect  } from 'react';

import {Parallax,ParallaxLayer} from '@react-spring/parallax'
import './HomePage.css';
import TextBlock from './TextBlock';
import { SiFaceit } from "react-icons/si";
import Cookies from 'js-cookie';
import axios from "axios";
import { SlMagnifier,SlGraph  } from "react-icons/sl";
import {  Link,useNavigate } from "react-router-dom"

export const HomePage = () => {
    const navigate = useNavigate();

    const [tokenState,setTokenState] = useState('');

    const [nickname, setNickname] = useState('');
   
    useEffect(() => {
        const token = Cookies.get('token');
        if (!token) {
            // Если токен отсутствует, перенаправляем на страницу регистрации
            navigate("/message");
        } else {
            setTokenState(token);
        }
    }, []);
    const handleSubmit = async (event) => {
        event.preventDefault(); 
        const data = {
            nickname: nickname
        };
    
   
      const token = Cookies.get('token');
     
      axios
        .get("http://localhost:8080/api/v1/faceit/info",{

            params: {
                nickname: data.nickname
            },
            headers: {
                Authorization: `Bearer ${token}`
            }
        } )
        .then((response) => {
            const {nickname,country,avatar} = response.data.playerInfo.items[0];
            const playerStats = response.data.playerStats.lifetime;
            const { 
                "Average K/D Ratio": averageKDRatio,
                "Average Headshots %": averageHeadshots,
                "Total Headshots %": totalHeadshots,
                Matches,
                Wins,
                "Win Rate %": winRate,
                "Current Win Streak": currentWinStreak,
                "Longest Win Streak": longestWinStreak
            } = playerStats;
            const lvl = response.data.skillLevel;
                    Cookies.set('avatar', avatar);

                     Cookies.set('username', nickname);
                     Cookies.set('country', country);

                     Cookies.set('k_d', averageKDRatio);
                     Cookies.set('av_headshots', averageHeadshots);
                     Cookies.set('tot_headshots', totalHeadshots);
                     Cookies.set('matches', Matches);

                     Cookies.set('wins', Wins);
                     Cookies.set('win_rate', winRate);
                     Cookies.set('current_win', currentWinStreak);
                     Cookies.set('longest_win', longestWinStreak);
                     Cookies.set('lvl', lvl);
            
                     navigate("/stats");
                    //  window.location.reload();
        })
        .catch((error) => {
            console.error("Error:", error);
        });
        
    };
  return (
    
    <div className="hm">
    <form onSubmit={handleSubmit}>
       

             

        <Parallax pages={2} style={{top: '0',left:'0'}} className="animation">

          
            <div className="MirWall">
            
         
                <ParallaxLayer offset={0} speed={0.1}>
                    <div className="animation_layer parallax" id="wallpapers"></div>
                </ParallaxLayer>
                <ParallaxLayer offset={0} speed={0.1}>
                    <div className="animation_layer parallax" id="mirage"></div>
                </ParallaxLayer>
              
                <div className="search">
                    <div className='search-wrapper'>
                        <input className='inputNick'
                         type="text" 
                         placeholder='m0nesy' 
                         value={nickname} 
                         onChange={(event) => setNickname(event.target.value)}  required
                        />
                        <button type="submit" className='buttonHome'> <SlMagnifier className='iconHome'/></button>
                    </div>
                </div>

                <ParallaxLayer offset={0} speed={-0.1}>
                    <div className="animation_layer parallax" id="ter"></div>
                </ParallaxLayer>

                <ParallaxLayer offset={0} speed={-0.3} horizontal >
                    <div className="animation_layer parallax" id="ct"></div>
                </ParallaxLayer>

                <ParallaxLayer offset={0} speed={0.11} vertical>
                    <div className="animation_layer parallax" id="firebox"></div>
                </ParallaxLayer>
                <ParallaxLayer offset={0} speed={1} horizontal>
                    <div className="animation_layer parallax" id="leftHand"></div>
                </ParallaxLayer>
                <ParallaxLayer offset={0} speed={-1} horizontal>
                    <div className="animation_layer parallax" id="rightHand"></div>
                </ParallaxLayer>

            </div>

            <ParallaxLayer offset={1} speed={0.25}>
              <TextBlock />
            </ParallaxLayer>
           
        </Parallax>

    </form>
    </div>

  )
}
export default HomePage;
