var request = require('request');

var offer = { tradeofferid: '603784123',
    accountid_other: 58926625,
    message: '',
    expiration_time: 1439197522,
    trade_offer_state: 2,
    items_to_receive:
        [ { appid: '730',
            contextid: '2',
            assetid: '2855108584',
            classid: '926978479',
            instanceid: '0',
            amount: '5',
            missing: false } ],
    is_our_offer: false,
    time_created: 1437987922,
    time_updated: 1437987934,
    from_real_time_trade: false,
    steamid_other: '76561197991709700' };
//76561198193580819
//76561198019501084
//76561198170903117
//76561198040905470
//76561198046155189
//76561198162884430
//76561198193580819
//76561198135943514
//76561197991709700
//76561198085273190
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
