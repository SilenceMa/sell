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
            <table class="table table-bordered table-hover">
                <thead>
                <tr>
                    <th>
                        类目id
                    </th>
                    <th>
                        名字
                    </th>
                    <th>
                        type
                    </th>
                    <th>
                        创建时间
                    </th>
                    <th>
                        修改时间
                    </th>
                    <th>
                        修改
                    </th>

                </tr>
                </thead>
                <tbody>
                <#list categoryList as category>
                <tr>
                    <td>
                    ${category.categoryId}
                    </td>
                    <td>
                    ${category.categoryName}
                    </td>
                    <td>
                    ${category.categoryType}
                    </td>
                    <td>
                    ${category.createTime}
                    </td>
                    <td>
                    ${category.updateTime}
                    </td>
                    <td>
                        <a href="/sell/seller/category/index?categoryId=${category.categoryId}">修改</a>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>