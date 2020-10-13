<%@ page import="javax.sql.DataSource" %>
<html xmlns="http://www.w3.org/1999/html">
<head>
    <meta name='layout' content='main'/>
    <title><g:message code="springSecurity.login.title"/></title>

<style type='text/css' media='screen'>
    #login {
        margin: 15px 0px;
        padding: 0px;
        text-align: center;
    }

    #login .inner {
        width: 340px;
        padding-bottom: 12px;
        margin: 60px auto;
        text-align: left;
        border: 0px solid #aab;
        background-color: #EAEAEA;
        /*-moz-box-shadow: 2px 2px 2px #eee;
        -webkit-box-shadow: 2px 2px 2px #eee;
        -khtml-box-shadow: 2px 2px 2px #eee;
        box-shadow: 2px 2px 2px #eee;*/
    }

    #login .inner .fheader {
        padding: 18px 26px 14px 26px;
        /*background-color: #f7f7ff;*/
        /*margin: 0px 0 14px 0;*/
        color: #2e3741;
        font-size: 18px;
        font-weight: bold;
        text-align: center;
        font-size: 20px;
        border-bottom: 1px solid #c8c8c8;
    }

    #login .inner .cssform p {
        clear: left;
        margin: 0 0 9px;
        padding: 3px 0 5px 105px;
        /*padding-left: 105px;*/
        /*margin-bottom: 9px;*/
        height: 1%;
    }

    #login .inner .cssform input[type='text'] {
        width: 145px;
    }

    #login .inner .cssform input[type='password'] {
        width: 145px;
    }

    #login .inner .cssform label {
        font-weight: bold;
        float: left;
        text-align: right;
        margin-left: -105px;
        width: 110px;
        padding-top: 3px;
        padding-right: 10px;
    }

    #login #remember_me_holder {
        padding-left: 121px;

    }

    #login #submit {
        margin-left: 18px;
    }

    #login #remember_me_holder label {
        float: none;
        margin-left: 0;
        text-align: left;
        width: 200px
    }

    #login .inner .login_message {
        padding: 6px 25px 20px 25px;
        color: #c33;
    }

    #login .inner .text_ {
        width: 120px;
    }

    #login .inner .chk {
        height: 12px;
    }

    .forgotPass {
        padding-left: 19px;
    }

    div.login_message p {
        content: #000000;
        border: 1px solid #e2a0a0;
        background: #fef0f1;
        margin: 0;
        padding: 4px 0 3px 10px;
        border-radius: 6px;
    }

    #message {
        font-family: Arial, Helvetica, sans-serif;
        position: fixed;
        top: 2px;
        left: 0px;
        width: 100%;
        z-index: 105;
        text-align: center;
        font-weight: bold;
        font-size: 100%;
        color: #00008B;
        padding: 10px 0px 10px 0px;
        background-color: #F5F5DC;
    }

    #message span {
        text-align: center;
        width: 95%;
        float: left;
    }

    .close-notify {
        white-space: nowrap;
        float: right;
        margin-right: 10px;
        color: #fff;
        text-decoration: none;
        border: 2px #A52A2A solid;
        padding-left: 3px;
        padding-right: 3px;
    }

.close-notify a {
    color: #00008B;
}
/*//////////NEW STYLE///////////////*/

#login #remember_me_holder.rememberMeHolderStyle {
    float: left;
    margin-top: -4px;
    margin-bottom: -3px;
}
#login .inner .cssform .rememberMeText {
   /*border: 1px solid;*/
   padding: 0;
   margin-left: 2px;
}
.rememberMeInput {
    float: left;
    margin-top: -3px;
}
    #login .inner .cssform .forgotPassword {
        margin-bottom: 7px;
    }

</style>

    <g:javascript>
        var sesStorage = window.sessionStorage;
        sesStorage.setItem("budgetType","incNexp");
        sesStorage.setItem("budgetSortType","name_wise");

        sesStorage.setItem("dashboardURL","");
        sesStorage.setItem('selectedValue','-1')

        var browserName = getBrowserName();
//        alert("Browser Name: " + browserName);
//        if (browserName == 'Chrome' || browserName == 'Safari') {
//            $(function () {
//                $('#message').fadeIn('slow');
//                $('#message a.close-notify').click(function () {
//                    $('#message').fadeOut('slow');
//                    return false;
//                });
//            });
//        }

        function getContextPath() {
            return "<%=request.getContextPath()%>";
        }

        if (browserName == 'IE11' || browserName == 'MSIE') {
            var conPath = getContextPath();
            //alert("Con Path: " + conPath);
            window.location = conPath + "/login/showWarning";
        }
     </g:javascript>
</head>

<body>

<g:if test='${params.session_expired_ajax}'>
    <g:javascript>
        window.location.href = '/login/auth?session_expired=true';
    </g:javascript>
</g:if>

<div id="message" style="display: none;">
    <span><g:message code="browser.warning.message.label"/> <g:link
            url="http://www.mozilla.org/en-US/firefox/new/">Download</g:link></span><a href="#" class="close-notify">X</a>
</div>

<div id='login'>
    <div class='inner'>
        <div class="fheader"><g:message code="springSecurity.login.header"/></div>

        <div style="border-top: 1px solid #ffffff; padding-top: 14px;">
            <g:if test='${flash.message}'>
                <div class='login_message'><p>${flash.message}</p></div>
            </g:if>
            <g:if test='${params.session_expired}'>
                <div class='login_message'><p>Session expired, please login again</p></div>
            </g:if>

            <form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
                <p>
                    <label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
                    <input type='text' class='text_' name='username' id='username'/>
                </p>

                <p>
                    <label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
                    <input type='password' class='text_' name='password' id='password'/>
                </p>

                %{--<p id="remember_me_holder" class="rememberMeHolderStyle">--}%
                    %{--<span class="rememberMeInput">--}%
                    %{--<input style="width: 20px;" type='checkbox'  name='${rememberMeParameter}' id='remember_me'--}%
                           %{--<g:if test='${hasCookie}'>checked='checked'</g:if> />--}%
                    %{--</span>--}%
                    %{--<span class="rememberMeText"><g:message code="springSecurity.login.remember.me.label"/></span>--}%
                %{--</p>--}%

                <p class="forgotPassword">
                    <g:link controller="login" class="forgotPass" action="forgotPassword">Forgot password?</g:link>
                </p>

                <p class="buttonClass loginbutton" style="float: none;">
                    <input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
                </p>
            </form>
        </div>
    </div>
</div>
<script type='text/javascript'>
    <!--
    (function () {
        document.forms['loginForm'].elements['j_username'].focus();
    })();
    // -->
</script>
</body>
</html>
