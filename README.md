# 过磅数据管理系统 V2.0

## 更新日志

### 2.2
### 新增功能
- 增加出入库类型：返仓

### 修复问题
- 修复了 V2.1 批量打印不更新磅单编号
- 修复了一些其他bug

### 2.1
### 新增功能
- 每条记录增加备注属性

### 重构逻辑
- 使用dtpweb框架重构打印业务

---

### 2.0
### 新增功能
- 管理员用户可管理用户列表。
- 删除用户时需验证管理员密码。

### 修复问题
- 修复司磅员姓名显示错误。
- 修复部分页面的显示问题。

---

## 使用指南

### 文件结构
推荐安装路径：`D:\Program Files\过磅数据管理系统\`
- `README.md`：说明文档。
- `application.properties`：核心配置文件，请勿随意修改。
- `admin.properties`：管理员账户配置文件。编辑`admin.username`和`admin.password`即可更新。
- `*.cmd` / `*.bat`：服务器启动脚本，双击运行。
- `WeighTrack-2.*.jar`：主程序文件。
- `过磅明细.xlsx`：系统导出的Excel文件。
- `/logs`：日志文件存储目录。

---

### 导航栏功能说明

#### 左侧
- **鸿聚一号煤场出入库明细**  
  首页，创建记录页面。
- **添加新纪录**  
  进入创建记录页面（同首页）。
- **历史记录**  
  查看和管理历史数据，支持筛选、修改、批量打印：
  - 筛选项：车牌号为空/已过空/未过空/已打印。
  - 车牌号不为空时，筛选匹配的数据。
- **今日记录**  
  显示当天工作区，用于数据修改和打印。

#### 右侧
- **入库数据汇总**  
  查看所有入库数据的汇总信息。
- **出库数据汇总**  
  查看所有出库数据的汇总信息。

> **日期自定义搜索规则：**
> - **年份**  
>   - 留空：补充两位数年份（个位数补零）。  
>   - 输入三位数：提示警告。
> - **月份**  
>   - 开始月份留空：设为1月。  
>   - 结束月份留空：设为12月。
> - **日期**  
>   - 开始日期留空：设为首日。  
>   - 结束日期留空：设为当月最后一天。

---

## 页面功能详解

### 新建记录
- 最近一次记录在约5小时内会自动保留并填充。
- 数值输入自动处理小数点，无需手动输入。
- 煤种选择“其他”时，可输入新煤种，添加到下拉菜单中。
- 可通过左侧按钮进入煤种管理页面，支持批量删除煤种。

### 修改记录
- 点击打印时间按钮自动设置为当前时间。
- 已过空后建议设置打印时间。

### 历史记录 & 今日记录
- 支持批量打印：勾选多选框后点击操作按钮。
- 打印时请勿操作网页，待打印结束后再进行其他操作。
