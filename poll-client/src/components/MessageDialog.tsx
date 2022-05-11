import React from "react";
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import {Button} from "@material-ui/core";


export type DialogProps = {
  dialogTitle: string
  dialogDescription: string
  openDialog: boolean
  handleCloseDialog: () => void
  doCallback: () => void
}


export default function MessageDialog({openDialog, handleCloseDialog, dialogTitle, dialogDescription, doCallback}: DialogProps) {
  return (
    <Dialog
      open={openDialog}
      onClose={handleCloseDialog}
      aria-labelledby="alert-dialog-title"
      aria-describedby="alert-dialog-description"
    >
      <DialogTitle id="alert-dialog-title">{dialogTitle}</DialogTitle>
      <DialogContent>
        <DialogContentText id="alert-dialog-description">
          {dialogDescription}
        </DialogContentText>
      </DialogContent>
      <DialogActions>
        <Button onClick={handleCloseDialog} color="primary">
          Hayır
        </Button>
        <Button onClick={doCallback} color="primary" autoFocus>
          Evet
        </Button>
      </DialogActions>
    </Dialog>
  )
}
