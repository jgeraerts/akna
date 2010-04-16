(function() {
    tinymce.create("tinymce.plugins.AutoSavePlugin", {init:function(a, b) {
        var c = this;
        c.editor = a;
        window.onbeforeunload = tinymce.plugins.AutoSavePlugin._beforeUnloadHandler
    },getInfo:function() {
        return{longname:"Auto save",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/autosave",version:tinymce.majorVersion + "." + tinymce.minorVersion}
    },"static":{_beforeUnloadHandler:function() {
        var a;
        tinymce.each(tinyMCE.editors, function(b) {
            if (b.getParam("fullscreen_is_enabled")) {
                return
            }
            if (b.isDirty()) {
                a = b.getLang("autosave.unload_msg");
                return false
            }
        });
        return a
    }}});
    tinymce.PluginManager.add("autosave", tinymce.plugins.AutoSavePlugin)
})();