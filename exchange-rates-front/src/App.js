
import './App.css';
import Routes from './components/Routes';
import { BrowserRouter as Router } from "react-router-dom";
import Header from './components/header.js'
import Footer from './components/Footer.js'
import { RatesContextProvider } from './context/RatesContext';

function App() {
  return (<Router>
    <RatesContextProvider>
      <div className="App">
        <div id="wrapper">
          <Header />
          <Routes />
          <Footer/>
        </div>
      </div>
    </RatesContextProvider>
  </Router>
  );
}

export default App;
