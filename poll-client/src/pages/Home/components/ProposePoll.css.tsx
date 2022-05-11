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

    }),
);

export default useStyles;
