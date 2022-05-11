import React, {useEffect, useState} from "react";
import AppLayout from "../../layout/AppLayout";
import {Button, styled} from "@material-ui/core";
import {Div} from "../../components/Grid";
import {useHistory} from "react-router-dom";
import useStyles from "./AdminDashboard.css";
import MuiAlert from "@material-ui/lab/Alert";
import PollList from "./components/ListPoll";
import CreatePoll from "./components/CreatePoll";
import PollApi from "../../services/PollApi";
import BackdropUI from "../../components/Backdrop";
import Snackbar from "@material-ui/core/Snackbar";
import { PollRequest, PollResponse} from "../../types/PollModel";



const Alert = (props: any) => {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}
export default function AdminDashBoard() {
  const classes = useStyles();
  const history = useHistory();
  const [openBackdrop, setOpenBackdrop] = React.useState(false);
  const [snackState, setSnackState] = useState({openSnack: false, status: "success", clientMessage: ""});
  const {openSnack, clientMessage} = snackState;


  function createPollAction(pollReq : PollRequest,callback : (data?:any) => void){
   handleOpenBackdrop();
    PollApi.createPoll(pollReq)
       .finally(()=>{
          handleOpenSnackBar({
            ...snackState, openSnack: true, status: "success",
            clientMessage: 'İşleminiz başarıyla gerçekleştirldii'
          });
          window.setTimeout(function(){ history.push("/home") }, 1000);
        })
    }

  const handleCloseBackdrop = () => {
    setOpenBackdrop(false)
  };

  const handleOpenBackdrop = () => {
    setOpenBackdrop(true)
  };


  const handleOpenSnackBar = (state: any) => {
    setSnackState(state);
  };

  const handleCloseSnackBar = (event: any, reason: string) => {
    if (reason === 'clickaway') {
      return;
    }
    setSnackState({...snackState, openSnack: false});
  };


  const callbackSnackBar = (status: string, message: string) => {
    handleOpenSnackBar({...snackState, openSnack: true, status: status, clientMessage: message});
  };

  let alertMessage = <Alert severity="success" onClose={handleCloseSnackBar}> {clientMessage} </Alert>

  return (
    <AppLayout>
      <PageContainer>
        <PageMain>
          <PageContent>
            <CreatePoll onPollCreate={createPollAction}  />
          </PageContent>
          <Div>
            <PollList backdropOpen={handleOpenBackdrop} backdropClose={handleCloseBackdrop}/>
          </Div>
        </PageMain>
      </PageContainer>
      <BackdropUI handleClose={handleCloseBackdrop} open={openBackdrop}/>
      <Snackbar anchorOrigin={{vertical: 'top', horizontal: 'right'}}
                open={openSnack}
                autoHideDuration={1500}
                onClose={handleCloseSnackBar}>
        {alertMessage}
      </Snackbar>
    </AppLayout>
  )
}
const PageContainer = styled('div')(({theme}) => ({
  width: '100%',
  margin: '0',
}))

const PageMain = styled('div')(({theme}) => ({
  width: '100%',
  display: 'grid',
  gridTemplateColumns: '2fr 5fr',
  gridGap: theme.spacing(5)
}))

const PageContent = styled('div')(({theme}) => ({
  width: '100%',
  display: 'grid',

}))
