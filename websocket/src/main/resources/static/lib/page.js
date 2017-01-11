function goPages(){
    gotoPages($("#goPage").val());
}
function gotoPages(page){
    if( validate(page) ){
        document.forms[0].elements['currentPage'].value = page;
        document.forms[0].submit();
    }
}
function IsInteger(varString) {
    return /^[0-9]+$/i.test(varString);
}

function validate(page){
    var totalPage=$("#totalPage").val();
    var current_page=$("#currentPage").val();
    if(page == null || page == ""){
        $.jBox.messager('页号无效', '提示');
        return false;
    }
    if(!IsInteger(page)){
        $.jBox.messager("页号必须为整数！");
        return false;
    }
    if(page == current_page)
        return false;
    if(page > totalPage){
        $.jBox.messager('超出最大页', '提示');
        return false; }
    if(page < 1){
        $.jBox.messager("超出最小页！");
        return false;
    }
    return true;
}
function tip(message){
    $.jBox.tip(message, '提示！',{timeout:500});
}