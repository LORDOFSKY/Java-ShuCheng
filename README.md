# Java-ShuCheng
Java网上图书商城，项目基于MVC设计模式，采用B/S结构



## 1.1前台首页设计

首页模块包括3个主要的部分，采用内嵌框架技术。位置分别为上、左、中。

![首页](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E9%A6%96%E9%A1%B5.png)
### 1.上部的实现
上部包含两大部分内容：
* 网站标题；
* 菜单;
菜单部分就是一系列的超链接，用户在未登录时看到的是“登录”和“注册”链接；在登录后看到的是“我的购物车”、“我的订单”、“修改密码”、“退出”超链接，以及当前用户名称。
### 2.左部的实现
左部显示所有分类，包含所有1级、2级分类。左部使用了Javascript的Q6Menu组件来完成显示所有分类。当用户点击某个1级分类名称时会展示当前1级分类的所有2级分类。
### 3.中部的实现
中部是网站的骨干，点击任何超链接都会在中部显示。默认只是使用纯文本显示欢迎信息。


## 1.2　用户模块
注册用户是构成网站主体的一个重要组成部分，网站设置注册用户的目的之一在于方便网站信息的管理。
### 1.2.1　用户注册
用户在登录之前需要先进行注册，在首页中点击“注册”链接就可以到达注册页面。
![注册](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E7%94%A8%E6%88%B7%E6%B3%A8%E5%86%8C.png)
#### 1.表单校验
注册页面使用JQuery对用户输入的数据进行校验：
* 用户名：
* 不能为空；
* 长度必须在2-15之间；
* 不能是已注册过的；
* 登录密码：
* 不能为空；
* 长度必须在2-15之间；
* 确认密码：
* 不能为空；
* 必须与登录密码相同；
* Email：
* 不能为空；
* 必须是正确的Email格式；
* 不能是注册过的Email；
* 验证码：
* 不能为空；
* 必须与图片上的验证码相同。

表单校验中，用户名是否被注册过、Email是否被注册过、验证码是否正确这三项都需要请求服务器，所以这里使用的是JQuery的ajax()来完成对服务器的访问。

#### 2.激活
当用户注册成功后还需要激活成功后才能登录。在注册成功后，系统给用户的邮箱发送一份激活邮件。当用户登录自己的邮箱后，在激活邮件中点击激活链接完成激活后，才可以去登录。


### 1.2.2　用户登录
在首页点击“登录”链接就可以来到登录页面。
![登录](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E7%94%A8%E6%88%B7%E7%99%BB%E5%BD%95.png)
#### 1.表单校验
* 用户名：
* 不能为空；
* 长度必须在2-15之间；
* 用户名是否存在；
* 密码：
* 不能为空；
* 长度必须在2-15之间；
* 验证码：
* 不能为空；
* 是否正确。
登录表单校验使用的JQuery完成，其中用户名是否存在，以及验证码是否正确需要使用JQuery的ajax()向服务器发送异步请求。

#### 2.登录成功
用户登录成功后，会回到首页。这时在首页会显示当前用户的名称，以及“我的购物车”、“我的订单”、“修改密码”、“退出”链接。也就是说，这几个功能只能登录用户才能使用，而游客是无法使用的。


### 1.2.3　修改当前用户密码
用户在登录成功后，点击修改密码链接就会到达修改密码页面。
![图书详细](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E4%BF%AE%E6%94%B9%E5%AF%86%E7%A0%81.png)
#### 1.表单校验
* 原密码：
* 不能为空；
* 长度必须在2-15之间；
* 是否正确；
* 新密码：
* 不能为空；
* 长度必须在2-15之间；
* 确认密码：
* 不能为空；
* 必须与新密码相同；
* 验证码：
* 不能为空；
* 是否正确。
表单校验使用JQuery完成。其中原密码和验证码是否正确，需要异步访问服务器，这里使用的是JQuery的ajax()完成的。

### 1.2.4　退出
当用户登录后，点击退出链接可以完成退出。退出成功后会到达登录页面！

## 1.3　图书模块
### 1.3.1　图书列表
在首页左部点击某个2级分类，会在首页的中部显示图书列表页面。图书列表使用分页显示。
![图书列表](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%9B%BE%E4%B9%A6%E5%88%97%E8%A1%A8.png)
可以在图书列表上方输入关键字进行搜索。

### 1.3.2　图书详细
点击某本图书，会到达图书详细页面。
![图书详细](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%9B%BE%E4%B9%A6%E8%AF%A6%E6%83%85.png)

### 1.3.3　高级搜索
在图书列表页面点击高级搜索到达搜索页面。
![高级搜索](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E9%AB%98%E7%BA%A7%E6%90%9C%E7%B4%A2.png)
高级搜索有三个条件：书名、作者、出版社，三个条件的关系是并列的。而且三个条件都是模糊查询。


