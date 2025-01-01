# 过磅数据管理系统 V2.0

## 2.0 更新内容
### 新增功能

* 管理员用户登录后可以管理用户列表
* 管理员删除用户需要验证管理员密码

### bug修复

* 修复了司磅员姓名显示问题
* 修复了部分页面显示问题

---
# 如何使用：

## 文件结构
服务器文件夹，例如D:\Program Files\过磅数据管理系统\

* README.md 为说明文档
* application.properties 为系统核心配置文件，请勿随意修改
* admin.properties 为管理员账户密码配置文件，admin.username为账户，admin.password为密码，在等号后覆盖输入即可
* 某某.cmd 或 某某.bat 文件为服务器启动脚本程序，双击运行
* WeighTrack-2.*.jar 为服务器主程序
* 过磅明细.xlsx 为过磅系统导出数据excel文件
* /logs 文件夹为日志文件夹


## 导航栏

### 左半边

#### 鸿聚一号煤场出入库明细
点击即可进入首页，首页为创建记录页面。

#### 添加新纪录
点击进入创建记录页面，也是首页。

#### 历史记录
点击进入历史记录页面，可以查看历史数据。历史记录包括几天内所有已打印的数据，支持修改和批量打印。
车牌号筛选搜索：

* 车牌号为空
  * 已过空：显示所有已过空数据（已排除已打印记录）
  * 未过空：显示所有未过空数据（已排除已打印记录）
  * 已打印：警告：车牌号未空且查询已打印，否则查询所有数据
* 车牌号不为空
  * 已过空：显示匹配到的已过空数据（已排除已打印记录）
  * 未过空：显示匹配到的未过空数据（已排除已打印记录）
  * 已打印：显示匹配到的已打印数据

#### 今日记录
今日记录页面展示当天的工作区，主要用于数据修改、打印等操作。

### 右半边

#### 入库数据汇总
点击进入入库数据汇总页面，可以查看所有入库数据的汇总信息。


#### 出库数据汇总
点击进入出库数据汇总页面，查看出库数据的汇总情况。

> **自定义搜索日期**
>
> **输入示例：** 25年2月5日
>
> **输入规则：**
>
> - **年份：**
>  - 如果年份为空，自动补充为两位数（个位数补零）。
>  - 如果年份为三位数，提示警告。
>
> - **月份：**
>   - 如果开始月份为空，自动设为1月。
>   - 如果结束月份为空，自动设为12月。
>
> - **日期：**
>  - 如果开始日期为空，自动设为开始月份的第一天。
>  - 如果结束日期为空，自动设为结束月份的最后一天。

## 页面

### 新建记录
- 最近一次输入的记录会在约5小时内保留，刷新页面后会自动填充到表单中。
- 毛重、皮重、原发字段忽略小数点，直接键入数值，按下回车键后会自动补齐小数点。
- 若选择煤种为“其他”，会显示新煤种输入框，输入后该煤种将被添加到下拉菜单中。
- 点击左侧煤种按钮可以进入删除煤种页面，支持多选框选择并删除煤种。

### 修改记录
- 点击打印时间设置为当前时间
- 已过空后建议设置打印时间

### 历史记录 && 今日记录
- 多选框可以批量打印，选择多选框后按下第一行的按钮即可
- 批量打印后打印机工作期间不建议继续操作网页，以免造成数据丢失，请等待打印结束后再操作
