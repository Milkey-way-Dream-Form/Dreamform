
function scrollBanner( scrollamount ){
    updateBannerWide();

    var banner = document.querySelector('.banneritems');
    var bannerIndex = document.querySelector('.contentIndexes');

    banner.style.left = (scrollamount * banner.clientWidth * -1) + 'px';

    setChangeSubClassName(bannerIndex,'normalIndex');
    bannerIndex.children[scrollamount].setAttribute('class','activeIndex');
}

function startBannerScrollTimer( timedelay ){
    setInterval(function(){
        /* 브라우저가 최소화 되거나, 페이지가 다른 탭으로 이동했을 때 */
        /* 브라우저가 인터벌 시간을 자동으로 1초로 최적화 시켜버리기 때문에 */
        /* 배너가 이상하게 떨리면서 이동하지 않는 버그가 발생 */
        /* 따라서 브라우저가 최소화 되거나 탭이 이동(Unfocused) 상태이면 */
        /* 타이머 내용을 실행시키지 않음으로써, 버그 방지 */
        if( document.hasFocus() ){
            window.curBannerIdx = window.curBannerIdx + 1;

            if( window.curBannerIdx > window.imgs.length - 1 ){
                window.curBannerIdx = 0;
            }

            scrollBanner(window.curBannerIdx);
        }
    },timedelay * 1000);
}

function updateBannerWide(){
    var banner = document.querySelector('.banneritems');
    var bannerImg = document.getElementsByClassName('bannerImgs');
    bannerImg[window.curBannerIdx].style.width = banner.clientWidth + 'px';
}

function initBannerImgs(){
    var banner = document.querySelector('.banneritems');
    var bannerIndex = document.querySelector('.contentIndexes');
    var bannerItem,bannerImg;

    for(var i = 0; i < window.imgs.length; i++){
        bannerImg = appendDOM(banner,'img','bannerImgs');
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
        bannerImg.style.width = banner.clientWidth + 'px';
        bannerImg.src = window.imgs[i];
    }
}

onStatusResizing( null,function(){
    updateBannerWide();
},function(){
    window.curBannerIdx = 0;
    scrollBanner(window.curBannerIdx);
});

window.onload = function(){
    /* 현재 메인에 표시되는 배너의 순서를 기억합니다 */
    window.curBannerIdx = 0;
    /* 여기에 사용할 배너 이미지 경로들을 추가하시면 됩니다. */
    window.imgs = [
        "./imgs/banner1.png",
        "./imgs/banner2.png",
    ];
    initBannerImgs();
    /* 5초 간격으로 배너를 회전(Spin) 시키는 타이머(Timer) 실행 */
    startBannerScrollTimer(5);
}