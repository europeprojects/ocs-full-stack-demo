import {createStyles, makeStyles, Theme} from '@material-ui/core/styles';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    selected : {
      backgroundColor: '#ececec'
    }
  }),
);

export default useStyles;
