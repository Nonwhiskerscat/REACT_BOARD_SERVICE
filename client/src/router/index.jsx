import MainPage from "../pages/MainPage";
import { Routes, Route } from "react-router-dom";
import SignUpPage from "../pages/SignUpPage";
import LoginPage from "../pages/LoginPage";
import WritePage from "../pages/WritePage";
import ViewPage from "../pages/ViewPage";
import ProtectedRoute from "../components/ProtectedRoute";
import { Navigate } from "react-router-dom";

export default function Router() {
    return (
        <Routes>
            <Route path="/login" element={<LoginPage />} />
            <Route path="/signup" element={<SignUpPage />} />

            <Route element={<ProtectedRoute />}>
                <Route path="/" element={<MainPage />} />
                <Route path="/write" element={<WritePage />} />
                <Route path="/view" element={<ViewPage />} />
            </Route>
        </Routes>
    );
}
