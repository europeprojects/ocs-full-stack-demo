import React, {useState} from "react"
import AccountCircle from "@material-ui/icons/AccountCircle";
import MenuItem from "@material-ui/core/MenuItem";
import {AuthService} from "../../services/Auth";
import {PollUser} from "../../types/AuthModel";
import {useHistory} from "react-router-dom"
import {
  Button,
  ClickAwayListener,
  createStyles,
  Grow,
  MenuList,
  Paper,
  Popper,
  Portal,
  Snackbar,
  Theme
} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import Dialog from "@material-ui/core/Dialog";
import MuiAlert from "@material-ui/lab/Alert";

const Alert = (props: any) => {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      display: 'flex',
    },
    paper: {
      marginRight: theme.spacing(2),
    },
    displayName: {
      display: 'inline-grid',
      gridTemplateColumns: '1fr',
      paddingLeft: '.5rem',
      fontSize: '.7rem'
    }
  }),
);

export default function ({user}: { user: PollUser }) {
  const history = useHistory();
  const classes = useStyles();
  const [open, setOpen] = React.useState(false);
  const anchorRef = React.useRef<HTMLButtonElement>(null);
  const [snackState, setSnackState] = useState({openSnackBar: false, status: "success", clientMessage: ""});
  const {openSnackBar, status, clientMessage} = snackState;

  const handleToggle = () => {
    setOpen((prevOpen) => !prevOpen);
  };

  const handleClose = (event: React.MouseEvent<EventTarget>) => {
    if (anchorRef.current && anchorRef.current.contains(event.target as HTMLElement)) {
      return;
    }

    setOpen(false);
  };


  const handleOpenSnackBar = (state: any) => {
    setSnackState(state);
  };

  const handleCloseSnackBar = (event: any, reason: string) => {
    if (reason === 'clickaway') {
      return;
    }
    setSnackState({...snackState, openSnackBar: false});
  };

  function handleListKeyDown(event: React.KeyboardEvent) {
    if (event.key === 'Tab') {
      event.preventDefault();
      setOpen(false);
    }
  }

  // return focus to the button when we transitioned from !open -> open
  const prevOpen = React.useRef(open);
  React.useEffect(() => {
    if (prevOpen.current && !open) {
      anchorRef.current!.focus();
    }

    prevOpen.current = open;
  }, [open]);


  const onLogoutClick = (event: React.MouseEvent<EventTarget>) => {
    AuthService.logout().finally(()=>{handleClose(event); history.push('/login')});


  };

  let alertMessage = status === "success" ?
    <Alert severity="success" onClose={handleCloseSnackBar}> {clientMessage} </Alert>
    : <Alert severity="error" onClose={handleCloseSnackBar}> {clientMessage} </Alert>;

  return (
    <div className={classes.root}>
      <Button
        ref={anchorRef}
        color="inherit"
        onClick={handleToggle}
      >
        <AccountCircle fontSize="large"/>
        <span className={classes.displayName}>
          {user && (
            <>
              <span>{user.username}</span>
            </>
          )}
        </span>
      </Button>
      <Popper open={open} anchorEl={anchorRef.current} role={undefined} transition disablePortal>
        {({TransitionProps, placement}) => (
          <Grow
            {...TransitionProps}
            style={{transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom'}}
          >
            <Paper>
              <ClickAwayListener onClickAway={handleClose}>
                <MenuList autoFocusItem={open} onKeyDown={handleListKeyDown}>
                  {user.type == 'user' && (
                      <>
                        <MenuItem onClick={handleClose}>Hesabım</MenuItem>
                      </>
                  )}
                  <MenuItem onClick={onLogoutClick}>Çıkış Yap</MenuItem>
                </MenuList>
              </ClickAwayListener>
            </Paper>
          </Grow>
        )}
      </Popper>

      <Portal>
        <Snackbar anchorOrigin={{vertical: 'top', horizontal: 'right'}}
                  open={openSnackBar}
                  autoHideDuration={status === 'success' ? 1500 : 3000}
                  onClose={handleCloseSnackBar}>
          {alertMessage}
        </Snackbar>
      </Portal>
    </div>
  )
}
