const searchButton = document.querySelector("#searchButton");
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
document.getElementById('schoolName').value = urlParams.get('schoolName');

searchButton.addEventListener("click", () => {
    if (document.getElementById('schoolName').value.length < 2) {
        alert('학교명은 두글자 이상 입력해주세요.');
        return;
    }

    searchSchool(document.getElementById('schoolName').value);
});


function confirm() {
    var table = document.getElementById("schoolList");

    if (table.getElementsByClassName('selected')[0] == undefined) {
        alert("선택된 행이 없습니다.");
        return;
    }

    window.opener.getSchoolName(table.getElementsByClassName('selected')[0].getElementsByTagName('td')[0].innerHTML);
    window.close();
}

function searchSchool(schoolName) {
    fetch("/api/school-search?schoolName=" + schoolName + "&schoolType=" + document.getElementById('schoolType').value, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            let tableHTML = '';

            data.forEach(school => {
                tableHTML += `<tr><td>${school.name}</td><td>${school.gubun}</td><td>${school.campusGubun}</td><td>${school.address}</td></tr>`;
            });

            if (data.length < 1) {
                alert('조회된 데이터가 없습니다.');
                return;
            }

            // HTML 테이블에 결과 추가
            schoolList.innerHTML = tableHTML;

            const rows = document.querySelectorAll("tbody tr");
            rows.forEach(row => {
                row.addEventListener("click", () => {
                    // 이전 선택된 행을 찾아서 색상을 되돌립니다.
                    const selectedRow = document.querySelector(".selected");
                    if (selectedRow) {
                        selectedRow.classList.remove("selected");
                    }
                    // 선택한 행에 색상을 지정합니다.
                    row.classList.add("selected");
                });
            });
        })
        .catch(error => {
            console.error('Error:', error);
        });
}