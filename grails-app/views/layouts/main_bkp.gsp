<!doctype html>
    <html lang="en" class="no-js">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Budget View"/></title>
        %{--<meta http-equiv="cache-control" content="max-age=0" />--}%
        %{--<meta http-equiv="cache-control" content="no-cache" />--}%
        %{--<meta http-equiv="expires" content="0" />--}%
        %{--<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />--}%
        %{--<meta http-equiv="pragma" content="no-cache" />--}%

		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'dcmegamenu.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main_line.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'tab_style.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery.jscrollpane.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'jquery-ui-1.9.2.custom.css')}" type="text/css">
        <link rel="stylesheet" href="${resource(dir: 'css', file: 'blue.css')}" type="text/css">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" href="//cdn.datatables.net/1.10.4/css/jquery.dataTables.min.css">

        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.3/themes/smoothness/jquery-ui.css">
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.3/jquery-ui.js"></script>

        <g:javascript library="application" />

        <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="//cdn.datatables.net/1.10.4/js/jquery.dataTables.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/moment.js/2.8.4/moment.min.js"></script>
        <script src="//cdn.datatables.net/plug-ins/1.10.10/sorting/datetime-moment.js"></script>
        <g:javascript src="jquery-ui.js" />
        <g:javascript src="jquery.corner.js" />
        <g:javascript src="jquery.PrintArea.js" />
        <g:javascript src="jquery.jscrollpane.min.js" />

        <g:javascript src="jquery.tinyscrollbar.min.js" />
        <g:javascript src="autoresize.js" />

        <link rel="stylesheet" href="../jqwidgets/styles/jqx.base.css" type="text/css"/>
        <g:javascript src="../scripts/demos.js"  />
        <g:javascript src="../jqwidgets/jqxcore.js"  />
        <g:javascript src="../jqwidgets/jqxdatetimeinput.js"  />
        <g:javascript src="../jqwidgets/jqxcalendar.js"  />
        <g:javascript src="../jqwidgets/jqxtooltip.js"  />
        <g:javascript src="../jqwidgets/globalization/globalize.js"  />

        %{--Auto-Complete comboBox--}%
        <g:javascript src="../jqwidgets/jqxbuttons.js"  />
        <g:javascript src="../jqwidgets/jqxscrollbar.js"  />
        <g:javascript src="../jqwidgets/jqxlistbox.js"  />
        <g:javascript src="../jqwidgets/jqxcombobox.js"  />

    	<g:layoutHead/>
		<r:layoutResources />
    <script>
        var appContext = '${request.contextPath}';
        var sessionExpMsg = '<g:message code="session.expiry.msg" default="Your session is about to finish, do you want to keep current session?"/>';
    </script>

</head>
	<body>

        <g:render template="/common/header" />
        <div id="content-whole">
            <div id="content-whole-inner">
                <div class="logoDiv">
                    <div class="logoDivLeft">
                        <a href="#"><img src="${resource(dir: 'images', file: 'logo.png')}" alt="Budget View"/></a>
                    </div>
                    <div class="logoDivRight">
                        <div id="spinner" class="spinner" style="display:none;">
                            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt.Loading',default:'Loading...')}" />
                        </div>
                    </div>
                </div>

                <g:layoutBody/>
                <div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt.Loading&hellip" default="Loading&hellip;"/></div>
                <g:javascript library="application"/>
                <r:layoutResources />
            </div>

            <g:render template="/common/footer" />
        </div>
	</body>
</html>

