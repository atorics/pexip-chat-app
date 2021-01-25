import React, { ChangeEvent } from 'react';
import {Button, Form} from 'react-bootstrap';

import { History } from 'history';
import {Profile} from '../../profile/profile';

interface LoginProps {
  history: History
}


class Login extends React.Component <LoginProps, {value: String}>{

   
    
    constructor(props: LoginProps) {
      super(props);
      this.state = {
        value: ""
      };
  
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
    
    handleChange(event: ChangeEvent<HTMLInputElement>) {
      this.setState({value: event.target.value});
    }

    handleSubmit(event: ChangeEvent<HTMLFormElement>) {
      
      const requestOptions = {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ name: this.state.value })
        }
        
      fetch('http://localhost:3000/profiles', requestOptions)
        .then(async response => {
          // check for error response
          if (!response.ok) {
            const data = await response.json();
              // get error message from body or default to response status
              const error = (data && data.message) || response.status;
              return Promise.reject(error);
          }
          const regex = /\/profiles\//ig;
          let id : String = response.headers.get('Location')?.replace(regex, '')!;

          let profile: Profile  = {
            id:  id,
            name: this.state.value
          };

          //this.setState({ postId: data.id })
          this.props.history.push({
            pathname: '/chat',
            state: {profile:profile}
          });
        })
        .catch(error => {
            //this.setState({ errorMessage: error.toString() });
            console.error('There was an error!', error);
        });

  
      event.preventDefault();
    
     
    }

    render() {
      return (
            <Form onSubmit={this.handleSubmit}>
              <Form.Group controlId="formBasicEmail">
                <Form.Label>Name</Form.Label>
                <Form.Control type="text" placeholder="Enter your name" 
                  onChange={this.handleChange} />
                <Form.Text className="text-muted">
                  This name would be shown in the chat
                </Form.Text>
              </Form.Group>              
              <Button variant="primary" type="submit">
                Start
              </Button>
            </Form>
        );
    }
  }

  export default Login;