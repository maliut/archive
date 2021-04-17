/**
 * 用于对百度地图的展示
 * 2015/07/23 first version
 * 2015/07/24 增加标注弹出窗口功能
 */
var map = new BMap.Map("container");          // 创建地图实例  
var point = new BMap.Point(116.404, 39.915);  // 创建点坐标  
map.centerAndZoom(point, 15);                 // 初始化地图，设置中心点坐标和地图级别  
map.addControl(new BMap.NavigationControl());    // 增加控件
map.addControl(new BMap.ScaleControl());    
map.addControl(new BMap.OverviewMapControl());  

//创建地址解析器实例     
var myGeo = new BMap.Geocoder();     

// 根据合法的地址字符串展示标注
function showSpot(spot) {
	myGeo.getPoint(spot.address, function(point){      
          if (point) {      
              map.centerAndZoom(point, 16);      
              var marker = new BMap.Marker(point);
              map.addOverlay(marker);
              marker.addEventListener("click", function(){    
            	  var opts = {    
            			  width : 250,     // 信息窗口宽度    
            			  height: 100,     // 信息窗口高度    
            			  title : spot.name,  // 信息窗口标题   
            			  offset : new BMap.Size(-3,-18)	
            			 }    
            	 var infoWindow = new BMap.InfoWindow("价格：" + spot.dayPrice + "<br>地址：" + spot.address, opts);  // 创建信息窗口对象    
            	 map.openInfoWindow(infoWindow, point);      // 打开信息窗口
             });
          }      
      }, "");
}