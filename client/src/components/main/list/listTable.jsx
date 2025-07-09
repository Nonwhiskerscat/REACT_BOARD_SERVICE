
import '../../../scss/main/table.scss';
import { useSearchParams, useLocation  } from "react-router-dom";
import { useEffect, useState } from "react";
import { gPushCall } from '../../../method/ajax';
import { API_URL } from '../../../method/ajax';

export default function ListTable() {
    const [rows, setRows] = useState([]);

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    const paramMode = searchParams.get("mode");

    var param = new FormData();
    param.append('mode', paramMode);

    useEffect(() => {
        // 모든 .site_map .element 요소에서 selected 제거
        const allElements = document.querySelectorAll(".site_map .element");
        allElements.forEach((el) => {
            el.classList.remove("selected");
        });
        
        let targetElement;
        // keyname 속성이 mode와 일치하는 요소에 selected 추가
        if (paramMode) {
            targetElement = document.querySelector(`.site_map .element[keyname="${paramMode}"]`);
        }

        else {
            targetElement = document.querySelector(`.site_map .element[keyname="all"]`);
        }

        if (targetElement) {
            targetElement.classList.add("selected");
        }
    }, [searchParams]);

    useEffect(() => {
        
        const param = new FormData();
        param.append("mode", paramMode);

        gPushCall(API_URL + "/board/list", param, (res) => {
            if(!res) setRows(new Array());
            else setRows(res);
        });
    }, [paramMode]); // paramMode 가 바뀔 때마다 다시 호출

    return(
        <>
            <div className="table_area">
                <table>
                    <thead>
                    <tr>
                        <th className='num'>번호</th>
                        <th className='time'>날짜</th>
                        <th className='title'>제목</th>
                        <th className='name'>이름</th>
                        <th className='click'>조회수</th>
                        <th className='like'>추천수</th>
                    </tr>
                    </thead>
                    <tbody>
                        {rows.length === 0 ? (
                            <tr>
                            <td colSpan={6} style={{ textAlign: 'center', padding: '20px' }}>
                                데이터가 없습니다
                            </td>
                            </tr>
                        ) : (
                            rows.map((row, index) => (
                                <tr idx={row.idx} key={row.idx} onClick={()=> {
                                    window.location.href = "/view?idx=" + row.idx;
                                }}>
                                    <td>{index + 1}</td>
                                    <td>{row.regDate}</td>
                                    <td className="title">{row.title} [{row.commentCnt}]</td>
                                    <td>{row.regNickname}</td>
                                    <td>{row.clickCnt}</td>
                                    <td>{row.likeCnt}</td>
                                </tr>
                            ))
                        )}
                    </tbody>
                </table>
            </div>
        </>
    )
}