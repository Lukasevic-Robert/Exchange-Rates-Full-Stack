import React, { useEffect, useState, useContext } from 'react'
import { makeStyles } from '@material-ui/core/styles';
import RatesService from '../../services/RatesService';
import { RatesContext } from '../../context/RatesContext';
import { TableCell, Table, TableBody, TableContainer, TableHead, TableRow, Paper } from '@material-ui/core';
import ArrowDropUpIcon from '@material-ui/icons/ArrowDropUp';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import StopIcon from '@material-ui/icons/Stop';

const useStyles = makeStyles({
    table: {
        minWidth: 650,
    },
    container: {
        marginTop: 60,
        minWidth: 1200
    },
    tableRow: {
        height: 60,
    },
});

export default function SpecificRatesTable() {

    const value = useContext(RatesContext);
    const classes = useStyles();
    const [specificCcyRates, setSpecificCcyRates] = useState([]);


    useEffect(() => {
        if (!isEmptyObject(value.request)) {
            getCurrencyList();
        }

    }, [value.refreshRatesTable, value.request]);

    const getCurrencyList = async () => {
        await RatesService.getCurrencyList(value.request).then((response) => {
            setSpecificCcyRates(response.data);
        }).catch((error) => {
            console.log(error);
        }
        );

    }
    const isEmptyObject = (value) => {
        return (Object.prototype.toString.call(value) === '[object Object]' &&
            JSON.stringify(value) === '{}')
    }

    return (
        <TableContainer className={classes.container} component={Paper}>
            <Table className={classes.table} aria-label="simple table">
                <TableHead>
                    <TableRow className={classes.tableRow} style={{ backgroundColor: '#f8ffd9'}}>
                        <TableCell style={{ fontSize: 20 }}>Date</TableCell>
                        <TableCell style={{ fontSize: 20 }} align="center">Ratio</TableCell>
                        <TableCell style={{ fontSize: 20 }} align="right">Change unit</TableCell>
                        <TableCell style={{ fontSize: 20 }} align="right">Change %</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {specificCcyRates && specificCcyRates.map((row, index) => (
                        <TableRow className={classes.tableRow} key={index}>
                            <TableCell style={{ fontSize: 17 }} component="th" scope="row">{row.date}</TableCell>
                            <TableCell style={{ fontSize: 17 }} align="center">{row.ratio}</TableCell>
                            <TableCell style={{ fontSize: 17 }} align="right">{row.changeUnit + ` `}{` ` + row.changeUnit < 0 ? <ArrowDropDownIcon style={{ color: '#ff3636', position: 'absolute', fontSize: 25 }} /> : row.changeUnit > 0 ? <ArrowDropUpIcon style={{ color: '#36ff83', position: 'absolute', fontSize: 25 }} /> : <StopIcon style={{ color: '#ebedec', position: 'absolute' }} />}</TableCell>
                            <TableCell style={{ fontSize: 17 }} align="right">{row.changePercent + ` %`}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    )
}