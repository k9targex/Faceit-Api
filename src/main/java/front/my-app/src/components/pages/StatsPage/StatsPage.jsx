
import './StatsPage.css'
import Cookies from 'js-cookie';
import axios from "axios";
import React, { useState,useEffect  } from 'react';
import {  useNavigate } from "react-router-dom"
import { GiSkullCrack,GiTripleSkulls,GiAngelOutfit } from "react-icons/gi";
import { FiPercent } from "react-icons/fi";
import { LuCrown } from "react-icons/lu";
import { BsGraphUp } from "react-icons/bs";
import { IoHeartDislikeOutline  } from "react-icons/io5";
import { RiHeartAdd2Line } from "react-icons/ri";
import { SlMagnifier,SlGraph  } from "react-icons/sl";
import { IoMdExit } from "react-icons/io";
export const StatsPage = () => {
  
  const navigate = useNavigate();
  const [nickname, setNickname] = useState('');
  const [tokenState,setTokenState] = useState('');
  const [isPlayerInList, setIsPlayerInList] = useState(false);
  const [playerStatusStats, setPlayerStatusStats] = useState(false);
 
  useEffect(() => {
    const token = Cookies.get('token');
      if (!token) {
          navigate("/message");

      } else {
          setTokenState(token);
      }
  }, []);

  const handleClickStats = (event) => {
    setPlayerStatusStats(false); 
    console.log("sdf");
  };


  useEffect(() => { 
    if(playerStatusStats)
      document.addEventListener('click', handleClickStats);
  return () => {
      if(playerStatusStats)
    document.removeEventListener('click', handleClickStats);
  };
  }, [playerStatusStats]);


  const backgroundImageUrl = Cookies.get('avatar');
  const username = Cookies.get('username');

  const k_d =Cookies.get('k_d');
  const av_headshots =Cookies.get('av_headshots');
  const tot_headshots =Cookies.get('tot_headshots');
  const matches =Cookies.get('matches');

  const wins =Cookies.get('wins');
  const win_rate =Cookies.get('win_rate');
  const current_win =Cookies.get('current_win');
  const longest_win =Cookies.get('longest_win');
  const nicknamesString = Cookies.get('favoritePlayers');

 
  const nicknames = nicknamesString ? JSON.parse(nicknamesString) : [];
  
    const avatarStyle = {
        backgroundImage: `url(${backgroundImageUrl})`, 
        backgroundSize: 'cover', 
        backgroundPosition: 'center', 
    };
    const handleExit = () => {
      // �믮����� ।�४� �� ��㣮� �������
      navigate("/signin");
    };
    const handleSubmit = async (event,nickname) => {
        event.preventDefault(); 
        const data = {
            nickname: nickname
        };
    

      axios
      .post('http://localhost:8080/api/v1/faceit/info', data,
        {
        headers:
        {
          Authorization: `Bearer ${tokenState}`
        }
      
      })
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
          if (error.response.status === 404) {
             
            setPlayerStatusStats(true);
          }
          if (error.response.status === 401) {
            navigate("/message");
          }
          else{
            console.error("Error:", error);
          }
        });
        
    };
    const handleDislike = async (event,nickname) => {
      event.preventDefault(); 
      const data = {
          nickname: nickname
      };
  
   
    axios
      .delete("http://localhost:8080/users/deletePlayer",{

          params: {
              nickname: data.nickname
          },
          headers: {
              Authorization: `Bearer ${tokenState}`
          }
      } )
      .then((response) => {
        const   nicknamesString = Cookies.get('favoritePlayers');
        console.log(nicknamesString);

 
        const nicknames = nicknamesString ? JSON.parse(nicknamesString) : [];  
        console.log(nicknames);
        const excludedNickname = data.nickname;  

        const filteredNicknames = nicknames.filter(player => player !== excludedNickname);

        console.log(excludedNickname);
        
        console.log(filteredNicknames);
        const updatedNicknamesString = JSON.stringify(filteredNicknames);
        

        Cookies.set('favoritePlayers',updatedNicknamesString);
        window.location.reload();
      })
      .catch((error) => {
        if (error.response.status === 401) {
          navigate("/message");
        }
          console.error("haha:", error);
      });
   
  };
  const handleLike = async (event) => {
    event.preventDefault(); 
    const data = {
        nickname: Cookies.get('username')
    };
    console.log(data.nickname);

    axios.post("http://localhost:8080/users/addPlayer?nickname=" + data.nickname, null, {
      headers: {
          Authorization: `Bearer ${tokenState}`
      }
  })
    .then((response) => {
      const   nicknamesString = Cookies.get('favoritePlayers');
      const nicknames = nicknamesString ? JSON.parse(nicknamesString) : [];  
      const nicknameToAdd = data.nickname; 
      if (!nicknames.includes(nicknameToAdd)) {
        nicknames.push(nicknameToAdd);
    
        const updatedNicknamesString = JSON.stringify(nicknames);
        
        Cookies.set('favoritePlayers', updatedNicknamesString);
  
        console.log(updatedNicknamesString);
        window.location.reload();
      }
    })
    .catch((error) => {
      if (error.response.status === 401) {
        navigate("/message");
      }
    });
    
 
};