## 1.4　购物车
购物车使用数据库来保存数据，也就是说添加到购物车中的图书，不会因为关闭浏览器，或者是关闭电脑而消失。而且修改数量，是通过异步请求来操作数据库的。

### 1.4.1　添加图书到购物车
在图书详细页面，给出数量，然后点击“购买”就可以把图书添加到购物车中，并且会到达购物车列表页面。
![添加购物车](https://github.com/LORDOFSKY/JavaShuCheng/blob/master/.settings/readmeImgage/%E6%B7%BB%E5%8A%A0%E5%9B%BE%E4%B9%A6%E5%88%B0%E8%B4%AD%E7%89%A9%E8%BD%A6.png)

### 1.4.2　我的购物车
也可以在首页上部点击“我的购物车”链接查询购物车。购物车列表页面会显示所有车中所有条目，每个条目会显示图书图片、图书名称、图书当前价、数量，以及小计。


### 1.4.3　修改条目数量
在购物车列表页面中，点击某个条目上的数量来完成修改数量。这项操作会修改底层数据库。所以这里需要使用JQuery的异步处理访问服务器，完成对数据库表的修改。
当数量为1时，如果把数量减1，会弹出确认对话框，提示是否删除该条目。


### 1.4.4　删除条目
在购物车列表页面中，点击某个条目后面的“删除”链接会删除当前条目。
也可以勾选N个条目，然后点击“批量删除”链接，完成一次删除多个条目。


## 1.5　订单模块
对订单的操作，对应数据库中的两张表，即订单表和订单条目表（t_order和t_orderitem）。而且订单模块的功能比较多：
* 生成订单：通过购物车中勾选的条目来生成订单；
* 我的订单：显示当前用户的所有订单，每个订单所包含的订单条目也需要显示；
* 订单支付：使用易宝的第三方支付平台完成，对“在线支付”的理解也是一个挑战；
* 订单详细：显示指定的某个订单；
* 订单取消和订单的确认收货：这两个功能都是对订单状态的修改。


### 1.5.1　选中条目，准备生成订单
在购物车列表页面中，勾选要购买的条目，然后点击“结算”按钮，完成选中条目，准备生成订单，这会到达订单准备页面。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E9%80%89%E4%B8%AD%E6%9D%A1%E7%9B%AE%E7%94%9F%E6%88%90%E8%AE%A2%E5%8D%95.png)
 

### 1.5.2　生成订单
在订单准备页面，输入收货地址，然后点击“提交订单”按钮，完成下单（生成订单）。这时会到达“下单成功”页面。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E7%94%9F%E6%88%90%E8%AE%A2%E5%8D%95.png)
这时订单已经生成，但状态为“未付款”。可以在“下单成功”页面点击“支付”按钮到达“支付”页面。


### 1.5.3　订单列表
在首页上部点击“我的订单”链接，就会到达订单列表页面。该页面会显示当前用户的所有订单信息。该页使用分页显示订单！
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E8%AE%A2%E5%8D%95%E5%88%97%E8%A1%A8.png)


### 1.5.4　支付页面
在“下单成功”页面，或者“订单列表”页面中点击“支付”按钮都可以到达“支付”页面。在“支付”页面中选择银行，后点击下一步就会跳转到银行的支付页面了。这里使用的是“易宝”第三方支付平台！
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E6%94%AF%E4%BB%98%E9%A1%B5%E9%9D%A2.png)
  

### 1.5.5　订单详细页面
在订单列表页面中，点击某个订单的“查看”、“取消”、“确认收货”都会到达“订单详细”页面。其中点击“查看”到达“订单详细”页面后没有按钮；点击“取消”到达“订单详细”页面有“取消按钮”按钮；点击“确认收货”按钮到达“订单详细”页面有“确认收货”按钮。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E6%94%AF%E4%BB%98%E8%AF%A6%E6%83%85.png)


### 1.5.6　订单状态
订单的状态分为5种：
* 1：未付款状态。当订单刚刚生成时，就是1状态；
* 2：已付款状态，但未发货。当订单刚刚支付之后，就是2状态；
* 3：已发货，但未消确认收货。当订单刚刚发货之后，就是3状态；
* 4：交易成功。当订单确认收货之后，就是4状态。一旦订单为4状态后，就不能再改变状态；
* 5：已取消。当订单被取消后，就是5状态。只有1状态（未付款状态）的订单可以取消，其他状态的订单是不可以取消的。一旦订单为5状态后，就不能再改变状态。

## 2.后台设计与实现
书城后台的设计是为管理员方便管理系统而设计的，其中包括分类管理、图书管理，以及订单管理。


### 2.1　管理员登录
后台管理员登录页面，登录成功后到达后台主页。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E7%AE%A1%E7%90%86%E5%91%98%E7%99%BB%E5%BD%95.png)


