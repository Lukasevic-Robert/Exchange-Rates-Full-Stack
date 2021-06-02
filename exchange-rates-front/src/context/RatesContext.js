import React, { createContext, useState } from 'react';

const RatesContext = createContext();
const { Provider } = RatesContext;

const RatesContextProvider = ({ children }) => {

    const [refreshRatesTable, setRefreshRatesTable] = useState(false);
    const [refreshAllInOneRatesTable, setRefreshAllInOneRatesTable] = useState(false);
    const [requestedRates, setRequestedRates] = useState([]);
    const [request, setRequest] = useState({});
    const [requestedCcyCode, setRequestedCcyCode] = useState('USD');
    const [allInOnerequest, setAllInOnerequest] = useState({});


    return (
        <Provider value={{
            refreshRatesTable, setRefreshRatesTable,
            requestedRates, setRequestedRates,
            request, setRequest,
            allInOnerequest, setAllInOnerequest,
            refreshAllInOneRatesTable, setRefreshAllInOneRatesTable,
            requestedCcyCode, setRequestedCcyCode
        }}>
            {children}
        </Provider>
    )

};
export { RatesContext, RatesContextProvider };