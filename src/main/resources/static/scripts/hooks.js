
function hook_clicked( data ){
    if( data == 'HOOK_CLICKED_FINDID' ){
        if( window.createCustomPopup ){
            var popup = window.createCustomPopup();
            popup.setBackdropEnabled(true);
            popup.setWidth(240);
            popup.setHeight(120);
            popup.center();
            popup.addSystemButtons();
            popup.show();
        }
        return;
    }
}