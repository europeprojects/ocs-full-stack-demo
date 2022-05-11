import React, {useState} from "react";
import AppLayout from "../../../layout/AppLayout";
import useStyles from "./ListPoll.css";
import {createStyles, styled, Theme, withStyles} from '@material-ui/core/styles';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import {AppBar, FormControl, InputLabel, MenuItem, Select, Toolbar} from "@material-ui/core";
import {useHistory} from "react-router-dom";
import Paper from '@material-ui/core/Paper';
import PollApi from "../../../services/PollApi";
import {ChoiceResponse, PagedPollSummaryResponse, PollSummaryResponse} from "../../../types/PollModel";
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormLabel from '@material-ui/core/FormLabel';
import Typography from '@material-ui/core/Typography';
import {Div} from "../../../components/Grid";


type PollListProp = {
    backdropOpen: any
    backdropClose: any
}

export default function UserPollList({backdropOpen,backdropClose} : PollListProp) {
    const classes = useStyles();
    const history = useHistory();
    const [pollSummaries, setPollSummaries] = React.useState< PollSummaryResponse[] >([]);

    const initUserPolls = () => {
        backdropOpen();
        PollApi.getEndUserPolls().then((data : PagedPollSummaryResponse) => {
            const items : PollSummaryResponse[] = Array.from(data.content);
            setPollSummaries(items)
        }) .finally(() => {
            backdropClose()
        });

    }

    React.useEffect(() => {
        initUserPolls()
    }, []);


    const votedPoll = (pollId:number, choiceId:number) => {

        if (window.confirm("Anket için oy kullanmak istediğinize emin misiniz!")) {
            PollApi.votedPoll(pollId,choiceId).then((data : boolean) => {
                initUserPolls();
                if(data) alert('Oyunuz alındı');
            }).finally(() => {
            });
        }
    }

    return (
        <AppLayout>
            <PageContainer>
                    {pollSummaries.map((row) => {
                        return (
                            <Paper style={{margin:25,padding:15}} elevation={5} >
                                <PollVoter
                                    actionVote={votedPoll}
                                    id={row.id} question={row.question} choices={row.choices}
                                    username={row.username} isActive={row.isActive} totalVotes={row.totalVotes}
                                    selectedChoice={row.selectedChoice} updateTime={row.updateTime}
                                />

                            </Paper>
                        );
                    })}

            </PageContainer>
        </AppLayout>
    )
}

type PollVoterProp = {
    actionVote:any
    id:number
    question:string
    choices: ChoiceResponse[]
    username:string
    isActive:boolean
    totalVotes:number
    selectedChoice:number
    updateTime:string
}

function percentage(x:any, total:any)
{
    if(!!x&&!!total){
        const num : number = (x/(total<0 ? 1 : total))*100
        return (num).toFixed(2)
    }
    return 0;
}


export function PollVoter({actionVote,id,question,choices,username,isActive,totalVotes,selectedChoice,updateTime}:PollVoterProp) {
    const classes = useStyles();
    const [pollId, setPooldId] = React.useState<number>(id);
    const [choiceId, setChoiceId] = React.useState<number>(selectedChoice | 0);

    const handleSubmit = (event:any) => {
        event.preventDefault();
        actionVote(pollId,choiceId)
    };

    const handleRadioChange = (event:any) => {
        setChoiceId(parseInt(event.target.value));
    };

    return (
        <form onSubmit={handleSubmit}   >
            <FormControl component="fieldset" style={{width:'100%'}} >
                <FormLabel component="legend">
                    <Typography variant="h6" gutterBottom>
                        Anket Sorusu
                    </Typography>
                    <Typography variant="overline" display="block" gutterBottom>
                        {question}
                    </Typography>
                </FormLabel>
                <RadioGroup  value={choiceId} aria-label="quiz" name={`${id}`} onChange={handleRadioChange}>
                {
                    choices.map((choice: ChoiceResponse, index: number) => {
                        return (
                            <>
                            <FormControlLabel style={{width:300,display:'inline'}} disabled={!!selectedChoice}  value={choice.id}
                                              control={<Radio />} label={`${choice.text} (${choice.voteCount} oy) %${percentage(choice.voteCount,totalVotes)}`} />

                            </>
                        );
                    })
                }
                </RadioGroup>
                <br/>

                <div style={{width: '100%'}}>
                    <Button disabled={!!selectedChoice || choiceId<=0 } type="submit" variant="outlined" color="primary" >
                       Oy Gonder
                    </Button>
                    <Typography style={{float:'right'}} variant="caption" display="block" gutterBottom>
                        Toplam {totalVotes} oy kullanıldı
                    </Typography>
                </div>
            </FormControl>
        </form>
    )
}


const StyledTableCell = withStyles((theme: Theme) =>
    createStyles({
        head: {
            backgroundColor: 'rgb(40,95,196)',
            color: 'white',
        },
        body: {
            fontSize: 14,
            cursor: "pointer",
            margin: '0',
            padding: theme.spacing(2),

        },
    }),
)(TableCell);

const StyledTableRow = withStyles((theme: Theme) =>
    createStyles({
        root: {
            '&:nth-of-type(odd)': {
                backgroundColor: theme.palette.action.hover,
            },
        },
    }),
)(TableRow);

const PageContainer = styled('div')(({theme}) => ({
    width: '60%',
    maxWidth: theme.breakpoints.width('xl'),
    margin: '0 auto',
    padding: theme.spacing(2)
}))


