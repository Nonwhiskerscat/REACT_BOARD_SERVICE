import React, { createContext, useCallback, useContext, useState } from "react";
import axios from "axios";

const altPort = "7102";
const altHost = window.location.hostname;
export const API_URL = `http://${altHost}:${altPort}`;

/**
 * Content-Type 자동 처리
 */
function prepareDataAndHeaders(data) {
    if (data instanceof FormData) {
        return { payload: data, headers: {} };
    }

    if (typeof data === 'string') {
        return {
            payload: data,
            headers: {
            'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
            },
        };
    }

    return {
        payload: JSON.stringify(data),
        headers: { 'Content-Type': 'application/json' },
    };
}

export function gPushCall(url, data, fnCallBack, fnErrorCallBack) {
    const { payload, headers } = prepareDataAndHeaders(data);

    axios.post(url, payload, { headers })
    .then((res) => {
        if(fnCallBack) fnCallBack(res.data);
        else {
            var data = res.data;
            console.log(data);
            data.result == true ? alert('성공 > ' + data.message) : alert('실패 > ' +  data.message);
        }
    })
    .catch((err) => {
        if (fnErrorCallBack) {
            const responseText = err?.response?.data || err.message;
            fnErrorCallBack(err, err?.message, responseText);
        } else {
            alert(err?.response?.data || err.message);
        }
    });
}

