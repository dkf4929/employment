var count = 1;
var sCount = 0; // 학교 검색 했는지 여부
var member;

window.onload = function () {
    previewImage(document.getElementById('filePath').value);
}

function schoolSearch() {
    const w = 500;
    const h = 500;
    const left = screen.width / 2 - w / 2;
    const top = screen.height / 2 - h / 2;

    window.open("./school-search?schoolName=" + document.getElementById('schoolName').value, "_blank", "width=${w}, height=${h}, top=${top}, left=${left}");
}

const inputField = document.querySelector("#phoneNumber");

inputField.addEventListener("input", () => {
    const value = inputField.value;
    const newValue = value.replace(/[^0-9]/g, ""); // 숫자 이외의 문자를 제거
    inputField.value = newValue;
});

function getSchoolName(data) {
    document.getElementById('schoolName').value = data;
    document.getElementById('schoolName').readOnly = true
    sCount ++;
}


const myForm = document.getElementById("myForm");
const submitBtn = document.getElementById("save");

myForm.addEventListener('submit', event => {
    event.preventDefault();

    const formData = new FormData(myForm);

    fetch('/member', {
        method: 'PUT',
        body: formData
    }).then(response => {
        if (!response.ok) {
            return response.text().then(error => { throw new Error(error); });
        }

        alert("수정되었습니다.");
        location.href = "/";
    }).then(data => {
    }).catch(error => {
        alert(error.message); // 서버에서 발생한 예외 메시지를 알림 창에 표시
    });
});

function getUploadResponse(data) {
    var data = JSON.parse(data);
    document.getElementById('imageFileName').value = data.fileName;
    document.getElementById('attachFileId').value = data.id;
    document.getElementById('filePath').value = data.filePath;

    previewImage(data.filePath);
}