### 2.2　后台主页
管理员登录成功后，到达主页。主页是框架页，由上、下两部分构成。
* 上部显示标题和菜单，分别为：“退出”、“分类管理”、“图书管理”、“订单管理”，以及当前用户名称；
* 中部默认显示欢迎图片，当点击上部链接后，都在中部显示。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%90%8E%E5%8F%B0%E4%B8%BB%E9%A1%B5.png)
    

### 2.3　分类管理
#### 2.3.1　分类列表
当点击首页上部的“分类管理”链接到达分类列表页面。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%88%86%E7%B1%BB%E5%88%97%E8%A1%A8.png)


#### 2.3.2　添加分类
在分类列表页面中有“添加一级分类”链接，点击该链接直接“添加一级分类页面”。
列表中每个一级分类后面都存在“添加二级分类”链接，添加可以到达“添加二级分类页面”。

![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E6%B7%BB%E5%8A%A0%E5%88%86%E7%B1%BB.png)

![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E6%B7%BB%E5%8A%A0%E5%88%86%E7%B1%BB2.png)

添加二级分类需要指定父分类。


#### 2.3.3　修改分类
在分类列表中，每个一级分类，以及二级分类后都存在“修改”链接。点击一级分类后的“修改”链接进入“修改一级分类页面”；点击二级分类后的“修改”链接进入“修改二级分类页面”。

![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E4%BF%AE%E6%94%B9%E5%88%86%E7%B1%BB.png)

![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E4%BF%AE%E6%94%B9%E5%88%86%E7%B1%BB2.png)



#### 2.3.4　删除分类
在分类列表中，每个一级分类，以及二级分类后都存在“删除”链接。点击一级分类后的“删除”链接完成删除一级分类；点击二级分类后的“删除”完成删除二级分类。
注意，如果一级分类下存在子分类，那么不能删除。
注意，如果一级分类下存在图书，那么不能删除。



### 2.4　图书管理
后台图书管理这一部分，很多地方都与前台的图书操作相同，例如：分页显示所有图书、按分类查询图书、高级查询图书、查看图书详细信息。后台图书管理还包括前台所没有的功能，例如：添加新图书、编辑和删除图书。
  
#### 2.4.1　图书列表页面
图书列表页面分为两个部分：
* 左部：显示所有分类，与前台是相同的，用来通过指定分类来查询图书；
* 中部：默认显示“添加图书”和“高级查询”两项功能，当管理员点击某一分类后，中部显示图书列表。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%90%8E%E5%8F%B0%E5%9B%BE%E4%B9%A6%E5%88%97%E8%A1%A8.png)
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%90%8E%E5%8F%B0%E5%9B%BE%E4%B9%A6%E5%88%97%E8%A1%A82.png)



#### 2.4.2　添加图书
点击“图书管理”链接后，在中部会出现“添加图书”链接，点击后会进入添加图书表单页面。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%90%8E%E5%8F%B0%E6%B7%BB%E5%8A%A0%E5%9B%BE%E4%B9%A6.png)
表单使用了JQuery进行校验，如果校验无误，添加图书会成功！
    

#### 2.4.3　高级搜索
点击“图书管理”链接后，在中部会出现“高级搜索”链接，点击后会进入高级搜索表单页面。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%90%8E%E5%8F%B0%E9%AB%98%E7%BA%A7%E6%90%9C%E7%B4%A2.png)
这个高级搜索与前台是相同的，只需要在表单中输入条件后搜索，即可在图书列表中显示结果。


#### 2.4.4　图书详细
在图书列表中点击某一本图书后会进入图书详细页面。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%90%8E%E5%8F%B0%E5%9B%BE%E4%B9%A6%E8%AF%A6%E6%83%85.png)
图书详细页面上方有一个名为“编辑或删除”的复选框，如果管理员勾选它，那么页面会显示表单，并多出两个按钮：“编辑”和“删除”。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%90%8E%E5%8F%B0%E5%9B%BE%E4%B9%A6%E8%AF%A6%E6%83%852.png)


### 2.5　订单管理
#### 2.5.1　订单列表
点击“订单管理”链接会到达订单列表页面。该页面会分页显示所有订单！
订单列表页面上包含按状态查询的链接，分别为：未付款、已付款、已发货、交易成功、已取消，管理员可以点击这几个链接按状态查询显示订单。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%90%8E%E5%8F%B0%E8%AE%A2%E5%8D%95%E8%AF%A6%E6%83%85.png)


#### 2.5.2　订单详细
在订单列表页面中，点击某个订单后面的：查看、取消、发货链接会进入到订单详细页面。订单详细页面会显示当前订单的信息，而且会根据点击的链接显示不同的按钮。
![整体流程](https://github.com/LORDOFSKY/Java-ShuCheng/blob/master/.settings/readmeImgage/%E5%90%8E%E5%8F%B0%E8%AE%A2%E5%8D%95%E8%AF%A6%E6%83%852.png)
点击发货或取消按钮完成相应操作即可。
