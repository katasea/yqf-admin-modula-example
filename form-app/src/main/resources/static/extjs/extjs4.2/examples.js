Ext.example = function(){
    var msgCt;

    function createBox(t, s, ishide){
    	var baseStr = '<div class="msg"><h3>' + t + '</h3><p>' + s + '<br/></p>';
    	if(!ishide) {
    		baseStr = baseStr + '<br/><p style="text-align:right;font-size:12px; width:100%;"><font color="blank"><u style="cursor:hand;" onclick="Ext.example.hide(this);">关闭</u></font></p>';
    	}else {
    		baseStr = baseStr + '<br/>';
    	}
    	baseStr = baseStr + '</div>';
       return baseStr
    }
    return {
        msg : function(title, format,ishide,time){
        	if(ishide == null || ishide == '') {
        		ishide = false;
        	}
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, createBox(title, s, ishide), true);
            m.hide();
            m.slideIn('t');
            if(!Ext.isEmpty(ishide)&&ishide) {
            	if(Ext.isEmpty(time)){
	              time=1000;
	             }
	            m.ghost("t", { delay: time, remove: true});
            }
        },
		hide : function(v){
			var msg=Ext.get(v.parentElement.parentElement.parentElement);
	        msg.ghost("t", {remove:true});
		},
        init : function(){
            if(!msgCt){
                // It's better to create the msg-div here in order to avoid re-layouts 
                // later that could interfere with the HtmlEditor and reset its iFrame.
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
//            var t = Ext.get('exttheme');
//            if(!t){ // run locally?
//                return;
//            }
//            var theme = Cookies.get('exttheme') || 'aero';
//            if(theme){
//                t.dom.value = theme;
//                Ext.getBody().addClass('x-'+theme);
//            }
//            t.on('change', function(){
//                Cookies.set('exttheme', t.getValue());
//                setTimeout(function(){
//                    window.location.reload();
//                }, 250);
//            });
//
//            var lb = Ext.get('lib-bar');
//            if(lb){
//                lb.show();
//            }
        }
    };
}();


Ext.onReady(Ext.example.init, Ext.example);
