import React from "react";

import {ThemeProvider, withStyles} from '@material-ui/core/styles';
import {CssBaseline} from "@material-ui/core";
import {BrowserRouter} from "react-router-dom";
import {Provider} from "react-redux";
import Theme from "./Theme";
import store from "./store"
import AppRouter from "./AppRouter";
import {SnackbarProvider} from "notistack";

const App: React.FC<any> = () => {
  return (
    <ThemeProvider theme={Theme}>
      <CssBaseline/>
      <AppCss/>
      <BrowserRouter>
        <Provider store={store}>
          <SnackbarProvider anchorOrigin={{horizontal: 'center', vertical: 'top'}}>
            <AppRouter/>
          </SnackbarProvider>
        </Provider>
      </BrowserRouter>
    </ThemeProvider>)
};

const styles = {
  '@global': {
    'html,body,#app': {
      height: '100%'
    }
  }
}
const AppCss = withStyles(styles)(() => null);

export default App;
