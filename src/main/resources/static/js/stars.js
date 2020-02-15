/**
 * 提交一级回复
 */
function post(){
    var parentId = $("#comment_parentId").val();
    var content = $("#comment_content").val();
    comment2target(parentId,1,content);
}

/**
 * 二级评论
 * @param commentId
 */
function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();
    comment2target(commentId,2,content);
}

/**
 * 将用户提交评论封装---Ajax
 * @param commentId
 * @param type
 * @param content
 */
function comment2target(commentId,type,content) {
    if (!content){
        alert("不能为空")
        return;
    }
    //Ajax
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":commentId,
            "content":content,
            "type":type
        }),
        success:function (response) {
            if (response.code == 200){
                window.location.reload();
            }else {
                if (response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if (isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=442c04c44023f7f4e65c&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.code+"---"+response.message)
                }
            }

        },
        dataType:"JSON"
    })
}


/**
 * 展开二级评论
 */
function collapseComments(e) {
    //使用toggleClass进行展开折叠
/*   var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    comments.toggleClass("in active");*/

    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);

    //获取二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse){
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    }else{
        var subCommentContainer = $("#comment-"+id);
        if (subCommentContainer.children().length != 1){
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态
            e.setAttribute("data-collapse","in");
            e.classList.add("active");
        }else {
            $.getJSON( "/comment/"+id, function( data ) {
                //追加标签
                //reverse()关键字不将后面的放前面
                $.each( data.data.reverse(), function(index,comment) {

                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"img-rounded",
                        "src":comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<h5/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"menu"
                    })).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format("YYYY-MM-DD")
                    }));

                    var mediaElement = $("<div/>",{
                        "class":"media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);
                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse","in");
                e.classList.add("active")
            });
        }
    }
}

function showTag() {
    $("#select-tag").show();
}

function addTag(e) {
    var value = e.getAttribute("data-tag");
    var pervious = $("#tag").val();
    if (pervious.split(",").indexOf(value) == -1){
        if (pervious){
            $("#tag").val(pervious+","+value);
        }else {
            $("#tag").val(value);
        }
    }
}








/*
$(function () {

    function validate_add_form(){
        //获取校验的数据
        //1、验证title
        var title = $("#title").val();
        var regTitle = /^[\S]{1,25}$/;
        if (!regTitle.test(title)){
            //给当前输入框的父元素 Class 添加 has-error
            $("#title").parent().addClass("has-error");
            $("#title").next("span").text("标题不能超过1-25!");
            return false;
        }else {
            //成功提示
            $("#title").parent().addClass("has-success");
            $("#title").next("span").text("");
        }

        //2、验证description
        var description = $("#description").val();
        var regDescription = /^[\S]{1,800}$/;
        if (!regDescription.test(description)){
            $("#description").next("span").text("问题补充不得超过800字!");
            return false;
        }else {
            //成功提示
            $("#description").parent().addClass("has-success");
            $("#description").next("span").text("");
        }

        //3、验证tag
        var tag = $("#tag").val();
        var regTag = /^(\S){1,10}，$/;
        if (!regTag.test(tag)){
            $("#tag").next("span").text("标签不得超过1-10!");
            return false;
        }else {
            //成功提示
            $("#tag").parent().addClass("has-success");
            $("#tag").next("span").text("");
        }
    }

    //publish.html 的表单验证
    $("#question_form").submit(function () {

       if (validate_add_form()){
           alert("成功")
       }else {
           alert("失败")
       }

    })
})*/
