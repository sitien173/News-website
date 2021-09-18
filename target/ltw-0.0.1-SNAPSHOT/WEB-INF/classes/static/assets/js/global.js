const URL = location.origin;
const isValidImage = () => {
    return document.getElementById("file")
        .files[0]
        .name
        .match(/.(jpg|jpeg|png|gif)$/i);
}

const fakeProcessUpload = async  () => {
    // fake process (real use ajax)
    const maxPercent = 100;
    let currentPercent = 1;
    const btnSubmit  = document.getElementById("btn-submit");

    btnSubmit.disabled = true;
    const processBar = document.getElementById("process-bar");
    processBar.classList.remove("d-none");
    for(currentPercent; currentPercent <= maxPercent; currentPercent ++){
        processBar.style.width = currentPercent + "%";
        processBar.setAttribute("aria-valuenow", ""+currentPercent+"");
        processBar.textContent = currentPercent + "%";
        await new Promise(r => setTimeout(r, 10));
    }
    btnSubmit.disabled = false;
    setTimeout(() => {
        processBar.classList.add("d-none");
    },2000);
}
const checkPassword = (event) => {
    const password = document.getElementById("password");
    const rePassword = document.getElementById("re-password");
    const error = document.getElementById("repass-error");
    const btnSubmit  = document.getElementById("btn-submit");
    if(password.value !== rePassword.value){
        error.style.display="block";
        error.textContent = "Mật khẩu không khớp";
        event.preventDefault();
    }else {
        btnSubmit.disabled = true;
    }
}