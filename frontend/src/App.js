import { BrowserRouter, Routes, Route } from 'react-router-dom';
import NavBar from './components/NavBar';
import PublicPage from './pages/PublicPage';
import PrivatePage from './pages/PrivatePage';

function App() {
	return (
		<BrowserRouter>
				<NavBar />
				<Routes>
					<Route path="/" element={<PublicPage />} />
					<Route
						path="private"
						element={
								<PrivatePage />
						}
					/>
				</Routes>
		</BrowserRouter>
	);
}

export default App;
