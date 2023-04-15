import React from "react";
import { NavLink, useNavigate } from "react-router-dom";
import { AppBar, Toolbar, Button, Stack } from "@mui/material";

function NavBar() {
  const navLinkStyles = ({ isActive }) => {
    return {
      fontWeight: isActive ? "bold" : "normal",
      // textDecoration: isActive ? 'underline' : 'none',
      opacity: isActive ? 1 : 0.5,
	  color: 'inherit',
	  textDecoration: 'none',
    };
  };

  return (
    <AppBar position="static">
      <Toolbar style={{display:'flex', justifyContent:'center'}}>
        <Stack direction="row" spacing={2}>
          <Button color="inherit">
            <NavLink to="/" style={navLinkStyles}>
              public
            </NavLink>
          </Button>
          <Button color="inherit">
            <NavLink to="private" style={navLinkStyles}>
              private
            </NavLink>
          </Button>
        </Stack>
      </Toolbar>
    </AppBar>
  );
}

export default NavBar;
