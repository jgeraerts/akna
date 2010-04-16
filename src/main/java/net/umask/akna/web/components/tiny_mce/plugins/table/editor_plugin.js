(function() {
    var b = tinymce.each;

    function a(d, e) {
        var f = e.ownerDocument,c = f.createRange(),g;
        c.setStartBefore(e);
        c.setEnd(d.endContainer, d.endOffset);
        g = f.createElement("body");
        g.appendChild(c.cloneContents());
        return g.innerHTML.replace(/<(br|img|object|embed|input|textarea)[^>]*>/gi, "-").replace(/<[^>]+>/g, "").length == 0
    }

    tinymce.create("tinymce.plugins.TablePlugin", {init:function(c, d) {
        var e = this;
        e.editor = c;
        e.url = d;
        b([
            ["table","table.desc","mceInsertTable",true],
            ["delete_table","table.del","mceTableDelete"],
            ["delete_col","table.delete_col_desc","mceTableDeleteCol"],
            ["delete_row","table.delete_row_desc","mceTableDeleteRow"],
            ["col_after","table.col_after_desc","mceTableInsertColAfter"],
            ["col_before","table.col_before_desc","mceTableInsertColBefore"],
            ["row_after","table.row_after_desc","mceTableInsertRowAfter"],
            ["row_before","table.row_before_desc","mceTableInsertRowBefore"],
            ["row_props","table.row_desc","mceTableRowProps",true],
            ["cell_props","table.cell_desc","mceTableCellProps",true],
            ["split_cells","table.split_cells_desc","mceTableSplitCells",true],
            ["merge_cells","table.merge_cells_desc","mceTableMergeCells",true]
        ], function(f) {
            c.addButton(f[0], {title:f[1],cmd:f[2],ui:f[3]})
        });
        if (c.getParam("inline_styles")) {
            c.onPreProcess.add(function(f, h) {
                var g = f.dom;
                b(g.select("table", h.node), function(j) {
                    var i;
                    if (i = g.getAttrib(j, "width")) {
                        g.setStyle(j, "width", i);
                        g.setAttrib(j, "width")
                    }
                    if (i = g.getAttrib(j, "height")) {
                        g.setStyle(j, "height", i);
                        g.setAttrib(j, "height")
                    }
                })
            })
        }
        c.onInit.add(function() {
            if (!tinymce.isIE && c.getParam("forced_root_block")) {
                function f() {
                    var g = c.getBody().lastChild;
                    if (g && g.nodeName == "TABLE") {
                        c.dom.add(c.getBody(), "p", null, '<br mce_bogus="1" />')
                    }
                }

                if (tinymce.isGecko) {
                    c.onKeyDown.add(function(h, j) {
                        var g,i,k = h.dom;
                        if (j.keyCode == 37 || j.keyCode == 38) {
                            g = h.selection.getRng();
                            i = k.getParent(g.startContainer, "table");
                            if (i && h.getBody().firstChild == i) {
                                if (a(g, i)) {
                                    g = k.createRng();
                                    g.setStartBefore(i);
                                    g.setEndBefore(i);
                                    h.selection.setRng(g);
                                    j.preventDefault()
                                }
                            }
                        }
                    })
                }
                c.onKeyUp.add(f);
                c.onSetContent.add(f);
                c.onVisualAid.add(f);
                c.onPreProcess.add(function(g, i) {
                    var h = i.node.lastChild;
                    if (h && h.childNodes.length == 1 && h.firstChild.nodeName == "BR") {
                        g.dom.remove(h)
                    }
                });
                f()
            }
            if (c && c.plugins.contextmenu) {
                c.plugins.contextmenu.onContextMenu.add(function(i, g, k) {
                    var l,j = c.selection,h = j.getNode() || c.getBody();
                    if (c.dom.getParent(k, "td") || c.dom.getParent(k, "th")) {
                        g.removeAll();
                        if (h.nodeName == "A" && !c.dom.getAttrib(h, "name")) {
                            g.add({title:"advanced.link_desc",icon:"link",cmd:c.plugins.advlink ? "mceAdvLink" : "mceLink",ui:true});
                            g.add({title:"advanced.unlink_desc",icon:"unlink",cmd:"UnLink"});
                            g.addSeparator()
                        }
                        if (h.nodeName == "IMG" && h.className.indexOf("mceItem") == -1) {
                            g.add({title:"advanced.image_desc",icon:"image",cmd:c.plugins.advimage ? "mceAdvImage" : "mceImage",ui:true});
                            g.addSeparator()
                        }
                        g.add({title:"table.desc",icon:"table",cmd:"mceInsertTable",ui:true,value:{action:"insert"}});
                        g.add({title:"table.props_desc",icon:"table_props",cmd:"mceInsertTable",ui:true});
                        g.add({title:"table.del",icon:"delete_table",cmd:"mceTableDelete",ui:true});
                        g.addSeparator();
                        l = g.addMenu({title:"table.cell"});
                        l.add({title:"table.cell_desc",icon:"cell_props",cmd:"mceTableCellProps",ui:true});
                        l.add({title:"table.split_cells_desc",icon:"split_cells",cmd:"mceTableSplitCells",ui:true});
                        l.add({title:"table.merge_cells_desc",icon:"merge_cells",cmd:"mceTableMergeCells",ui:true});
                        l = g.addMenu({title:"table.row"});
                        l.add({title:"table.row_desc",icon:"row_props",cmd:"mceTableRowProps",ui:true});
                        l.add({title:"table.row_before_desc",icon:"row_before",cmd:"mceTableInsertRowBefore"});
                        l.add({title:"table.row_after_desc",icon:"row_after",cmd:"mceTableInsertRowAfter"});
                        l.add({title:"table.delete_row_desc",icon:"delete_row",cmd:"mceTableDeleteRow"});
                        l.addSeparator();
                        l.add({title:"table.cut_row_desc",icon:"cut",cmd:"mceTableCutRow"});
                        l.add({title:"table.copy_row_desc",icon:"copy",cmd:"mceTableCopyRow"});
                        l.add({title:"table.paste_row_before_desc",icon:"paste",cmd:"mceTablePasteRowBefore"});
                        l.add({title:"table.paste_row_after_desc",icon:"paste",cmd:"mceTablePasteRowAfter"});
                        l = g.addMenu({title:"table.col"});
                        l.add({title:"table.col_before_desc",icon:"col_before",cmd:"mceTableInsertColBefore"});
                        l.add({title:"table.col_after_desc",icon:"col_after",cmd:"mceTableInsertColAfter"});
                        l.add({title:"table.delete_col_desc",icon:"delete_col",cmd:"mceTableDeleteCol"})
                    } else {
                        g.add({title:"table.desc",icon:"table",cmd:"mceInsertTable",ui:true})
                    }
                })
            }
        });
        c.onKeyDown.add(function(f, g) {
            if (g.keyCode == 9 && f.dom.getParent(f.selection.getNode(), "TABLE")) {
                if (!tinymce.isGecko && !tinymce.isOpera) {
                    tinyMCE.execInstanceCommand(f.editorId, "mceTableMoveToNextRow", true);
                    return tinymce.dom.Event.cancel(g)
                }
                f.undoManager.add()
            }
        });
        if (!tinymce.isIE) {
            if (c.getParam("table_selection", true)) {
                c.onClick.add(function(f, g) {
                    g = g.target;
                    if (g.nodeName === "TABLE") {
                        f.selection.select(g)
                    }
                })
            }
        }
        c.onNodeChange.add(function(g, f, i) {
            var h = g.dom.getParent(i, "td,th,caption");
            f.setActive("table", i.nodeName === "TABLE" || !!h);
            if (h && h.nodeName === "CAPTION") {
                h = null
            }
            f.setDisabled("delete_table", !h);
            f.setDisabled("delete_col", !h);
            f.setDisabled("delete_table", !h);
            f.setDisabled("delete_row", !h);
            f.setDisabled("col_after", !h);
            f.setDisabled("col_before", !h);
            f.setDisabled("row_after", !h);
            f.setDisabled("row_before", !h);
            f.setDisabled("row_props", !h);
            f.setDisabled("cell_props", !h);
            f.setDisabled("split_cells", !h || (parseInt(g.dom.getAttrib(h, "colspan", "1")) < 2 && parseInt(g.dom.getAttrib(h, "rowspan", "1")) < 2));
            f.setDisabled("merge_cells", !h)
        });
        if (!tinymce.isIE) {
            c.onBeforeSetContent.add(function(f, g) {
                if (g.initial) {
                    g.content = g.content.replace(/<(td|th)([^>]+|)>\s*<\/(td|th)>/g, tinymce.isOpera ? "<$1$2>&nbsp;</$1>" : '<$1$2><br mce_bogus="1" /></$1>')
                }
            })
        }
    },execCommand:function(f, e, g) {
        var d = this.editor,c;
        switch (f) {case"mceTableMoveToNextRow":case"mceInsertTable":case"mceTableRowProps":case"mceTableCellProps":case"mceTableSplitCells":case"mceTableMergeCells":case"mceTableInsertRowBefore":case"mceTableInsertRowAfter":case"mceTableDeleteRow":case"mceTableInsertColBefore":case"mceTableInsertColAfter":case"mceTableDeleteCol":case"mceTableCutRow":case"mceTableCopyRow":case"mceTablePasteRowBefore":case"mceTablePasteRowAfter":case"mceTableDelete":d.execCommand("mceBeginUndoLevel");this._doExecCommand(f, e, g);d.execCommand("mceEndUndoLevel");return true}
        return false
    },getInfo:function() {
        return{longname:"Tables",author:"Moxiecode Systems AB",authorurl:"http://tinymce.moxiecode.com",infourl:"http://wiki.moxiecode.com/index.php/TinyMCE:Plugins/table",version:tinymce.majorVersion + "." + tinymce.minorVersion}
    },_doExecCommand:function(s, aa, af) {
        var W = this.editor,av = W,h = this.url;
        var o = W.selection.getNode();
        var X = W.dom.getParent(o, "tr");
        var ar = W.dom.getParent(o, "td,th");
        var G = W.dom.getParent(o, "table");
        var l = W.contentWindow.document;
        var aw = G ? G.getAttribute("border") : "";
        if (X && ar == null) {
            ar = X.cells[0]
        }
        function aq(y, x) {
            for (var ay = 0; ay < y.length; ay++) {
                if (y[ay].length > 0 && aq(y[ay], x)) {
                    return true
                }
                if (y[ay] == x) {
                    return true
                }
            }
            return false
        }

        function ak(x, i) {
            var y;
            ae = f(G);
            x = x || 0;
            i = i || 0;
            x = Math.max(p.cellindex + x, 0);
            i = Math.max(p.rowindex + i, 0);
            W.execCommand("mceRepaint");
            y = e(ae, i, x);
            if (y) {
                W.selection.select(y.firstChild || y);
                W.selection.collapse(1)
            }
        }

        function ai() {
            var i = l.createElement("td");
            if (!tinymce.isIE) {
                i.innerHTML = '<br mce_bogus="1"/>'
            }
        }

        function k(y) {
            var x = W.dom.getAttrib(y, "colspan");
            var i = W.dom.getAttrib(y, "rowspan");
            x = x == "" ? 1 : parseInt(x);
            i = i == "" ? 1 : parseInt(i);
            return{colspan:x,rowspan:i}
        }

        function am(ay, aA) {
            var i,az;
            for (az = 0; az < ay.length; az++) {
                for (i = 0; i < ay[az].length; i++) {
                    if (ay[az][i] == aA) {
                        return{cellindex:i,rowindex:az}
                    }
                }
            }
            return null
        }

        function e(x, y, i) {
            if (x[y] && x[y][i]) {
                return x[y][i]
            }
            return null
        }

        function B(aD, ay) {
            var aA = [],y = 0,aB,az,ay,aC;
            for (aB = 0; aB < aD.rows.length; aB++) {
                for (az = 0; az < aD.rows[aB].cells.length; az++,y++) {
                    aA[y] = aD.rows[aB].cells[az]
                }
            }
            for (aB = 0; aB < aA.length; aB++) {
                if (aA[aB] == ay) {
                    if (aC = aA[aB + 1]) {
                        return aC
                    }
                }
            }
        }

        function f(aF) {
            var i = [],aG = aF.rows,aD,aC,az,aA,aE,ay,aB;
            for (aC = 0; aC < aG.length; aC++) {
                for (aD = 0; aD < aG[aC].cells.length; aD++) {
                    az = aG[aC].cells[aD];
                    aA = k(az);
                    for (aE = aD; i[aC] && i[aC][aE]; aE++) {
                    }
                    for (aB = aC; aB < aC + aA.rowspan; aB++) {
                        if (!i[aB]) {
                            i[aB] = []
                        }
                        for (ay = aE; ay < aE + aA.colspan; ay++) {
                            i[aB][ay] = az
                        }
                    }
                }
            }
            return i
        }

        function n(aH, aE, az, ay) {
            var y = f(aH),aG = am(y, az);
            var aI,aD;
            if (ay.cells.length != aE.childNodes.length) {
                aI = aE.childNodes;
                aD = null;
                for (var aF = 0; az = e(y, aG.rowindex, aF); aF++) {
                    var aB = true;
                    var aC = k(az);
                    if (aq(aI, az)) {
                        ay.childNodes[aF]._delete = true
                    } else {
                        if ((aD == null || az != aD) && aC.colspan > 1) {
                            for (var aA = aF; aA < aF + az.colSpan; aA++) {
                                ay.childNodes[aA]._delete = true
                            }
                        }
                    }
                    if ((aD == null || az != aD) && aC.rowspan > 1) {
                        az.rowSpan = aC.rowspan + 1
                    }
                    aD = az
                }
                C(G)
            }
        }

        function P(x, i) {
            while ((x = x.previousSibling) != null) {
                if (x.nodeName == i) {
                    return x
                }
            }
            return null
        }

        function ag(ay, az) {
            var x = az.split(",");
            while ((ay = ay.nextSibling) != null) {
                for (var y = 0; y < x.length; y++) {
                    if (ay.nodeName.toLowerCase() == x[y].toLowerCase()) {
                        return ay
                    }
                }
            }
            return null
        }

        function C(ay) {
            if (ay.rows == 0) {
                return
            }
            var y = ay.rows[0];
            do{
                var x = ag(y, "TR");
                if (y._delete) {
                    y.parentNode.removeChild(y);
                    continue
                }
                var az = y.cells[0];
                if (az.cells > 1) {
                    do{
                        var i = ag(az, "TD,TH");
                        if (az._delete) {
                            az.parentNode.removeChild(az)
                        }
                    } while ((az = i) != null)
                }
            } while ((y = x) != null)
        }

        function q(ay, aB, aA) {
            ay.rowSpan = 1;
            var x = ag(aB, "TR");
            for (var az = 1; az < aA && x; az++) {
                var y = l.createElement("td");
                if (!tinymce.isIE) {
                    y.innerHTML = '<br mce_bogus="1"/>'
                }
                if (tinymce.isIE) {
                    x.insertBefore(y, x.cells(ay.cellIndex))
                } else {
                    x.insertBefore(y, x.cells[ay.cellIndex])
                }
                x = ag(x, "TR")
            }
        }

        function T(aG, aI, aC) {
            var y = f(aI);
            var ay = aC.cloneNode(false);
            var aH = am(y, aC.cells[0]);
            var aD = null;
            var aB = W.dom.getAttrib(aI, "border");
            var aA = null;
            for (var aF = 0; aA = e(y, aH.rowindex, aF); aF++) {
                var aE = null;
                if (aD != aA) {
                    for (var az = 0; az < aC.cells.length; az++) {
                        if (aA == aC.cells[az]) {
                            aE = aA.cloneNode(true);
                            break
                        }
                    }
                }
                if (aE == null) {
                    aE = aG.createElement("td");
                    if (!tinymce.isIE) {
                        aE.innerHTML = '<br mce_bogus="1"/>'
                    }
                }
                aE.colSpan = 1;
                aE.rowSpan = 1;
                ay.appendChild(aE);
                aD = aA
            }
            return ay
        }

        switch (s) {case"mceTableMoveToNextRow":var M = B(G, ar);if (!M) {
            W.execCommand("mceTableInsertRowAfter", ar);
            M = B(G, ar)
        }W.selection.select(M);W.selection.collapse(true);return true;case"mceTableRowProps":if (X == null) {
            return true
        }if (aa) {
            W.windowManager.open({url:h + "/row.htm",width:400 + parseInt(W.getLang("table.rowprops_delta_width", 0)),height:295 + parseInt(W.getLang("table.rowprops_delta_height", 0)),inline:1}, {plugin_url:h})
        }return true;case"mceTableCellProps":if (ar == null) {
            return true
        }if (aa) {
            W.windowManager.open({url:h + "/cell.htm",width:400 + parseInt(W.getLang("table.cellprops_delta_width", 0)),height:295 + parseInt(W.getLang("table.cellprops_delta_height", 0)),inline:1}, {plugin_url:h})
        }return true;case"mceInsertTable":if (aa) {
            W.windowManager.open({url:h + "/table.htm",width:400 + parseInt(W.getLang("table.table_delta_width", 0)),height:320 + parseInt(W.getLang("table.table_delta_height", 0)),inline:1}, {plugin_url:h,action:af ? af.action : 0})
        }return true;case"mceTableDelete":var H = W.dom.getParent(W.selection.getNode(), "table");if (H) {
            H.parentNode.removeChild(H);
            W.execCommand("mceRepaint")
        }return true;case"mceTableSplitCells":case"mceTableMergeCells":case"mceTableInsertRowBefore":case"mceTableInsertRowAfter":case"mceTableDeleteRow":case"mceTableInsertColBefore":case"mceTableInsertColAfter":case"mceTableDeleteCol":case"mceTableCutRow":case"mceTableCopyRow":case"mceTablePasteRowBefore":case"mceTablePasteRowAfter":if (!G) {
            return true
        }if (X && G != X.parentNode) {
            G = X.parentNode
        }if (G && X) {
            switch (s) {case"mceTableCutRow":if (!X || !ar) {
                return true
            }W.tableRowClipboard = T(l, G, X);W.execCommand("mceTableDeleteRow");break;case"mceTableCopyRow":if (!X || !ar) {
                return true
            }W.tableRowClipboard = T(l, G, X);break;case"mceTablePasteRowBefore":if (!X || !ar) {
                return true
            }var w = W.tableRowClipboard.cloneNode(true);var j = P(X, "TR");if (j != null) {
                n(G, j, j.cells[0], w)
            }X.parentNode.insertBefore(w, X);break;case"mceTablePasteRowAfter":if (!X || !ar) {
                return true
            }var Y = ag(X, "TR");var w = W.tableRowClipboard.cloneNode(true);n(G, X, ar, w);if (Y == null) {
                X.parentNode.appendChild(w)
            } else {
                Y.parentNode.insertBefore(w, Y)
            }break;case"mceTableInsertRowBefore":if (!X || !ar) {
                return true
            }var ae = f(G);var p = am(ae, ar);var w = l.createElement("tr");var v = null;p.rowindex--;if (p.rowindex < 0) {
                p.rowindex = 0
            }for (var ad = 0; ar = e(ae, p.rowindex, ad); ad++) {
                if (ar != v) {
                    var F = k(ar);
                    if (F.rowspan == 1) {
                        var K = l.createElement("td");
                        if (!tinymce.isIE) {
                            K.innerHTML = '<br mce_bogus="1"/>'
                        }
                        K.colSpan = ar.colSpan;
                        w.appendChild(K)
                    } else {
                        ar.rowSpan = F.rowspan + 1
                    }
                    v = ar
                }
            }X.parentNode.insertBefore(w, X);ak(0, 1);break;case"mceTableInsertRowAfter":if (!X || !ar) {
                return true
            }var ae = f(G);var p = am(ae, ar);var w = l.createElement("tr");var v = null;for (var ad = 0; ar = e(ae, p.rowindex, ad); ad++) {
                if (ar != v) {
                    var F = k(ar);
                    if (F.rowspan == 1) {
                        var K = l.createElement("td");
                        if (!tinymce.isIE) {
                            K.innerHTML = '<br mce_bogus="1"/>'
                        }
                        K.colSpan = ar.colSpan;
                        w.appendChild(K)
                    } else {
                        ar.rowSpan = F.rowspan + 1
                    }
                    v = ar
                }
            }if (w.hasChildNodes()) {
                var Y = ag(X, "TR");
                if (Y) {
                    Y.parentNode.insertBefore(w, Y)
                } else {
                    G.appendChild(w)
                }
            }ak(0, 1);break;case"mceTableDeleteRow":if (!X || !ar) {
                return true
            }var ae = f(G);var p = am(ae, ar);if (ae.length == 1 && G.nodeName == "TBODY") {
                W.dom.remove(W.dom.getParent(G, "table"));
                return true
            }var E = X.cells;var Y = ag(X, "TR");for (var ad = 0; ad < E.length; ad++) {
                if (E[ad].rowSpan > 1) {
                    var K = E[ad].cloneNode(true);
                    var F = k(E[ad]);
                    K.rowSpan = F.rowspan - 1;
                    var al = Y.cells[ad];
                    if (al == null) {
                        Y.appendChild(K)
                    } else {
                        Y.insertBefore(K, al)
                    }
                }
            }var v = null;for (var ad = 0; ar = e(ae, p.rowindex, ad); ad++) {
                if (ar != v) {
                    var F = k(ar);
                    if (F.rowspan > 1) {
                        ar.rowSpan = F.rowspan - 1
                    } else {
                        X = ar.parentNode;
                        if (X.parentNode) {
                            X._delete = true
                        }
                    }
                    v = ar
                }
            }C(G);ak(0, -1);break;case"mceTableInsertColBefore":if (!X || !ar) {
                return true
            }var ae = f(W.dom.getParent(G, "table"));var p = am(ae, ar);var v = null;for (var ab = 0; ar = e(ae, ab, p.cellindex); ab++) {
                if (ar != v) {
                    var F = k(ar);
                    if (F.colspan == 1) {
                        var K = l.createElement(ar.nodeName);
                        if (!tinymce.isIE) {
                            K.innerHTML = '<br mce_bogus="1"/>'
                        }
                        K.rowSpan = ar.rowSpan;
                        ar.parentNode.insertBefore(K, ar)
                    } else {
                        ar.colSpan++
                    }
                    v = ar
                }
            }ak();break;case"mceTableInsertColAfter":if (!X || !ar) {
                return true
            }var ae = f(W.dom.getParent(G, "table"));var p = am(ae, ar);var v = null;for (var ab = 0; ar = e(ae, ab, p.cellindex); ab++) {
                if (ar != v) {
                    var F = k(ar);
                    if (F.colspan == 1) {
                        var K = l.createElement(ar.nodeName);
                        if (!tinymce.isIE) {
                            K.innerHTML = '<br mce_bogus="1"/>'
                        }
                        K.rowSpan = ar.rowSpan;
                        var al = ag(ar, "TD,TH");
                        if (al == null) {
                            ar.parentNode.appendChild(K)
                        } else {
                            al.parentNode.insertBefore(K, al)
                        }
                    } else {
                        ar.colSpan++
                    }
                    v = ar
                }
            }ak(1);break;case"mceTableDeleteCol":if (!X || !ar) {
                return true
            }var ae = f(G);var p = am(ae, ar);var v = null;if ((ae.length > 1 && ae[0].length <= 1) && G.nodeName == "TBODY") {
                W.dom.remove(W.dom.getParent(G, "table"));
                return true
            }for (var ab = 0; ar = e(ae, ab, p.cellindex); ab++) {
                if (ar != v) {
                    var F = k(ar);
                    if (F.colspan > 1) {
                        ar.colSpan = F.colspan - 1
                    } else {
                        if (ar.parentNode) {
                            ar.parentNode.removeChild(ar)
                        }
                    }
                    v = ar
                }
            }ak(-1);break;case"mceTableSplitCells":if (!X || !ar) {
                return true
            }var m = k(ar);var D = m.colspan;var I = m.rowspan;if (D > 1 || I > 1) {
                ar.colSpan = 1;
                for (var an = 1; an < D; an++) {
                    var K = l.createElement("td");
                    if (!tinymce.isIE) {
                        K.innerHTML = '<br mce_bogus="1"/>'
                    }
                    X.insertBefore(K, ag(ar, "TD,TH"));
                    if (I > 1) {
                        q(K, X, I)
                    }
                }
                q(ar, X, I)
            }G = W.dom.getParent(W.selection.getNode(), "table");break;case"mceTableMergeCells":var ap = [];var S = W.selection.getSel();var ae = f(G);if (tinymce.isIE || S.rangeCount == 1) {
                if (aa) {
                    var u = k(ar);
                    W.windowManager.open({url:h + "/merge_cells.htm",width:240 + parseInt(W.getLang("table.merge_cells_delta_width", 0)),height:110 + parseInt(W.getLang("table.merge_cells_delta_height", 0)),inline:1}, {action:"update",numcols:u.colspan,numrows:u.rowspan,plugin_url:h});
                    return true
                } else {
                    var V = parseInt(af.numrows);
                    var d = parseInt(af.numcols);
                    var p = am(ae, ar);
                    if (("" + V) == "NaN") {
                        V = 1
                    }
                    if (("" + d) == "NaN") {
                        d = 1
                    }
                    var c = G.rows;
                    for (var ab = p.rowindex; ab < ae.length; ab++) {
                        var ah = [];
                        for (var ad = p.cellindex; ad < ae[ab].length; ad++) {
                            var g = e(ae, ab, ad);
                            if (g && !aq(ap, g) && !aq(ah, g)) {
                                var O = am(ae, g);
                                if (O.cellindex < p.cellindex + d && O.rowindex < p.rowindex + V) {
                                    ah[ah.length] = g
                                }
                            }
                        }
                        if (ah.length > 0) {
                            ap[ap.length] = ah
                        }
                        var g = e(ae, p.rowindex, p.cellindex);
                        b(av.dom.select("br", g), function(y, x) {
                            if (x > 0 && av.dom.getAttrib("mce_bogus")) {
                                av.dom.remove(y)
                            }
                        })
                    }
                }
            } else {
                var E = [];
                var S = W.selection.getSel();
                var Z = null;
                var ao = null;
                var A = -1,ax = -1,z,au;
                if (S.rangeCount < 2) {
                    return true
                }
                for (var an = 0; an < S.rangeCount; an++) {
                    var aj = S.getRangeAt(an);
                    var ar = aj.startContainer.childNodes[aj.startOffset];
                    if (!ar) {
                        break
                    }
                    if (ar.nodeName == "TD" || ar.nodeName == "TH") {
                        E[E.length] = ar
                    }
                }
                var c = G.rows;
                for (var ab = 0; ab < c.length; ab++) {
                    var ah = [];
                    for (var ad = 0; ad < c[ab].cells.length; ad++) {
                        var g = c[ab].cells[ad];
                        for (var an = 0; an < E.length; an++) {
                            if (g == E[an]) {
                                ah[ah.length] = g
                            }
                        }
                    }
                    if (ah.length > 0) {
                        ap[ap.length] = ah
                    }
                }
                var ao = [];
                var Z = null;
                for (var ab = 0; ab < ae.length; ab++) {
                    for (var ad = 0; ad < ae[ab].length; ad++) {
                        ae[ab][ad]._selected = false;
                        for (var an = 0; an < E.length; an++) {
                            if (ae[ab][ad] == E[an]) {
                                if (A == -1) {
                                    A = ad;
                                    ax = ab
                                }
                                z = ad;
                                au = ab;
                                ae[ab][ad]._selected = true
                            }
                        }
                    }
                }
                for (var ab = ax; ab <= au; ab++) {
                    for (var ad = A; ad <= z; ad++) {
                        if (!ae[ab][ad]._selected) {
                            alert("Invalid selection for merge.");
                            return true
                        }
                    }
                }
            }var t = 1,r = 1;var U = -1;for (var ab = 0; ab < ap.length; ab++) {
                var J = 0;
                for (var ad = 0; ad < ap[ab].length; ad++) {
                    var F = k(ap[ab][ad]);
                    J += F.colspan;
                    if (U != -1 && F.rowspan != U) {
                        alert("Invalid selection for merge.");
                        return true
                    }
                    U = F.rowspan
                }
                if (J > r) {
                    r = J
                }
                U = -1
            }var R = -1;for (var ad = 0; ad < ap[0].length; ad++) {
                var N = 0;
                for (var ab = 0; ab < ap.length; ab++) {
                    var F = k(ap[ab][ad]);
                    N += F.rowspan;
                    if (R != -1 && F.colspan != R) {
                        alert("Invalid selection for merge.");
                        return true
                    }
                    R = F.colspan
                }
                if (N > t) {
                    t = N
                }
                R = -1
            }ar = ap[0][0];ar.rowSpan = t;ar.colSpan = r;for (var ab = 0; ab < ap.length; ab++) {
                for (var ad = 0; ad < ap[ab].length; ad++) {
                    var Q = ap[ab][ad].innerHTML;
                    var L = Q.replace(/[ \t\r\n]/g, "");
                    if (L != "<br/>" && L != "<br>" && L != '<br mce_bogus="1"/>' && (ad + ab > 0)) {
                        ar.innerHTML += Q
                    }
                    if (ap[ab][ad] != ar && !ap[ab][ad]._deleted) {
                        var p = am(ae, ap[ab][ad]);
                        var at = ap[ab][ad].parentNode;
                        at.removeChild(ap[ab][ad]);
                        ap[ab][ad]._deleted = true;
                        if (!at.hasChildNodes()) {
                            at.parentNode.removeChild(at);
                            var ac = null;
                            for (var ad = 0; cellElm = e(ae, p.rowindex, ad); ad++) {
                                if (cellElm != ac && cellElm.rowSpan > 1) {
                                    cellElm.rowSpan--
                                }
                                ac = cellElm
                            }
                            if (ar.rowSpan > 1) {
                                ar.rowSpan--
                            }
                        }
                    }
                }
            }b(av.dom.select("br", ar), function(y, x) {
                if (x > 0 && av.dom.getAttrib(y, "mce_bogus")) {
                    av.dom.remove(y)
                }
            });break}
            G = W.dom.getParent(W.selection.getNode(), "table");
            W.addVisual(G);
            W.nodeChanged()
        }return true}
        return false
    }});
    tinymce.PluginManager.add("table", tinymce.plugins.TablePlugin)
})();