import React, { ChangeEvent, useState } from "react";
import * as _ from "./style";
import { gPushCall } from "../../method/ajax";
import { Link } from 'react-router-dom';
import '../../scss/login/main.scss'

const Login = () => {
    const [userCode, setUserCode] = useState("");
    const [userPassword, setUserPassword] = useState("");

    var param = new FormData();
    param.append('code', userCode);
    param.append('password', userPassword);

    const loginHandler = () => {
        gPushCall("/api/auth/login", param, (res) => {
            if(res.result) {
                window.location.href = "/";
            }

            else {
                alert('실패 > ' +  res.message);
            }
        });
    }

    return (
    <_.LoginContainer>
        <_.LoginForm>
            <h1 style={{color: '#005f2f'}}>LOGIN</h1>
            <div className="login_input_flex">
                <_.LoginInput
                    type="text"
                    name="code"
                    placeholder="ID를 입력해주세요"
                    onChange={(e) => {
                        setUserCode(e.target.value);
                    }}
                />
                <_.LoginInput
                    type="password"
                    name="password"
                    placeholder="비밀번호를 입력해주세요"
                    onChange={(e) => {
                        setUserPassword(e.target.value);
                    }}
                />
            </div>
            <div className="login_btn_flex">
                <Link>
                    <_.LoginButton onClick={loginHandler}>로그인</_.LoginButton>
                </Link>
                <Link to="../signUp">
                    <_.LoginButton>회원가입</_.LoginButton>
                </Link>
            </div>
        </_.LoginForm>
    </_.LoginContainer>
    );
};

export default Login;