
function scrollBanner( scrollamount ){
    var banner = document.querySelector('.banneritems');
    var bannerIndex = document.querySelector('.contentIndexes');
    var target = scrollamount * -1280;
    var animTimer = setInterval(function(){
        banner.style.left = (banner.offsetLeft + (banner.offsetLeft >= (scrollamount * -1280) ? -80 : 80)) + 'px';
        if( banner.offsetLeft == target ){
            clearInterval(animTimer);
        }
    },15);
    setChangeSubClassName(bannerIndex,'normalIndex');
    bannerIndex.children[scrollamount].setAttribute('class','activeIndex');
}

function startBannerScrollTimer( timedelay ){
    setInterval(function(){
        window.curBannerIdx = window.curBannerIdx + 1;

        if( window.curBannerIdx > window.imgs.length - 1 ){
            window.curBannerIdx = 0;
        }

        scrollBanner(window.curBannerIdx);
    },timedelay * 1000);
}

function initBannerImgs(){
    var banner = document.querySelector('.banneritems');
    var bannerIndex = document.querySelector('.contentIndexes');
    var bannerItem,bannerImg;

    for(var i = 0; i < window.imgs.length; i++){
        bannerImg = appendDOM(banner,'img');
        bannerItem = appendDOM(bannerIndex,'div','normalIndex',{'type':'click','callback':function(event){
                /* 사용자가 이미 선택되어 있는 배너를 다시 선택하면 */
                /* 같은 위치에 배너를 다시 스크롤 시키면서 */
                /* 배너가 깜빡이는 현상(Flickering)이 발생함 */
                /* 그래서 조건을 비교하여 동일한 배너를 */
                /* 다시 선택 하였는지, 확인하여 동일한 배너이면 */
                /* 콜백함수를 반환해서, 스크롤 처리를 막음 */
                if(window.curBannerIdx == event.target.idx){
                    return;
                }

                window.curBannerIdx = event.target.idx;
                scrollBanner(event.target.idx);
            }});
        bannerItem.idx = i;
        bannerImg.src = window.imgs[i];
        bannerImg.style.left = (i * 1280) + 'px';
    }
}

window.onload = function(){
    /* 현재 메인에 표시되는 배너의 순서를 기억합니다 */
    window.curBannerIdx = 0;
    /* 여기에 사용할 배너 이미지 경로들을 추가하시면 됩니다. */
    window.imgs = [
        "./imgs/banner1.jpg",
        "./imgs/banner2.jpg",
        "./imgs/banner3.jpg"
    ];
    initBannerImgs();
    /* 5초 간격으로 배너를 회전(Spin) 시키는 타이머(Timer) 실행 */
    startBannerScrollTimer(5);
}