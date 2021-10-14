document.addEventListener("DOMContentLoaded", function () {
    const btnSubmit = document.getElementById("btn-submit");

    document.getElementById("form").addEventListener("submit",checkPassword);
    document.getElementById("btn-exit").addEventListener("click",function () {
        location.href = '/';
    })
    document.getElementById("file").addEventListener("change", function (event) {
        const avtError = document.getElementById("avtError");
        if (!isValidImage()) {
            avtError.classList.remove("d-none");
            avtError.textContent = "Vui lòng chọn file có định dạng ảnh!";
            btnSubmit.disabled = true;
        }else {
            avtError.style.display = "none";
            btnSubmit.disabled = false;
        }
    });
});
