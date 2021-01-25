import React from 'react';
import { Profile } from './profile';


interface ProfileProps {
    participantsCount: Function, 
    currentParticipant: Profile
}

class ProfileList extends React.Component <ProfileProps, {firstLoad: boolean, isFetching: boolean, profiles: Profile[]}>{
    
    constructor(props: ProfileProps ){
        super(props);
        this.state = {
            firstLoad: false,
            isFetching: false,
            profiles: []
        };
    }

    componentDidMount() {
        this.fetchUsersRest();
        this.fetchUserWS();
        
    }

    fetchUserWS() {
        const socket =  new WebSocket('ws://localhost:8080/ws/profiles');
        socket.addEventListener('message',async (event: any) => {
            const profiles = JSON.parse(event.data);
            this.state.profiles.push(profiles);
            this.setState({
                profiles: this.state.profiles
            });
            this.props.participantsCount(this.state.profiles.length);
        });
    }

    fetchUsersRest () {
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
          }
          
        fetch('http://localhost:3000/profiles', requestOptions)
          .then(async response => {
            const data = await response.json();
            // check for error response
            if (!response.ok) {
              // get error message from body or default to response status
              const error = (data && data.message) || response.status;
              return Promise.reject(error);
            }
           
            this.setState({
                profiles: data, 
                isFetching: true, 
                firstLoad: true
              }
            );
            this.props.participantsCount(data.length);
          })
          .catch(error => {
              //this.setState({ errorMessage: error.toString() });
              console.error('There was an error!', error);
          });
    }

    render(){
        let currentParticipant = this.props.currentParticipant;
        if (this.state.isFetching) {
            return <div className="participants-names">{ this.state.profiles.map(
                (profile) => {
                    let cssClass: string = "";
                    if (currentParticipant.id === profile.id){
                        cssClass = "currentParticipant";
                    }
                    return  <div key={profile.id.toString()} className={cssClass}>{profile.name}<hr className="participants-name" /></div>
                }
            )}<hr /></div>;
        } else {
            
            return <div>Loading...</div>;
        }
    }
}

export default ProfileList;