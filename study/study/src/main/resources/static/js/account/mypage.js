const profileImgUrl = document.querySelector(".profile-img-url");
const usernameText = document.querySelector(".username-text");
const textInput = document.querySelectorAll(".text-input");
const fileInput = document.querySelector(".file-input");



async function imgSubmit(){
	let formData = new FormData(document.querySelector("form"));
	
	const url = "/api/v1/user/account/profile/img";
	const option = {
		method : "PUT",
		headers : {},
		body : formData
	};
 	const response = await fetch(url, option);
 	if(response.ok){
		alert("프로필 이미지 변경이 되었습니다.");
		return response.json();
	}else{
		throw new Error("Faild to upload img.");
	}
}


fileInput.onchange = () => {
	let reader = new FileReader();
	
	reader.onload = (e) => {
		profileImgUrl.src = e.target.result;
		if(confirm("이미지를 변경하시겠습니까?")){
			const result = imgSubmit();
			console.log(JSON.stringify(result));
		}
	}
	reader.readAsDataURL(fileInput.files[0]);
}

getAuthenticationReq()
	.then(result => {
		let principal = result.data.user;
		usernameText.textContent = principal.username;
		textInput[0].value = principal.username;
		textInput[1].value = principal.email;
		textInput[2].value = principal.name;
		profileImgUrl.src = "/image/profile/" + principal.profile_img_url;
	})
	.catch(error => {
		console.log(error);
	});
	
	
	
	
	
	
	
	
	
	
	
	
