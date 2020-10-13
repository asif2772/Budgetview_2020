<%--
  Created by IntelliJ IDEA.
  User: zahangir
  Date: 11/11/12
  Time: 8:37 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html" %>
<html>
  <head>
      <title><g:message code="default.passwordReset.email.label", default="Password Reset Email" /></title>
      <meta name='layout' content='main' />
  </head>
  <body>
     <div class="content scaffold-create" role="main">

         Beste ${user.firstName} ${user.lastName},<br/><br/>

         <g:message code="default.passwordReset.msg_nl1.label", default="We hebben je verzoek om je wachtwoord te resetten ontvangen." /><br/>
         <g:message code="default.passwordReset.msg_nl2.label", default="Je nieuwe wachtwoord is: " /> ${password} <br/><br/>
         <g:message code="default.passwordReset.msg_nl3.label", default="If you still can't login please contact support: service@budgetview.nl" /><br/><br/>

         <g:message code="default.passwordReset.msg_nl4.label", default="Met vriendelijke groet," /><br/><br/>
         <g:message code="default.passwordReset.msg_nl5.label", default="Budget View" /><br/><br/><br/>


         Dear ${user.firstName} ${user.lastName},<br/><br/>

         <g:message code="default.passwordReset.msg_en1.label", default="We received your request to reset your password." /><br/>
         <g:message code="default.passwordReset.msg_en2.label", default="Your new password : " /> ${password} <br/><br/>
         <g:message code="default.passwordReset.msg_en3.label", default="Mocht u problemen blijven ondervinden met inloggen dan kunt u contact met ons opnemen via service@budgetview.nl" /><br/><br/>

         <g:message code="default.passwordReset.msg_en4.label", default="Kind regards," /><br/><br/>
         <g:message code="default.passwordReset.msg_en5.label", default="Budget View" />

     </div>
  </body>
</html>