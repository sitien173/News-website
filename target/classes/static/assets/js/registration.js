document.addEventListener("DOMContentLoaded", function () {
    const btnSubmit = document.getElementById("btn-submit");

    document.getElementById("form").addEventListener("submit",checkPassword);

    document.getElementById("btn-signin").addEventListener("click",deleteImage);

    document.getElementById("form").addEventListener("submit",checkPassword);
    document.getElementById("avatar").addEventListener("change", function (event) {
        const avtError = document.getElementById("avt-error");
        if (!isValidImage()) {
            avtError.style.display = "block";
            avtError.textContent = "Vui lòng chọn file có định dạng ảnh!";
            btnSubmit.disabled = true;
        } else {
            avtError.style.display = "none";
            btnSubmit.disabled = false;
            fakeProcessUpload(event);
        }
    });
});
