import React, {useEffect, useState} from "react";
import AppLayout from "../../../layout/AppLayout";
import {Button, styled, TextField, Typography,} from "@material-ui/core";
import {useHistory} from "react-router-dom";
import MuiAlert from "@material-ui/lab/Alert";
import useStyles from "./CreatePoll.css";
import PollApi from "../../../services/PollApi";
import {ChoiceRequest, PollRequest} from "../../../types/PollModel";
import BackdropUI from "../../../components/Backdrop";




const Alert = (props: any) => {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}

type CreatePollProp = {
  onPollCreate: any,
}

export default function CreatePoll({onPollCreate} : CreatePollProp) {
  const classes = useStyles();
  const history = useHistory();

  const [question, setQuestion] = React. useState<string>('')
  const [choiceA, setChoiceA] = React. useState<string>('')
  const [choiceB, setChoiceB] = React. useState<string>('')
  const [choiceC, setChoiceC] = React. useState<string>('')
  const [choiceD, setChoiceD] = React. useState<string>('')


  const pollReq: PollRequest = {
    question ,
    choices : [{index : 1, text :choiceA},{index : 2, text :choiceB}
    ,{index : 3, text :choiceC},{index : 4, text :choiceD}]
  }

  const onCreate = () =>{
    if(onPollCreate) {
        onPollCreate(pollReq,clear);
    }
  }

  const clear = (data?:any) =>{
    setQuestion('');
    setChoiceA('');
    setChoiceB('');
    setChoiceC('');
    setChoiceD('');
  }

  let validate = !!question && !!choiceA && !!choiceB && !!choiceC  && !!choiceD;

  return (
      <AppLayout>
        <PageContainer>
          <PageMain>
            <PageContent>
              <Typography variant="h5" gutterBottom>
               Anket Oluştur
              </Typography>
              <TextField
                  label="Anket Sorusu"
                  id="filled-read-only-input"
                  variant="outlined"
                  multiline
                  rows={5}
                  fullWidth
                  value={question}
                  onChange={event => setQuestion(event.target.value)}
              />
              <TextField
                  label="Şık 1"
                  id="filled-read-only-input"
                  variant="outlined"
                  multiline
                  rows={1}
                  fullWidth
                  value={choiceA}
                  onChange={event => setChoiceA( event.target.value)}
              />
              <TextField
                  label="Şık 2"
                  id="filled-read-only-input"
                  variant="outlined"
                  multiline
                  rows={1}
                  fullWidth
                  value={choiceB}
                  onChange={event => setChoiceB(event.target.value)}
              />
              <TextField
                  label="Şık 3"
                  id="filled-read-only-input"
                  variant="outlined"
                  multiline
                  rows={1}
                  fullWidth
                  value={choiceC}
                  onChange={event => setChoiceC(event.target.value)}
              />
              <TextField
                  label="Şık 4"
                  id="filled-read-only-input"
                  variant="outlined"
                  multiline
                  rows={1}
                  fullWidth
                  value={choiceD}
                  onChange={event => setChoiceD(event.target.value)}
              />

              <ActionButton disabled={!validate} onClick={onCreate} variant="contained" color="primary">KAYDET</ActionButton>
            </PageContent>
          </PageMain>
        </PageContainer>

      </AppLayout>
  )
}
const PageContainer = styled('div')(({theme}) => ({
  width: '100%',
  maxWidth: theme.breakpoints.width('lg'),
  margin: '0 auto',
  padding: theme.spacing(2)
}))

const PageMain = styled('div')(({theme}) => ({
  width: '100%',
  display: 'grid',
}))

const PageContent = styled('div')(({theme}) => ({
  width: '100%',
  display: 'grid',
  margin: '0 auto',
  gridGap: theme.spacing(3),
}))

const ActionButton = styled(Button)(({theme}) => ({
  padding: theme.spacing(2),
  color: 'white',
}))


