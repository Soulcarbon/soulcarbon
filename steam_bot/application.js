"use strict";

var fs = require('fs');
var crypto = require('crypto');

var request = require('request');
var Steam = require('steam');
var SteamWebLogOn = require('steam-weblogon');
var getSteamAPIKey = require('steam-web-api-key');
var SteamTradeOffers = require('steam-tradeoffers'); // change to 'steam-tradeoffers' if not running from the examples subdirectory


var admin = ''; // put your steamid here so the bot can send you trade offers

var logOnOptions = {
    account_name: 'Demon123123666w',
    password: 'Laracr123$w'
};

var authCode = 'PGM7V'; // code received by email

try {
    logOnOptions['sha_sentryfile'] = getSHA1(fs.readFileSync('sentry'));
} catch (e) {
    if (authCode != '') {
        logOnOptions['auth_code'] = authCode;
    }
}

// if we've saved a server list, use it
if (fs.existsSync('servers')) {
    Steam.servers = JSON.parse(fs.readFileSync('servers'));
}

var logger = require("./loggerInit");


var steamClient = new Steam.SteamClient();
var steamUser = new Steam.SteamUser(steamClient);
var steamFriends = new Steam.SteamFriends(steamClient);
var steamWebLogOn = new SteamWebLogOn(steamClient, steamUser);
var offers = new SteamTradeOffers();

steamClient.connect();
steamClient.on('connected', function () {
    steamUser.logOn(logOnOptions);
});

steamClient.on('logOnResponse', function (logonResp) {
    if (logonResp.eresult == Steam.EResult.OK) {
        logger.info("Logged in bot : " + logOnOptions.account_name);
        steamFriends.setPersonaState(Steam.EPersonaState.Online);

        steamWebLogOn.webLogOn(function (sessionID, newCookie) {
            getSteamAPIKey({
                sessionID: sessionID,
                webCookie: newCookie
            }, function (err, APIKey) {
                offers.setup({
                    sessionID: sessionID,
                    webCookie: newCookie,
                    APIKey: APIKey
                }, function () {

                    var requestJson2 = {
                        key: "jdsXFpw_g!00*"
                    };

                    var timerId = setTimeout(function tick() {
                        request({
                            url: "http://82.146.55.131/game/winner",
                            method: "POST",
                            headers: {
                                "content-type": 'application/x-www-form-urlencoded'
                            },
                            body: "data=" + JSON.stringify(requestJson2)
                        }, function (error, response, body) {
                            if (body != "is empty") {
                                var winner = JSON.parse(body);
                                logger.info(winner);

                                var appIdList = [];
                                var receivedItems = winner.list;
                                for (var i = 0; i < receivedItems.length; i++) {
                                    if (appIdList.indexOf(receivedItems[i].appid) == -1) {
                                        appIdList.push(receivedItems[i].appid);
                                        var appId = receivedItems[i].appid;
                                        offers.loadMyInventory({
                                            appId: appId,
                                            contextId: 2
                                        }, function (err, items) {
                                            var assetIdList = [];
                                            var winnerItems = [];
                                            // picking first tradable item
                                            for (var k = 0; k < receivedItems.length; k++) {
                                                if(appId == receivedItems.appid) {
                                                    if(assetIdList.in)
                                                }
                                            }
                                            for (var j = 0; j < items.length; j++) {
                                                if (items[j].tradable) {
                                                    winnerItems.push({
                                                        appid: appId,
                                                        contextid: 2,
                                                        amount: 1,
                                                        assetid: items[j].id
                                                    });
                                                }
                                            }

                                            offers.makeOffer({
                                                partnerSteamId: winner.player.steamId,
                                                accessToken: winner.player.token,
                                                itemsFromMe: winnerItems,
                                                itemsFromThem: [],
                                                message: 'Поздровляем с победой'
                                            }, function (err, response) {
                                                logerr.info(err);
                                            });

                                        });
                                    }
                                }
                            }
                            timerId = setTimeout(tick, 10000);
                        });
                    }, 10000);
                });
            });
        });
    }
});

steamClient.on('servers', function (servers) {
    fs.writeFile('servers', JSON.stringify(servers));
});

steamUser.on('updateMachineAuth', function (sentry, callback) {
    fs.writeFileSync('sentry', sentry.bytes);
    callback({sha_file: getSHA1(sentry.bytes)});
});


steamUser.on('tradeOffers', function (number) {
    try {
        logger.info("Get trade offers + " + number);
        if (number > 0) {
            offers.getOffers({
                get_received_offers: 1,
                active_only: 1,
                time_historical_cutoff: Math.round(Date.now() / 1000)
            }, function (error, body) {
                if (body.response.trade_offers_received) {
                    body.response.trade_offers_received.forEach(function (offer) {
                        if (offer.trade_offer_state == 2) {
                            if (offer.steamid_other == admin /*|| offer.steamid_other == '76561198019192353'*/) {
                                logger.info("Accept offers admin");
                                offers.acceptOffer({tradeOfferId: offer.tradeofferid});
                            } else {
                                if (!offer.items_to_give) {
                                    if (offer.message) {
                                        var message = offer.message;
                                        var position = message.indexOf("token");
                                        if (~position) {
                                            var token = message.substring(position + 6, message.length);
                                            if (token) {
                                                logger.info("Accept offer");
                                                offers.acceptOffer({tradeOfferId: offer.tradeofferid});
                                                var requestJson = {
                                                    steamid_other: offer.steamid_other,
                                                    weaponJsonList: offer.items_to_receive,
                                                    token: token,
                                                    key: "gzdpaSe_503_!_"
                                                };

                                                request({
                                                    url: "http://82.146.55.131/game/addPlayer",
                                                    method: "POST",
                                                    headers: {
                                                        "content-type": 'application/x-www-form-urlencoded'
                                                    },
                                                    body: "data=" + JSON.stringify(requestJson)
                                                }, function (error, response, body) {
                                                    logger.info("Get offer on server : " + body)
                                                });
                                            } else {
                                                logger.info("Decline offer");
                                                offers.declineOffer({tradeOfferId: offer.tradeofferid});
                                            }
                                        } else {
                                            logger.info("Decline offer");
                                            offers.declineOffer({tradeOfferId: offer.tradeofferid});
                                        }
                                    } else {
                                        logger.info("Decline offer");
                                        offers.declineOffer({tradeOfferId: offer.tradeofferid});
                                    }

                                } else {
                                    logger.info("Decline offer");
                                    offers.declineOffer({tradeOfferId: offer.tradeofferid});
                                }
                            }
                        }
                    });
                }
            });
        }
    } catch (error) {
        console.log("Error:");
        console.log(error);
    }
});


function getSHA1(bytes) {
    var shasum = crypto.createHash('sha1');
    shasum.end(bytes);
    return shasum.read();
}