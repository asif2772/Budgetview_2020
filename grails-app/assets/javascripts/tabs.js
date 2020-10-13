window.onload=function() {
//    alert("okk");
    // get tab container
  	var container = document.getElementById("tabContainer");
	var tabcon = document.getElementById("tabscontent");
	//alert(tabcon.childNodes.item(1));
    // set current tab
    var navitem = document.getElementById("tabHeader_1");

    //store which tab we are on
    var ident = navitem.id.split("_")[1];
    //alert(ident)
    navitem.parentNode.setAttribute("data-current",ident);
    //set current tab with class of activetabheader
    navitem.setAttribute("class","tabActiveHeader");

    //hide two tab contents we don't need
    var current = navitem.parentNode.getAttribute("data-current");
   	var pages = tabcon.getElementsByTagName("div");

    for (var i = 1; i < pages.length; i++) {
     var trimmed = pages.item(i).className.replace(/^\s+|\s+$/g, '') ;
         if( trimmed == "fieldContainer" || trimmed == "fieldContainer  required"){
             pages.item(i).style.display = "block";
         }else if (trimmed == "slideOptionDiv open") {
             pages.item(i).style.display = "block";
         } else {
             pages.item(i).style.display = "none";
         }
    }

    //this adds click event to tabs
    var tabs = container.getElementsByTagName("li");

    for (var i = 0; i < tabs.length; i++) {
      tabs[i].onclick=displayPage;
    }
}

// on click of one of tabs
function displayPage() {

  var current = this.parentNode.getAttribute("data-current");
    //alert(current)
  //remove class of activetabheader and hide old contents
  document.getElementById("tabHeader_" + current).removeAttribute("class");
  document.getElementById("tabpage_" + current).style.display="none";

  var ident = this.id.split("_")[1];
  //add class of activetabheader to new active tab and show contents
  this.setAttribute("class","tabActiveHeader");
  document.getElementById("tabpage_" + ident).style.display="block";
  this.parentNode.setAttribute("data-current",ident);
}

function showTabPage(tabIndex) {
//    alert(current)
    //remove class of activetabheader and hide old contents
    document.getElementById("tabHeader_" + tabIndex).removeAttribute("class");
    document.getElementById("tabpage_" + tabIndex).style.display="none";

    var ident = this.id.split("_")[1];
    //add class of activetabheader to new active tab and show contents
    this.setAttribute("class","tabActiveHeader");
    document.getElementById("tabpage_" + ident).style.display="block";
    this.parentNode.setAttribute("data-current",ident);
}

