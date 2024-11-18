
$(document).ready(function () {
    // 获取当前时间
    var now = new Date();


    $('#emptyload-time').datetimepicker({
        language: 'zh-CN', // 中文语言包
        autoclose: 1, // 选中时间后自动关闭
        format: 'hh:ii', // 时间格式，只显示小时和分钟
        startView: 'day', // 初始视图设置为小时选择界面
        minView: 'hour', // 最小显示单位为小时，选择到分钟
        minuteStep: 1, // 时间选择间隔为5分钟
    });
    $('#fullload-time').datetimepicker({
        language: 'zh-CN', // 中文语言包
        autoclose: 1, // 选中时间后自动关闭
        format: 'hh:ii', // 时间格式，只显示小时和分钟
        startView: 'day', // 初始视图设置为小时选择界面
        minView: 'hour', // 最小显示单位为小时，选择到分钟
        minuteStep: 1, // 时间选择间隔为5分钟
    });
});
