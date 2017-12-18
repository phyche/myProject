<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/res/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="${ctx}/res/ajaxfileupload.js"></script>
<script src="${ctx}/res/js/alertLayer/layer.js${sourceVer}"></script>
<body id="all" style="display: block;">
<div>
	设置redis：
	<div style="-webkit-box-pack: center;">
		<table>
			<tr>
				<td>key:</td>
				<td><input type="text" name="rediskey" id="rediskey" style="width: 100%;"/></td>
			</tr>
			<tr>
				<td>value:</td>
				<td><input type="text" name="redisval" id="redisval" style="width: 100%;"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="提交" id="setredisbtn">
				</td>
			</tr>
		</table>
	</div>
</div>
<div>
	设置hashRedis：
	<div style="-webkit-box-pack: center;">
		<table>
			<tr>
				<td>primaryKey:</td>
				<td><input type="text" id="primaryKey" style="width: 100%;"/></td>
			</tr>
			<tr>
				<td>vicekey:</td>
				<td><input type="text" id="vicekey" style="width: 100%;"/></td>
			</tr>
			<tr>
				<td>hashvalue:</td>
				<td><input type="text" id="hashval" style="width: 100%;"></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="提交" id="sethashRedisbtn">
				</td>
			</tr>
		</table>
	</div>
</div>
<div>
	获取redis：
	<div style="-webkit-box-pack: center;">
		<table>
			<tr>
				<td>key:</td>
				<td><input type="text" name="getRediskey" id="getRediskey"  /></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="获取" id="getredisbtn">
				</td>
			</tr>
			<tr>
				<td style="vertical-align: top">value:</td>
				<td>
					<div id="redisvalshow" style="width: 80%;height:auto;"></div>
				</td>
			</tr>
		</table>
	</div>
</div>
<div>
	获取HashRedis：
	<div style="-webkit-box-pack: center;">
		<table>
			<tr>
				<td>primaryKey:</td>
				<td><input type="text" id="getHashRedisPrimaryKey"  /></td>
			</tr>
			<tr>
				<td>vicekey:</td>
				<td><input type="text" id="getHashRedisVicekey"  /></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="获取" id="gethashredisbtn">
				</td>
			</tr>
			<tr>
				<td style="vertical-align: top">value:</td>
				<td>
					<div id="hashredisvalshow" style="width: 80%;height:auto;"></div>
				</td>
			</tr>
		</table>
	</div>
</div>
<div>
	删除redis：
	<div style="-webkit-box-pack: center;">
		<table>
			<tr>
				<td>key:</td>
				<td><input type="text" name="getRediskey" id="delRediskey"  /></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="删除" id="delredisbtn">
				</td>
			</tr>
		</table>
	</div>
</div>
<div>
	删除HashRedis：
	<div style="-webkit-box-pack: center;">
		<table>
			<tr>
				<td>primaryKey:</td>
				<td><input type="text" id="delHashRedisPrimaryKey"  /></td>
			</tr>
			<tr>
				<td>vicekey:</td>
				<td><input type="text" id="delHashRedisVicekey"  /></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="删除" id="delHashredisbtn">
				</td>
			</tr>
		</table>
	</div>
</div>

<div>
	切数据：
	<div style="-webkit-box-pack: center;">
		<input type="button" value="切换数据" id="switchData">
	</div>
</div>

<div>
	更改卡头像：
	<div style="-webkit-box-pack: center;">
		<table>
			<input type="file" class="fileStyle" id="images" name="picture"/>
			<input type="hidden" id="pic" name="pic" />
			<tr>
				<td>cardNo:</td>
				<td><input type="text" id="cardNo"/></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="上传图片" id="updatePic">
				</td>
				<td colspan="2">
					<input type="button" value="把图片更新到卡号" id="updateCardPic">
				</td>
			</tr>
		</table>
		<div id="preview"><img id="imghead" border="0" style="padding:15px;"/></div>
	</div>
</div>

<div>
	查sql：
	<div style="-webkit-box-pack: center;width: auto;">
		<table width="100%">
			<tr>
				<td>sql:</td>
				<td>
					<textarea  name="qsql" id="qsql" style="width: 80%;height: 100px;">select * from njcc_advertisement</textarea>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" value="查询" id="qsqlbtn">
					<input type="button" value="导出" id="qsqlExport">
				</td>
			</tr>
		</table>
	</div>
	<div style="width: 100%;height: auto" id="showSqlRes"></div>
