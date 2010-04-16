(function() {
    var a = tinymce.dom.Event,c = tinymce.grep,d = tinymce.each,b = tinymce.inArray;

    function e(j, i, h) {
        var g,k;
        g = j.createTreeWalker(i, NodeFilter.SHOW_ALL, null, false);
        while (k = g.nextNode()) {
            if (h) {
                if (!h(k)) {
                    return false
                }
            }
            if (k.nodeType == 3 && k.nodeValue && /[^\s\u00a0]+/.test(k.nodeValue)) {
                return false
            }
            if (k.nodeType == 1 && /^(HR|IMG|TABLE)$/.test(k.nodeName)) {
                return false
            }
        }
        return true
    }

    tinymce.create("tinymce.plugins.Safari", {init:function(f) {
        var g = this,h;
        if (!tinymce.isWebKit) {
            return
        }
        g.editor = f;
        g.webKitFontSizes = ["x-small","small","medium","large","x-large","xx-large","-webkit-xxx-large"];
        g.namedFontSizes = ["xx-small","x-small","small","medium","large","x-large","xx-large"];
        f.addCommand("CreateLink", function(k, j) {
            var m = f.selection.getNode(),l = f.dom,i;
            if (m && (/^(left|right)$/i.test(l.getStyle(m, "float", 1)) || /^(left|right)$/i.test(l.getAttrib(m, "align")))) {
                i = l.create("a", {href:j}, m.cloneNode());
                m.parentNode.replaceChild(i, m);
                f.selection.select(i)
            } else {
                f.getDoc().execCommand("CreateLink", false, j)
            }
        });
        f.onKeyUp.add(function(j, o) {
            var l,i,m,p,k;
            if (o.keyCode == 46 || o.keyCode == 8) {
                i = j.getBody();
                l = i.innerHTML;
                k = j.selection;
                if (i.childNodes.length == 1 && !/<(img|hr)/.test(l) && tinymce.trim(l.replace(/<[^>]+>/g, "")).length == 0) {
                    j.setContent('<p><br mce_bogus="1" /></p>', {format:"raw"});
                    p = i.firstChild;
                    m = k.getRng();
                    m.setStart(p, 0);
                    m.setEnd(p, 0);
                    k.setRng(m)
                }
            }
        });
        f.addCommand("FormatBlock", function(j, i) {
            var l = f.dom,k = l.getParent(f.selection.getNode(), l.isBlock);
            if (k) {
                l.replace(l.create(i), k, 1)
            } else {
                f.getDoc().execCommand("FormatBlock", false, i)
            }
        });
        f.addCommand("mceInsertContent", function(j, i) {
            f.getDoc().execCommand("InsertText", false, "mce_marker");
            f.getBody().innerHTML = f.getBody().innerHTML.replace(/mce_marker/g, f.dom.processHTML(i) + '<span id="_mce_tmp">XX</span>');
            f.selection.select(f.dom.get("_mce_tmp"));
            f.getDoc().execCommand("Delete", false, " ")
        });
        f.onKeyPress.add(function(o, p) {
            var q,v,r,l,j,k,i,u,m,t,s;
            if (p.keyCode == 13) {
                i = o.selection;
                q = i.getNode();
                if (p.shiftKey || o.settings.force_br_newlines && q.nodeName != "LI") {
                    g._insertBR(o);
                    a.cancel(p)
                }
                if (v = h.getParent(q, "LI")) {
                    r = h.getParent(v, "OL,UL");
                    u = o.getDoc();
                    s = h.create("p");
                    h.add(s, "br", {mce_bogus:"1"});
                    if (e(u, v)) {
                        if (k = h.getParent(r.parentNode, "LI,OL,UL")) {
                            return
                        }
                        k = h.getParent(r, "p,h1,h2,h3,h4,h5,h6,div") || r;
                        l = u.createRange();
                        l.setStartBefore(k);
                        l.setEndBefore(v);
                        j = u.createRange();
                        j.setStartAfter(v);
                        j.setEndAfter(k);
                        m = l.cloneContents();
                        t = j.cloneContents();
                        if (!e(u, t)) {
                            h.insertAfter(t, k)
                        }
                        h.insertAfter(s, k);
                        if (!e(u, m)) {
                            h.insertAfter(m, k)
                        }
                        h.remove(k);
                        k = s.firstChild;
                        l = u.createRange();
                        l.setStartBefore(k);
                        l.setEndBefore(k);
                        i.setRng(l);
                        return a.cancel(p)
                    }
                }
            }
        });
        f.onExecCommand.add(function(i, k) {
            var j,m,n,l;
            if (k == "InsertUnorderedList" || k == "InsertOrderedList") {
                j = i.selection;
                m = i.dom;
                if (n = m.getParent(j.getNode(), function(o) {
                    return/^(H[1-6]|P|ADDRESS|PRE)$/.test(o.nodeName)
                })) {
                    l = j.getBookmark();
                    m.remove(n, 1);
                    j.moveToBookmark(l)
                }
            }
        });
        f.onClick.add(function(i, j) {
            j = j.target;
            if (j.nodeName == "IMG") {
                g.selElm = j;
                i.selection.select(j)
            } else {
                g.selElm = null
            }
        });
        f.onInit.add(function() {
            g._fixWebKitSpans()
        });
        f.onSetContent.add(function() {
            h = f.dom;
            d(["strong","b","em","u","strike","sub","sup","a"], function(i) {
                d(c(h.select(i)).reverse(), function(l) {
                    var k = l.nodeName.toLowerCase(),j;
                    if (k == "a") {
                        if (l.name) {
                            h.replace(h.create("img", {mce_name:"a",name:l.name,"class":"mceItemAnchor"}), l)
                        }
                        return
                    }
                    switch (k) {case"b":case"strong":if (k == "b") {
                        k = "strong"
                    }j = "font-weight: bold;";break;case"em":j = "font-style: italic;";break;case"u":j = "text-decoration: underline;";break;case"sub":j = "vertical-align: sub;";break;case"sup":j = "vertical-align: super;";break;case"strike":j = "text-decoration: line-through;";break}
                    h.replace(h.create("span", {mce_name:k,style:j,"class":"Apple-style-span"}), l, 1)
                })
            })
        });
        f.onPreProcess.add(function(i, j) {
            h = i.dom;
            d(c(j.node.getElementsByTagName("span")).reverse(), function(m) {
                var k,l;
                if (j.get) {
                    if (h.hasClass(m, "Apple-style-span")) {
                        l = m.style.backgroundColor;
                        switch (h.getAttrib(m, "mce_name")) {case"font":if (!i.settings.convert_fonts_to_spans) {
                            h.setAttrib(m, "style", "")
                        }break;case"strong":case"em":case"sub":case"sup":h.setAttrib(m, "style", "");break;case"strike":case"u":if (!i.settings.inline_styles) {
                            h.setAttrib(m, "style", "")
                        } else {
                            h.setAttrib(m, "mce_name", "")
                        }break;default:if (!i.settings.inline_styles) {
                            h.setAttrib(m, "style", "")
                        }}
                        if (l) {
                            m.style.backgroundColor = l
                        }
                    }
                }
                if (h.hasClass(m, "mceItemRemoved")) {
                    h.remove(m, 1)
                }
            })
        });
        f.onPostProcess.add(function(i, j) {
            j.content = j.content.replace(/<br \/><\/(h[1-6]|div|p|address|pre)>/g, "</$1>");
            j.content = j.content.replace(/ id=\"undefined\"/g, "")
        })
    },getInfo:function() {
        return{longname:"Safari compatibility",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/safari",version:tinymce.majorVersion + "." + tinymce.minorVersion}
    },_fixWebKitSpans:function() {
        var g = this,f = g.editor;
        a.add(f.getDoc(), "DOMNodeInserted", function(h) {
            h = h.target;
            if (h && h.nodeType == 1) {
                g._fixAppleSpan(h)
            }
        })
    },_fixAppleSpan:function(l) {
        var g = this.editor,m = g.dom,i = this.webKitFontSizes,f = this.namedFontSizes,j = g.settings,h,k;
        if (m.getAttrib(l, "mce_fixed")) {
            return
        }
        if (l.nodeName == "SPAN" && l.className == "Apple-style-span") {
            h = l.style;
            if (!j.convert_fonts_to_spans) {
                if (h.fontSize) {
                    m.setAttrib(l, "mce_name", "font");
                    m.setAttrib(l, "size", b(i, h.fontSize) + 1)
                }
                if (h.fontFamily) {
                    m.setAttrib(l, "mce_name", "font");
                    m.setAttrib(l, "face", h.fontFamily)
                }
                if (h.color) {
                    m.setAttrib(l, "mce_name", "font");
                    m.setAttrib(l, "color", m.toHex(h.color))
                }
                if (h.backgroundColor) {
                    m.setAttrib(l, "mce_name", "font");
                    m.setStyle(l, "background-color", h.backgroundColor)
                }
            } else {
                if (h.fontSize) {
                    m.setStyle(l, "fontSize", f[b(i, h.fontSize)])
                }
            }
            if (h.fontWeight == "bold") {
                m.setAttrib(l, "mce_name", "strong")
            }
            if (h.fontStyle == "italic") {
                m.setAttrib(l, "mce_name", "em")
            }
            if (h.textDecoration == "underline") {
                m.setAttrib(l, "mce_name", "u")
            }
            if (h.textDecoration == "line-through") {
                m.setAttrib(l, "mce_name", "strike")
            }
            if (h.verticalAlign == "super") {
                m.setAttrib(l, "mce_name", "sup")
            }
            if (h.verticalAlign == "sub") {
                m.setAttrib(l, "mce_name", "sub")
            }
            m.setAttrib(l, "mce_fixed", "1")
        }
    },_insertBR:function(f) {
        var j = f.dom,h = f.selection,i = h.getRng(),g;
        i.insertNode(g = j.create("br"));
        i.setStartAfter(g);
        i.setEndAfter(g);
        h.setRng(i);
        if (h.getSel().focusNode == g.previousSibling) {
            h.select(j.insertAfter(j.doc.createTextNode("\u00a0"), g));
            h.collapse(1)
        }
        f.getWin().scrollTo(0, j.getPos(h.getRng().startContainer).y)
    }});
    tinymce.PluginManager.add("safari", tinymce.plugins.Safari)
})();