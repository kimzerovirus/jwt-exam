import React from "react";
import axios from 'axios';

import { Button, Container, Box, TextField } from "@mui/material";

function LoginPage() {
  const handleSubmit = (e) => {
    e.preventDefault();
    const loginForm = {
      email: e.target.email.value,
      password: e.target.password.value,
    };

    axios.post('http://localhost:8080/api/login', loginForm).then(({data}) => {
      console.log(data)
    })
  };

  return (
    <Container
      style={{ display: "flex", justifyContent: "center" }}
      sx={{ mt: 6 }}
    >
      <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
        <TextField
          margin="normal"
          required
          fullWidth
          id="email"
          label="Email Address"
          name="email"
          autoComplete="email"
          autoFocus
        />
        <TextField
          margin="normal"
          required
          fullWidth
          name="password"
          label="Password"
          type="password"
          id="password"
          autoComplete="current-password"
        />
        <Button
          type="submit"
          fullWidth
          variant="contained"
          sx={{ mt: 3, mb: 2 }}
        >
          Sign In
        </Button>
        <Button
          fullWidth
          variant="outlined"
        >
          <a href="http://localhost:8080/oauth2/authorization/google">
            GOOGLE LOGIN
          </a>
        </Button>
      </Box>
    </Container>
  );
}

export default LoginPage;
