package com.zxgs.test;


public class TestSolr {

	public static void main(String[] args) {
		System.out.println("ss");
		
//		XZQHInfoSolr solr=XZQHInfoSolr.getInstance();
//		solr.updateFileds();
//		ZhsqCoreQuery solr=ZhsqCoreQuery.getInstance();
		
		//城口
//		solr.updateFiledById("1498413", "31.94989466,108.66003750");
//		//巫溪
//		solr.updateFiledById("1498414", "31.40091343,109.56532808");
//		//开县
//		solr.updateFiledById("1498415", "108.38835560,31.16304715");
//		//巫山
//		solr.updateFiledById("1498416", "109.87444923,31.07710355");
//		//云阳
//		solr.updateFiledById("1498417", "108.69276195,30.93293434");
//		//万州
//		solr.updateFiledById("1498418", "108.40399774,30.80998134");
//		//梁平
//		solr.updateFiledById("1498419", "107.76527039,30.65671294");
//		//忠县
//		solr.updateFiledById("1498420", "108.03350511,30.30218347");
//		//石柱
//		solr.updateFiledById("1498421", "108.10948792,30.00210467");
//		//垫江
//		solr.updateFiledById("1498422", "107.32847817,30.32999141");
//		System.out.println("。。。。。");
		
		//生成了随机秒数60-150之间 	var num = Math.round(Math.random()*90+60); 	alert(num); 	//循环执行，每隔60-150秒钟执行一次showMsgIcon() 	window.setInterval(showMsgIcon, 1000*num); 	function showMsgIcon(){ 		$.getJSON("${ctx}/todoTask/getTodoTaskList.do?processed=0", function(result){ 			if(result.rows.length > 0){ 				var msgPicFadeOutIn = setInterval(function(){$("#msgPic").fadeOut(500).fadeIn(500);}, 1000); 				$("#msgPic").attr("title", "您有待办任务、未读消息，点击查看"); 				$("#msgPic").click(function(){ 					if(msgPicFadeOutIn != ""){ 						clearInterval(msgPicFadeOutIn); 						msgPicFadeOutIn = ""; 					} 					$("#msgPic").fadeOut(); 					toAction("/todoTask/showMyUnProcessedTask.do", "我的待办任务", this); 				}); 			}else{ 				$("#msgPic").fadeOut(); 			} 		}); 	}

	}

}
