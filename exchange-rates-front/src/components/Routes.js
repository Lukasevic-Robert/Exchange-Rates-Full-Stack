import React from 'react'
import { Switch, Route, Redirect } from "react-router-dom";
import Specific from './specific/Specific';
import AllInOne from './all-in-one/AllInOne';

export default function Routes() {
    return (
     
            <Switch>
                <Route exact path="/" component={AllInOne} />
                <Route path="/all-in-one" component={AllInOne} />
                <Route path="/specific" component={Specific} />
            </Switch>

    
    )
}
