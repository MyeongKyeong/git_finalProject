//담당: 김명경
$(document).ready(function(){
	withdrawList();
});
//전체(혹은 검색)리스트 출력 기능
function printList(data){
	$.each(data.list, function(index, items){
		withdrawReason='';
		if(items.withdraw_lowFrequency ==1){
			withdrawReason = '이용빈도 낮음'
		}if(items.withdraw_rejoin ==1){
			if (withdrawReason==''){
				withdrawReason += "재가입"//앞에 작성된 것이 없다면 쉼표 없이
			}else withdrawReason += ", 재가입"//앞에 작성된 항목이 있으면 쉼표와 함꼐
		}if(items.withdraw_lowContents ==1){
			if (withdrawReason==''){
				withdrawReason += "콘텐츠/상품 부족"
			}else withdrawReason += ", 콘텐츠/상품 부족"
		}if(items.withdraw_protectInfo ==1){
			if (withdrawReason==''){
				withdrawReason += "개인정보보호"
			}else withdrawReason += ", 개인정보보호"
		}if(items.withdraw_lowBenefit ==1){
			if (withdrawReason==''){
				withdrawReason += "혜택 부족"
			}else withdrawReason += ", 혜택 부족"
		}if(items.withdraw_others ==1){
			if (withdrawReason==''){
				withdrawReason += "기타"
			}else withdrawReason += ", 기타"
		}
		console.log("탈퇴번호:"+items.withdraw_seq);
		
		$('<tr/>').append($('<td/>',{
			text: items.withdraw_seq //탈퇴번호
		})).append($('<td/>',{
			text: items.mem_id //탈퇴회원 아이디
		})).append($('<td/>',{
			text: withdrawReason,//탈퇴사유(중복가능)
		})).append($('<td/>',{
			text: items.withdraw_detailReason,//탈퇴사유(기타-서술)
			width : '300px'
		})).append($('<td/>',{
			text: items.withdraw_logtime //탈퇴시점
		})).appendTo($('#tbody'));
	});//each
}
//탈퇴사유 최신순 전체 출력
function withdrawList(data){
	$.ajax({
		type: 'post',
		url: '/market/admin/getWithdrawList',
		data: {'pg': $('#pg').val(),
			   'viewNum': $('#viewNum').val()},
		dataType: 'json',
		success: function(data){
			//리스트 출력 초기화
			$("#tbody tr:gt(0)").remove();
			
			let withdrawReason;
			printList(data);//리스트 출력
			
			//페이징처리
			$('#boardPagingDiv').html(data.withdrawBP.pagingHTML);
			drawPie(); //원 그래프 그리기
		},error: function(err){
			console.log(err);
		}
	});//ajax
}

//selectPrint눌렀을때 (출력 개수 정하기(20,50,100개)
$('#selectPrint').change(function(){
	var viewNum = $(this).val();
	$('#viewNum').val(viewNum);
	
	withdrawList(); //리스트 출력
});

//페이징 처리
function boardPaging(pg){
	var keyword = document.getElementById("keyword").value;
	$('#pg').val(pg);

	 if($('#searchType').val() == '선택'||keyword ==''){//검색값 없이 페이징 처리
		location.href='/market/admin/withdrawList?pg='+pg
						+'&viewNum='+$('#viewNum').val();
	 }else{//검색값과 함께 페이징 처리
		$('#withdrawSearchBtn').trigger('click','research');
	 }
}

//탈퇴사유 조건 검색======================================================
//검색
$('#withdrawSearchBtn').click(function(event, str){
	if(str != 'research'){
		$('input[name=searchPg]').val(1);
	}if($('#searchType').val() == '선택')
		alert('검색 조건을 선택하세요');
	else if($('#keyword').val() == ''){
		alert('검색어를 입력하세요');
	}else{
		search_viewNum_change();
		//조건 검색후 selectPrint눌렀을때 (20개보기 50개보기..)
		$('#selectPrint').change(function(){
			var viewNum = $(this).val();
			$('#viewNum').val(viewNum);
			search_viewNum_change();
		});
	}
});

//탈퇴 사유 [검색 + 출력 개수 ] 출력
function search_viewNum_change(){
	$.ajax({
		type: 'post',
		url: '/market/admin/getSearchWithdrawList',
		data: {'pg': $('#pg').val(), 
			   'viewNum': $('#viewNum').val(),
			   'keyword':$('#keyword').val()},
		dataType: 'json',
		success: function(data){
			$("#tbody tr:gt(0)").remove();//이전 출력 리스트 초기화
			let withdrawReason;
			printList(data); //리스트 출력
			//페이징처리
			$('#boardPagingDiv').html(data.getSearchWithdrawBP.pagingHTML);
			drawPie();
		},error: function(err){
			console.log(err);
		}
	});//ajax
}
//우측========================================================================
//탈퇴 회원 분석 그래프(파이 원 그래프)
function drawPie(){
	$.ajax({
		type: 'post',
		url: '/market/admin/getWithdrawTotal',
		dataType: 'json',
		success: function(data){//탈퇴회원 전체 수
			//탈퇴사유 총 수(복수 체크 개수 모두 포함)
			let all = data.map.withdrawTotal 
					+ data.map.lowFrequencyTotal
					+ data.map.rejoinTotal
					+ data.map.lowContentsTotal
					+ data.map.lowBenefitTotal
					+ data.map.othersTotal;
			
		//각 항목별 퍼센테이지 계산(정수로 전환. 해당탈퇴사유 개수/전체탈퇴사유 개수*100)
		let lowFrequency = parseInt(data.map.lowFrequencyTotal/all*100);
		let rejoin = parseInt(data.map.rejoinTotal/all*100);
		let lowContent = parseInt(data.map.lowContentsTotal/all*100);
		let protectInfo = parseInt(data.map.protectInfoTotal/all*100);
		let lowBenefit = parseInt(data.map.lowBenefitTotal/all*100);
		let others = parseInt(data.map.othersTotal/all*100);
		//파이 차트 그리기
	data = { 
			datasets: [{ //datasets: 한 항목에 대한 정보 집합(배열 형태. [{}]가 1개의 집합)
				//항목별 색깔. (순서대로)
				backgroundColor: ['red','yellow','blue','green','orange','purple'], 
				//퍼센테이지. 마우스를 갖다대면 라벨 이름과 퍼센테이지가 함께 뜬다
				data: [lowFrequency, lowContent, rejoin,protectInfo,lowBenefit,others] }], 
				//항목에 해당하는 한글 라벨
				labels: ['이용빈도 낮음','재가입','혜택 부족','콘텐츠 부족','개인정보보호','기타'] };
			var ctx1 = document.getElementById("withdrawReason"); 
			var myPieChart = new Chart(ctx1, { 
								type: 'pie', 
								data: data, 
								options: {} 
							});
		}
	});//ajax
}
//회원 증감 추이 그래프(선 그래프)
function drawGraph(){
	var ctx = document.getElementById('currentMember').getContext('2d'); var chart = new Chart(ctx, { 
		// 챠트 종류를 선택 
		type: 'line', // 챠트를 그릴 데이타 
		data: { labels: ['1월', '2월', '3월', '4월', '5월', '6월', '7월'], 
			datasets: [{ label: '회원 증감 추이', 
						backgroundColor: 'transparent', 
						borderColor: 'red', 
						data: [0, 10, 5, 2, 20, 30, 45] }] }, 
			options: {} }); 
}		


















