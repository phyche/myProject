$(function() {
    document.getElementById('JweixinTip').style.display='none';
})
function isWeiXin(){
    var ua = window.navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i) == 'micromessenger'){
        return true;
    }else{
        return false;
    }
}

function onclickapp(value){
    if(isWeiXin()){
        document.getElementById('JweixinTip').style.display='block';
    }else{
        window.location.href=value;
    }
}
