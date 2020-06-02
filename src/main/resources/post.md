因为平时查成绩十分繁琐，要点击很多次，上学期也学了一点python，所以想着用个脚本比较快速的查成绩。话不多说，正式开始。

1.首先打开南京航空航天大学研究生成绩管理的页面，没有验证码还是比较方便爬取的。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-1.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-1.jpg)

首先按f12键打开开发工具，先看看form表单的结构，我们看到input标签下有一个hidden的隐藏域`__VIEWSTATE`。在后面的爬取中，我发现这个`__VIEWSTATE`会在一定时间内会发生变化，这个我们先不管，我们之后再来处理它。我们注意到`action="login.aspx"`。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-2.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-2.jpg)

2.我们准备登陆，首先打开开发工具的Network，并勾选preserve log,记录我们登录过程中的页面加载过程中的请求和响应情况。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-3.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-3.jpg)

3.现在万事具备，我们点击登录。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-4.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-4.jpg)

我们点击`login.aspx`这个Name的请求。在当中记录了Request URL和Request Method,以及Form Data中的隐藏域`__VIEWSTATE`。

继续下拉，我们看到Form Data中的`_ctl0:txtusername`,这就是我们登录时所填写的用户名，以及`_ctl0:txtpassword`这是我们的密码。除此之外，`_ctl0:ImageButton1.x`，`_ctl0:ImageButton1.y`这两个也是Form Data所需要向服务器`POST`的数据。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-5.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-5.jpg)

4.接下来就是找到查询成绩的页面。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-6.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-6.jpg)

在学生成绩查询上我们右键”审查元素”，发现是指向一个 [成绩页面](http://gsmis.nuaa.edu.cn:81/nuaapyxx/grgl/xskccjcx.aspx) 的链接，而且是以Frame的形式打开。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-7.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-7.jpg)

5.接下来就是使用正则表达式或者Python中一个著名的beautifulSoup的页面解析库，这里我也正是采用了beautifulSoup进行页面的解析。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-8.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-8.jpg)

6.通过”审查元素”，我们看到课程名和成绩是在属性为`nowrap="nowrap"`的表格里，但是其他的学位课程等其他无用的标签也包含属性``nowrap="nowrap"`，所以我们要去掉。（要去掉的元素同时还包含属性`'bgcolor': '#C8E1FB'`，所以还是比较容易除去的）。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-9.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-9.jpg)

7.接下来就是代码了，其实功能也很简单，加上注释不到60行就搞定了。想查成绩的时候，轻轻点击一下运行，成绩文件自动建立就OK了。

![http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-10.jpg](http://www.wang-xin.cn/wp-content/uploads/2018/06/nuaa-10.jpg)

源代码为：

```python
# -*- coding: utf-8 -*-
import urllib
import urllib2
import cookielib
import os
from bs4 import BeautifulSoup
import sys;
reload(sys);
sys.setdefaultencoding("utf8")

#声明一个CookieJar对象实例来保存cookie
cookie = cookielib.CookieJar()

#利用urllib2库的HTTPCookieProcessor对象来创建cookie处理器
handler=urllib2.HTTPCookieProcessor(cookie)

#通过handler来构建opener
opener = urllib2.build_opener(handler)

# 登录教务系统的URL
loginUrl = 'http://gsmis.nuaa.edu.cn/nuaapyxx/login.aspx'

# 请求头文件
headers = {
    'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0',
}
req = urllib2.Request(url=loginUrl,  headers=headers)

# 为了获得隐藏域__VIEWSTATE，先打开一次页面获得viewstate，再进行登录
opener.open(req)

result = opener.open(req)
content = result.read().decode('utf-8')
soup = BeautifulSoup(content, "lxml")
for tabb in soup.find_all(name='input', attrs={'name':'__VIEWSTATE'}):
    viewstate = tabb.attrs['value']
postdata = urllib.urlencode({
    '__VIEWSTATE': viewstate,

    # 此处为用户名/学号
    '_ctl0:txtusername': '######',

    '_ctl0:ImageButton1.x': '30',
    '_ctl0:ImageButton1.y': '26',

    # 此处为用户密码
    '_ctl0:txtpassword': '######'
})
req = urllib2.Request(url=loginUrl, data=postdata, headers=headers)
opener.open(req)
gradeUrl = 'http://gsmis.nuaa.edu.cn:81/nuaapyxx/grgl/xskccjcx.aspx'
req = urllib2.Request(url=gradeUrl, headers=headers)
result = opener.open(req)
content = result.read().decode('utf-8')
soup = BeautifulSoup(content, "lxml")
count = 0
filename = u"成绩"
path = filename + ".txt"
[s.extract() for s in soup(name='tr', attrs={'bgcolor': '#C8E1FB'})]
for tabb in soup.find_all(name='tr', attrs={'nowrap':'nowrap'}):
    for tdd in tabb.find_all(name='font', attrs={'size':'2'}):
        count += 1
        if (count % 5 == 1):
            with open(path, "a") as fp:
                fp.write("课程:“%s”," % tdd.get_text())
        if (count % 5 == 4):
            with open(path, "a") as fp:
                fp.write("成绩:%s;\n" % tdd.get_text())
```
