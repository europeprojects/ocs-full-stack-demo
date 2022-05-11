import React, {useEffect} from 'react';
import clsx from 'clsx';
import Drawer from '@material-ui/core/Drawer';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import {createStyles, makeStyles, Theme} from "@material-ui/core/styles";
import ProfileMenu from "./ProfileMenu";
import {Box, LinearProgress} from "@material-ui/core";
import {APP_MENU} from "../../data/AppMenu";
import MainMenu, {MainMenuItem} from "../../components/MainMenu";
import {ApiStatusServiceInstance} from "../../components/ApiClient";
import {useSnackbar} from "notistack";
import Protected from "../../components/Protected";
import {PollUser} from "../../types/AuthModel";

const drawerWidth = 0;

export const useAppLayoutStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: 'flex',
      height: '100%'
    },
    appBar: {
      transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
      zIndex: theme.zIndex.drawer + 1,
    },
    appBarShift: {
      width: `calc(100% - ${drawerWidth}px)`,
      marginLeft: drawerWidth,
      transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.easeOut,
        duration: theme.transitions.duration.enteringScreen,
      }),
    },
    menuButton: {
      marginRight: theme.spacing(2),
    },
    hide: {
      display: 'none',
    },
    drawer: {
      width: drawerWidth,
      flexShrink: 0,
    },
    drawerPaper: {
      width: drawerWidth,
    },
    drawerHeader: {
      display: 'flex',
      alignItems: 'center',
      padding: theme.spacing(2.5, 0),
      justifyContent: 'flex-end',
    },
    content: {
      flexGrow: 1,
      transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
      }),
      marginLeft: -drawerWidth,
    },
    contentShift: {
      transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.easeOut,
        duration: theme.transitions.duration.enteringScreen,
      }),
      marginLeft: 0,
    },
    progress: {
      backgroundColor: "#FFC300"//
    }
  }),
);

export default function AppLayout({children}: React.PropsWithChildren<any>) {
  const {enqueueSnackbar} = useSnackbar();
  const classes = useAppLayoutStyles();
  const [open, setOpen] = React.useState(false);
  const [loading, setLoading] = React.useState(false);

  const handleMenuClick = () => {
    setOpen(state => !state);
  };

  useEffect(() => {
    ApiStatusServiceInstance.onProgress("progress-listener", inProgress => {
      setLoading(inProgress)
    })
    ApiStatusServiceInstance.onError("error-listener", message => {
      enqueueSnackbar(message, {   variant: 'warning',autoHideDuration: 2000, preventDuplicate: true,anchorOrigin: {vertical: 'top', horizontal: 'right'}})
    })
  }, [enqueueSnackbar])

  return (
    <div className={classes.root}>
      <AppBar
        position="fixed"
        className={classes.appBar}
      >
        <Toolbar>

          <Box flexGrow={1}>
            <Typography variant="h6" noWrap onClick={() => { window.location.href = "/"; }}>
                Anket Uygulaması
            </Typography>
          </Box>
          <Protected>
            {(user: PollUser) => (
              <ProfileMenu user={user}/>
            )}
          </Protected>
        </Toolbar>
        {loading && (
          <LinearProgress
            color="primary"
            classes={{
              colorPrimary: classes.progress,
            }}/>
        )}
      </AppBar>
      <Drawer
        className={classes.drawer}
        variant="persistent"
        anchor="left"
        open={open}
        classes={{
          paper: classes.drawerPaper,
        }}
      >

        <Divider/>
        <MainMenu items={APP_MENU as MainMenuItem[]}/>
      </Drawer>
      <div className={clsx(classes.content, {
        [classes.contentShift]: open,
      })}>
        <div className={classes.drawerHeader}/>
        {children}
      </div>
    </div>
  );
}
