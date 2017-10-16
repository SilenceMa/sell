<html>
<head>
    <meta charset="UTF-8">
    <title>卖家商品列表</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">

        <div class="col-md-6 column">
            <table class="table">
                <thead>
                <tr>
                    <th>
                        订单id
                    </th>
                    <th>
                        订单总金额
                    </th>

                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                    ${orderDto.orderId}
                    </td>
                    <td>
                    ${orderDto.orderAmount}
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    <#--订单详情-->
        <div class="col-md-12 column">
            <table class="table">
                <thead>
                <tr>
                    <th>
                        商品id
                    </th>
                    <th>
                        商品名称
                    </th>
                    <th>
                        价格
                    </th>
                    <th>
                        数量
                    </th>
                    <th>
                        订单总额
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list orderDto.orderDetails as orderDetails>
                <tr>
                    <td>
                        ${orderDetails.productId}
                    </td>
                    <td>
                        ${orderDetails.productName}
                    </td>
                    <td>
                        ${orderDetails.productPrice}
                    </td>
                    <td>
                        ${orderDetails.productQuantity}
                    </td>
                    <td>
                        ${orderDetails.productQuantity * orderDetails.productPrice}
                    </td>
                </tr>
                </#list>

                </tbody>
            </table>
        </div>

        <div class="col-md-12 column">
            <#if orderDto.getOrderStatusEnum().message == "新订单">
                <a href="/sell/seller/order/finish?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-success">完结订单</a>
                <a href="/sell/seller/order/cancel?orderId=${orderDto.orderId}" type="button" class="btn btn-default btn-danger">取消订单</a>
            </#if>
        </div>
    </div>
</div>
</body>
</html>