var question = {
    questionText: function (focusid) {
        var focusblurid = $(focusid);
        var defval = focusblurid.val();
        focusblurid.focus(function () {
            var thisval = $(this).val();
            if (thisval == defval) {
                $(this).val("");
            }
        });
        focusblurid.blur(function () {
            var thisval = $(this).val();
            if (thisval == "") {
                $(this).val(defval);
            }
        });
    },
    saveQuestion: function (ele) {
        var questiontype = $("#question-type").val();
        var questiondesc = $("#question-desc").val();
        if (util.trim(questiondesc) == "" || questiondesc.length < 10) {
            util.layerAlert("", util.getLan("question.tips.1"));
            return;
        }
        var url = "/index.php?s=exc&c=helpController&m=saveQuestion";
        var param = {
            questiontype: questiontype,
            questiondesc: questiondesc
        };
        var callback = function (data) {
            if (data.code == 200) {
                util.layerAlert("", data.msg, 1);
            } else {
                util.layerAlert("", data.msg, 2);
            }
        };
        util.network({btn: ele, url: url, param: param, success: callback});
    },
    delQuestion: function (data) {
        var questionid = $(data).data('questionid');
        var url = "/index.php?s=exc&c=helpController&m=delQuestion";
        var param = {
            fid: questionid
        };
        var callback = function (data) {
            if (data.code == 200) {
                location.reload();
            } else {
                util.layerAlert("", data.msg, 2);
            }
        };
        util.network({url: url, param: param, success: callback});
    }
};

$(function () {
    /* 文本域 */
    question.questionText("#question-desc");
    /* 删除 */
    $(".delete").click(function () {
        question.delQuestion(this);
    });
    /* 提交问题 */
    $("#submitQuestion").on("click", function () {
        question.saveQuestion(this);
    });
    $('.look').click(function(){
        $('.js-data-title').html($(this).data('question'));
        $('.js-data-data').html($(this).data('answer'));
        $('#question-detail').show();
    });

});