</div>
<div id="tmp"></div>
<!-- 结果集表格 -->
<script type="text/javascript">
    var imagePathStr = '${imageRequestPathStr}';
    $(function() {
//        $(document).ajaxStart(function(){layer.load();});
//        $(document).ajaxStop(function(){layer.closeAll('loading');});

        var index ;
        $(document).ajaxStart(function(){
            index = layer.load();
        });
        $(document).ajaxStop(function(){
            layer.close(index);
        });

        $("body").css("visibility","visible");
        $("#setredisbtn").bind("click",function(){
            ajaxreq('${ctx}/redis/setredis',
                {"key":$("#rediskey").val(),
                    "val":$('#redisval').val()},
                function(json){
                    alert(json.msg);
                });
        });
        $("#sethashRedisbtn").bind("click",function(){
            ajaxreq('${ctx}/redis/setHashredis',
                {"primaryKey":$("#primaryKey").val(),
                    "vicekey":$('#vicekey').val(),
                    "val":$('#hashval').val()},
                function(json){
                    alert(json.msg);
                });
        });
        $("#getredisbtn").bind("click",function(){
            ajaxreq('${ctx}/redis/getredis',
                {"key":$("#getRediskey").val()},
                function(json){
                    $('#redisvalshow').text(json.data);
                });
        });
        $("#gethashredisbtn").bind("click",function(){
            ajaxreq('${ctx}/redis/getHashredis',
                {"primaryKey":$("#getHashRedisPrimaryKey").val(),
                    "vicekey":$("#getHashRedisVicekey").val()},
                function(json){
                    $('#hashredisvalshow').text(json.data);
                });
        });
        $("#delredisbtn").bind("click",function(){
            ajaxreq('${ctx}/redis/delredis',
                {"key":$("#delRediskey").val()},
                function(json){
                    alert(json.msg);
                });
        });
        $("#delHashredisbtn").bind("click",function(){
            ajaxreq('${ctx}/redis/delHashredis',
                {"primaryKey":$("#delHashRedisPrimaryKey").val(),
                    "vicekey":$("#delHashRedisVicekey").val()},
                function(json){
                    alert(json.msg);
                });
        });

        $("#qsqlbtn").bind("click",function(){
            var reg=new RegExp("\r\n","g");
            var str=$("#qsql").val().replace(/(\r\n|\n|\r)/gm, '');
            $('#showSqlRes').empty();
            ajaxreq('${ctx}/redis/qsql',
                {"qsql":str},
                function(json){
                    console.info(json);
                    var $table = $('<table border="1"></table>');
                    var $thr = $('<tr></tr>');
                    $.each(json,function(index,item){
                        var $tr=$('<tr></tr>');
                        for(var p in item){
                            if(index==0){
                                var $thd = $('<td></td>').append(p);
                                $thr.append($thd);
                            }
                            var $td = $('<td></td>').append(item[p]);
                            $tr.append($td);
                        }
                        if(index==0){
                            $table.append($thr);
                        }
                        $table.append($tr);
                    });
                    $('#showSqlRes').append($table);
                });
        });

        $("#switchData").bind("click",function(){
            ajaxreq('${ctx}/redis/switchData',
                null,
                function(json){
                    alert(json.msg);
                });
        });

        $("#updateCardPic").bind("click",function(){
            var cardNo = $("#cardNo").val();
            var pic = $("#pic").val();
            if(cardNo===null||cardNo===""){
                alert("cardNo不能为空！");
                return false;
			}
            if(pic===null||pic===""){
                alert("图片不能为空！");
                return false;
            }

			ajaxreq('${ctx}/redis/updateCardPic',
				{"cardNo":cardNo,
                    "pic":pic},
				function(json){
                    alert(json.msg);
                    //layer.msg(json.msg, {time:5000});
				});

        });

        $("#updatePic").bind("click",function(){
            $.ajaxFileUpload({
                url : fhpt_ctx+"/lyk/image/upload.html?type=activateCard",   //submit to UploadFileServlet
                secureuri : false,
                fileElementId : 'images',
                dataType : 'json', //or json xml whatever you like~
                success : function(data, status) {
//                    layer.msg("成功上传图片！", {time:5000});
                    alert("成功上传图片！");
                    $("#pic").val(data.imageGroup.sourceUrl);
                    $("#imghead").attr("src",imagePathStr + data.imageGroup.sourceUrl);
                },
                error : function(data, status, e) {
                    alert(data+"       "+status+"            "+e);
                }
            });
        });

    });
    function ajaxreq(url,data,callback){
        $.ajax({
            url: url,
            dataType: 'json',
            data: data,
            success: function(data){
                callback(data);
            }
        });
    }
    //	383840403737393966656665
    //	Esc 27
    var keycode = '';
    $(document).keydown(function (event) {
        var kc = event.keyCode;
        if(kc == 27){
            keycode='';
            $('#all').hide();
        }else{
            keycode += kc;
            if(keycode.length>=24)keycode = keycode.substr(keycode.length-24);
            if(keycode == '383840403737393966656665')
                $('#all').show();
        }
    });

    $("#qsqlExport").bind("click",function(){
        var reg=new RegExp("\r\n","g");
        var str=$("#qsql").val().replace(/(\r\n|\n|\r)/gm, '');
        var body = $(document.body),
            form = $("<form method='post'></form>"),
            input;
        form.attr("action","${ctx}/redis/reportBySql");
        form.attr("method","post");
        input = $("<input type='hidden'>");
        input.attr({"name":'qsql'});
        input.val(str);
        form.append(input);
        form.appendTo(document.body);
        form.submit();
        document.body.removeChild(form[0]);
    });
</script>