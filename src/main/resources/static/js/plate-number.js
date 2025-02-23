const provinces = ['京', '津', '沪', '渝', '冀', '豫', '云', '辽', '黑',
    '湘', '皖', '鲁', '新', '苏', '浙', '赣', '鄂', '桂',
    '甘', '晋', '蒙', '陕', '吉', '闽', '贵', '粤', '青',
    '藏', '川', '宁', '琼'];

const licensePlate = document.getElementById('plate-number-licensePlate');
const provinceKeyboard = document.getElementById('plate-number-province-keyboard');
const mainKeyboard = document.getElementById('plate-number-main-keyboard');

// 生成省份键盘
provinces.forEach(province => {
    const btn = document.createElement('div');
    btn.className = 'plate-number-button plate-number-province-btn';
    btn.textContent = province;
    btn.addEventListener('click', () => {
        const current = licensePlate.value;
        if (current.length === 0) {
            licensePlate.value = province;
        } else {
            licensePlate.value = province + current.slice(1);
        }
    });
    provinceKeyboard.appendChild(btn);
});

// 生成主键盘（字母+数字）
// 生成字母（排除I/O）
for (let i = 65; i <= 90; i++) {
    const char = String.fromCharCode(i);
    if (char === 'I' || char === 'O') continue;

    const btn = document.createElement('div');
    btn.className = 'plate-number-button';
    btn.textContent = char;
    btn.addEventListener('click', () => {
        if (licensePlate.value.length > 0 && licensePlate.value.length < 8) {
            licensePlate.value += char;
        }
    });
    mainKeyboard.appendChild(btn);
}

// 生成数字
for (let i = 0; i <= 9; i++) {
    const btn = document.createElement('div');
    btn.className = 'plate-number-button';
    btn.textContent = i;
    btn.addEventListener('click', () => {
        if (licensePlate.value.length > 0 && licensePlate.value.length < 8) {
            licensePlate.value += i;
        }
    });
    mainKeyboard.appendChild(btn);
}

// 添加删除按钮
const deleteBtn = document.createElement('div');
deleteBtn.className = 'plate-number-button plate-number-delete-btn';
deleteBtn.textContent = '⌫';
deleteBtn.addEventListener('click', () => {
    licensePlate.value = licensePlate.value.slice(0, -1);
});
mainKeyboard.appendChild(deleteBtn);

// 输入限制
licensePlate.addEventListener('input', () => {
    licensePlate.value = licensePlate.value
        .toUpperCase()
        .replace(/[^京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼A-HJ-NP-Z0-9]/g, '');
});
$('.plate-number-keyboard-container').hide();
$('#plate-number-licensePlate').on('click', function () {
    let flag = $('#plate-number-flag-show').text();
    if (flag === '1') {
        $('.plate-number-keyboard-container').hide();
        $('#plate-number-flag-show').text('0');
        console.log('虚拟键盘隐藏');
    } else if (flag === '0') {
        $('.plate-number-keyboard-container').show();
        $('#plate-number-flag-show').text('1');
        console.log('虚拟键盘显示');
    }
})
