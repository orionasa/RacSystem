
function exitButton() {
    $(PrimeFaces.escapeClientId('loginForm')).fadeOut(1000);
    $(PrimeFaces.escapeClientId('raclogo')).fadeIn(1000);
}

$(document).ready(function() {
    $(PrimeFaces.escapeClientId('loginForm')).hide();
    $(PrimeFaces.escapeClientId('raclogo')).click(function() {
        $(PrimeFaces.escapeClientId('loginForm')).fadeIn(1000);
        $(PrimeFaces.escapeClientId('raclogo')).fadeOut(1000);
    });
});