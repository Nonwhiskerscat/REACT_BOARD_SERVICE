import React, { useState } from "react";
import * as _ from "./style";
import { gPushCall } from "../../method/ajax";
import { Link } from 'react-router-dom';
import '../../scss/signup/main.scss'
import { HandleLogout } from "../../method/auth";
import { API_URL } from "../../method/ajax";

const SignUp = () => {
    const [userCode, setUserCode] = useState("");
    const [userPassword, setUserPassword] = useState("");
    const [userPasswordCheck, setUserPasswordCheck] = useState("");
    const [userEmail, setUserEmail] = useState("");
    const [userNickname, setUserNickname] = useState("");
    const [userPhoneNumber, setUserPhoneNumber] = useState("");
    const [userAddress, setUserAddress] = useState("");
    const [userAddressDetail, setUserAddressDetail] = useState("");
    const handleLogout = HandleLogout();
    
    var param  = new FormData();
    param.append('code', userCode);
    param.append('password', userPassword);
    param.append('passwordCheck', userPasswordCheck);
    param.append('email', userEmail);
    param.append('nickname', userNickname);
    param.append('phoneNumber', userPhoneNumber);
    param.append('address', userAddress);
    param.append('addressDetail', userAddressDetail);

    return (
    <_.SignUpContainer>
        <_.SignUpForm>
            <h1 style={{color: '#005f2f'}}>SIGN UP</h1>
            <div className="signup_input_flex">
                <_.SignUpInput
                    type="text"
                    name="code"
                    placeholder="ID를 입력해주세요"
                    onChange={(e) => {
                    setUserCode(e.target.value);
                    }}
                />
                <_.SignUpInput
                    type="password"
                    name="password"
                    placeholder="비밀번호를 입력해주세요"
                    onChange={(e) => {
                    setUserPassword(e.target.value);
                    }}
                />
                <_.SignUpInput
                    type="password"
                    name="passwordCheck"
                    placeholder="비밀번호를 확인해주세요"
                    onChange={(e) => {
                    setUserPasswordCheck(e.target.value);
                    }}
                />
                <_.SignUpInput
                    type="text"
                    name="email"
                    placeholder="이메일을 입력해주세요"
                    onChange={(e) => {
                        setUserEmail(e.target.value);
                    }}
                />
                <_.SignUpInput
                    type="text"
                    name="nickname"
                    placeholder="닉네임을 입력해주세요"
                    onChange={(e) => {
                    setUserNickname(e.target.value);
                    }}
                />
                <_.SignUpInput
                    type="text"
                    name="phoneNumber"
                    placeholder="휴대폰번호를 입력해주세요"
                    onChange={(e) => {
                    setUserPhoneNumber(e.target.value);
                    }}
                />
                <_.SignUpInput
                    type="text"
                    name="address"
                    placeholder="주소를 입력해주세요"
                    onChange={(e) => {
                    setUserAddress(e.target.value);
                    }}
                />
                <_.SignUpInput
                    type="text"
                    name="addressDetail"
                    placeholder="상세주소를 입력해주세요"
                    onChange={(e) => {
                    setUserAddressDetail(e.target.value);
                    }}
                />
            </div>
            <div className="signup_btn_flex">
                <Link>
                    <_.SignUpButton onClick={() => gPushCall(API_URL + "/api/auth/signUp", param, function(res) {
                        if(res.result) {
                            window.alert('가입이 완료되었습니다.');
                            handleLogout();
                            window.location.href = '/login';
                        }
                        
                        else {
                            alert(res.message);
                        }
                        
                    }, function() {
                        console.log('냥냥');
                    })}>가입 완료</_.SignUpButton>
                </Link>
                <Link to="../login">
                    <_.SignUpButton>뒤로가기</_.SignUpButton>
                </Link>
            </div>
        </_.SignUpForm>
    </_.SignUpContainer>
    );
};

export default SignUp;