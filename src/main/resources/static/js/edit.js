var count = 1;
var sCount = 0; // 학교 검색 했는지 여부

function schoolSearch() {
    const w = 500;
    const h = 500;
    const left = screen.width / 2 - w / 2;
    const top = screen.height / 2 - h / 2;

    window.open("./school-search?schoolName=" + document.getElementById('schoolName').value, "_blank", "width=${w}, height=${h}, top=${top}, left=${left}");
}

window.onload = function() {
    const base64ImageData = `[[${file}]]`;

    if (base64ImageData && base64ImageData !== "null") { // null이 아니거나 "null" 문자열이 아닌 경우에만 처리
        const imageByteArray = new Uint8Array(atob(base64ImageData).split('').map(char => char.charCodeAt(0)));

        const blob = new Blob([imageByteArray], { type: 'image/jpeg' });

        const imageUrl = URL.createObjectURL(blob);

        const img = document.getElementById('viewImage');
        img.src = imageUrl;
        img.alt = '이미지를 불러올 수 없습니다';
        img.style.width = '100%';
    }

    const fileInput = document.getElementById('file');

    if (fileInput.files && fileInput.files.length > 0) {
        const file = fileInput.files[0];
        const reader = new FileReader();

        reader.readAsDataURL(file);

    }
};

document.getElementById('file').addEventListener('change', function(e) {
    const fileInput = e.target;
    const imagePreviewElement = document.getElementById('viewImage');
    const fileNameInput = document.getElementById('fileName');

    if (fileInput.files && fileInput.files.length > 0) {
        const file = fileInput.files[0];

        fileNameInput.style.display = 'block';
        fileNameInput.value = fileInput.files[0].name;

        const reader = new FileReader();

        reader.onload = function(event) {
            imagePreviewElement.src = event.target.result;
        }

        reader.readAsDataURL(file);

    } else {
        fileNameInput.style.display = 'none';
        fileNameInput.value = '';
        imagePreviewElement.src = '';
    }
});


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
        method: 'PATCH',
        body: formData
    }).then(response => {
        if (!response.ok) {
            return response.text().then(error => { throw new Error(error); });
        }
    }).then(data => {
    }).catch(error => {
        alert(error.message); // 서버에서 발생한 예외 메시지를 알림 창에 표시
    });
});