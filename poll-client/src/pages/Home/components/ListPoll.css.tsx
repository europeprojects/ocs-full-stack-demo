import {createStyles, makeStyles, Theme} from '@material-ui/core/styles';

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            width: '100%',
        },
        container: {
            maxHeight: 600,
            height: 600
        },
        paper: {
            width: '100%',
            marginBottom: theme.spacing(2),
        },
        table: {
            width: '100%',

        },
        visuallyHidden: {
            border: 0,
            clip: 'rect(0 0 0 0)',
            height: 1,
            margin: -1,
            overflow: 'hidden',
            padding: 0,
            position: 'absolute',
            top: 20,
            width: 1,
        },
        margin: {
            margin: theme.spacing(1),
        },
        formControl: {
            margin: theme.spacing(1),
            minWidth: 120,
        },
        selected : {
            backgroundColor: '#ececec'
        }


    }),
);

export default useStyles;
