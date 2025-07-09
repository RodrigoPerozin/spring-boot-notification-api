try{
    var data = getTokenJSON();
    var user = JSON.parse(data.user);
    if(user.userDTO.roleDTO.role==="administrator"){
        document.getElementsByClassName("sidebar")[0].innerHTML += `<button class="sidebar-btn" onclick="openDocumentation()">Docs</button>`
    }
    document.getElementById("user-name").innerHTML = user.userDTO.user || "Guest";
    document.getElementById("user-email").innerHTML = user.userDTO.email || "unknown@gmail.com";
    document.getElementById("user-role").innerHTML = user.userDTO.roleDTO.role.charAt(0).toUpperCase() + user.userDTO.roleDTO.role.slice(1) || "Guest";
    document.getElementById("session-started-at").innerHTML = dateFormatted(new Date(data.iat*1000)) || "N/A";
    document.getElementById("session-expires-at").innerHTML = dateFormatted(new Date(data.exp*1000)) || "N/A";
}catch(e){
    console.error("Error parsing user data:", e);
    document.getElementById("user-name").innerHTML = "Guest";
}

function goToHome(){
    goTo("/home");
}