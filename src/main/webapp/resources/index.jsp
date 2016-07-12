<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8"/>
    <sec:csrfMetaTags/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>eCare mobile</title>

    <link rel="shortcut icon" href="/resources/img/favicon.ico" type="image/x-icon"/>

    <link rel="stylesheet" href="/resources/css/bootstrap.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-theme.css">
    <link rel="stylesheet" href="/resources/css/theme.css">
    <link rel="stylesheet" href="/resources/css/bootstrap-datepicker3.min.css">

    <link rel="import" href="/resources/public/home.html">
    <link rel="import" href="/resources/public/plan.html">
    <link rel="import" href="/resources/public/plans.html">
    <link rel="import" href="/resources/public/option.html">
    <link rel="import" href="/resources/public/options.html">
    <link rel="import" href="/resources/public/user.html">
    <link rel="import" href="/resources/public/users.html">
    <link rel="import" href="/resources/public/contract1.html">
    <link rel="import" href="/resources/public/contract2.html">
    <link rel="import" href="/resources/public/profile.html">
</head>

<body>
<div id="topnavcover" class="hidden">
    <nav class="navbar navbar-inverse navbar-fixed-top" id="topnav">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <div class="navbar-brand">eCare Mobile</div>
            </div>
            <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
                <ul class="nav navbar-nav" id="left_nav">
                    <li id="nav_home" onclick="loadPage('home', 'home')"><a href="#">Home</a></li>
                    <li id="nav_plans" onclick="loadPage('plans', 'plans')"><a href="#">Plans</a></li>
                    <li id="nav_options" onclick="loadPage('options', 'options')"><a href="#">Options</a></li>
                    <li id="nav_users" onclick="loadPage('users', 'users')"><a href="#">Customers</a></li>
                    <li id="nav_profile" onclick="loadPage('profile', 'profile')"><a href="#"
                                                                                     onfocus="this.blur()"><span
                            class="glyphicon glyphicon-user" aria-hidden="true"/></a></li>
                    <li id="nav_basket" onclick="loadBasket()"><a href="#" onfocus="this.blur()"><span
                            class="glyphicon glyphicon-shopping-cart" aria-hidden="true"/></a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right" id="right_nav">
                    <li id="nav_login"><a href="#" data-toggle="modal" data-target="#loginModal" onfocus="this.blur()">Sign
                        in</a></li>
                    <p id="nav_username" class="navbar-text navbar-right">
                        Signed in as
                        <a href="#" id="nav_fullname" class="navbar-link" onclick="loadPage('profile', 'profile')"></a>
                        [<a href="#" class="navbar-link"
                            onclick="logout()">Sign out</a>]
                    </p>
                </ul>
            </div>
        </div>
    </nav>
</div>
<div class="modal fade" id="loginModal" role="dialog" data-backdrop="static">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body">
                <form class="form-signin" onsubmit="return submitModal()">
                    <h2 class="form-signin-heading">Please sign in</h2>
                    <label for="inputEmail" class="sr-only">Email address</label>
                    <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required=""
                           autofocus="">
                    <label for="inputPassword" class="sr-only">Password</label>
                    <input type="password" id="inputPassword" class="form-control" placeholder="Password" required="">
                    <button class="btn btn-lg btn-primary btn-block" type="submit" id="loginSubmit"
                            data-toggle="popover" data-placement="right" title="Popover title"
                            data-content="Popover on right.">Sign in
                    </button>
                    <button class="btn btn-lg btn-default btn-block" type="button" id="loginCancel"
                            onclick="closeModal()">Cancel
                    </button>
                    <div class="alert alert-info" id="loginError" role="alert" hidden="hidden">E-mail and password pair
                        doesn't exist
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div id="page"><!-- pages content place --></div>
<div class="navbar-fixed-bottom row-fluid navbar-inverse">
    <div class="navbar-inner">
        <div class="container">
            <p class="text-muted text-center">T-Systems - <a href="http://vk.com/daddyksen" id="nav_fullname"
                                                             class="navbar-link" target="_blank">Andrei Makarevich</a> -
                2016</p>
        </div>
    </div>
