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
            amount: '1',
            missing: false } ],
    is_our_offer: false,
    time_created: 1437987922,
    time_updated: 1437987934,
    from_real_time_trade: false,
    steamid_other: '76561198058689165' };

var requestJson = {
    steamid_other : offer.steamid_other,
    weaponJsonList : offer.items_to_receive,
    key : "gzdpaSe_503_!_"
};

request({
    url: "http://localhost:8080/platform/data/action",
    method: "POST",
    headers: {
        "content-type" : 'application/x-www-form-urlencoded'
    },
    body: "className=ru.sw.modules.game.Game&actionName=addPlayer&data="+JSON.stringify(requestJson)
}, function (error, response, body) {
    console.log("body : " + body);
});