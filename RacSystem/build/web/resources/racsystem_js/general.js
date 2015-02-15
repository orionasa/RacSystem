/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function goster() {
    $('#dialogGroup').slideDown("slow");
    $('html, body').animate({scrollBottom: 0}, 'fast');
    window.scrollTo(0, document.body.scrollHeight);
}
function kaybet() {

    $('#dialogGroup').hide();
}

function jumpToTop() {
    $('html, body').animate({scrollTop: 0}, 'fast');
}

function jumpToBot() {
    $('html, body').animate({scrollBottom: 0}, 'fast');
}
$(document).ready(function() {
    $('#dialogGroup').hide();
});
