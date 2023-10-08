function previewImage(filePath) {
    var previewImage = document.getElementById('previewImage');
    var noImage = document.getElementById('noImage');

    if (filePath) {
        // 이미지 파일이 있는 경우
        previewImage.src = "/attach/view?filePath=" + filePath;
        previewImage.style.display = 'block';
        noImage.style.display = 'none';
    } else {
        // 이미지 파일이 없는 경우
        previewImage.style.display = 'none';
        noImage.style.display = 'block';
    }
}

function imageUploadPopup() {
    const w = 650;
    const h = 330;
    const left = screen.width / 2 - w / 2;
    const top = screen.height / 2 - h / 2;

    window.open("../attach?attachType=IMAGE&attachEntity=MEMBER", "_blank", `width=${w}, height=${h}, top=${top}, left=${left}`);
}