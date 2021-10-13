const submitForm = () => {
    const btn = document.getElementById("btn-submit");
    btn.classList.add("loading");
    btn.disabled = true;
}
document.addEventListener("DOMContentLoaded", function () {
    const submit = document.getElementById("form");
    submit.addEventListener("submit", submitForm);

    document.getElementById("reload-captcha").addEventListener("click",function () {
        const captcha = document.getElementById('captcha-img');
        captcha.setAttribute("src","/captcha/generator?date=" + new Date().getTime());
    })
});