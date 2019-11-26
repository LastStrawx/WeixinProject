    var vue = new vue({
        el:"#vueTable",
        date:{
            poducts:null
        }
    });

    var totalCount = 0;

    //layUi的分页
    layui.use('laypage',function () {
        var laypage = layui.laypage;

        laypage.render({
            elem:"pagecontainer",
            count:totalCount,
            layout:['count','prev','page','next','limit','refresh','skip'],
            jump:function (obj,first) {
                //首次不执行
                if(!first){
                    getData(obj.curr,obj.limit);
                }
            }
        })
    })

    var currPage = 1;//页面首次加载，当前页是1
    var pageSize = 8;

    getData(currPage,pageSize);

    //异步请求
    function getData(currPage,pageSize) {
        axios
            .get("/lanqiao1121/products.do", {
                params: {
                    currPage: currPage,
                    pageSize: pageSize
                }
            })
            .then(function (response) {
                vue.products = response.data;
                totalCount = response.totalCount;
            })
            .catch(function (error) {
                console.log(error);
            })
    }