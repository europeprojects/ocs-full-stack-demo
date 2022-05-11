import React, {useEffect, useState} from "react";
import AppLayout from "../../layout/AppLayout";
import {Button, styled} from "@material-ui/core";
import {Div} from "../../components/Grid";
import {useHistory} from "react-router-dom";
import useStyles from "./UserDashboard.css";
import MuiAlert from "@material-ui/lab/Alert";
import PollList from "./components/ListPoll";
import PollApi from "../../services/PollApi";
import BackdropUI from "../../components/Backdrop";
import Snackbar from "@material-ui/core/Snackbar";
import {PollRequest, PollResponse} from "../../types/PollModel";
import ProposePoll from "./components/ProposePoll";
import UserPollList from "./components/UserListPoll";



const Alert = (props: any) => {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}
export default function UserDashBoard() {
  const classes = useStyles();
  const history = useHistory();
  const [openBackdrop, setOpenBackdrop] = React.useState(false);
  const [tab, setTab] = React.useState<string>('polls');
  const [snackState, setSnackState] = useState({openSnack: false, status: "success", clientMessage: ""});
  const {openSnack, clientMessage} = snackState;


  function proposePollAction(pollReq : PollRequest,callback : (data?:any) => void){

   handleOpenBackdrop();

    PollApi.proposePoll(pollReq)
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

    const changeTab = (value : string) => {
        setTab(value);
    };

  const callbackSnackBar = (status: string, message: string) => {
    handleOpenSnackBar({...snackState, openSnack: true, status: status, clientMessage: message});
  };
  let alertMessage = <Alert severity="success" onClose={handleCloseSnackBar}> {clientMessage} </Alert>

  return (
<>
      <PageContainer>
        <PageMain>
          <PageContent >
              <div  style={{ textAlign: 'center', padding: 5 , margin: 5}}>

                    <Button className={tab == 'polls' ? classes.selected : ''}  onClick={()=>{changeTab('polls')}} disabled={tab=='polls'}
                            style={{width: 300, marginRight: 10}} color="primary" variant="outlined">Mevcut
                      Anketler</Button>

                    <Button className={tab == 'propose' ? classes.selected : ''}   onClick={()=>{changeTab('propose')}} disabled={tab=='propose'} style={{width: 300}} color="primary" variant="outlined">Anket Oner</Button>

              </div>
              {
                tab != 'polls' ?
                    <ProposePoll onPollPropose={proposePollAction}/> :
                    <UserPollList backdropOpen={handleOpenBackdrop} backdropClose={handleCloseBackdrop}/>
              }
          </PageContent>

        </PageMain>
      </PageContainer>
      <BackdropUI handleClose={handleCloseBackdrop} open={openBackdrop}/>
      <Snackbar anchorOrigin={{vertical: 'top', horizontal: 'right'}}
                open={openSnack}
                autoHideDuration={1500}
                onClose={handleCloseSnackBar}>
        {alertMessage}
      </Snackbar>
  </>
  )
}
const PageContainer = styled('div')(({theme}) => ({
  width: '100%',
  margin: 'auto aoto',
}))

const PageMain = styled('div')(({theme}) => ({
  width: '100%',

}))

const PageContent = styled('div')(({theme}) => ({
  width: '50%',
  marginLeft: 'auto',
  marginRight: 'auto',
  marginTop: 100
}))
