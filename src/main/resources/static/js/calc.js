
let monthNames;
let dateValues;
let getDaysInMonth = function(month, year) {
  return new Date(year, month+1, 0).getDate();
};
let correction = (dayX, monthX, yearX) => {
  if(dateValues[dayX] > getDaysInMonth(dateValues[monthX], dateValues[yearX])) {
    dateValues[dayX] = getDaysInMonth(dateValues[monthX], dateValues[yearX]);
    dateValues.refreshDate(dayX);
  }
}

document.addEventListener('DOMContentLoaded', () => {
  let docs = [{day1: day1}, {day2: day2}, {month1: month1}, {month2: month2}, {year1: year1}, {year2: year2}];
  let docsConstr = Object.keys(docs);
  let date = new Date();
  monthNames = ["January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  ];

  function DefValues(...docsConstr) {
    this.month1 = date.getMonth();
    this.month2 = date.getMonth();
    this.day1 = date.getDate();
    this.day2 = date.getDate(); 
    this.year1 = date.getFullYear();
    this.year2 = date.getFullYear()
  }
  DefValues.prototype.refreshDate = function(id) {
    let doc = document.querySelector('#'+id);
    if (id !== 'month1' && id !== 'month2') {
      return doc.innerText = dateValues[id]
    }
    doc.innerText = monthNames[dateValues[id]];
  }

  DefValues.prototype.dateIncr = function(format, dayX, monthX, yearX) {
    let monthFormat = (dayX, monthX, yearX) => {
      if (dateValues[monthX] < 11) {
        ++dateValues[monthX];
        correction(dayX, monthX, yearX);
        dateValues.refreshDate(monthX);
      } else {
        dateValues[monthX] = 0;
        dateValues.refreshDate(monthX);
        ++dateValues[yearX];
        dateValues.refreshDate(yearX);
      }
    }
    switch(format) {
      case('dayFormat'):
        if (dateValues[dayX] < getDaysInMonth(dateValues[monthX], dateValues[yearX])) {
          ++dateValues[dayX];
        } else {
          dateValues[dayX] = 1;
          dateValues.refreshDate(dayX);
          monthFormat(dayX, monthX, yearX);
        }
        break;
      case('monthFormat'):
        monthFormat(dayX, monthX, yearX);
        break;
    }
  }

  DefValues.prototype.dateDecr = function(format, dayX, monthX, yearX) {
    let monthFormat = (dayX, monthX, yearX) => {
      if (dateValues[monthX] > 0) {
        --dateValues[monthX];
        correction(dayX, monthX, yearX);
        dateValues.refreshDate(monthX);
      } else {
        dateValues[monthX] = 11;
        dateValues.refreshDate(monthX);
        --dateValues[yearX];
        dateValues.refreshDate(yearX);
      }
    }
    switch(format) {
      case ('dayFormat'):
        if (dateValues[dayX] > 1) {
          --dateValues[dayX];
        } else {
          dateValues[dayX] = getDaysInMonth(dateValues[monthX], dateValues[yearX]);
          dateValues.refreshDate(dayX);
          monthFormat(dayX, monthX, yearX);
        }
        break;
      case ('monthFormat'):
        monthFormat(dayX, monthX, yearX);
        break;
    }
  }

  dateValues = new DefValues();
  docs.forEach((doc) => {
    let key = Object.keys(doc)[0];
    dateValues.refreshDate(key);
  })

});

let decrement = () => {
  let { id, innerText } = event.target.nextElementSibling;
  switch(id) {
    case ('day1'):
      dateValues.dateDecr('dayFormat', 'day1', 'month1', 'year1');
      break;
    case ('day2'):
      dateValues.dateDecr('dayFormat', 'day2', 'month2', 'year2');
      break;
    case ('month1'):
      dateValues.dateDecr('monthFormat', 'day1', 'month1', 'year1');
      break;
    case ('month2'):
      dateValues.dateDecr('monthFormat', 'day2', 'month2', 'year2');
      break;
    case ('year1'):
      dateValues.year1--
      break;
    case ('year2'):
      dateValues.year2--
      break;
  }
  dateValues.refreshDate(id);
}

let increment = () => {
    let { id, innerText } = event.target.previousElementSibling;
    switch (id) {
      case ('day1'):
        dateValues.dateIncr('dayFormat', 'day1', 'month1', 'year1');
        break;
      case ('day2'):
        dateValues.dateIncr('dayFormat', 'day2', 'month2', 'year2');
        break;
      case ('month1'):
        dateValues.dateIncr('monthFormat', 'day1', 'month1', 'year1');
        break;
      case ('month2'):
        dateValues.dateIncr('monthFormat', 'day2', 'month2', 'year2');
        break;
      case ('year1'):
        dateValues.year1++
        break;
      case ('year2'):
        dateValues.year2++
        break;
    }
    dateValues.refreshDate(id);
  }

let changeColor = () => {
  let mainColors = document.querySelectorAll('.normal-colors');
  let inverseColors = document.querySelectorAll('.inverse-colors');
  let fontColors = document.querySelectorAll('.just-font-color');
  let bgColor = document.querySelector('.just-bg-color');
  let container = document.querySelector('.calculator-container');
  let switcher = document.querySelector('#change-color-icon');
  switcher.style.color === '' ? switcher.style.color = 'gold' : '';
  switch(switcher.style.color) {
    case ('gold'):
      inverseColors.forEach((el) => {
        el.style = "background-color: navy; color: gold;";
      });
      mainColors.forEach((el) => {
        el.style = "background-color: gold; color: navy;";
      });
      switcher.style.color = 'darkgrey';
      fontColors.forEach((el) => {
        el.style = "color: navy;";
      });
      bgColor.style = "background-color: beige";
      break;
    case('darkgrey'):
      inverseColors.forEach((el) => {
        el.style = "background-color: #333; color: white;";
      });
      mainColors.forEach((el) => {
        el.style = "background-color: white; color: #333;";
      });
      switcher.style.color = 'sandybrown ';
      fontColors.forEach((el) => {
        el.style = "color: #333;";
      });
      bgColor.style = "background-color: white";
      break;
    case('sandybrown'):
      inverseColors.forEach((el) => {
        el.style = "background-color: #3d0000; color: sandybrown;";
      });
      mainColors.forEach((el) => {
        el.style = "background-color: sandybrown; color: #3d0000;";
      });
      fontColors.forEach((el) => {
        el.style = "color: #3d0000;";
      });
      switcher.style.color = 'brown';
      bgColor.style = "background-color: #dda777";
      break;
    case('brown'):
    inverseColors.forEach((el) => {
        el.style = "background-color: brown; color: white;";
      });
      mainColors.forEach((el) => {
        el.style = "background-color: white; color: brown;";
      });
      fontColors.forEach((el) => {
        el.style = "color: brown;";
      });
      switcher.style.color = 'gold';
      bgColor.style = "background-color: white";
      break;
  } 
}

let count = () => {
  fetch('/calculator', {
    method: 'POST',  
    body: JSON.stringify(dateValues),
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then((res) => res.text())
  .then((text) => {
    let resField = document.querySelector('#resField');
    resField.innerText = text;
  })    
}

