// import {useState, useEffect, createContext, useContext} from 'react'

// export const AuthContext = createContext(null);

// export function useAuth() {
// 	return useContext(AuthContext)
// }

// export function Authentication({children}) {
// 	const {user, signIn} = useAuth();
// 	const token = window.localStorage.getItem("token")

// 	useEffect(() => {
// 		if (!user && !token || user && !token) window.location.href = './login';
// 		else if (!user && token) apiCall(BASE_URL + 'member/my-info', METHOD_GET).then(data => signIn({
// 			userId: data.userId,
// 			username: data.username
// 		}))
// 	}, [user])

// 	window.onpageshow = function (event) {
// 		if (event.persisted || (window.performance && window.performance.navigation.type == 2)) {
// 			if (!user || !token) window.location.href = './login';
// 		}
// 	}

// 	return children;
// }

// export default function AuthProvider({children}) {
// 	const [user, setUser] = useState(null);
// 	const signIn = (authenticatedUser) => setUser(authenticatedUser);
// 	const signOut = () => {
// 		window.localStorage.removeItem("token");
// 		setUser(null)
// 	};


// 	const value = {user, signIn, signOut}

// 	return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
// }