import React, { Component } from 'react';
import { Tab, Tabs } from 'react-bootstrap';
import { RouteComponentProps, StaticContext } from 'react-router';
import ProfileList from '../../profile/profileList';

import Conversation from './conversation';

type Props = RouteComponentProps<{}, StaticContext, {profile: { id: string, name:string }; }>;

class Chat extends Component <Props, {participantsCount: number}>{

    constructor(props: Props ){
        super(props);
        this.state = {
            participantsCount: 0
        };

        this.childHandler = this.childHandler.bind(this)
    }

    /*
     Function that gets called when
     we bubble up our `return` from Child to update number of participants
    */
   childHandler(dataFromChild: number) {
        this.setState({
            participantsCount: dataFromChild
        });
    }

    
    render() {
        let profile = this.props.location.state.profile;
     
        let title = "Participants (" + this.state.participantsCount + ")";

        return <Tabs defaultActiveKey="profile" id="uncontrolled-tab-example">
          <Tab eventKey="profile" title={title}>
            <ProfileList currentParticipant={profile} participantsCount={this.childHandler} ></ProfileList>
          </Tab>
          <Tab eventKey="chat" title="Chat" >
            <Conversation currentParticipant={profile}></Conversation>
          </Tab>
      </Tabs>
        
    }
}

export default Chat;