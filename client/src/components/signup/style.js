import styled from "styled-components";

export const SignUpContainer = styled.div.attrs({
    className: 'signup_container',
})`
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
`;

export const SignUpForm = styled.div.attrs({
    className: 'signup_form',
})`
    width: 500px;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    margin-top: 30px;
`;

export const SignUpInput = styled.input.attrs({
    className: 'signup_input',
})`
    width: 100%;
    height: 40px;
    border: 0.5px solid gray;
    margin-top: 20px;
    background-color: #ffffff !important;
`;

export const SignUpButton = styled.button.attrs({
    className: 'signup_button',
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