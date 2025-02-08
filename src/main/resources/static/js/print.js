/* eslint-disable @typescript-eslint/no-unused-vars */

/**
 * @type {import("/dtpweb").DTPWeb}
 */
var api = null;
var dataInfo = {
    printerDpi: 203,
    printerWidth: 384,
};
window.onload = function () {
    // 检测打印接口是否可用
    dtpweb.checkServer(function (val) {
        api = val;
        if (val) {
            updatePrinterList();
        } else {
            api = undefined;
            alert("未检测到打印机插件！");
        }
    });
};


/**
 * 更新打印机列表。
 */
function updatePrinterList() {
    var printerElements = document.getElementById("select-printlist");

    // 为了避免打印的时候，数据打印不完全的问题，js接口中采用的是ajax同步请求方式；
    // 为了避免服务未打开的时候，调用接口时出现假死状态，在合适的地方调用皆苦前最好先检测下url是否可用；
    if (api) {
        var printers = api.getPrinters({onlyLocal: false});
        // 先清空当前打印机列表
        printerElements.innerHTML = "";
        // 重新添加打印机列表
        if (printers instanceof Array && printers.length > 0) {
            for (var i = 0; i < printers.length; i++) {
                var item = printers[i];
                var shownName = item.deviceName || item.name;
                // 如果检测到局域网内的其他打印机，则可以获取ip和hostname，如果是本地打印机，则参数中只有name属性，表示打印机名称；
                var name = api.isLocalPrinter(item) ? shownName : shownName + "@" + item.hostname;
                var value = api.isLocalPrinter(item) ? item.name : item.name + "@" + item.ip;
                printerElements.options.add(new Option(name, value));
            }
        } else {
            printerElements.options.add(new Option("未检测到打印机", ""));
        }
    }
}

/**
 * 获取当前选中的打印机；
 */
function getCurrPrinter() {
    var printerElement = document.getElementById("select-printlist");
    if (!printerElement.value) return {};

    var arr = printerElement.value.split("@");
    return {
        name: arr[0],
        ip: arr[1],
    };
}

/**
 * 打印表格
 */
function drawTableTest(printData) {
    // 纸张大小
    var labelWidth = 180;
    var labelHeight = 60;
    // 边距
    var margin = 6;
    // 标题位置, 居中
    var titleX = (labelWidth - margin * 2) / 2 - 20;
    var titleY = 0;
    // 第一行
    var firstRowX = margin;
    var firstRowY = 7.5;
    // 最后一行
    var lastRowX = margin;
    var lastRowY = 55;

    api.startPrintJob({
        width: labelWidth,
        height: labelHeight,
        // 打印方向
        orientation: 90,
        jobName: '',
        printer: getCurrPrinter(),
        action: 0x1000,
        printerDPI: dataInfo.printerDpi,
        printerWidth: dataInfo.printerWidth,
        callback: (res) => {
            if (!res) {
                console.log("打印机链接失败！");
                return;
            }

            api.drawText({
                text: '鸿聚一号煤场过磅单',
                x: titleX,
                y: titleY,
                fontHeight: 6,
                fontStyle: 1,
                horizontalAlignment: 0,
                verticalAlignment: 0,
            });
            api.drawText({
                text: '磅单编号：' + printData.poundID,
                x: firstRowX,
                y: firstRowY,
                fontHeight: 4,
                fontStyle: 1,
                horizontalAlignment: 0,
                verticalAlignment: 0,
            });
            api.drawText({
                text: '单位：吨',
                x: firstRowX + 60,
                y: firstRowY,
                fontHeight: 4,
                fontStyle: 1,
                horizontalAlignment: 0,
                verticalAlignment: 0,
            });
            api.drawText({
                text: '打印日期：' + printData.printDateTime,
                x: firstRowX + 100,
                y: firstRowY,
                fontHeight: 4,
                fontStyle: 1,
                horizontalAlignment: 0,
                verticalAlignment: 0,
            });
            api.drawTable({
                x: margin,
                y: margin + 10.5,
                width: labelWidth - margin * 2,
                height: labelHeight - margin * 2 - 15,
                columnCount: 4,  // 减少列数使布局更合理
                rowCount: 5,
                columnWidths: [0.2, 0.3, 0.25, 0.25],  // 调整后的列宽比例
                lineWidth: 0.4,
                fontHeight: 4.5,
                fontStyle: 1,
                tableRows: [
                    [
                        {type: "text", text: "车号"},
                        {type: "text", text: printData.plateNumber, columnSpan: 1},
                        {type: "text", text: "发货单位", columnSpan: 1},
                        {type: "text", text: printData.outputUnit, columnSpan: 1},
                    ],
                    [
                        {type: "text", text: "煤种"},
                        {type: "text", text: printData.coalType, columnSpan: 1},
                        {type: "text", text: "收货单位", columnSpan: 1},
                        {type: "text", text: printData.inputUnit, columnSpan: 1},
                    ],
                    [
                        {type: "text", text: "毛重"},
                        {type: "text", text: printData.grossWeight},
                        {type: "text", text: "皮重"},
                        {type: "text", text: printData.tare},
                    ],
                    [
                        {type: "text", text: "净重", columnSpan: 1},
                        {type: "text", text: printData.netWeight, columnSpan: 1},
                        {type: "text", text: "原发/矿发", columnSpan: 1},
                        {type: "text", text: printData.primaryWeight ? printData.primaryWeight : '', columnSpan: 1},
                    ],
                    [
                        {type: "text", text: "备注", columnSpan: 1},
                        {type: "text", text: printData.note ? printData.note : '', columnSpan: 3},
                    ]
                ],
            });
            api.drawText({
                text: '司磅员：' + printData.Weigher,
                x: lastRowX,
                y: lastRowY,
                fontHeight: 4,
                fontStyle: 1,
                horizontalAlignment: 0,
                verticalAlignment: 0,
            });
            api.drawText({
                text: '司机：',
                x: lastRowX + labelWidth / 2,
                y: lastRowY,
                fontHeight: 4,
                fontStyle: 1,
                horizontalAlignment: 0,
                verticalAlignment: 0,
            });
            api.commitJob({
                callback: (resp) => {
                    console.log(resp);
                },
            });
        },
    });
}

//#endregion
