var fs = require('fs');
var crypto = require('crypto');
'https://steamcommunity.com/tradeoffer/new/?partner=207631488&token=8MtV4MKG'

var request = require('request');
var Steam = require('steam');
var SteamWebLogOn = require('steam-weblogon');
var getSteamAPIKey = require('steam-web-api-key');
var SteamTradeOffers = require('steam-tradeoffers'); // change to 'steam-tradeoffers' if not running from the examples subdirectory

var admin = '76561198019192353'; // put your steamid here so the bot can send you trade offers

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
        console.log('Logged in!');
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
        console.log("Get trade offers + " + number);
        if (number > 0) {
            offers.getOffers({
                get_received_offers: 1,
                active_only: 1,
                time_historical_cutoff: Math.round(Date.now() / 1000)
            }, function (error, body) {
                if (body.response.trade_offers_received) {
                    body.response.trade_offers_received.forEach(function (offer) {
                        console.log(offer);
                        if (offer.trade_offer_state == 2) {
                            if(offer.steamid_other == admin /*|| offer.steamid_other == '76561198019192353'*/) {
                                console.log("Accept offers admin");
                                offers.acceptOffer({tradeOfferId: offer.tradeofferid});
                            } else {
                                if (!offer.items_to_give) {
                                    console.log("Accept offer");
                                    offers.acceptOffer({tradeOfferId: offer.tradeofferid});
                                    var requestJson = {
                                        steamid_other : offer.steamid_other,
                                        weaponJsonList : offer.items_to_receive,
                                        key : "gzdpaSe_503_!_"
                                    };

                                    request({
                                        url: "http://localhost:8080/game/addPlayer",
                                        method: "POST",
                                        headers: {
                                            "content-type" : 'application/x-www-form-urlencoded'
                                        },
                                        body: "data="+JSON.stringify(requestJson)
                                    }, function (error, response, body) {
                                        console.log("body : " + body);
                                    });
                                } else {
                                    console.log("Decline offer");
                                    offers.declineOffer({tradeOfferId: offer.tradeofferid});
                                }
                            }
                        }
                    });
                }
            });
        }
    } catch(error) {
        console.log("Error:");
        console.log(error);
    }
});

var requestJson2 = {
    key : "jdsXFpw_g!00*"
};


setInterval(function (){
    request({
        url: "http://localhost:8080/game/winner",
        method: "POST",
        headers: {
            "content-type" : 'application/x-www-form-urlencoded'
        },
        body: "data="+JSON.stringify(requestJson2)
    }, function (error, response, body) {
        offers.makeOffer ({
            partnerSteamId: admin,
            itemsFromMe: [
                {
                    appid: 730,
                    contextid: 2,
                    amount: 1,
                    assetid: '2956841757'
                }
            ],
            itemsFromThem: [],
            message: 'Поздровляем с победой'
        }, function(err, response){
            if (err) {
                throw err;
            }
            console.log(response);
        });
        if(body != "is empty") {
            console.log("body : " + body);
        }
    });
},10000);


function getSHA1(bytes) {
    var shasum = crypto.createHash('sha1');
    shasum.end(bytes);
    return shasum.read();
}