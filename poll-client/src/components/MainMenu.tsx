import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import ExpandLess from "@material-ui/icons/ExpandLess";
import ExpandMore from "@material-ui/icons/ExpandMore";
import {Link} from "react-router-dom";
import {Collapse} from "@material-ui/core";


export type MainMenuItem = {
  name: string
  icon?: string
  path?: string
  items?: MainMenuSubItem[]
}

export type MainMenuSubItem = {
  name: string
  icon?: string
  path?: string
}

export type MainMenuProps = {
  items: MainMenuItem[]
}

export default function MainMenu({items}: MainMenuProps) {
  const classes = useStyles();
  return (
    <List className={classes.root}>
      {items.map(item => (
        <MainSubMenu key={item.name} item={item}/>
      ))}
    </List>
  );
}

function MainSubMenu({item}: { item: MainMenuItem }) {
  const [open, setOpen] = React.useState(false);
  const classes = useStyles();

  const handleClick = () => {
    setOpen(!open);
  };

  return (
    <>
      {item.items && item.items.length ? (
        <>
          <ListItem button onClick={handleClick}>
            {item.icon && (
              /*
                     <ListItemIcon>
                       {item.icon}
                     </ListItemIcon>*/
              <img src={item.icon} width="24" height="24" alt=""/>
            )}<span>&nbsp;&nbsp;</span>
            <ListItemText primary={item.name}/>
            {open ? <ExpandLess/> : <ExpandMore/>}
          </ListItem>
          <Collapse in={open} timeout="auto" unmountOnExit>
            <List component="div" disablePadding>
              {item.items.map(subItem => (
                // @ts-ignore
                <ListItem className={classes.nestedItem} button component={Link} to={subItem.path} key={subItem.name}>
                  {subItem.icon && (
                    /*
                     <ListItemIcon>
                       {item.icon}
                     </ListItemIcon>*/
                    <img src={item.icon} width="24" height="24" alt=""/>
                  )}
                  <ListItemText primary={subItem.name}/>
                </ListItem>
              ))}
            </List>
          </Collapse>
        </>
      ) : (
        // @ts-ignore
        <ListItem button component={Link} to={item.path}>
          {item.icon && (
            <ListItemIcon>
              {item.icon}
            </ListItemIcon>
          )}<span>&nbsp;&nbsp;</span>
          <ListItemText primary={item.name}/>
        </ListItem>
      )}
    </>
  )
}

const useStyles = makeStyles(theme => ({
  root: {
    width: '100%',
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
  nestedItem: {
    paddingLeft: theme.spacing(4)
  }
}))
