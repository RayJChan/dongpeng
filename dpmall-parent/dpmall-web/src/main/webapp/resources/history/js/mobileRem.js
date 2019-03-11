! function(doc, win) {
	var docEl = doc.documentElement,
		resizeEvt = "orientationchange" in window ? "orientationchange" : "resize",
		recalc = function() {
			var clientWidth = docEl.clientWidth;
			clientWidth && (clientWidth >= 750 && (clientWidth = 750), docEl.style.fontSize = 100 * (clientWidth / 750) + "px")
		};
	doc.addEventListener && (win.addEventListener(resizeEvt, recalc, !1), recalc())
}(document, window);