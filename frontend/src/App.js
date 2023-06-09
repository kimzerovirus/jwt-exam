import { BrowserRouter, Routes, Route } from "react-router-dom";
import NavBar from "./components/NavBar";
import PublicPage from "./pages/PublicPage";
import PrivatePage from "./pages/PrivatePage";
import AuthProvider, { Authentication} from "./security/AuthProvider";
import LoginPage from "./pages/LoginPage";

function App() {
  return (
    <BrowserRouter>
      <AuthProvider>
        <NavBar />
        <Routes>
          <Route path="/" element={<PublicPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="private" element={
            <Authentication>
              <PrivatePage />
            </Authentication>
          } />
        </Routes>
      </AuthProvider>
    </BrowserRouter>
  );
}

export default App;
