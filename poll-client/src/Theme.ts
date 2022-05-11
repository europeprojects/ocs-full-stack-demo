import {orange} from '@material-ui/core/colors';
import {createMuiTheme} from '@material-ui/core/styles';


const Theme = createMuiTheme({
  palette: {
    primary: {
      main: '#2842c4',
    },

    error: {
      main: orange.A400,
    },
    background: {
      default: '#fff',
    },
  },
    typography: {
      button: {
        textTransform: 'none'
      }
    },
    overrides: {
      MuiTableCell: {
        root: {
          padding: 10,
          textAlign: "center"
        }
      }
    }
});

export default Theme;
