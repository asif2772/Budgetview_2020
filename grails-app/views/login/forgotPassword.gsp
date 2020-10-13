<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
      <meta name='layout' content='main' />
      <title> <g:message code="default.forgotPassword.title.label" default="Forgot password?" /></title>
  </head>
  <body>
  <div class="content scaffold-create" role="main">
      %{--<fieldset class="form">--}%
	    <g:if test="${flash.message}">
		   <div class="message" role="status">${flash.message}</div>
		</g:if>
        %{--<form action='${postUrl}' method='POST'  class='cssform' autocomplete='off'>--}%
        <g:form action="forgotPassword" >
            <fieldset class="form">
                <div class="fieldcontain required" style="width:50%" >
                    <label for="User Name">
                        <g:message code="default.passwordReset.userName.label" default="User Name" />
                        <span class="required-indicator">*</span>
                    </label>
                    <g:textField id="forgotPassword" name="userName" required="" style="width: 300px;height:20px;margin-top: 10px;" />
                </div>

                <div style="width:30%"  class="fieldcontain">
                  <div class="buttonClass" style="wmargin-right:540px;margin-top:10px;margin-bottom:10px;margin-right:245px;">
                      <div class="buttonCLassInner">
                          <g:submitButton name="reset" style="height:22px;" class="save" value="${message(code: 'default.passwordReset.reset.label', default: 'Reset')}" />
                      </div>
                  </div>
                </div>
            </fieldset>
        </g:form>
        %{--</form>--}%
      %{--</fieldset>--}%
  </div>
  </body>
</html>