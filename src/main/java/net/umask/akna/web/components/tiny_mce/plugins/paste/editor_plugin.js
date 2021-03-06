(function() {
    var a = tinymce.each;
    tinymce.create("tinymce.plugins.PastePlugin", {init:function(c, d) {
        var e = this,b;
        e.editor = c;
        e.url = d;
        e.onPreProcess = new tinymce.util.Dispatcher(e);
        e.onPostProcess = new tinymce.util.Dispatcher(e);
        e.onPreProcess.add(e._preProcess);
        e.onPostProcess.add(e._postProcess);
        e.onPreProcess.add(function(h, i) {
            c.execCallback("paste_preprocess", h, i)
        });
        e.onPostProcess.add(function(h, i) {
            c.execCallback("paste_postprocess", h, i)
        });
        function g(i) {
            var h = c.dom;
            e.onPreProcess.dispatch(e, i);
            i.node = h.create("div", 0, i.content);
            e.onPostProcess.dispatch(e, i);
            i.content = c.serializer.serialize(i.node, {getInner:1});
            if (/<(p|h[1-6]|ul|ol)/.test(i.content)) {
                e._insertBlockContent(c, h, i.content)
            } else {
                e._insert(i.content)
            }
        }

        c.addCommand("mceInsertClipboardContent", function(h, i) {
            g(i)
        });
        function f(l) {
            var p,k,i,j = c.selection,o = c.dom,h = c.getBody(),m;
            if (o.get("_mcePaste")) {
                return
            }
            p = o.add(h, "div", {id:"_mcePaste"}, "\uFEFF");
            if (h != c.getDoc().body) {
                m = o.getPos(c.selection.getStart(), h).y
            } else {
                m = h.scrollTop
            }
            o.setStyles(p, {position:"absolute",left:-10000,top:m,width:1,height:1,overflow:"hidden"});
            if (tinymce.isIE) {
                i = o.doc.body.createTextRange();
                i.moveToElementText(p);
                i.execCommand("Paste");
                o.remove(p);
                if (p.innerHTML === "\uFEFF") {
                    c.execCommand("mcePasteWord");
                    l.preventDefault();
                    return
                }
                g({content:p.innerHTML});
                return tinymce.dom.Event.cancel(l)
            } else {
                k = c.selection.getRng();
                p = p.firstChild;
                i = c.getDoc().createRange();
                i.setStart(p, 0);
                i.setEnd(p, 1);
                j.setRng(i);
                window.setTimeout(function() {
                    var q = "",n = o.select("div[id=_mcePaste]");
                    a(n, function(r) {
                        q += (o.select("> span.Apple-style-span div", r)[0] || o.select("> span.Apple-style-span", r)[0] || r).innerHTML
                    });
                    a(n, function(r) {
                        o.remove(r)
                    });
                    if (k) {
                        j.setRng(k)
                    }
                    g({content:q})
                }, 0)
            }
        }

        if (c.getParam("paste_auto_cleanup_on_paste", true)) {
            if (tinymce.isOpera || /Firefox\/2/.test(navigator.userAgent)) {
                c.onKeyDown.add(function(h, i) {
                    if (((tinymce.isMac ? i.metaKey : i.ctrlKey) && i.keyCode == 86) || (i.shiftKey && i.keyCode == 45)) {
                        f(i)
                    }
                })
            } else {
                c.onPaste.addToTop(function(h, i) {
                    return f(i)
                })
            }
        }
        if (c.getParam("paste_block_drop")) {
            c.onInit.add(function() {
                c.dom.bind(c.getBody(), ["dragend","dragover","draggesture","dragdrop","drop","drag"], function(h) {
                    h.preventDefault();
                    h.stopPropagation();
                    return false
                })
            })
        }
        e._legacySupport()
    },getInfo:function() {
        return{longname:"Paste text/word",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/paste",version:tinymce.majorVersion + "." + tinymce.minorVersion}
    },_preProcess:function(d, i) {
        var b = this.editor,c = i.content,g,f;

        function g(h) {
            a(h, function(j) {
                if (j.constructor == RegExp) {
                    c = c.replace(j, "")
                } else {
                    c = c.replace(j[0], j[1])
                }
            })
        }

        if (/(class=\"?Mso|style=\"[^\"]*\bmso\-|w:WordDocument)/.test(c) || i.wordContent) {
            i.wordContent = true;
            g([/^\s*(&nbsp;)+/g,/(&nbsp;|<br[^>]*>)+\s*$/g]);
            if (b.getParam("paste_convert_middot_lists", true)) {
                g([
                    [/<!--\[if !supportLists\]-->/gi,"$&__MCE_ITEM__"],
                    [/(<span[^>]+:\s*symbol[^>]+>)/gi,"$1__MCE_ITEM__"],
                    [/(<span[^>]+mso-list:[^>]+>)/gi,"$1__MCE_ITEM__"]
                ])
            }
            g([/<!--[\s\S]+?-->/gi,/<\/?(img|font|meta|link|style|div|v:\w+)[^>]*>/gi,/<\\?\?xml[^>]*>/gi,/<\/?o:[^>]*>/gi,/ (id|name|language|type|on\w+|v:\w+)=\"([^\"]*)\"/gi,/ (id|name|language|type|on\w+|v:\w+)=(\w+)/gi,[/<(\/?)s>/gi,"<$1strike>"],/<script[^>]+>[\s\S]*?<\/script>/gi,[/&nbsp;/g,"\u00a0"]]);
            if (!b.getParam("paste_retain_style_properties")) {
                g([/<\/?(span)[^>]*>/gi])
            }
        }
        f = b.getParam("paste_strip_class_attributes");
        if (f != "none") {
            function e(l, h) {
                var k,j = "";
                if (f == "all") {
                    return""
                }
                h = tinymce.explode(h, " ");
                for (k = h.length - 1; k >= 0; k--) {
                    if (!/^(Mso)/i.test(h[k])) {
                        j += (!j ? "" : " ") + h[k]
                    }
                }
                return' class="' + j + '"'
            }

            g([
                [/ class=\"([^\"]*)\"/gi,e],
                [/ class=(\w+)/gi,e]
            ])
        }
        if (b.getParam("paste_remove_spans")) {
            g([/<\/?(span)[^>]*>/gi])
        }
        i.content = c
    },_postProcess:function(e, g) {
        var d = this,c = d.editor,f = c.dom,b;
        if (g.wordContent) {
            a(f.select("a", g.node), function(h) {
                if (!h.href || h.href.indexOf("#_Toc") != -1) {
                    f.remove(h, 1)
                }
            });
            if (d.editor.getParam("paste_convert_middot_lists", true)) {
                d._convertLists(e, g)
            }
            b = c.getParam("paste_retain_style_properties");
            if (tinymce.is(b, "string")) {
                b = tinymce.explode(b)
            }
            a(f.select("*", g.node), function(l) {
                var m = {},j = 0,k,n,h;
                if (b) {
                    for (k = 0; k < b.length; k++) {
                        n = b[k];
                        h = f.getStyle(l, n);
                        if (h) {
                            m[n] = h;
                            j++
                        }
                    }
                }
                f.setAttrib(l, "style", "");
                if (b && j > 0) {
                    f.setStyles(l, m)
                } else {
                    if (l.nodeName == "SPAN" && !l.className) {
                        f.remove(l, true)
                    }
                }
            })
        }
        if (c.getParam("paste_remove_styles") || (c.getParam("paste_remove_styles_if_webkit") && tinymce.isWebKit)) {
            a(f.select("*[style]", g.node), function(h) {
                h.removeAttribute("style");
                h.removeAttribute("mce_style")
            })
        } else {
            if (tinymce.isWebKit) {
                a(f.select("*", g.node), function(h) {
                    h.removeAttribute("mce_style")
                })
            }
        }
    },_convertLists:function(e, c) {
        var g = e.editor.dom,f,j,b = -1,d,k = [],i,h;
        a(g.select("p", c.node), function(r) {
            var n,s = "",q,o,l,m;
            for (n = r.firstChild; n && n.nodeType == 3; n = n.nextSibling) {
                s += n.nodeValue
            }
            s = r.innerHTML.replace(/<\/?\w+[^>]*>/gi, "").replace(/&nbsp;/g, "\u00a0");
            if (/^(__MCE_ITEM__)+[\u2022\u00b7\u00a7\u00d8o]\s*\u00a0*/.test(s)) {
                q = "ul"
            }
            if (/^__MCE_ITEM__\s*\w+\.\s*\u00a0{2,}/.test(s)) {
                q = "ol"
            }
            if (q) {
                d = parseFloat(r.style.marginLeft || 0);
                if (d > b) {
                    k.push(d)
                }
                if (!f || q != i) {
                    f = g.create(q);
                    g.insertAfter(f, r)
                } else {
                    if (d > b) {
                        f = j.appendChild(g.create(q))
                    } else {
                        if (d < b) {
                            l = tinymce.inArray(k, d);
                            m = g.getParents(f.parentNode, q);
                            f = m[m.length - 1 - l] || f
                        }
                    }
                }
                a(g.select("span", r), function(t) {
                    var p = t.innerHTML.replace(/<\/?\w+[^>]*>/gi, "");
                    if (q == "ul" && /^[\u2022\u00b7\u00a7\u00d8o]/.test(p)) {
                        g.remove(t)
                    } else {
                        if (/^[\s\S]*\w+\.(&nbsp;|\u00a0)*\s*/.test(p)) {
                            g.remove(t)
                        }
                    }
                });
                o = r.innerHTML;
                if (q == "ul") {
                    o = r.innerHTML.replace(/__MCE_ITEM__/g, "").replace(/^[\u2022\u00b7\u00a7\u00d8o]\s*(&nbsp;|\u00a0)+\s*/, "")
                } else {
                    o = r.innerHTML.replace(/__MCE_ITEM__/g, "").replace(/^\s*\w+\.(&nbsp;|\u00a0)+\s*/, "")
                }
                j = f.appendChild(g.create("li", 0, o));
                g.remove(r);
                b = d;
                i = q
            } else {
                f = b = 0
            }
        });
        h = c.node.innerHTML;
        if (h.indexOf("__MCE_ITEM__") != -1) {
            c.node.innerHTML = h.replace(/__MCE_ITEM__/g, "")
        }
    },_insertBlockContent:function(h, e, i) {
        var c,g,d = h.selection,m,j,b,k,f;

        function l(p) {
            var o;
            if (tinymce.isIE) {
                o = h.getDoc().body.createTextRange();
                o.moveToElementText(p);
                o.collapse(false);
                o.select()
            } else {
                d.select(p, 1);
                d.collapse(false)
            }
        }

        this._insert('<span id="_marker">&nbsp;</span>', 1);
        g = e.get("_marker");
        c = e.getParent(g, "p,h1,h2,h3,h4,h5,h6,ul,ol,th,td");
        if (c && !/TD|TH/.test(c.nodeName)) {
            g = e.split(c, g);
            a(e.create("div", 0, i).childNodes, function(o) {
                m = g.parentNode.insertBefore(o.cloneNode(true), g)
            });
            l(m)
        } else {
            e.setOuterHTML(g, i);
            d.select(h.getBody(), 1);
            d.collapse(0)
        }
        e.remove("_marker");
        j = d.getStart();
        b = e.getViewPort(h.getWin());
        k = h.dom.getPos(j).y;
        f = j.clientHeight;
        if (k < b.y || k + f > b.y + b.h) {
            h.getDoc().body.scrollTop = k < b.y ? k : k - b.h + 25
        }
    },_insert:function(d, b) {
        var c = this.editor;
        if (!c.selection.isCollapsed()) {
            c.getDoc().execCommand("Delete", false, null)
        }
        c.execCommand(tinymce.isGecko ? "insertHTML" : "mceInsertContent", false, d, {skip_undo:b})
    },_legacySupport:function() {
        var c = this,b = c.editor;
        a(["mcePasteText","mcePasteWord"], function(d) {
            b.addCommand(d, function() {
                b.windowManager.open({file:c.url + (d == "mcePasteText" ? "/pastetext.htm" : "/pasteword.htm"),width:parseInt(b.getParam("paste_dialog_width", "450")),height:parseInt(b.getParam("paste_dialog_height", "400")),inline:1})
            })
        });
        b.addButton("pastetext", {title:"paste.paste_text_desc",cmd:"mcePasteText"});
        b.addButton("pasteword", {title:"paste.paste_word_desc",cmd:"mcePasteWord"});
        b.addButton("selectall", {title:"paste.selectall_desc",cmd:"selectall"})
    }});
    tinymce.PluginManager.add("paste", tinymce.plugins.PastePlugin)
})();