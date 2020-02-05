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
})