<!doctype html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Budget View"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main_line.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'tab_style.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jscrollpane.css')}" type="text/css">
    %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui-1.9.2.custom.css')}" type="text/css">--}%
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'dcmegamenu.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'blue.css')}" type="text/css">
    %{--<link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui-1.css')}" type="text/css">--}%

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    %{--<g:javascript src="jquery-ui.js" />--}%
    <g:javascript src="jquery.corner.js"/>
    %{--<g:javascript src="jquery.PrintArea.js" />--}%

    %{--<g:javascript src="jquery-ui-1.js" />--}%
    %{--actually imports '/app/js/myscript.js'   --}%
    %{--<g:javascript src="jquery.jscrollpane.min.js" />--}%
    %{--<g:javascript src="jquery.mousewheel.js" />--}%
    <g:javascript src="jquery.tinyscrollbar.min.js"/>
    <g:javascript src="autoresize.js"/>

    %{--<g:javascript src="hoverIntent.js" />--}%
    %{--<g:javascript src="superfish.js" />--}%

    <g:javascript src="jquery.dcmegamenu.1.3.3.js"/>
    <g:javascript src="jquery.hoverIntent.minified.js"/>

    <g:layoutHead/>
    %{--<g:javascript library="prototype"></g:javascript>--}%
    %{--<g:javascript>
        function showSpinner(visible) {
            $('spinner').style.display = visible ? "inline" : "none";
        }
        Ajax.Responders.register({
            onLoading: function() {
                showSpinner(true);
            },
            onComplete: function() {
                if(!Ajax.activeRequestCount) showSpinner(false);
            }
        });
    </g:javascript>--}%

    <r:layoutResources/>

    <script type="text/javascript">
        $(document).ready(function () {

            $('#mega-menu-1').dcMegaMenu({
                rowItems:'3',
                speed:'fast',
                effect:'fade'

            });
            //$('ul.sf-menu').superfish();
            $('.inner').corner('6px');
            //$('.pagination').corner('bottom');
            $('.content').corner('6px');
            $('.buttons').corner('6px');
            $('.save').corner('6px');
            $('#income').corner('6px');
            $('#footer').corner('bottom');
            $('.allIncomeHead').corner('top');
            $('input').corner('6px');
            /*$('#submit').corner('6px');
             $('#create').corner('6px');

             $('.save').corner('6px');*/
            //#login
        });
    </script>

</head>

<body>
<g:render template="/common/header"/>


<div id="content-whole">
    <div id="content-whole-inner">
        <div class="logoDiv">
            <div class="logoDivLeft">
                <a href="#"><img src="${assetPath(src:'spinner.gif')}" alt="Budget View"/></a>
            </div>
            %{--<div class="logoDivRight">
                <div id="spinner" class="spinner" style="display:none;">
                    <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
                </div>
            </div>--}%
        </div>

        <g:layoutBody/>
        <g:javascript library="application"/>
        %{--<r:layoutResources />--}%
        <r:layoutResources disposition="defer"/>
    </div>
    <g:render template="/common/footer"/>
</div>
</body>
</html>

