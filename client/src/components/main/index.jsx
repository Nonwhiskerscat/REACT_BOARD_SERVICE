
import '../../scss/main/common.scss';
import SiteMap from "./semi/sitemap";
import Header from "../dom/MainHeader";
import ListTable from "./list/listTable";

export default function Main() {
    return (
        <>
            <Header>게시판 홈</Header>
            <main>
                <SiteMap />
                <ListTable />
            </main>
            <footer />
        </>
    );
}