"function" == typeof define && define.cmd && define(function (require, exports, module) {
    function o(e) {
        var t = new RegExp("(^| )" + e + "=([^;]*)(;|$)"), i = document.cookie.match(t);
        return i ? unescape(i[2]) : null
    }

    function a() {
        var e = location.search, t = {};
        if (/\?/.test(e)) {
            for (var i = e.substring(e.indexOf("?") + 1).split("&"), o = 0, n = i.length; o < n; o++) {
                var r = i[o].split("=");
                t[r[0]] = r[1]
            }
            return t.source ? t.source : ""
        }
        return ""
    }

    function n(e) {
        var t = "";
        if ("object" == typeof (e = e || {})) {
            var i = [];
            for (var o in e) if (e.hasOwnProperty(o)) {
                var n = e[o] || "";
                i.push(encodeURIComponent(o) + "=" + encodeURIComponent(n))
            }
            t = i.join("&")
        } else t = e.toString();
        return t
    }

    function c(e, t, i) {
        if (i = i || {}, t = (t = t || "POST").toUpperCase(), -1 < ["POST", "GET"].indexOf(t)) {
            if ("GET" == t) return r(e + "?" + n(i), t);
            if ("POST" == t) return i.csrf_token = o("bili_jct") || "", r(e, t, n(i))
        }
        return console.log("该请求方式暂不支持"), null
    }

    function i(e) {
        var t = $.Deferred();
        return t.reject(e), t
    }

    function r(e, t, i) {
        var o = $.Deferred(), n = new mOxie.XMLHttpRequest;
        return n.open(t, e), n.withCredentials = !0, n.responseType = "json", n.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"), n.onreadystatechange = function () {
            var e;
            4 == n.readyState && (200 == n.status ? (e = n.response, o.resolve(e)) : (e = n.response, o.reject(e)))
        }, "POST" == t ? n.send(i) : n.send(), o
    }

    var t;
    require("//static.biligame.com/lib/plupload/2.1.9/moxie.min"), mOxie.Env.swf_url = "/flash/Moxie.swf";
    var d = (t = String.prototype.startsWith ? function (e, t) {
        return e.startsWith(t)
    } : function (e, t) {
        return 0 === e.lastIndexOf(t, 0)
    })(location.href, "http:") ? "http" : "https";

    function u(e) {
        return t(e, "/") ? d + ":" + e : e
    }

    function s(e, i, o) {
        return e.slice(1).reduce(function (e, t) {
            return e.pipe(null, function () {
                return c(t, i, o)
            })
        }, c(e[0], i, o))
    }

    var e = {
        sendmOxieAjax: c,
        moxieAjaxWithFallback: s,
        getLoginInfo: function () {
            return c(u("//api.biligame.com/user/info"), "GET", {_: Date.now()})
        },
        isLogin: function () {
            return !(!o("DedeUserID") || "" == o("DedeUserID") || '""' == o("DedeUserID"))
        },
        getBookingSum: function (e) {
            return c(u("//activity.biligame.com/order/common_sum"), "POST", {game_id: e || ""})
        },
        booking: function (e, t) {
            return c(u("//activity.biligame.com/order/common_order"), "POST", {
                game_id: e || "",
                phone: t || "",
                source: a()
            })
        },
        getPhoneSms: function (e, t) {
            return c(u("//activity.biligame.com/sms/send_sms"), "POST", {game_id: e || "", phone: t || ""})
        },
        getPhoneSms4GeeTest3: function (e, t, i) {
            return c(u("//activity.biligame.com/sms/send_sms_geet"), "POST", {
                game_id: e || "",
                phone: t || "",
                geet_version: "3",
                challenge: i.challenge,
                validate: i.validate,
                seccode: i.seccode,
                userid: i.userid,
                gs: i.gs + ""
            })
        },
        phoneOrder: function (e, t, i) {
            return c(u("//activity.biligame.com/order/phone_order"), "POST", {
                game_id: e || "",
                phone: t || "",
                sms_code: i || "",
                source: a()
            })
        },
        getBookingStatus: function (e) {
            return c(u("//activity.biligame.com/order/common_count"), "POST", {game_id: e || ""})
        },
        updatePhone: function (e, t) {
            return c(u("//activity.biligame.com/order/update_phone"), "POST", {game_id: e || "", phone: t || ""})
        },
        getGiftCode: function (e) {
            return c(u("//ka.biligame.com/api/getCode2.do"), "GET", {ka_info_id: e || ""})
        },
        checkGetCode: function (e) {
            return c(u("//ka.biligame.com/api/checkGetCode.do"), "GET", {ka_info_id: e})
        },
        checkGetCode2: function (e) {
            return s([u("//qcloud-sdkact-api.biligame.com/gift/my_gift"), u("//ali-sdkact-api.biligame.com/gift/my_gift"), u("//ws-sdkact-api.biligame.com/gift/my_gift")], "GET", {gift_id: e})
        },
        sendSmsForGetCode: function (e, t) {
            return c(u("//ka.biligame.com/api/sendSmsForGetCode.do"), "GET", {ka_info_id: e, phone: t})
        },
        getCodeWithSmsCode: function (e, t, i) {
            return c(u("//ka.biligame.com/api/getCodeWithSmsCode.do"), "GET", {ka_info_id: e, phone: t, sms_code: i})
        },
        getSignParams: function (e) {
            if (e) {
                return c(u("//api.biligame.com/vendor_sign"), "GET", {vendor_id: e || "", fallback_uid: -1})
            }
            return i("vendor id 不能为空！")
        },
        getNewsList: function (e) {
            return c(u("//api.biligame.com/news/list.action"), "GET", {
                gameExtensionId: e.gameExtensionId || "",
                positionId: e.positionId || 2,
                typeId: e.typeId || "",
                pageNum: e.pageNum || 1,
                pageSize: e.pageSize || 10
            })
        },
        getNewsDetail: function (e) {
            return c(u("//api.biligame.com/news/" + e + ".action"), "GET", {})
        },
        searchVideo: function (e, t, i) {
            return c(u("//activity.biligame.com/search/search_video"), "GET", {keyword: e, page: t, pagesize: i})
        },
        getLotteryInfo: function (e) {
            if (e) {
                return c(u("//activity.biligame.com/lottery/query"), "POST", {lottery_id: e || ""})
            }
            return i("lottery id 不能为空！")
        },
        ALT_FIRST_ORDER: 10,
        ALT_FIRST_MICROBLOG: 1,
        ALT_FIRST_TIEBA: 2,
        ALT_FIRST_WECHAT: 3,
        ALT_FIRST_QQZONE: 4,
        ALT_FIRST_LOGIN: 5,
        addLotteryTimes: function (e, t) {
            if (e) {
                return c(u("//activity.biligame.com/lottery/chance/bonus"), "POST", {
                    lottery_id: e || "",
                    bonus_type: t || ""
                })
            }
            return i("lottery id 不能为空！")
        },
        executeLottery: function (e) {
            if (e) {
                return c(u("//activity.biligame.com/lottery/excute"), "POST", {lottery_id: e || ""})
            }
            return i("lottery id 不能为空！")
        },
        lotteryExecute: function (e, t) {
            if (e) {
                return c(u("//qcloud-sdkact-api.biligame.com/lottery/execute"), "POST", {
                    lottery_activity_id: e || "",
                    game_ids: t || ""
                })
            }
            return i("lottery id 不能为空！")
        },
        deliverAddress: function (e, t) {
            if (e) {
                return c(u("//qcloud-sdkact-api.biligame.com/lottery/deliver_address"), "POST", {
                    winning_id: e || "",
                    name: t.name || "",
                    phone: t.phone || "",
                    address: t.address || ""
                })
            }
            return i("winning id 不能为空！")
        },
        replaceLottery: function (e) {
            if (e) {
                return c(u("//activity.biligame.com/lottery/excute/replace"), "POST", {lottery_id: e || ""})
            }
            return i("lottery id 不能为空！")
        },
        getLotteryUsed: function (e) {
            if (e) {
                return c(u("//activity.biligame.com/lottery/query/used"), "POST", {lottery_id: e || ""})
            }
            return i("lottery id 不能为空！")
        },
        geetestBooking: function (e, t, i, o, n) {
            return c(u("//activity.biligame.com/order/common_order_geet"), "POST", {
                game_id: e || "",
                phone: t || "",
                challenge: i.geetest_challenge,
                validate: i.geetest_validate,
                seccode: i.geetest_seccode,
                userid: o,
                gs: n
            })
        },
        geetest3Booking: function (e, t, i) {
            var o = u("//activity.biligame.com/order/common_order_geet3");
            return i ? i.phone = t || "" : i = t, i.game_id = e, i.source = a(), c(o, "POST", i)
        },
        geetestGetGiftCode: function (e, t, i, o) {
            return c(u("//ka.biligame.com/api/getCode2_geet.do"), "POST", {
                ka_info_id: e || "",
                challenge: t.geetest_challenge,
                validate: t.geetest_validate,
                seccode: t.geetest_seccode,
                userid: i,
                gs: o
            })
        },
        geetestGetGiftCode2: function (e, t) {
            var i = [u("//qcloud-sdkact-api.biligame.com/gift/get_gift"), u("//ali-sdkact-api.biligame.com/gift/get_gift"), u("//ws-sdkact-api.biligame.com/gift/get_gift")];
            return t.gift_id = e || "", s(i, "POST", t)
        },
        geetestGiftCodeCom: function () {
        },
        gtExchangeGiftCode: function (e, t, i, o, n) {
            return c(e = u(e), "GET", {
                user_id: t,
                gift_code: i,
                server_id: o,
                challenge: n.gt_validate && n.gt_validate.geetest_challenge || "",
                validate: n.gt_validate && n.gt_validate.geetest_validate || "",
                seccode: n.gt_validate && n.gt_validate.geetest_seccode || "",
                userid: n.gt_userid || "",
                gs: n.gt_gs || ""
            })
        },
        getOrderStatus: function (e) {
            return c(u("//activity.biligame.com/order/common_count"), "POST", {game_id: e || ""})
        },
        getAnswerStatus: function (e) {
            return c(u("//qcloud-sdkact-api.biligame.com/survey/query_survey"), "POST", {survey_id: e || ""})
        },
        submitAnswer: function (e) {
            return c(u("//qcloud-sdkact-api.biligame.com/survey/submit"), "POST", {survey: e || ""})
        },
        getLotteryCljjQuery: function (e) {
            return c(u("//qcloud-sdkact-api.biligame.com/lottery/cljj/query"), "POST", {lottery_activity_id: e || 2})
        },
        getLotteryCljjExecute: function (e, t) {
            return c(u("//qcloud-sdkact-api.biligame.com/lottery/cljj/execute"), "POST", {
                lottery_activity_id: e || 2,
                oldposition: t || ""
            })
        },
        getLotteryCljjSaveCard: function (e) {
            return c(u("//qcloud-sdkact-api.biligame.com/lottery/cljj/save_card"), "POST", {lottery_activity_id: e || 2})
        },
        getLotteryCljjShare: function (e) {
            return c(u("//qcloud-sdkact-api.biligame.com/lottery/cljj/share"), "POST", {lottery_activity_id: e || 2})
        },
        getStarsInfo: function (e) {
            return c(u("//activity.biligame.com/vote/get_up_info"), "GET", {vote_id: e})
        },
        applyForStar: function (e, t) {
            return c(u("//activity.biligame.com/vote/up"), "POST", {vote_id: e, role_id: t})
        },
        bookedByGraphicValid: function (e, t, i, o) {
            var n = u("//activity.biligame.com/order/captcha/order"),
                r = {game_id: e, token: t, captcha_code: i, phone: o};
            return r.source = a(), c(n, "POST", r)
        }
    };
    module.exports = e
});