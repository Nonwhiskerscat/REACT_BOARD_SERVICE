import React, { createContext, useContext, useEffect, useState } from "react";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import { API_URL } from "../method/ajax";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);       // LoginVO
    const [loading, setLoading] = useState(true); // 첫 로딩 플래그
    const navigate = useNavigate();
    const location = useLocation();

    /** 세션 → /api/auth/me 로드 */
    const fetchMe = async () => {
    try {
        const { data } = await axios.get(API_URL + "/api/auth/me");
        if (location.pathname == "/login") navigate("/");
        setUser(data);
    } catch (e) {
        if (e.response && e.response.status === 401) {
            console.log("서버 응답 코드 401 - 커스텀 처리");
            // 여기에 701 에러 전용 로직
        } else {
            console.log(e);
        }
        setUser(null);
        if (location.pathname !== "/login") navigate("/login");
    } finally {
        setLoading(false);
    }
    };

    // ① 첫 마운트  ② location 변경마다 호출
    useEffect(() => { fetchMe(); }, []);

    return (
    <AuthContext.Provider value={{ user, loading, refresh: fetchMe }}>
        {children}
    </AuthContext.Provider>
    );
};

export const useAuth = () => useContext(AuthContext);