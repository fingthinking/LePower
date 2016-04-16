        	作者：i_catdor@163.com
        	时间：2016-03-08
        	描述：菜单 .js
        
		<script src="js/classie.js"></script>
		<script>
			(function() {
				function SVGDDMenu(el, options) {
					this.el = el;
					this.init();
				}
				SVGDDMenu.prototype.init = function() {
					this.shapeEl = this.el.querySelector('div.morph-shape');
					var s = Snap(this.shapeEl.querySelector('svg'));
					this.pathEl = s.select('path');
					this.paths = {
						reset: this.pathEl.attr('d'),
						open: this.shapeEl.getAttribute('data-morph-open')
					};
					this.isOpen = false;
					this.initEvents();
				};
				SVGDDMenu.prototype.initEvents = function() {
					this.el.addEventListener('click', this.toggle.bind(this));
					// For Demo purposes only
					[].slice.call(this.el.querySelectorAll('a')).forEach(function(el) {
						el.onclick = function() {
							return false;
						}
					});
				};
				SVGDDMenu.prototype.toggle = function() {
					var self = this;
					if (this.isOpen) {
						classie.remove(self.el, 'menu--open');
					} else {
						classie.add(self.el, 'menu--open');
					}
					this.pathEl.stop().animate({
						'path': this.paths.open
					}, 320, mina.easeinout, function() {
						self.pathEl.stop().animate({
							'path': self.paths.reset
						}, 1000, mina.elastic);
					});
					this.isOpen = !this.isOpen;
				};
				new SVGDDMenu(document.getElementById('menu'));
			})();
		</script>

		<!--
        	作者：i_catdor@163.com
        	时间：2016-03-08
        	描述：input .js
        -->
		<script>
			(function() {
				// trim polyfill : https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/Trim
				if (!String.prototype.trim) {
					(function() {
						// Make sure we trim BOM and NBSP
						var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
						String.prototype.trim = function() {
							return this.replace(rtrim, '');
						};
					})();
				}
				[].slice.call(document.querySelectorAll('input.input__field')).forEach(function(inputEl) {
					// in case the input is already filled..
					if (inputEl.value.trim() !== '') {
						classie.add(inputEl.parentNode, 'input--filled');
					}
					// events:
					inputEl.addEventListener('focus', onInputFocus);
					inputEl.addEventListener('blur', onInputBlur);
				});

				function onInputFocus(ev) {
					classie.add(ev.target.parentNode, 'input--filled');
				}

				function onInputBlur(ev) {
					if (ev.target.value.trim() === '') {
						classie.remove(ev.target.parentNode, 'input--filled');
					}
				}
			})();
		</script>