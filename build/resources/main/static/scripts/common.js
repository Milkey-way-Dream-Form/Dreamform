window.includeCSS = function( url ){
    var link = document.createElement('link');
    link.rel = 'stylesheet';
    link.href = url;
    document.head.appendChild( link );
}

function onStatusResizing( onstart,oncontinue,onend ){
    var resizeTimer = null;
    var isResizing = false;
    window.addEventListener('resize',function(event){
        if( oncontinue ){
            oncontinue();
        }
        if( !isResizing ){
            if( onstart ){
                onstart();
            }
            isResizing = true;
        }
        clearTimeout( resizeTimer );
        resizeTimer = setTimeout(function(){
            if( onend ){
                onend();
            }
            isResizing = false;
        },500);
    });
}

function setClassName( target,classname ){
    target.setAttribute('class',classname);
}

function setChangeSubClassName( target,classname ){
    var child = target.children;
    for(var i = 0; i < child.length; i++){
        setClassName(child[i],classname);
    }
}

function appendDOM( parent,tag,classname,event ){
    var dom = document.createElement(tag);
    classname ? dom.setAttribute('class',classname) : null;
    event ? dom.addEventListener(event.type,event.callback) : null;
    parent.appendChild(dom);
    return dom;
}

// function toggleOnlyLoggedElement(){
//     var elements = document.getElementsByClassName('showonlylogged');
//     for(var i = 0; i < elements.length; i++){
//         if( !sessionStorage.session_id ){
//             /* 세션 정보가 없으면 로그인 전용 요소들을 숨김 */
//             elements[i].style.display = 'none';
//         }
//     }
// }

// function onUserMenuButton_clicked(){
//     this.isClicked = !this.isClicked;
//     var usermenu = document.querySelector('.usermenu');
//     if( usermenu ){
//         if( usermenu.children.length > 0 ){
//             if( this.isClicked ){
//                 usermenu.children[0].style.display = 'block';
//             }else{
//                 usermenu.children[0].style.display = 'none';
//             }
//         }
//     }
// }

// addEventListener('load',function(event){
//     toggleOnlyLoggedElement();
// });
