
import '../../scss/writer/editor.scss';
import Header from "../dom/SubHeader";
import { gPushCall } from "../../method/ajax";
import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { API_URL } from '../../method/ajax';

export default function Write() {
    const [title, setTitle] = useState("");
    const [body, setBody] = useState("");
    const [res, setRes] = useState(new Object());

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const paramIdx = searchParams.get("idx");

    useEffect(() => {
        if(paramIdx) {
            const param = new FormData();
            param.append("idx", paramIdx);
            param.append("click", true);

            gPushCall(API_URL + "/board/view", param, function(res) {            
                if(res.result && res.result == 'failed') {
                    alert(res.message);
                    window.location.href = "/";
                }
                else {
                    console.log(res);
                    setRes(res);
                }
            });
                
        }
    }, [paramIdx]);

    useEffect(() => {
        if (res && Object.keys(res).length > 0) {
            setTitle(res.title);
            setBody(res.body);
        } else {
            setTitle('');
            setBody('');
        }
    }, [res]);

    const SaveHandler = () => {
        var param  = new FormData();
        param.append('title', title);
        param.append('body', body);
        gPushCall(API_URL + "/board/write", param, function() {
            window.location.href = "/";
        });
    }

    const ModifyHandler = () => {
        var param  = new FormData();
        param.append('title', title);
        param.append('body', body);
        param.append('idx', res.idx);
        gPushCall(API_URL + "/board/update", param, function(res) {
            if(res.result == 'success') {
                window.alert('게시글 편집이 완료되었습니다.');
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

    return (
        <>
            <Header> {res && Object.keys(res).length === 0 ? "새 글 작성" : "게시글 편집"} </Header>
            <main>
                <div className="editor_flex">
                    <div className="title_area">
                        <p>제목</p>
                        <input type="text" id="editorTitle" value= {title} onChange={(e) => {
                            setTitle(e.target.value);
                        }}/>
                    </div>
                    <div className="content_area">
                        <p>내용</p>
                        <textarea
                            id="editorBody"
                            value= {body}
                            onChange={(e) => setBody(e.target.value)}
                        />
                    </div>
                </div>
                <div className="btn_area">
                    <div className="left_area">
                        <button id="backBoard" onClick={() => { window.location.href = "/"; }}>메인 화면</button>
                    </div>
                    <div className="right_area">
                        {res && Object.keys(res).length === 0 ? 
                            <button id="submitBoard" onClick={ SaveHandler }>작성 완료</button>
                        : 
                            <button id="modifyBoard" onClick={ ModifyHandler }>편집 완료</button>
                        }
                        
                    </div>
                </div>
            </main>
            <footer />
        </>
    );
}