const goTo = (pageName) => {
    window.open(pageName, "_self");
}

function urlBase64ToUint8Array(base64String) {
    const padding = '='.repeat((4 - base64String.length % 4) % 4);
    const base64 = (base64String + padding).replace(/\-/g, '+').replace(/_/g, '/');
    const raw = atob(base64);
    const output = new Uint8Array(raw.length);
    for (let i = 0; i < raw.length; ++i) output[i] = raw.charCodeAt(i);
    return output;
}

function login () {
    var loginElement = document.getElementById("login");
    var passwordElement = document.getElementById("password");
    var passwordMD5 = md5(passwordElement.value);

    if (loginElement.value === "" && loginElement.value.length <= 3) {
        Swal.fire({
            toast: true,
            position: "top-end",
            icon: "warning",
            title: "Please fill the user field correctly!",
            showConfirmButton: false,
            timer: 1500,
            theme: "dark"
        });
        loginElement.focus();
        return;
    }else if(passwordElement.value === "" && passwordElement.value.length < 8) {
        Swal.fire({
            toast: true,
            position: "top-end",
            icon: "warning",
            title: "Please fill the password field correctly!",
            showConfirmButton: false,
            timer: 1500,
            theme: "dark"
        });
        passwordElement.focus();
        return;
    }

    axios.post("/login", {
        login: loginElement.value,
        password: passwordMD5
    }).then(response => {
        if (response.data.status === "ok") {
            goTo("home");
        }
    });
}