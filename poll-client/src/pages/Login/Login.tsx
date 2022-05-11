import React, {useState} from "react";
import AppLayout from "../../layout/AppLayout";
import {PollUser} from "../../types/AuthModel";
import {
  Avatar,
  Button,
  Checkbox,
  Container,
  FormControlLabel,
  Grid,
  Link,
  TextField,
  Typography
} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";
import PollTwoToneIcon from '@material-ui/icons/PollTwoTone';
import {AuthService} from "../../services/Auth";
import {useHistory} from "react-router-dom"

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),

  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

function Login() {
  const history = useHistory();
  const classes = useStyles();
  const [usernameOrEmail, setUsernameOrEmail] = useState<string>('')
  const [password, setPassword] = useState<string>('')
  const [loading, setLoading] = React.useState(false);

  const onLoginFormSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault()
    setLoading(true)
    AuthService.login(usernameOrEmail, password).then((result) => {
          let {authenticated, user} = result;
          if (authenticated) {
             history.push('/home');
           } else {
            alert('Lütfen bilgilerinizi kontrol edin.')
          }
        }
    ).finally(() => {setLoading(false);});

    return false;
  }

  return (
    <AppLayout>
      <Container component="main" maxWidth="xs">
        <div className={classes.paper}>
          <Avatar className={classes.avatar}>
            <PollTwoToneIcon/>
          </Avatar>
          <Typography component="h1" variant="h5" color="textPrimary">
            ANKET UYGULAMASI
          </Typography>

          <form onSubmit={onLoginFormSubmit} className={classes.form}>
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              label="Kullanıcı Adı veya Email"
              name="usernameOrEmail"
              autoFocus
              value={usernameOrEmail}
              onChange={e => setUsernameOrEmail(e.target.value)}
            />
            <TextField
              variant="outlined"
              margin="normal"
              required
              fullWidth
              name="password"
              label="Şifre"
              type="password"
              value={password}
              onChange={e => setPassword(e.target.value)}
            />


            <Button
              type="submit"
              fullWidth
              variant="contained"
              className={classes.submit}
              disabled={loading}
            >
              GİRİŞ YAP
            </Button>
          </form>
        </div>
      </Container>
    </AppLayout>
  )
}

export default Login;
