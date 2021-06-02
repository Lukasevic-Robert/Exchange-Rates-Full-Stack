import React, { useEffect, useState, useContext } from 'react'
import { makeStyles } from '@material-ui/core/styles';
import RatesService from '../../services/RatesService';
import { RatesContext } from '../../context/RatesContext';
import { TableCell, Table, TableBody, TableContainer, TableHead, TableRow, Paper } from '@material-ui/core';
import ArrowDropUpIcon from '@material-ui/icons/ArrowDropUp';
import ArrowDropDownIcon from '@material-ui/icons/ArrowDropDown';
import StopIcon from '@material-ui/icons/Stop';
import { useHistory } from "react-router-dom";

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
    tableCellName: {
        '&:hover': {
            cursor: 'pointer',
            fontWeight: 'bold'
        }
    }
});

export default function AllInOneRatesTable() {

    const value = useContext(RatesContext);
    const classes = useStyles();
    let history = useHistory();

    useEffect(() => {
        if (!isEmptyObject(value.allInOnerequest)) {
            getCurrencyList();
        }

    }, [value.refreshAllInOneRatesTable, value.allInOnerequest]);

    const getCurrencyList = async () => {
        await RatesService.getFxRatesForSpecificDate(value.allInOnerequest).then((response) => {
            console.log('getCurrencyList' + JSON.stringify(response.data))
            // value.requestedRates(response.data);
            value.setRequestedRates(response.data);

        }).catch((error) => {
            console.log(error);
        }
        );
    }

    const isEmptyObject = (value) => {
        return (Object.prototype.toString.call(value) === '[object Object]' &&
            JSON.stringify(value) === '{}')
    }

    const handleRedirect = (v) => {
        value.setRequestedCcyCode(v);
        history.push(`/specific`)
    }

    return (
        <TableContainer className={classes.container} component={Paper}>
            <Table className={classes.table} aria-label="simple table">
                <TableHead>
                    <TableRow className={classes.tableRow} style={{ backgroundColor: '#f8ffd9' }}>
                        <TableCell style={{ fontSize: 20 }}>Currency name</TableCell>
                        <TableCell style={{ fontSize: 20 }} align="left">Currency code</TableCell>
                        <TableCell style={{ fontSize: 20 }} align="center">Ratio</TableCell>
                        <TableCell style={{ fontSize: 20 }} align="right">Change unit</TableCell>
                        <TableCell style={{ fontSize: 20 }} align="right">Change %</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {value.requestedRates && value.requestedRates.map((row, index) => (
                        <TableRow className={classes.tableRow} key={index}>
                            <TableCell className={classes.tableCellName} style={{ fontSize: 17, color: '#414866'}} onClick={() => handleRedirect(row.ccyCode)} component="th" scope="row">
                                {row.ccyName}
                            </TableCell>
                            <TableCell style={{ fontSize: 17 }} align="left">{row.ccyCode}</TableCell>
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
