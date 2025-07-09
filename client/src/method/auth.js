import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { API_URL } from "./ajax";

export const isAuthenticated = () => {
    return !!localStorage.getItem('token');
};

export const HandleLogout = () => {
    const { refresh } = useAuth();
    const navigate = useNavigate();

    return async () => {
        try {
            await axios.post(API_URL + "/api/auth/logout", null);
        } catch (e) {
            // 에러 무시하고 계속 진행
        }
        localStorage.removeItem("token");
        await refresh();
        navigate("/login", { replace: true });
    };
}