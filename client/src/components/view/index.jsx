
import '../../scss/view/common.scss';

import Header from "../dom/SubHeader";
import BoardInfo from './semi/info';
import BoardComment from './semi/comment';

import { gPushCall } from "../../method/ajax";
import React, { useState } from "react";
import { API_URL } from '../../method/ajax';

export default function View() {
    const [title, setTitle] = useState("");
    const [body, setBody] = useState("");
    const [idx, setIdx] = useState("");

    var param  = new FormData();
    param.append('title', title);
    param.append('body', body);

    const deleteHandler = () => {
        if (window.confirm("해당 게시글을 삭제하시겠습니까?")) {
            var param  = new FormData();
            param.append('idx', idx);
            gPushCall(API_URL + "/board/delete", param, function(res) {
                if(res.result == 'success') {
                    window.alert('게시글 삭제가 완료되었습니다.');
                    window.location.href = "/";
                }

                else {
                    window.alert(res.message)
                    if(res.type == "notfound") {
                        window.location.href = "/";
                    }
                }
            });
        }
    };

    return (
        <>
            <Header>작성 글 정보</Header>
            <main>
                <BoardInfo onLoaded={(idx) => setIdx(idx)}/>
                {/* <BoardComment /> */}
                <div className="btn_area">
                    <div className="left_area">
                        <button id="backBoard" onClick={() => { window.location.href = "/"; }}>메인 화면</button>
                    </div>
                    <div className="right_area">
                        <button id="modifyBoard" onClick={() => { window.location.href = "/write?idx=" + idx; }}>게시글 수정</button>
                        <button id="deleteBoard" className='delete' onClick={ deleteHandler }>게시글 삭제</button>
                    </div>
                </div>
            </main>
            <footer />
        </>
    );
}