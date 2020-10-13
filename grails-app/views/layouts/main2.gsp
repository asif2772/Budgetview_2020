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
    <link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${assetPath(src: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'apple-touch-icon-retina.png')}">

    <asset:stylesheet href="budget_view_main_mnf.css"/>
    <asset:stylesheet href="budget_view_plugin_mnf.css"/>
    <asset:stylesheet href="jquery-ui-1.9.2.legacy/jquery-ui-1.css"/><!-- Arafat: Somehow without this script the page breaks ¯\_(ツ)_/¯ -->

    <asset:javascript src="jquery-et-al.js"/>
    <asset:javascript src="jquery-ui-1.9.2.legacy/jquery-ui.js" />  %{--It has accompanying css above--}%
    <asset:javascript src="jquery.multi-open-accordion-1.5.2.min.js" />
    %{--<g:javascript src="jquery.customSelect.js" />--}%
    <g:layoutHead/>
    <r:layoutResources />
</head>
<body>

<div id="content-whole">
    <div id="content-whole-inner" style="background:none;">
        <g:layoutBody/>
        <r:layoutResources />
    </div>
</div>
</body>
</html>
