const submitForm = () => {
    const btn = document.getElementById("btn-submit");
    btn.classList.add("loading");
    btn.disabled = true;
}

document.addEventListener("DOMContentLoaded", function () {
    const submit = document.getElementById("form");
    submit.addEventListener("submit", submitForm);
});