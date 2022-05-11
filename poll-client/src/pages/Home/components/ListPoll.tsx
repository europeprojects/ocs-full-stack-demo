import React, {useState} from "react";
import AppLayout from "../../../layout/AppLayout";
import useStyles from "./ListPoll.css";
import {createStyles, styled, Theme, withStyles} from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Button from '@material-ui/core/Button';
import SearchIcon from '@material-ui/icons/Search';
import {AppBar, FormControl, InputLabel, MenuItem, Select, Toolbar} from "@material-ui/core";
import {Div} from "../../../components/Grid";
import Divider from "@material-ui/core/Divider";
import {useHistory} from "react-router-dom";
import Paper from '@material-ui/core/Paper';
import TablePagination from "@material-ui/core/TablePagination";
import BackdropUI from "../../../components/Backdrop";
import PollApi from "../../../services/PollApi";
import {ChoiceResponse, PagedPollSummaryResponse, PollSummaryResponse} from "../../../types/PollModel";
import VisibilityOffOutlinedIcon from '@material-ui/icons/VisibilityOffOutlined';
import VisibilityOutlinedIcon from '@material-ui/icons/VisibilityOutlined';

type PollListProp = {
    backdropOpen: any
    backdropClose: any
}

export default function PollList({backdropOpen,backdropClose} : PollListProp) {

  const classes = useStyles();
  const history = useHistory();
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(5);
  const [count, setCount] = React.useState(0);
  const [pollSummaries, setPollSummaries] = React.useState< PollSummaryResponse[] >([]);
  const [selectedPoll,setSelectedPoll] =  React.useState<String >("created");


  const initAdminPolls = () => {

      backdropOpen();
      PollApi.getPolls().then((data : PagedPollSummaryResponse) => {
          const items : PollSummaryResponse[] = Array.from(data.content);
          setPollSummaries(items)
          setCount(data.totalElements || 0)
      }) .finally(() => {
          backdropClose()
          setSelectedPoll('created')
      });

  }

    const initProposePolls = () => {

        backdropOpen();
        PollApi.getProposalPolls().then((data : PagedPollSummaryResponse) => {
            const items : PollSummaryResponse[] = Array.from(data.content);
            setPollSummaries(items)
            setCount(data.totalElements || 0)
        }).finally(() => {
            backdropClose()
            setSelectedPoll('proposal')
        });
    }

  React.useEffect(() => {
      initAdminPolls()
  }, []);


  const deletePoll = (pollId:number) => {

      if (window.confirm("Anketi silmek istediğinize emin misiniz!")) {
          PollApi.deletePoll(pollId).then((data : boolean) => {
              initAdminPolls();
              if(data) alert('Anket başarıyla silindi');
          }).finally(() => {
              setSelectedPoll('created')
          });
      }

  }

  const doPassivePoll = (pollId:number) => {

      if (window.confirm("Anketi sonlandırmak istediğinize emin misiniz!")) {
          PollApi.doPassivePoll(pollId).then((data : boolean) => {
              initAdminPolls();
              if(data) alert('Anket pasife alındı');
          }).finally(() => {
              setSelectedPoll('created')
          });
      }

  }

    const doConfirmPoll = (pollId:number) => {

        if (window.confirm("Anketi onaylamak istediğinize emin misiniz!")) {
            PollApi.doConfirmPoll(pollId).then((data : boolean) => {
                initAdminPolls();
                if(data) alert('Anket onaylandı');
            }).finally(() => {
                setSelectedPoll('created')
            });
        }


    }

  const fetchAdminPolls = () => {
      initAdminPolls();
  }

  const fetchProposalPolls = () => {
      initProposePolls();
  }

  const localDateTime = (datetime: any) => {
    const date = new Date(Date.parse(datetime)).toLocaleDateString("tr-TR")
    const time = new Date(Date.parse(datetime)).toLocaleTimeString("tr-TR")
    return date + " " + time
  }

  return (
      <AppLayout>
        <PageContainer>

            <div  style={{ textAlign: 'center', padding: 5 , margin: 5}}>
                <Button className={selectedPoll == 'created' ? classes.selected : ''} style={{ width : 300,marginRight : 10}} disabled={selectedPoll == 'created'} onClick={fetchAdminPolls} color="primary" variant="outlined" >Mevcut Anketler</Button>
                <Button className={selectedPoll == 'proposal' ? classes.selected : ''}  style={{width : 300}} disabled={selectedPoll == 'proposal'} onClick={fetchProposalPolls} color="primary" variant="outlined">Anket Önerileri</Button>
            </div>
            <TableContainer component={Paper} className={classes.container}>
            <Table
                className={classes.table}
                stickyHeader
                aria-labelledby="tableTitle"
                aria-label="enhanced table">
              <EnhancedTableHead
                  columnData={{}}
                  classes={classes}
                  rowCount={pollSummaries.length}
                  numSelected={1} onSelectAllClick={() => {
              }}/>

              <TableBody>
                {
                  pollSummaries.map((row: PollSummaryResponse, index: number) => {
                    return (
                        <StyledTableRow
                            hover
                            role="checkbox"
                            tabIndex={-1}>
                            <Divider orientation="vertical"/>
                            <StyledTableCell   align="center">
                            <>
                                <div style={ { fontSize: 20 }}>
                                {!row.isActive ?

                                    <VisibilityOffOutlinedIcon/>
                                        : <VisibilityOutlinedIcon/>
                                }
                                </div>
                                 <div>
                                     {localDateTime(row.updateTime)}
                                 </div>
                            </>
                            </StyledTableCell>


                          <Divider orientation="vertical"/>
                          <StyledTableCell  style={ !row.isActive ? {textDecoration : 'line-through'} :{}} align="left">
                              <span title={row.question}>{row.question.substring(0,50)}</span>
                          </StyledTableCell>
                          <Divider orientation="vertical"/>
                            {
                                selectedPoll == 'created' ?
                                    <StyledTableCell align="left" >
                                        {
                                            row.choices.map((choice: ChoiceResponse, index: number) => {
                                                return (<> <span title={choice.text} >{choice.text.substring(0,10)} </span>: <span style={{color:'red'}}>{choice.voteCount}</span> &nbsp;|&nbsp; </>);
                                            })
                                        }
                                    </StyledTableCell> :  <StyledTableCell align="left" >
                                        {
                                            row.choices.map((choice: ChoiceResponse, index: number) => {
                                                return (<> <span title={choice.text} >{choice.text.substring(0,30)} </span> <br/> </>);
                                            })
                                        }</StyledTableCell>
                            }

                          <Divider orientation="vertical"/>
                          <StyledTableCell  align="center">{row.totalVotes}</StyledTableCell>
                          <Divider orientation="vertical"/>
                          <StyledTableCell align="center">
                              {
                                  selectedPoll == 'created' ?
                               <>
                              <Button disabled={!row.isActive} onClick={()=>{doPassivePoll(row.id)}} color="primary" variant="outlined" style={{ marginRight: 10 }}>Anketi Kapat</Button>
                              <Button onClick={()=>{deletePoll(row.id)}} color="secondary" variant="outlined">Anketi Sil</Button>
                               </> :
                              <>
                              <Button onClick={()=>{doConfirmPoll(row.id)}} color="primary" variant="outlined" style={{ marginRight: 10 }}>Anketi Onayla</Button>
                              <Button onClick={()=>{deletePoll(row.id)}} color="secondary" variant="outlined">Anketi Sil</Button>
                              </>}
                          </StyledTableCell>
                        </StyledTableRow>
                    );
                  })}
              </TableBody>
            </Table>
          </TableContainer>
          {

            //paging
          }

        </PageContainer>

      </AppLayout>
  )
}

interface EnhancedTableProps {
  classes: ReturnType<typeof useStyles>;
  numSelected: number;
  onSelectAllClick: (event: React.ChangeEvent<HTMLInputElement>) => void;
  rowCount: number;
  columnData: any
}

function EnhancedTableHead(props: EnhancedTableProps) {
  const {columnData, classes, rowCount} = props;

  return (
      <TableHead>
        <TableRow>
          <Divider orientation="vertical"/>
          <StyledTableCell key='questions'></StyledTableCell>
          <Divider orientation="vertical"/>
          <StyledTableCell key='questions'>Anket Sorusu</StyledTableCell>
          <Divider orientation="vertical"/>
            <StyledTableCell key='questions'>Şıklar</StyledTableCell>
            <Divider orientation="vertical"/>
          <StyledTableCell key='createdDate'>Toplam Oy</StyledTableCell>
          <Divider orientation="vertical"/>
          <StyledTableCell key='createdDate'>Aksiyonlar</StyledTableCell>
        </TableRow>
      </TableHead>
  );
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
  width: '100%',
  maxWidth: theme.breakpoints.width('xl'),
  margin: '0 auto',
  padding: theme.spacing(2)
}))


