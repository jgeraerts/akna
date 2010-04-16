(function() {
    tinymce.create("tinymce.plugins.AutoResizePlugin", {init:function(a, c) {
        var d = this;
        if (a.getParam("fullscreen_is_enabled")) {
            return
        }
        function b() {
            var h = a.getDoc(),e = h.body,j = h.documentElement,g = tinymce.DOM,i = d.autoresize_min_height,f;
            f = tinymce.isIE ? e.scrollHeight : j.offsetHeight;
            if (f > d.autoresize_min_height) {
                i = f
            }
            g.setStyle(g.get(a.id + "_ifr"), "height", i + "px");
            if (d.throbbing) {
                a.setProgressState(false);
                a.setProgressState(true)
            }
        }

        d.editor = a;
        d.autoresize_min_height = a.getElement().offsetHeight;
        a.onInit.add(function(f, e) {
            f.setProgressState(true);
            d.throbbing = true;
            f.getBody().style.overflowY = "hidden"
        });
        a.onChange.add(b);
        a.onSetContent.add(b);
        a.onPaste.add(b);
        a.onKeyUp.add(b);
        a.onPostRender.add(b);
        a.onLoadContent.add(function(f, e) {
            b();
            setTimeout(function() {
                b();
                f.setProgressState(false);
                d.throbbing = false
            }, 1250)
        });
        a.addCommand("mceAutoResize", b)
    },getInfo:function() {
        return{longname:"Auto Resize",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/autoresize",version:tinymce.majorVersion + "." + tinymce.minorVersion}
    }});
    tinymce.PluginManager.add("autoresize", tinymce.plugins.AutoResizePlugin)
})();