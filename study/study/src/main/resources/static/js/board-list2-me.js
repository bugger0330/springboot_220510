const boardListTable = document.querySelector(".board-list-table");
const boardListPage = document.querySelector(".board-list-page");
const pageButton = boardListPage.querySelectorAll("div");




for(let i = 0; i < pageButton.length; i++){
	pageButton[i].onclick = () => {
		let nowpage = pageButton[i].textContent;
		load(nowpage);
	}
}

let page = 1;
load(page);


function load(page){
	$.ajax({
		type : "get",
		url : "/board/list",
		data : {
			"page" : page
		},
		dataType : "text",
		success : function(data){
			let getList = JSON.parse(data);
			getLoad(getList.data);
			boardItemsReload();
		},
		error : function(data){
			alert("비동기 처리 오류");
		}
	});
}

function getLoad(data){
	while(boardListTable.hasChildNodes()){
		boardListTable.removeChild(boardListTable.firstChild);
	}
	let str = `<tr>
                    <th>번호</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>조회수</th>
                </tr>`;
                
    for(let i = 0; i < data.length; i++){
		str += `<tr class="board-items">
                    <td>${data[i].boardCode}</td>
                    <td>${data[i].title}</td>
                    <td>${data[i].username}</td>
                    <td>${data[i].boardCount}</td>
                </tr>`;
	}
	boardListTable.innerHTML = str;       
}

//페이지가 로드 될때 새롭게 tr을 불러와야 해서 함수 안에서 변수 선언을 새로 한다!
function boardItemsReload(){
	const boardItems = document.querySelectorAll(".board-items");
	for(let i = 0; i < boardItems.length; i++){
		boardItems[i].onclick = () => {
			let select = boardItems[i].querySelectorAll("td");
			
			$.ajax({
				type : "get",
				url : `/board/${select[0].textContent}`,
				data : {
					"boardCode" : select[0].value
				},
				dataType : "text",
				success : function(data){
					alert(data);
				},
				error : function(data){
					alert("비동기 처리 오류");
				}
			});
		}
	}
}
	



