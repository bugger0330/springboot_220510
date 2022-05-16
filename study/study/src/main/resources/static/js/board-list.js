const boardListTable = document.querySelector(".board-list-table");
const boardItems = document.querySelectorAll(".board-items");



let page = 1;

load();

function load(){
	$.ajax({
		type : "get",
		url : "/board/list",
		data : {
			"page" : page
		},
		dataType : "text",
		success : function(data){
			console.log(data);
			let boardList = JSON.parse(data);
		},
		error : function(data){
			alert("비동기 처리 오류");
		}
	});
}