try{
    var data = getTokenJSON();
    var user = JSON.parse(data.user);
    console.log(user);
    document.getElementById("user-name").innerHTML = user.userDTO.user || "Guest";
    if(user.userDTO.roleDTO.role==="administrator"){
        document.getElementsByClassName("sidebar")[0].innerHTML += `<button class="sidebar-btn" onclick="openDocumentation()">Docs</button>`
    }
}catch(e){
    console.error("Error parsing user data:", e);
    document.getElementById("user-name").innerHTML = "Guest";
}

function goToProfile(){
    goTo("/profile");
}