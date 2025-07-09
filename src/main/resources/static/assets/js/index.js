const goTo = (pageName) => {
    window.open(pageName, "_self");
}

const publicVapidKey = async () => {
  var key = await fetch('/public_key');
  return key.text();
}

function urlBase64ToUint8Array(base64String) {
    const padding = '='.repeat((4 - base64String.length % 4) % 4);
    const base64 = (base64String + padding).replace(/\-/g, '+').replace(/_/g, '/');
    const raw = atob(base64);
    const output = new Uint8Array(raw.length);
    for (let i = 0; i < raw.length; ++i) output[i] = raw.charCodeAt(i);
    return output;
}

function getTokenJSON() {
  try {
    const token = localStorage.getItem('session');
    const payload = JSON.parse(atob(token.split('.')[1]));
    return payload;
  } catch (e) {
    console.error('Token inválido:', e);
    return;
  }
}

function isTokenExpired(token) {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const exp = payload.exp;
    const now = Math.floor(Date.now() / 1000);
    var left = exp - Math.floor(Date.now() / 1000);
    if(left <= 300){
      Swal.fire({
          toast: true,
          position: "top-end",
          icon: "warning",
          title: "Your session will expire in less than 5 minutes!",
          showConfirmButton: true,
          confirmButtonText: "Refresh session",
          timer: 3000,
          theme: "dark"
      }).then((result) => {
        if(result.isConfirmed){
          refreshToken(token);
        }
      });
    }
    return exp < now;
  } catch (e) {
    console.error('Token inválido:', e);
    return true;
  }
}

function refreshToken(jwt){
  axios.post("/login/refresh", {
    token: jwt
  }).then(response => {
    if(response.status === 200){
      localStorage.setItem("session", response.data);
    }else{
      Swal.fire({
          toast: true,
          position: "top-end",
          icon: "error",
          title: "Error trying to refresh session!",
          showConfirmButton: false,
          timer: 1500,
          theme: "dark"
      });
      logout();
    }
  });
}

function logout(){
    localStorage.removeItem('session');
    goTo("/");
}

function checkToken() {
  const token = localStorage.getItem('session');
  if (!token) {
    return;
  }else{
    if (isTokenExpired(token)) {
    console.warn('Token expirado!');
    localStorage.removeItem('session');
    goTo("/");
    }
  }
}

function openDocumentation(){
  window.open("documentation/swagger-ui.html", "_blank");
}

function dateFormatted(date){
  var day = date.getDate();
  var month = date.getMonth() + 1;
  var year = date.getFullYear();
  var hours = date.getHours();
  var minutes = date.getMinutes();
  return `${day < 10 ? '0' + day : day}/${month < 10 ? '0' + month : month}/${year} ${hours < 10 ? '0' + hours : hours}:${minutes < 10 ? '0' + minutes : minutes}`;
}

setInterval(checkToken, 1000);

function revokeNotifications (){
  if (Notification.permission !== "denied") {
    Notification.requestPermission().then(async (permission) => {
        if (permission === "granted") {
          console.log("Permissão válida! 🎉");
          const reg = await navigator.serviceWorker.register('/assets/js/sw.js');
          const existingSubscription = await reg.pushManager.getSubscription();
          //if (existingSubscription) await existingSubscription.unsubscribe();
          if ('serviceWorker' in navigator && 'PushManager' in window && !existingSubscription)  {
            console.log('Service Worker registrado');
            var public_key = await publicVapidKey();
            const subscription = await reg.pushManager.subscribe({
                userVisibleOnly: true,
                applicationServerKey: urlBase64ToUint8Array(public_key)
            });
            await fetch('/subscribe', {
                method: 'POST',
                body: JSON.stringify(subscription),
                headers: {
                  'Content-Type': 'application/json'
                }
            }).then(response => {
                if (response.status !== 200) {
                  console.error('Falha ao inscrever: ', response.statusText);
                }
            }).catch(err => {
              console.error('Erro ao inscrever: ', err);
            });
          }

          if(existingSubscription){
            console.log('Inscrição existente encontrada!');
          }
        }
    });
  }
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
        user: loginElement.value,
        password: passwordMD5
    }).then(response => {
        if (response.status === 200) {
            localStorage.setItem("session", response.data);
            goTo("home");
        }else{
          Swal.fire({
            toast: true,
            position: "top-end",
            icon: "warning",
            title: "Error trying to log-in!",
            showConfirmButton: false,
            timer: 1500,
            theme: "dark"
          });
        }
    }).catch(error => {
      Swal.fire({
        toast: true,
        position: "top-end",
        icon: "warning",
        title: "Error trying to log-in! Verify your credentials.",
        showConfirmButton: false,
        timer: 2000,
        theme: "dark"
      });
    });
}