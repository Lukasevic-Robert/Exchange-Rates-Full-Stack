import axios from 'axios';


const API_BASE_URL = `http://localhost:8080/api/rates`;

const getCurrencyList = (params) => {
    return axios.post(API_BASE_URL + '/getFxRatesForCurrency', params);
}

const getAvailableCurrencyList = () => {
    return axios.get(API_BASE_URL + '/getAvailableCurrencies');
}

const getFxRatesForSpecificDate = (params) => {
    return axios.post(API_BASE_URL + '/FxRates', params);
}



export default {
    getCurrencyList,
    getAvailableCurrencyList,
    getFxRatesForSpecificDate
};