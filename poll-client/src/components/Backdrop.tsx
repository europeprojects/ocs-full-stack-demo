import React from "react";
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import {createStyles, makeStyles, Theme} from '@material-ui/core/styles';

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    backdrop: {
      zIndex: theme.zIndex.drawer + 1,
      color: '#fff',
    },
  }),
);

export type BackdropProps = {
  handleClose: () => void
  open: boolean
}

export default function BackdropUI({open, handleClose}: BackdropProps) {
  const classes = useStyles();

  return (
    <>
      <Backdrop style={{zIndex: 10000}} timeout={{enter: 100, exit: 100}} className={classes.backdrop} open={open}
                onClick={handleClose}>
        <CircularProgress color="inherit"/>
      </Backdrop>
    </>
  )
}
