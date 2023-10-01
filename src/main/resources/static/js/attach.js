document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('myForm');

    form.addEventListener('submit', function (e) {
        e.preventDefault();

        var xhr = new XMLHttpRequest();
        xhr.open('POST', '/attach/attachType/{attachType}/entity/{entity}', true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log(xhr.responseText);

                // 부모 창에 응답을 전달하고 창을 닫을 수 있도록 자신을 닫습니다.
                window.opener.getUploadResponse(xhr.responseText);
                window.close();
            }
        };

        var formData = new FormData(form);
        xhr.send(formData);
    });
});