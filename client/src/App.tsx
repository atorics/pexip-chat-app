import React from 'react';
import './App.css';
import Login from './components/login/login';
import Chat from './components/chat/chat';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  useParams, 
  
} from "react-router-dom";
import { Col, Container, Row } from 'react-bootstrap';

export default class App extends React.Component {
  render() {
    return (
    <Router>
      <div className="App">
        <Container>
          <Row>
            <Col></Col>
            <Col xs={7}>
              <div className="App-content">
                <Route path="/" exact component={Login} />
                <Route path="/chat" component={Chat} />
              </div>
            </Col>
            <Col></Col>
          </Row>
          
        </Container>
        {/* <header className="App-header">
        </header> */}
        
      </div>
    </Router>
    );
  }
}