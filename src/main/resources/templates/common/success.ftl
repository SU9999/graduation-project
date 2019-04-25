<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>成功页面</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>成功!</h4>
                <strong>${msg!""}</strong> &nbsp;&nbsp;<span id="time">3</span>s后自动跳转！<a href="${url}" class="alert-link">alert
                    link</a>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    var time = 2;
    var interval = window.setInterval(function () {
        if (time == -1) {
            window.clearInterval(interval);
            location.href = "${url}";
        }
        document.getElementById('time').innerText = time;
        time--;
    }, 1000);

    /*window.setTimeout(function () {
        window.clearInterval(interval);
    }, 3000);*/
</script>
</html>