import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { useLocation } from "react-router-dom";

export default function ProtectedRoute() {
    const { user, loading } = useAuth();
    const location = useLocation();
    if (loading) return null;

    // 이미 로그인인데 /login 이나 /signup 을 누르면 → /main
    if (user && ["/login", "/signup"].includes(location.pathname)) {
        return <Navigate to="/" replace />;
    }

    // 로그인 필요 페이지인데 미로그인 → /login
    if (!user && location.pathname !== "/login")
        return <Navigate to="/login" replace />;

    return <Outlet />;
}