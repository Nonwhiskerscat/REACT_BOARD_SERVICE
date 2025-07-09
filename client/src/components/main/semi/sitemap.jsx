import '../../../scss/main/sitemap.scss';

export default function SiteMap() {
    const items = [
        { key: "all", label: "전체 게시물" },
        { key: "best", label: "베스트 게시판" },
        { key: "my",  label: "마이 게시판" },
    ];

    return (
        <div className="site_map">
        <div className="site_map_flex">
            {items.map(item => (
                <div className="element" keyname={item.key} key={item.key}
                onClick={() => {
                    const url = new URL(window.location.href);
                    url.searchParams.set("mode", item.key); // ?mode=xxx 형태로 설정
                    window.location.href = url.toString();
                }}>
                    {item.label}
                </div>
            ))}
        </div>
        </div>
    );
}