</div>
<script src="/resources/js/jquery.min.js" type="text/javascript"></script>
<script src="/resources/js/jquery.cookie.js" type="text/javascript"></script>
<script src="/resources/js/jquery.rest.min.js" type="text/javascript"></script>
<script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/resources/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
<script>

    /* Globals */
    const BASKET_CUSTOMER = "basketCustomer";
    const BASKET_CONTRACT = "basketContract";
    const BASKET_NUMBER = "basketNumber";
    const BASKET_PLAN = "basketPlan";
    const BASKET_OPTIONS = "basketOptions";
    const BASKET_UP = "basketUp";

    /* REST-client */
    var client;

    $(document).ready(function () {

        client = new $.RestClient('/');

        client.add('customer', {
            stripTrailingSlash: true,
            stringifyData: true,
            ajax: {async: true}
        });
        client.add('plan', {
            stripTrailingSlash: true,
            stringifyData: true,
            ajax: {async: true}
        });
        client.add('option', {
            stripTrailingSlash: true,
            stringifyData: true,
            ajax: {async: true}
        });
        client.add('contract', {
            stripTrailingSlash: true,
            stringifyData: true,
            ajax: {async: true}
        });

        if ($.cookie("ecare.usrename") == null) {
            var cpage = $.cookie("currentpage");
            if (cpage != "home" && cpage != "plans" && cpage != "options") {
                $.cookie("currentpage", "home", {expires: 1, path: '/'});
            }
        }

        loadRights();
        startUp();
    });

    function loadRights() {
        if ($.cookie("ecare.username") == null) {
            $("#userData").empty();
            $("#nav_username").hide();
            $("#nav_logout").hide();
            $("#nav_users").hide();
            $("#nav_basket").hide();
            $("#nav_profile").hide();
            $("#nav_login").show();
        } else {
            $("#userData").empty();
            $("#nav_login").hide();
            $("#nav_users").hide();
            $("#nav_basket").show();
            $("#nav_profile").show();

            if ($.cookie("ecare.admin") != null || $.cookie("ecare.manager") != null) {
                $.cookie('ecare.suser', "true", {expires: 1, path: '/'});
            }
            if ($.cookie("ecare.suser") != null) {
                $("#nav_users").show();
            }
            $("#nav_fullname").empty();
            $("#nav_fullname").append($.cookie("ecare.firstname") + " " + $.cookie("ecare.lastname"));
            $("#nav_username").show();
        }
        $("#topnavcover").hide().removeClass('hidden').fadeIn();
    }

    function startUp() {
        var currentpage = $.cookie("currentpage");
        if (currentpage == null) currentpage = "home";
        var currentpagen = currentpage;

        if (currentpage == "option") currentpagen = "options";
        if (currentpage == "plan") currentpagen = "plans";
        if (currentpage == "user") currentpagen = "users";
        if (currentpage == "contract1") currentpagen = "basket";
        if (currentpage == "contract2") currentpagen = "basket";
        if (currentpage == "profile") currentpagen = "profile";

        loadPage(currentpage, currentpagen);

        $(".navbar-brand").animate({
            opacity: 1,
        }, 2000, function () {
            $(".navbar-brand").animate({
                opacity: 0.0,
            }, 1000, function () {
                $(".navbar-brand").css('color', '#e20074');
                $(".navbar-brand").animate({
                    opacity: 1,
                }, 200);
            });
        });
    }

    function loadPage(page, pagen) {

        $.cookie("currentpage", page, {expires: 1, path: '/'});

        /* Loading new content page */
        $("#page").empty();
        var link = document.querySelector('link[href$=\"' + page + '\.html"]');
        var content = link.import.querySelector("#" + page);
        document.querySelector("#page").appendChild(content.cloneNode(true));
        $("#navbar li").removeClass("active");
        document.querySelector("#nav_" + pagen).setAttribute("class", "active");
        /* end */

        if (page == "users") {
            createUsersPagination();
        }
        if (page == "user") {
            getCustomer();
        }
        if (page == "plans") {
            getAllPlans();
        }
        if (page == "plan") {
            getPlan();
        }
        if (page == "options") {
            getAllOptions();
        }
        if (page == "option") {
            getOption();
        }
        if (page == "contract1") {
            getContract1();
        }
        if (page == "contract2") {
            getContract2();
        }
        if (page == "profile") {
            loadProfile();
        }
    }

    function loadBasket() {
        if (getLs(BASKET_UP) != null) {
            if (getLs(BASKET_PLAN) == null) {
                loadPage("contract1", "basket");
            } else {
                loadPage("contract2", "basket");
            }
        }
    }

    function closeModal() {
        $("#loginModal").modal("hide");
        $("#inputEmail").val("");
        $("#inputPassword").val("");
        $("#loginError").hide();
    }

    function submitModal() {
        var token = $("meta[name='_csrf']").attr("content");
        if (!token) token = "";
        var header = $("meta[name='_csrf_header']").attr("content");
        if (!header) header = "";

        var ds = {
            username: $("#inputEmail").val(),
            password: $("#inputPassword").val()
        };
        $.ajax({
            type: 'POST',
            url: '/authenticate',
            data: ds,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "X-Login-Ajax-call": 'true'
            },
            beforeSend: function (request) {
                request.setRequestHeader(header, token);
            },
            success: function (response) {
                if (response == 'ok') {
                    location.reload(false);
                }
            },
            error: function () {
                $("#loginError").fadeOut().fadeIn("slow");
                $("#inputPassword").focus();
                $("#inputPassword").val("");
            }
        });
        return false;
    }

    function logout() {
        var token = $("meta[name='_csrf']").attr("content");
        if (!token) token = "";
        var header = $("meta[name='_csrf_header']").attr("content");
        if (!header) header = "";
        $.ajax({
            type: "POST",
            beforeSend: function (request) {
                request.setRequestHeader(header, token);
            },
            url: "/logout",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "X-Login-Ajax-call": 'true'
            },
            success: function (msg) {
                clearBasket();
                location.reload(false);
            },
            error: function () {
                alert("FAILURE !");
            }
        });
        return false;
    }

    function getPrettyDate(longdate) {
        var date = new Date(longdate);
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        day = day < 10 ? "0" + day : day;
        month = month < 10 ? "0" + month : month;
        return month + "/" + day + "/" + year;
    }

    function dateFromPretty(prettydate) {
        var date = new Date();
        var strings = prettydate.split("/");
        date.setDate(strings[1]);
        date.setMonth(strings[0] - 1);
        date.setFullYear(strings[2]);
        return date;
    }

    function setLs(name, value) {
        window.localStorage.setItem(name, value);
    }

    function getLs(name) {
        return window.localStorage.getItem(name);
    }

    function removeLs(name) {
        window.localStorage.removeItem(name);
    }

    function setLsArray(name, array) {
        window.localStorage.setItem(name, JSON.stringify(array));
    }

    function getLsArray(name) {
        var array = JSON.parse(window.localStorage.getItem(name));
        return array == null ? [] : array;
    }

    function clearBasket() {
        removeLs(BASKET_CUSTOMER);
        removeLs(BASKET_CONTRACT);
        removeLs(BASKET_NUMBER);
        removeLs(BASKET_PLAN);
        removeLs(BASKET_OPTIONS);
        removeLs(BASKET_UP);
    }
</script>
<script src="/resources/js/validator.js" type="text/javascript"></script>
</body>
</html>