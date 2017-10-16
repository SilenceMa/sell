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
            <form role="form" method="post" action="/sell/seller/product/save">

                <div class="form-group">
                    <label>商品名称</label>
                    <input class="form-control" name="productName" type="text" value="${(productInfo.productName)!''}"/>
                </div>

                <div class="form-group">
                    <label>价格</label>
                    <input name="productPrice" class="form-control" type="text" value="${(productInfo.productPrice)!''}"/>
                </div>

                <div class="form-group">
                    <label>库存</label>
                    <input name="productStock" class="form-control" type="number" value="${(productInfo.productStock)!''}"/>
                </div>
                <div class="form-group">
                    <label>描述</label>
                    <input name="productDescription" class="form-control" type="text" value="${(productInfo.productDescription)!''}"/>
                </div>

                <div class="form-group">
                    <label>图片</label>
                    <input name="productIcon" class="form-control" type="text" value="${(productInfo.productIcon)!''}"/>
                    <img width="200" height="200" src="${(productInfo.productIcon)!''}" alt="">
                </div>

                <div class="form-group">
                    <label>类目</label>
                    <select name="categoryType" class="form-control">
                        <#list categories as categorie>
                            <option value="${categorie.categoryType}"
                                <#if (productInfo.categoryType)?? && productInfo.categoryType = categorie.categoryType>
                                selected = "selected"
                                </#if>
                            >
                                ${categorie.categoryName}
                            </option>
                        </#list>
                    </select>
                </div>
                <input hidden name="productId" value="${(productInfo.productId)!''}">
                <button type="submit" class="btn btn-default">提交</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>