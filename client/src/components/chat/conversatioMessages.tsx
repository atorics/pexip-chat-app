import { Message } from "./message";
import React from 'react';
import moment from "moment";


class ConversationMessages extends React.Component <{}, {isFetching: boolean, messages: Message[]}>{
    constructor(props: {} ){
        super(props);
        this.state = {
            
            isFetching: false,
            messages: []
        };
    }

    componentDidMount() {
        this.fetchConversationRest();
         this.fetchMessagesWS();
        
    }

    fetchConversationRest () {
        const requestOptions = {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
          }
          
        fetch('http://localhost:3000/channels/conversation/messages', requestOptions)
          .then(async response => {
            const data = await response.json();
            // check for error response
            if (!response.ok) {
              // get error message from body or default to response status
              const error = (data && data.message) || response.status;
              return Promise.reject(error);
            }
  
            this.setState({
              messages: data, 
              isFetching: true, 
            })
           
          })
          .catch(error => {
              //this.setState({ errorMessage: error.toString() });
              console.error('There was an error!', error);
          });
    }

    fetchMessagesWS() {
        const socket =  new WebSocket('ws://localhost:8080/ws/conversations');
        socket.addEventListener('message',async (event: any) => {
            const message = JSON.parse(event.data);
            this.state.messages.push(message);
            this.setState({
                messages: this.state.messages
            });
            
        });
    }

    render(){
        if (this.state.isFetching) {
            console.log(this.state.messages);
            return <div className="participants-names">{ 
                this.state.messages.map(
                (message) => {
                    let name:string = "";
                    let createOn: string = "";
                    if (message.profile !=null && message.profile.name != null){
                        name = message.profile.name.toString();
                    }

                    if(message.createdOn != null) {
                        createOn = message.createdOn;
                    }

                    return  <div><b>{name}</b> {moment(createOn).format('DD MMM HH:mm')}<br /><div key={message.id}>{message.message}<hr className="participants-name" /></div></div>
                }
            )}<hr /></div>
        } else {
            
            return <div>Loading...</div>
        }    
    }
}

export default ConversationMessages;