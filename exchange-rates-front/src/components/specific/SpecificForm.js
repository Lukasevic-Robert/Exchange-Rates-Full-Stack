import React, { useState, useEffect, useContext } from 'react';
import RatesService from '../../services/RatesService';
import { makeStyles } from '@material-ui/core/styles';
import { FormControl, Input, TextField, Grid, Select, InputLabel, MenuItem, Button } from '@material-ui/core';
import * as moment from 'moment';
import { ValidatorForm } from 'react-material-ui-form-validator';
import { RatesContext } from '../../context/RatesContext';
import { useHistory } from "react-router-dom";


const useStyles = makeStyles((theme) => ({
    container: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    textField: {
        marginLeft: theme.spacing(1),
        marginRight: theme.spacing(1),
        width: 200,
    },
    formControl: {

        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
}));

export default function SpecificForm() {

    const { setRequest, setRefreshRatesTable, refreshRatesTable, requestedCcyCode } = useContext(RatesContext);
    const classes = useStyles();
    const [rates, setRates] = useState([]);
    const [tp, setTp] = useState('EU');
    const [ccy, setCcy] = useState(requestedCcyCode);
    const [dateFrom, setDateFrom] = useState(moment().subtract(1, 'months').format("YYYY-MM-DD"));
    const [dateTo, setDateTo] = useState(moment().format("YYYY-MM-DD"));
    const [availableCcy, setAvailableCcy] = useState([]);
    let history = useHistory();



    useEffect(() => {
        getAvailableCurrencyList();
        let request = { tp: tp, ccy: ccy, dtFrom: dateFrom, dtTo: dateTo };
        setRequest(request);
    }, [])



    const getAvailableCurrencyList = async () => {

        await RatesService.getAvailableCurrencyList().then(response => {
            let tempArray = [];
            tempArray = response.data.currencyItem;
            tempArray.shift();
            setAvailableCcy(tempArray);
        })

    }

    const handleChangeDateFrom = (event) => {
        setDateFrom(event.target.value)
    }

    const handleChangeDateTo = (event) => {
        setDateTo(event.target.value)
    }

    const handleChangeCcy = (event) => {
        setCcy(event.target.value);
    }

    const handleFormSubmit = (event) => {
        event.preventDefault();
        let request = { tp: 'EU', ccy: ccy, dtFrom: dateFrom, dtTo: dateTo };
        setRequest(request);
        setRefreshRatesTable(!refreshRatesTable);
    }


    return (

        <ValidatorForm id="rates-form" onSubmit={handleFormSubmit}>
            <Grid container justify="space-around">
                <FormControl className={classes.formControl}>
                    <InputLabel id="demo-simple-select-helper-label">Currency</InputLabel>
                    <Select
                        labelId="demo-simple-select-helper-label"
                        id="demo-simple-select-helper"
                        value={ccy}
                        onChange={handleChangeCcy}
                    >
                   
                        {availableCcy.map((item, index) =>
                            <MenuItem key={index} value={item.valiutos_kodas}>{item.pavadinimas + ` (` + item.valiutos_kodas + `)`}</MenuItem>
                        )}
                    </Select>
                </FormControl>
                <FormControl>
                    <Grid container justify="space-around">
                        <TextField
                            id="date"
                            label="Date From"
                            type="date"
                            value={dateFrom}
                            className={classes.textField}
                            onChange={handleChangeDateFrom}
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />
                        <TextField
                            id="date"
                            label="Date To"
                            type="date"
                            value={dateTo}
                            className={classes.textField}
                            onChange={handleChangeDateTo}
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />
                    </Grid>

                </FormControl>
              <FormControl style={{display: 'flex', flexDirection: 'row'}}>
                    <Button id="submit-specific" color="secondary" style={{marginRight: 10}} className={classes.submit} variant="contained" type="submit">Submit</Button>
                    <Button id="redirect-to-all-in-one" color="primary" style={{}}  variant="contained" onClick={() => history.push('/all-in-one')}>Back</Button>
              </FormControl>
            </Grid>

        </ValidatorForm>

    )
}
