import { ChangeEvent, Component } from "react";

import { Button, Col, Form, Row, Tab, Tabs } from 'react-bootstrap';
import { Profile } from "../../profile/profile";
import ConversationMessages from "./conversatioMessages";
import { Message } from "./message";

interface ConversationProps {
  currentParticipant: Profile
}

class Conversation extends Component <ConversationProps, {message: string, messages: Message[], isFetching: boolean}>{

    constructor(props: ConversationProps) {
      super(props);
      this.state = {
        message: "", 
        messages:[],
        isFetching: false,
      };
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    handleChange(event: ChangeEvent<HTMLInputElement>) {
      this.setState({message: event.target.value});
    }

    handleSubmit(event: ChangeEvent<HTMLFormElement>) {
      const requestOptions = {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ message: this.state.message, profile: this.props.currentParticipant })
      }
      
      fetch('http://localhost:3000/channels/conversation', requestOptions)
      .then(async response => {
        const data = await response.json();
        // check for error response
        if (!response.ok) {
          // get error message from body or default to response status
          const error = (data && data.message) || response.status;
          return Promise.reject(error);
        }
       
       
      })
      .catch(error => {
          //this.setState({ errorMessage: error.toString() });
          console.error('There was an error!', error);
      });

      event.preventDefault();
    }

    

    render(){
        return <div>
            <div className="ex3">
            <ConversationMessages></ConversationMessages>
            </div>
            <div>
              <Form onSubmit={this.handleSubmit}>
                
                <Form.Group as={Row} controlId="formPlaintextEmail">
                  
                  <Col sm="9">
                  <Form.Control type="text" placeholder="Message" onChange={this.handleChange}  />
                  </Col>
                  <Col sm="1">
                    <Button variant="primary" type="submit">Send</Button>
                  </Col>
                </Form.Group>
              </Form>
            </div>
        </div>
    }
}

export default Conversation;