var apiClient = (function () {
  const endPoints = '';
  const whitepinEndPoints = "http://121.141.157.230:51041";
  const path = {
    whitepin: {
      connect: "/whitepin/connect"
    },
    login   : "/auth/login",
    user    : {
      info : "/users",
      score: "/users/{userId}/score"
    },
    products: {
      list  : "/products",
      detail: "/products"
    },
    trades  : {
      info      : "/trades/{userId}/condition/{type}",
      close     : "/trades/close/{tradeId}/users/{userId}",
      create    : "/trades/create",
      evaluation: "/trades/evaluation"
    }
  };

  var request = function (path, method, data, successHandler, errorHandler) {
    console.log('url::',endPoints + path);
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
      , error    : function (jqxhr) {
        errorHandler(jqxhr);
      }
    });
  };

  return {
    endPoints        : endPoints,
    whitepinEndPoints: whitepinEndPoints,
    path             : path,
    request          : request
  };
})();

var handlebarsManager = (function () {
  var printTemplate = function (data, target, templateObject, type, prefixHtml, suffixHtml, clear) {
    if (clear) {
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

// copy from https://stackoverflow.com/questions/8853396/logical-operator-in-a-handlebars-js-if-conditional
Handlebars.registerHelper('ifCond', function (v1, operator, v2, options) {
  switch (operator) {
    case '==':
      return (v1 == v2) ? options.fn(this) : options.inverse(this);
    case '===':
      return (v1 === v2) ? options.fn(this) : options.inverse(this);
    case '!=':
      return (v1 != v2) ? options.fn(this) : options.inverse(this);
    case '!==':
      return (v1 !== v2) ? options.fn(this) : options.inverse(this);
    case '<':
      return (v1 < v2) ? options.fn(this) : options.inverse(this);
    case '<=':
      return (v1 <= v2) ? options.fn(this) : options.inverse(this);
    case '>':
      return (v1 > v2) ? options.fn(this) : options.inverse(this);
    case '>=':
      return (v1 >= v2) ? options.fn(this) : options.inverse(this);
    case '&&':
      return (v1 && v2) ? options.fn(this) : options.inverse(this);
    case '||':
      return (v1 || v2) ? options.fn(this) : options.inverse(this);
    default:
      return options.inverse(this);
  }
});

var sessionManager = (function () {
    var setSession = function (key, value) {
        sessionStorage.setItem(key, JSON.stringify(value));
    }
    
    var getSession = function (key) {
        return JSON.parse(sessionStorage.getItem(key));
    }
    
    return {
        setSession       : setSession,
        getSession       : getSession
      };
})();


var accountManager = (function () {
  const loginInfoKey = 'loginInfo';
  const redirectKey = 'redirectPath';
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
      data       : JSON.stringify({email: email, password: password}),
      cache      : false,
      processData: false,
      success    : function (response) {
        console.log(response);
        sessionStorage.setItem(loginInfoKey, JSON.stringify(response));

        let redirectPath = sessionStorage.getItem(redirectKey);

        if (typeof redirectPath == "undefined" || redirectPath == null) {
          redirectPath = '/';
        } else {
          sessionStorage.removeItem(redirectKey);
        }

        self.location = redirectPath;
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
   * redirect path 추가
   */
  var putRedirectPath = function (path) {
    if (!validator.isEmpty(path)) {
      sessionStorage.setItem(redirectKey, path);
    }
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
    putRedirectPath    : putRedirectPath,
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
