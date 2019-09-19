var apiClient = (function () {
  const endPoints = "http://localhost:9090";
  const path = {
    whitepin      : {
      connect : "/whitepin/connect"
    },
    login         : "/auth/login",
    user          : {
      info : "/users"
    }
  };

  var request = function (path, method, data, successHandler, errorHandler) {

    $.ajax({
      url        : endPoints + path,
      headers    : {
        dataType: 'json'
      },
      contentType: "application/json",
      type       : method,
      data       : data,
      cache      : false,
      processData: false,
      success    : function (response) {
        successHandler(response);
      }
      , error   : function (jqxhr) {
        errorHandler(jqxhr);
      }
    });
  };

  return {
    endPoints: endPoints,
    path     : path,
    request  : request
  };
})();

var handlebarsManager = (function () {
  var printTemplate = function (data, target, templateObject, type, prefixHtml, suffixHtml, clear) {
    if (clear) {
      console.log('clear target..');
      target.empty();
    }

    var template = Handlebars.compile(templateObject.html());
    var rendered = "";
    if (prefixHtml) {
      rendered += prefixHtml;
    }

    rendered += template(data);

    if (suffixHtml) {
      rendered += suffixHtml;
    }

    if (type === "html") {
      target.html(rendered);
    } else if (type === "append") {
      target.append(rendered);
    } else {
      console.error("invalid print template type : ", type);
    }
  };

  return {
    printTemplate: printTemplate
  };
})();

var accountManager = (function () {
  const loginInfoKey = 'loginInfo';
  /**
   * login 정보 조회
   */
  var getLoginInfo = function () {
    return JSON.parse(sessionStorage.getItem(loginInfoKey));
  };

  /**
   * 로그인 체크
   */
  var isSignedIn = function () {
    var loginInfo = sessionStorage.getItem(loginInfoKey);
    return typeof loginInfo != "undefined" && loginInfo != null;
  };

  var requestSignIn = function (email, password) {
    $.ajax({
      url        : apiClient.endPoints + apiClient.path.login,
      headers    : {
        dataType: 'json'
      },
      contentType: "application/json",
      type       : 'POST',
      data       : JSON.stringify({email : email, password : password}),
      cache      : false,
      processData: false,
      success    : function (response) {
        console.log(response);
        sessionStorage.setItem(loginInfoKey, JSON.stringify(response));

        self.location = '/';
      }, error   : function (jqxhr) {
        alert('올바르지 않은 ID/PASSWORD 입니다.');
        console.log(jqxhr);
      }
    });
  };

  /**
   * 로그아웃
   */
  var requestSignOut = function () {
    sessionStorage.removeItem(loginInfoKey);
    self.location = '/';
  };

  /**
   * 상단 로그인 템플릿 갱신
   */
  var updateLoginTemplate = function () {
    var $target = $('#loginTemplateDiv');
    var $templateObj = $('#loginTemplate');

    var loginInfo = sessionStorage.getItem(loginInfoKey);
    console.log('validator.isEmpty(loginInfo):', validator.isEmpty(loginInfo));
    console.log('loginInfo != \'null\':', loginInfo != 'null');
    console.log('loginInfo != null:', loginInfo != null);
    var isSignedIn = !validator.isEmpty(loginInfo) && loginInfo != 'null' && loginInfo != null;

    var data = {
      "isSignedIn": isSignedIn
    };

    console.log('check login info:', loginInfo, '\n', data);
    handlebarsManager.printTemplate(data, $target, $templateObj, 'html',
      null, null, true);
  };

  return {
    getLoginInfo       : getLoginInfo,
    isSignedIn         : isSignedIn,
    requestSignIn      : requestSignIn,
    requestSignOut     : requestSignOut,
    updateLoginTemplate: updateLoginTemplate
  };
})();

var validator = (function () {
  var emailRegex = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  var cellphoneRegex = /^[0-9]{3}[-]+[0-9]{3,4}[-]+[0-9]{4}$/;

  var isEmpty = function (input) {
    if (typeof input === "undefined" || input === '') {
      return true;
    }

    return false;
  };

  var isValidEmail = function (input) {
    return emailRegex.test(String(input).toLowerCase());
  };

  var isValidPassword = function (input) {
    if (isEmpty(input)) {
      return false;
    }

    return input.length >= 1;
  };

  var isValidCellPhone = function (input) {
    if (isEmpty(input)) {
      return false;
    }

    return cellphoneRegex.test(String(input));
  };

  return {
    isEmpty         : isEmpty,
    isValidEmail    : isValidEmail,
    isValidPassword : isValidPassword,
    isValidCellPhone: isValidCellPhone
  };
})();