useEffect(() => {
    const checkPlayerInList = () => {
    const nickname = Cookies.get('username');
    const nicknamesString = Cookies.get('favoritePlayers');
    const nicknames = nicknamesString ? JSON.parse(nicknamesString) : [];

      setIsPlayerInList(nicknames.includes(nickname));
  };

  checkPlayerInList();
}, [nickname]);



  return (
    <div className='stats-wrapper'>
            <form  onSubmit={(event) => handleSubmit(event, nickname)} >

      <div className='inputPlayer'>

      {playerStatusStats && (
                    <div className='noPlayerStats'>
                     <p>No such player</p>
                    </div>
                 )}


      {!isPlayerInList && (
      <button
        type="button"
        className='like'
        onClick={(event) => handleLike(event)} 
    >
        <RiHeartAdd2Line className='likeIcon'/>
    </button>
    )}


      <input className='inputFaceit'
                         type="text" 
                         placeholder='m0nesy' 
                         value={nickname} 
                         onChange={(event) => setNickname(event.target.value)}  required
                        />
      <button type="submit" className='buttonStats' > 
      <SlMagnifier className='findIcon'/>
      </button>

      <button type="submit" className='buttonExit' onClick={handleExit} > 
        <IoMdExit className='exitIcon'/>
      </button>
      </div>
      
      <div className='main-info'>
          <div className='avatarBox'>
            <div className='userAvatar' style={avatarStyle}></div>
          </div>
        <div className='userInfo'>
    
          <p className='userName'>{username}</p>
        </div>
    
      </div>
      <div className='main-stats'>
          
              <div className='stats k_d'>
                <p className='info'>K/D</p>
                <GiAngelOutfit   className='iconFaceit'/>
                <p className='user'>{k_d}</p>
                
              </div>

              <div className='stats av_headshots'>
                 <p className='info'>Average headshots</p>
                 <GiSkullCrack className='iconFaceit'/>
                 <p className='user'>{av_headshots}</p>
              </div>

              <div className='stats total_headshots'>
               <p className='info'>Total headshots</p>
                <GiTripleSkulls className='iconFaceit'/>
                <p className='user'>{tot_headshots}</p>

              </div>

              <div className='stats matches'>
                <p className='info'>Matches</p>
                <SlGraph  className='iconFaceit'/>
                <p className='user'>{matches}</p>

              </div>

              <div className='stats wins'>
                <p className='info'>Wins</p>
                <LuCrown  className='iconFaceit'/>
                <p className='user'>{wins}</p>

              </div>

              <div className='stats win_rate'>
                <p className='info'>Win rate</p>
                <FiPercent className='iconFaceit'/>
                <p className='user'>{win_rate}</p>

              </div>

              <div className='stats current_win'>
                <p className='info'>Current strick</p>
                <LuCrown  className='iconFaceit'/>

                <p className='user'>{current_win}</p>

              </div>
          
              <div className='stats longest_win'>
                <p className='info'>Longest strick</p>
                <BsGraphUp  className='iconFaceit'/>

   
                  <p className='user'>{longest_win}</p>


              </div>
        

      </div>
      <div className='favorite-players'>
        
      <p className='title'>Favorite players
      </p>
      <div className="listOfPlayers">
  
            <ul>
                {nicknames.map((nickname, index) => (
                   <div 
                   className='playerNickname'>
                    
                    <button type="button"         className='choosePlayer'
                     onClick={(event) => handleSubmit(event, nickname)} 
                     >
                     
                     <div className='boxForNick'>
                       <div className='hide'>
                      {nickname}
                      </div>
                      
                      </div>
                      </button> 
                      
                      <button className='dislike'  type="button"         
                     onClick={(event) => handleDislike(event, nickname)} 
                     >
                      <IoHeartDislikeOutline className='dislikeIcon' />
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