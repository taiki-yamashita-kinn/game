/**
 *ユーザー名、パスワードをローカルストレージにセット
 */
function setAutoPassword(){
	localStorage.setItem("userName", document.getElementById('userName').value);
	localStorage.setItem("password", document.getElementById('password').value);
}

function getAutoPassword(){
	var userName = localStorage.getItem("userName");
	var password = localStorage.getItem("password");

}
