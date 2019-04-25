<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Demo</title>
</head>
<body>
<span id="time">4</span>
<script>
    var time = 3;
    var interval = window.setInterval(function () {
        var node = document.getElementById('time');
        node.innerText = time;
        time --;
    }, 1000);
</script>
</body>
</html>