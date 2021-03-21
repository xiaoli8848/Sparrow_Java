var rmUrl="http://127.0.0.1:8080";

function initVersionList(dataList) {
    let htmlTmp="";
    for(let i=0;i<dataList.length;i++) {
        htmlTmp+=
            '                                        <a href="#" class="list-group-item list-group-item-action" aria-current="true">\n' +
            '                                            <div class="row row-cols-2">\n' +
            '                                                <div class="col-4">\n' +
            '                                                    <img class="mc-icon d-inline-block align-top" src="img/mc_icon.png" alt="MC_ICON">\n' +
            '                                                </div>\n' +
            '                                                <div class="col">\n' +
            '                                                    <h5 class="list-group-item-heading mc-version-name">'+dataList[i].versionName+'</h5>\n' +
            '                                                    <small class="list-group-item-text mc-version">'+dataList[i].version+'</small>\n' +
            '                                                </div>\n' +
            '                                            </div>\n' +
            '                                        </a>\n';
    }
    $(".version-list").html(htmlTmp);

    $("#launcher-version-list a").click(function(){
        $("#launch-panel-version-name").text($(this).children("div").children("div.col").children("h5").text());
        $("#launch-panel-version").text($(this).children("div").children("div.col").children("small").text());
    });
}

$("#window-close").click(function () {
    closeWindow();
});

$("#window-minimize").click(function () {
    minimizeWindow();
});

function closeWindow() {
    $.ajax({
        type: "post",
        url: rmUrl,
        data: "data: {close}"
    });
}

function minimizeWindow() {
    $.ajax({
        type: "post",
        url: rmUrl,
        data: "data: {minimize}"
    });
}


$.ajax({
    type: "get",
    url: "/data/versionList.json",
    success: function(res) {
        initVersionList(res);
    },
    error: function(err) {
        console.log(err);
    }
});
$.ajax({
    type: "get",
    url: "http://launchermeta.mojang.com/mc/game/version_manifest.json",
    success: function(res) {
        let htmlTmp="";
        let versions=res.versions;
        for(let i=0;i<versions.length;i++) {
            htmlTmp+=
                '                                    <a href="#" class="list-group-item list-group-item-action" aria-current="true">\n' +
                '                                        <h5 class="list-group-item-heading">'+versions[i].id+'</h5>\n' +
                '                                        <small class="list-group-item-text">'+versions[i].type+'</small>\n' +
                '                                    </a>\n';
        }
        $(".allversion-list").html(htmlTmp);
    },
    error: function(err) {
        console.log(err);
    }
});


