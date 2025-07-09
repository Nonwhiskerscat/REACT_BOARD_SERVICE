import { useAuth } from "../../context/AuthContext";
import { HeaderButton } from "./style";
import '../../scss/main/header.scss';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { HandleLogout } from "../../method/auth";

export default function Header({children}) {
    const navigate = useNavigate();
    const { refresh } = useAuth();
    const { user } = useAuth();
    const handleLogout = HandleLogout();

    return (
        <>
            <header>
                <div className="header_flex">
                    <div className="left">
                        <div>
                            {/* <HeaderButton onClick={() => { window.location.href = "/"; }}>메인 화면</HeaderButton> */}
                        </div>
                    </div>
                    <div className="center"><h1>{children}</h1></div>
                    <div className="right">
                        {user && (
                        <div>
                            <p style={{ marginRight: '1em' }}>{user.nickname}({user.code})</p>
                            <HeaderButton onClick={handleLogout}>로그아웃</HeaderButton>
                        </div>
                        )}
                    </div>
                </div>
            </header>
        </> 
    );
}