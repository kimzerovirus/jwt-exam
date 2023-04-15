import { Container } from "@mui/material";
import React, { useEffect, useState } from "react";
import { getCookie } from "../utils/cookieUtil";
import axios from "axios";

function PrivatePage() {
  const [userInfo, setUserInfo] = useState();
    console.log(getCookie('ac_token'))
  useEffect(() => {
    axios.get("http://localhost:8080/get-account", 
        {
            headers: {
              Authorization: `BEARER ${getCookie('ac_token')}`,
            },
        }
    ).then(({ data }) => {
      setUserInfo(data);
    }).catch(err => console.log(err));
  }, []);

  return (
    <Container
      style={{ display: "flex", justifyContent: "center" }}
      sx={{ mt: 6 }}
    >
      {userInfo}
    </Container>
  );
}

export default PrivatePage;
