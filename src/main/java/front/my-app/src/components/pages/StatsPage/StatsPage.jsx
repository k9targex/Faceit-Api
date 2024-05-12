
import './StatsPage.css'
import Cookies from 'js-cookie';
import axios from "axios";
import React, { useState,useContext, useRef } from 'react';
import { SiFaceit } from "react-icons/si";
import {  Link,useNavigate } from "react-router-dom"
import { GiSkullCrack,GiTripleSkulls } from "react-icons/gi";
import { FiPercent } from "react-icons/fi";
import { LuCrown } from "react-icons/lu";
import { BsGraphUp } from "react-icons/bs";
export const StatsPage = () => {
  const navigate = useNavigate();
  const [nickname, setNickname] = useState('');


  const backgroundImageUrl = Cookies.get('avatar');
  const username = Cookies.get('username');
  const country = Cookies.get('country');

  const k_d =Cookies.get('k_d');
  const av_headshots =Cookies.get('av_headshots');
  const tot_headshots =Cookies.get('tot_headshots');
  const matches =Cookies.get('matches');

  const wins =Cookies.get('wins');
  const win_rate =Cookies.get('win_rate');
  const current_win =Cookies.get('current_win');
  const longest_win =Cookies.get('longest_win');
  const nicknamesString = Cookies.get('favoritePlayers');

  
  // if (!nicknamesString || nicknamesString === '') {
  //     return <div>No favorite players selected</div>;
  // }

  // Преобразуем строку обратно в массив
  const nicknames = JSON.parse(nicknamesString);
    // Стили для установки заднего фона
    const avatarStyle = {
        backgroundImage: `url(${backgroundImageUrl})`, // Устанавливаем фоновое изображение
        backgroundSize: 'cover', // Устанавливаем размер фона
        backgroundPosition: 'center', // Устанавливаем позицию фона
    };
    // const handleButtonClick =  async (event,)  => {
    //   event.preventDefault(); 
    //   setNickname(clickedNickname);
    //   console.log(clickedNickname);
      
    // }
    const handleSubmit = async (event,nickname) => {
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
                     window.location.reload();
        })
        .catch((error) => {
            console.error("haha:", error);
        });
        
    };


  return (
    <div className='stats-wrapper'>
            <form  onSubmit={(event) => handleSubmit(event, nickname)} >

      <div className='inputPlayer'>
      <input className='inputFaceit'
                         type="text" 
                         placeholder='m0nesy' 
                         value={nickname} 
                         onChange={(event) => setNickname(event.target.value)}  required
                        />
      <button type="submit" className='buttonStats' > 
      </button>
      </div>
      
      <div className='main-info'>
          <div className='avatarBox'>
            <div className='userAvatar' style={avatarStyle}></div>
          </div>
        <div className='userInfo'>
          <p className='user'>{username}</p>
        </div>
      </div>
      <div className='main-stats'>
          
              <div className='stats k_d'>
                <p className='user'>{k_d}</p>
              </div>

              <div className='stats av_headshots'>
                <p className='user'>{av_headshots}</p> <GiSkullCrack className='iconFaceit'/>
              </div>

              <div className='stats total_headshots'>
                <p className='user'>{tot_headshots}</p>
                <GiTripleSkulls className='iconFaceit'/>
              </div>

              <div className='stats matches'>
                <p className='user'>{matches}</p>
                <GiSkullCrack className='iconFaceit'/>
              </div>

              <div className='stats wins'>
                <p className='user'>{wins}</p>
                <LuCrown  className='iconFaceit'/>
              </div>

              <div className='stats win_rate'>
                <p className='user'>{win_rate}</p>
                <FiPercent className='iconFaceit'/>
              </div>

              <div className='stats current_win'>
                <p className='user'>{current_win}</p>
                <BsGraphUp  className='iconFaceit'/>
              </div>
          
              <div className='stats longest_win'>
                  <p className='user'>{longest_win}</p>
                  <LuCrown  className='iconFaceit'/>
                  <LuCrown  className='iconFaceit'/>
                  <LuCrown  className='iconFaceit'/>

              </div>
        

      </div>
      <div className='favorite-players'>
        
      <p className='title'>Favorite Players
      </p>
      <div className="listOfPlayers">
  
            <ul>
                {nicknames.map((nickname, index) => (
                   <div 
                   className='playerNickname'>
                    
                    <button type="button"         className='choosePlayer'
                     onClick={(event) => handleSubmit(event, nickname)} 
                     > <div className='boxForNick'>
                       <div className='hide'>
                      {nickname}
                      </div>
                      </div>

                      </button>
                    
                    </div>
                    

                ))}
            </ul>
            </div>
      </div>
      </form>
    </div>
  )
}
export default StatsPage;