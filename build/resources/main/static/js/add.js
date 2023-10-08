var count = 1;
var sCount = 0; // 학교 검색 했는지 여부

const loginId = document.getElementById('loginId');

loginId.addEventListener('change', function() {
    count = 1;
});

// 학교 검색 팝업
function schoolSearch() {
    const w = 500;
    const h = 500;
    const left = screen.width / 2 - w / 2;
    const top = screen.height / 2 - h / 2;

    window.open("./school-search?schoolName=" + document.getElementById('schoolName').value, "_blank", "width=${w}, height=${h}, top=${top}, left=${left}");
}

function loginWithGoogle() {
    window.location.href = "/oauth2/authorization/google";
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

// 로그인 아이디 중복 체크
function checkDuplicate() {
    if (document.getElementById('loginId').value.length < 5) {
        document.getElementById('loginId').value = '';
        alert('ID는 5글자 이상이어야 합니다.');
        return;
    }

    fetch("/member/id-check?loginId=" + document.getElementById('loginId').value, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data > 0) {
                document.getElementById('loginId').value = '';
                alert('중복된 ID 입니다. 다른 ID를 입력하세요.');
                return;
            } else {
                count = 0;
                alert('사용할 수 있는 ID 입니다.');
            }
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

const myForm = document.getElementById("myForm");
const submitBtn = document.getElementById("signup");

submitBtn.addEventListener("click", (event) => {
    if (count != 0) {
        event.preventDefault(); // 폼 제출 취소
        alert('중복 체크 후 회원가입이 가능합니다.');
        return;
    } else if (sCount == 0) {
        event.preventDefault(); // 폼 제출 취소
        alert('학교를 검색해주세요.');
        return;
    }
});

myForm.addEventListener('submit', event => {
    event.preventDefault();

    const formData = new FormData(myForm);

    fetch('/member/add', {
        method: 'POST',
        body: formData
    }).then(response => {
        if (!response.ok) {
            return response.text().then(error => { throw new Error(error); });
        }
        alert("회원가입이 완료되었습니다.");
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

