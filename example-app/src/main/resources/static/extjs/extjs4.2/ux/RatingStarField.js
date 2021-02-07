
Ext.define('Pactera.widget.StarRating', {

    alias:"widget.starRating",

    extend: 'Ext.Component',

     

    afterRender:function(){

         this.callParent();

    },

     

    //label: 'score',//

    //labelWidth: 60,

    //lableAlign:'right',//left,center,right

    //aMsg: [], 

    /*[

        "很不满意|差得太离谱，与卖家描述的严重不符，非常不满",

        "不满意|部分有破损，与卖家描述的不符，不满意",

        "一般|质量一般，没有卖家描述的那么好",

        "满意|质量不错，与卖家描述的基本一致，还是挺满意的",

        "非常满意|质量非常好，与卖家描述的完全一致，非常满意"

        ]*/

     

    width: 275,

    height: 50,

    padding: 10,

    style: {

        color: '#000000',

        backgroundColor:'#FFFFFF'

    },

    initComponent: function(){

        var me = this;

        var date = new Date();

        var dateTime = date.getTime();

        var starId = me.id?"star_"+me.id:"star"+dateTime;

         

        var label = me.label?me.label:'评分';

        var labelWidth = me.labelWidth?me.labelWidth-13:87;

        var labelAlign = me.lableAlign?me.lableAlign:'right';

        var thisWidth = me.width?me.width:130;

        var aMsg = [

            "Unacceptable|Unacceptable",

            "Poor|Poor",

            "Fair|Fair",

            "Good|Good",

            "Excellent|Excellent"

            ]

        if(me.aMsg){

            aMsg = me.aMsg;

        }

         

        var html = '<div id="'+ starId +'" class="star" style="width:'+thisWidth+'px;">'

        + '<span style="width:'+labelWidth+'px;text-align:'+labelAlign+'" >'+ label + '</span>'

        + '<span>:</span>'

        +  '<ul>'

        +       '<li><a href="javascript:;">1</a></li>'

        +       '<li><a href="javascript:;">2</a></li>'

        +       '<li><a href="javascript:;">3</a></li>'

        +       '<li><a href="javascript:;">4</a></li>'

        +       '<li><a href="javascript:;">5</a></li>'

        +   '</ul>'

        +'<span></span>'

        +'<p></p>'

        +'</div>'

        this.html = html;

        this.listeners = {

            'boxready':function(){

                var oStar = document.getElementById(starId);

                var aLi = oStar.getElementsByTagName("li");

                var oUl = oStar.getElementsByTagName("ul")[0];

                var oSpan = oStar.getElementsByTagName("span")[1];

                var oP = oStar.getElementsByTagName("p")[0];

                var i = iScore = me.iStar = 0;

                 

                for (i = 1; i <= aLi.length; i++)

                {

                    aLi[i - 1].index = i;

                    //鼠标移过显示分数

                    aLi[i - 1].onmouseover = function ()

                    {

                        fnPoint(this.index);

                        //浮动层显示

                        oP.style.display = "block";

                        //计算浮动层位置

                        oP.style.left = oUl.offsetLeft + this.index * this.offsetWidth - 104 + "px";

                        //匹配浮动层文字内容

                        oP.innerHTML = "<em><b>" + this.index + "</b> " + aMsg[this.index - 1].match(/(.+)\|/)[1] + "</em>" + aMsg[this.index - 1].match(/\|(.+)/)[1]

                    };

                    //鼠标离开后恢复上次评分

                    aLi[i - 1].onmouseout = function ()

                    {

                        fnPoint();

                        //关闭浮动层

                        oP.style.display = "none"

                    };

                    //点击后进行评分处理

                    aLi[i - 1].onclick = function ()

                    {

                        me.iStar = this.index;

                        oP.style.display = "none";

                        //oSpan.innerHTML = "<strong>" + (this.index) + " 分</strong> (" + aMsg[this.index - 1].match(/\|(.+)/)[1] + ")"

                        me.value = this.index;

                    }

                }

                //评分处理

                function fnPoint(iArg)

                {

                    //分数赋值

                    iScore = iArg || me.iStar;

                    for (i = 0; i < aLi.length; i++) aLi[i].className = i < iScore ? "on" : "";   

                }

                 

                var setValue = function(score){

                    me.iStar =  score;

                    fnPoint(score);

                }

                 

                me.setValue = setValue;

            }

        }

         

        this.callParent();

         

    }

     

     

});