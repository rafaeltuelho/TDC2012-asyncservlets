<%-- 
    Document   : index
    Created on : Oct 15, 2012, 8:32:17 AM
    Author     : rafaeltuelho
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Async webapp</title>
        <script src="js/jquery-1.8.2.js"></script>
        <script src="js/bootstrap.js"></script>
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <h1><font color="blue">Async/Sync</font> Request</h1>
        <form class="form-horizontal" id="form1" action="./DeliciousClientSyncServlet" method="POST">
            <div class="control-group">
                <label class="control-label" for="_reqMode">Process Mode</label>
                <div class="controls">
                    <input type="radio" name="_reqMode" value="./DeliciousClientSyncServlet" checked>Sync<br>
                    <input type="radio" name="_reqMode" value="./DeliciousClientAsyncServlet">Async
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="_username">Usename</label>
                <div class="controls">
                    <input type="text" id="_username" name="_username" placeholder="User Name">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="_password">Password</label>
                <div class="controls">
                    <input type="password" id="_password" name="_password" placeholder="Password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="_reqID">Request ID</label>
                <div class="controls">
                    <input type="text" id="_reqID" name="_reqID" placeholder="ID do request" size="3">
                </div>
            </div>
            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn btn-primary">Submit</button>
                </div>
            </div>
        </form>
        <script type="text/javascript">
            $(document).ready(function() {
                $('input[name=_reqMode]:radio').change(function() {
                    console.info('form action: ' + this.value);
                    
                    $('#form1').attr('action', this.value);
                });
            });
        </script>
    </body>
</html>
