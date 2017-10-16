<html>
<head>
    <meta charset="UTF-8">
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <form role="form" method="post" action="/sell/seller/category/save">

                <div class="form-group">
                    <label>名称</label>
                    <input class="form-control" name="categoryName" type="text" value="${(category.categoryName)!''}"/>
                </div>

                <div class="form-group">
                    <label>type</label>
                    <input name="categoryType" class="form-control" type="number" value="${(category.categoryType)!''}"/>
                </div>

                <input hidden name="categoryId" value="${(category.categoryId)!''}">
                <button type="submit" class="btn btn-default">提交</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>