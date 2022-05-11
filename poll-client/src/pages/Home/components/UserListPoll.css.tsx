import {createStyles, makeStyles, Theme} from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
    root: {
        width: 600,
        backgroundColor: theme.palette.background.paper,
        padding: theme.spacing(5, 5),
    },
    inline: {
        display: 'inline',
    },
}));

export default useStyles;
