import styled from "styled-components";

export const LoginContainer = styled.div.attrs({
    className: 'login_container',
})`
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
`;

export const LoginForm = styled.div.attrs({
    className: 'login_form',
})`
    width: 500px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-top: 30px;
`;

export const LoginInput = styled.input.attrs({
    className: 'login_input',
})`
    width: 100%;
    height: 40px;
    border: 0.5px solid gray;
    margin-top: 20px;
    background-color: #ffffff !important;
`;

export const LoginButton = styled.button.attrs({
    className: 'login_button',
})`
    width: 80px;
    height: 40px;
    border: 0;
    cursor: pointer;
    margin-bottom: 20px;
    background-color: #005f2f;
    color: white;
    outline: none;
    border-radius: 1em;
    margin-top: 20px;
    font-weight: bold;
`;