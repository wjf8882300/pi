$(function() {

    function send(type) {
        var form = {};
        form.type = type;
        $.ajax({
            type: "post",
            url: "/pi/send",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(form),
            cache: false,
            success: function (data) {
                if (data.code != 200) {
                    fail_prompt(data.message);
                }
            },
            error: function (e) {
                fail_prompt("获取服务器数据异常!" + e);
            }
        });
    }

    $("button[name='control']").each(function() {
        $(this).bind('click', function() {
            send($(this).val());
        });
    });
})