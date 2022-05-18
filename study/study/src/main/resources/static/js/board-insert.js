const inputItems = document.querySelectorAll(".input-items");
const inputItems2 = document.querySelector(".textarea-item");

const submitBtn = document.querySelector(".submit-btn");

/*
	Promise = 비동기 통신 기능을 가진 객체
*/

function test(data){//new Promise()를 호출하는데 매개변수에 
	return new Promise((resolve, reject) => {  
		if(data > 100){
			resolve(data);
		}else{
			throw reject(new Error("data가 100보다 작거나 같습니다."));
		}
		
	});
}
/*(resolve, reject) => {  
	let data = 100;
	resolve(data);
} 함수가 실행되고 나서 매개변수에 담긴다
*/

test(500) // 호출
.then(testData => testData + 100)
.then(testData2 => alert(testData2))
.catch(error => console.log(error));

/*==============================================================================*/

/*submitBtn.onclick = () => {
	
	$.ajax({
		type : "post",
		url : "/board",
		data : JSON.stringify({
			title : inputItems[0].value,
			usercode : inputItems[1].value,
			content : inputItems2.value
		}),
		contentType : "application/json",
		dataType : "text",
		success : function(data){
			let aaa = JSON.parse(data);
			console.log(aaa);
			location.href="/board/dtl/"+aaa.data;

		},
		error : function(data){
			alert("비동기 처리 오류");
		}
	});
	
}*/
submitBtn.onclick = () => {
	submit();
}


function submit(){
	let url = "/board";


	let option = {
			method : "POST",
			headers : {
				"Content-Type" : "application/json",
			},
			body : JSON.stringify({
				title : inputItems[0].value,
				usercode : inputItems[1].value,
				content : inputItems2.value
			})
	};



	
	fetch(url, option)
	.then(response => {
		console.log(response);
		if(response.ok){
			return response.json();
		}else {
			throw new Error(response.json());
		}
		
	})
	.then(data => {location.href = "/board/dtl/" + data.data;})
	.catch(error => console.log(error));
}













