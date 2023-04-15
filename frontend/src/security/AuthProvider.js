import {useState, useEffect, createContext, useContext} from 'react'
import { useNavigate } from 'react-router-dom';
import { getCookie } from '../utils/cookieUtil';

export const AuthContext = createContext(null);

export function useAuth() {
	return useContext(AuthContext)
}

export function Authentication({children}) {
	const {user, signIn} = useAuth();
	const navigate = useNavigate();
	const token = getCookie('ac_token')

	useEffect(() => {
		if (!token) {
			navigate('/');
		}
	}, [user])

	window.onpageshow = function (event) {
		if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
			if (!user || !token) navigate('/');
		}
	}

	return children;
}

export default function AuthProvider({children}) {
	const [user, setUser] = useState(null);
	const signIn = (authenticatedUser) => setUser(authenticatedUser);
	const signOut = () => {
		window.localStorage.removeItem("token");
		setUser(null)
	};


	const value = {user, signIn, signOut}

	return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}