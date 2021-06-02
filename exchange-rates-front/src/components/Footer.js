import React from 'react'
import Link from '@material-ui/core/Link';
import Typography from '@material-ui/core/Typography';


export default function Footer() {
    return (
        <div style={{margin: 40}}>
            <Typography variant="body2" color="primary" align="center">
                {'Copyright © '}
                <Link color="inherit" href="https://github.com/Lukasevic-Robert/Exchange-Rates-Full_Stack">
                    Robert Lukaševič
          </Link>{' '}
                {new Date().getFullYear()}
                {'.'}
            </Typography>
        </div>


    )
}
