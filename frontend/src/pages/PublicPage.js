import React from "react";

import { Button, Container } from "@mui/material";

function PublicPage() {
  return (
    <Container
      style={{ display: "flex", justifyContent: "center" }}
      sx={{ mt: 6 }}
    >
      <Button>
        <a
          href ="http://localhost:8080/oauth2/authorization/google"
        >
          GOOGLE LOGIN
        </a>
      </Button>
    </Container>
  );
}

export default PublicPage;
