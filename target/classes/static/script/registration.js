const URL = location.origin;
let imgUploaded;
const isValidImage = () => {
    return document.getElementById("avatar")
        .files[0]
        .name
        .match(/.(jpg|jpeg|png|gif)$/i);
}

const upLoadImage = () => {
    const xhr = new XMLHttpRequest();
    xhr.open("POST",URL + "/file/upload",true);
    const processBar = document.getElementById("process-bar");
    xhr.upload.addEventListener("progress", e => {
        const percent = e.lengthComputable ? (e.loaded / e.total) * 100 : 0;
        processBar.style.width = percent.toFixed(2) + "%";
        processBar.setAttribute("aria-valuenow", ""+percent.toFixed(2)+"");
    })
    xhr.onreadystatechange = function() { // Call a function when the state changes.
        if (this.readyState === XMLHttpRequest.DONE && this.status === 200) {
          // TODO: delete image if exist
            deleteImage();
           const avtUploaded = document.getElementById("avt-uploaded");
           let eImg;
           if(avtUploaded.childElementCount === 0){
              eImg = document.createElement("img");
           }
           imgUploaded = this.responseText.replace(',', '').trim();
           eImg.setAttribute("src", URL + "/file?path=" + imgUploaded);
           avtUploaded.appendChild(eImg);
           avtUploaded.style.display = "block";
           // save data to input id= avt
           document.getElementById("avt").setAttribute("value", ""+imgUploaded+"");
        }
    }
    const formUpload = document.getElementById("form");
    xhr.send(new FormData(formUpload));
}

const deleteImage = () => {
    const image = document.getElementById("avt");
    if(!image.value) return;
    const xhr = new XMLHttpRequest();
    xhr.open("DELETE",URL + "/file/delete");
    const formData = new FormData();
    formData.set("path",image.value);
    xhr.send(formData);
}

const checkPassword = (event) => {
    const password = document.getElementById("password");
    const rePassword = document.getElementById("repassword");
    const error = document.getElementById("repass-error");
    const btn = document.getElementById("btn-submit");
    if(password.value !== rePassword.value){
        error.style.display="block";
        error.textContent = "Mật khẩu không khớp";
        event.preventDefault();
    }else {
        error.style.display="none";
        btn.classList.add("loading");
        btn.disabled = true;
    }
}

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
            upLoadImage(event);
        }
    });
});
