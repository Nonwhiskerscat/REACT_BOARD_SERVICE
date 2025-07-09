import '../../../scss/view/info.scss';
import { API_URL, gPushCall } from "../../../method/ajax";
import { useLocation } from "react-router-dom";
import { useEffect, useState } from "react";

export default function BoardInfo({ onLoaded }) {
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const paramIdx = searchParams.get("idx");

    if(!paramIdx) {
        alert('잘못된 접근입니다.');
        window.location.href = "/";
    }

    const [res, setRes] = useState(new Object());
    const [likeCnt, setLike] = useState();
    const [dislikeCnt, setDislike] = useState();

    useEffect(() => {       
        const param = new FormData();
        param.append("idx", paramIdx);
        param.append("click", true);

        gPushCall(API_URL + "/board/view", param, function(res) {            
            if(res.result && res.result == 'failed') {
                alert(res.message);
                window.location.href = "/";
            }
            else {
                setRes(res);
                setLike(res.likeCnt);
                setDislike(res.dislikeCnt);

                if (onLoaded) {
                    onLoaded(res.idx); 
                }
            }
        });
    }, [paramIdx]); // paramIdx 가 바뀔 때마다 다시 호출

    const likeHandler = (target) => {
        var param = new FormData();
        param.append('idx', res.idx);
        param.append('target', target);

        gPushCall(API_URL + "/board/feedback", param, function(res) {            
            if(res.result == 'success') {
                if(target == "like") {
                    setLike(res.count);
                }
                else if(target == "dislike") {
                    setDislike(res.count);
                }
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
        <div className="info_area">
            <input type="hidden" id="boardIdx" value={res.idx} />
            <section className="title_section">
                <div className="upper">
                    <p className="title">{res.title}</p>
                </div>
                <div className="lower">
                    <div className="left">
                        <div className="info_element ">
                            <p className="regUser">{res.regNickname}({res.regCode})</p>
                            <span className="border">·</span>
                            <p className="regIpAddress">{res.regIpAddress}</p>
                            <span className="border">·</span>
                            <p className="regDatetime">{res.regDate}</p>
                        </div>
                    </div>
                    <div className="right">
                        <div className="info_element">
                            <h4>Click</h4>
                            <span className="border">·</span>
                            <p className="clickCnt">{res.clickCnt}</p>
                        </div>
                        <div className="info_element">
                            <h4>Likes</h4>
                            <span className="border">·</span>
                            <p className="likeCnt">{likeCnt}</p>
                        </div>
                    </div>
                </div>
            </section>
            <section className="body_section">
                <p>{res.body}</p>
            </section>
            <section className="like_section">
                <div className="like_btn">
                    <button onClick={ () => {likeHandler("like")}}>👍</button>
                    <p>{likeCnt}</p>
                    <span className="border">·</span>
                    <button className="dislike" onClick={ () => {likeHandler("dislike")}}>👎</button>
                    <p>{dislikeCnt}</p>
                </div>
            </section>
        </div>
    );
}
