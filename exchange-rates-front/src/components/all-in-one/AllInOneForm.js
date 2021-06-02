import React, { useState, useEffect, useContext } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { FormControl, TextField, Grid, Button } from '@material-ui/core';
import * as moment from 'moment';
import { ValidatorForm } from 'react-material-ui-form-validator';
import { RatesContext } from '../../context/RatesContext';


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

export default function AllInOneForm() {

    const { setAllInOnerequest, refreshAllInOneRatesTable, setRefreshAllInOneRatesTable } = useContext(RatesContext);

    const classes = useStyles();
    const [date, setDate] = useState(moment().format("YYYY-MM-DD"));

    useEffect(() => {

        let request = { tp: 'EU', dt: date };
        setAllInOnerequest(request);
    }, [])


    const handleChangeDate = (event) => {
        setDate(event.target.value)
    }

    const handleFormSubmit = (event) => {
        event.preventDefault();
        let request = { tp: 'EU', dt: date };
        setAllInOnerequest(request);
        setRefreshAllInOneRatesTable(!refreshAllInOneRatesTable);
    }

    return (
        <ValidatorForm id="rates-form" onSubmit={handleFormSubmit}>
            <Grid container justify="center">
                <FormControl>
                    <TextField
                        id="date"
                        label="Date"
                        type="date"
                        value={date}
                        className={classes.textField}
                        onChange={handleChangeDate}
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />

                </FormControl>
                <Button id="submit-all-in-one" color="secondary" className={classes.submit} variant="contained" type="submit">Submit</Button>
            </Grid>

        </ValidatorForm>
    )
}
