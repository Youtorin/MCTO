import { Message } from "element-ui";
export const getfilters = (searchId) => {
  var field;
  var value;
  var operator;
  var dataType;
  var conditions = [];
  var OpraType;
  var SearchArea = document.getElementById(searchId);
  var items = [];
  if (SearchArea !== null) {
    items = SearchArea.querySelectorAll("[name='search']");
  }
  items.forEach(function (item) {
    dataType = item.getAttribute("data-type");
    var type = item.getAttribute("type");
    field = item.getAttribute("data-field");
    operator = item.getAttribute("data-operator") || 5;
    OpraType = item.getAttribute("data-opraType");
    if (type !== null && type !== "select") {
      value = item.value;
    } else {
      value = item.getAttribute("data-value");
    }
    if (field.trim().length > 0) {
      if (value.trim().length > 0 && dataType === "DateTime") {
        var regEx = new RegExp("\\-", "gi");
        value = value.replace(regEx, "/");

        var myDate = new Date(Date.parse(value));

        if (
          myDate.getHours() === 0 &&
          myDate.getMinutes() === 0 &&
          myDate.getSeconds() === 0
        ) {
          if (operator === 2 || operator === 4) {
            // < && <=
            myDate.setHours(23, 59, 59, 999);
          } else if (operator === 1 || operator === 3) {
            // > && >=
            myDate.setHours(0, 0, 0, 0);
          }
        }
        value = myDate.format("yyyy-MM-dd hh:mm:ss");
      }
      if (value.trim().length > 0) {
        conditions.push({
          Name: field,
          Value: value,
          Filter: operator,
          DbType: -1,
          OpraType: OpraType,
        });
      }
    }
  });
  return conditions;
};

/** **弹出提示****/

export const messageShow = (type, title) => {
  Message({
    type: type,
    message: title,
    duration: 2000,
  });
};

/*
去除左边的空格
*/

export const LTrim = (str) => {
  var whitespace = String(" \t\n\r");
  var s = String(str);
  if (whitespace.indexOf(s.charAt(0)) !== -1) {
    var j = 0;
    var i = s.length;
    while (j < i && whitespace.indexOf(s.charAt(j)) !== -1) {
      j++;
    }
    s = s.substring(j, i);
  }
  return s;
};

/*
去除右边的空格
*/

export const RTrim = (str) => {
  var whitespace = String(" \t\n\r");
  var s = String(str);

  if (whitespace.indexOf(s.charAt(s.length - 1)) !== -1) {
    var i = s.length - 1;
    while (i >= 0 && whitespace.indexOf(s.charAt(i)) !== -1) {
      i--;
    }
    s = s.substring(0, i + 1);
  }
  return s;
};

/*
去除空格
*/
export const Trim = (str) => {
  return RTrim(this.LTrim(str));
};

// 日期计算
export const dateAdd = (interval, number, date) => {
  switch (interval) {
    case "y": {
      // 年
      date.setFullYear(date.getFullYear() + number);

      break;
    }
    case "m": {
      // 月
      date.setMonth(date.getMonth() + number);
      break;
    }
    case "d": {
      // 日
      date.setDate(date.getDate() + number);
      break;
    }
    default: {
      date.setDate(date.getDate() + number);
      break;
    }
  }
  return date;
};

// 以指定格式串返回当前日期串
export const dateToString = (date, formatStr) => {
  var day = date.getDate();
  var month = date.getMonth() + 1;
  var year = date.getFullYear();
  month = month < 10 ? "0" + month : month;
  day = day < 10 ? "0" + day : day;
  return year + formatStr + month + formatStr + day;
};

// 根据日期字符串转换成日期对象
export const dateParse = (dateStr) => {
  if (dateStr === "") {
    return null;
  }
  // 根据日期字符串转换成2005/3/4格式
  var regEx = new RegExp("\\-", "gi");
  dateStr = dateStr.replace(regEx, "/");
  var milliseconds = Date.parse(dateStr);
  var dependedDate = new Date();
  dependedDate.setTime(milliseconds);
  return dependedDate;
};

function dispatchEventStroage() {
  const signSetItem = localStorage.setItem;
  localStorage.setItem = function (key, val) {
    let setEvent = new Event("setItemEvent");
    setEvent.key = key;
    setEvent.newValue = val;
    window.dispatchEvent(setEvent);
    signSetItem.apply(this, arguments);
  };
}

export const currentUser = {
  id: JSON.parse(localStorage.getItem("TOKEN")).id,
  username: JSON.parse(localStorage.getItem("TOKEN")).username,
};

export default dispatchEventStroage;

export const pagination = { rows: 999999999, page: 1 };
