import React from "react";
import axios from 'axios';

import { Container, Box } from "@mui/material";

function PublicPage() {
  const handleSubmit = (e) => {
    e.preventDefault();
    const loginForm = {
      username: e.target.email.value,
      password: e.target.password.value,
    };

    axios.post('http://localhost:8080/login', loginForm).then(({data}) => {
      console.log(data)
    })
  };

  return (
    <Container
    style={{ display: "flex", justifyContent: "center" }}
    sx={{ mt: 6 }}
  >
    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
      <h1>Hello World!</h1>
    </Box>
  </Container>
  );
}

export default PublicPage;
