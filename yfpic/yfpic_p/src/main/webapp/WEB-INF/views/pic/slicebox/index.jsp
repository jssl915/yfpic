<!DOCTYPE html>
<html lang="zh-cn">
    <head>
        <title>Slicebox - 3D图片滑块效果</title>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <meta name="description" content="Slicebox - 3D Image Slider with Fallback" />
        <meta name="keywords" content="jquery, css3, 3d, webkit, fallback, slider, css3, 3d transforms, slices, rotate, box, automatic" />
        <meta name="author" content="Pedro Botelho for Codrops" />
		<link rel="shortcut icon" href="../favicon.ico"> 
        <link rel="stylesheet" type="text/css" href="css/demo.css" />
		<link rel="stylesheet" type="text/css" href="css/slicebox.css" />
		<link rel="stylesheet" type="text/css" href="css/custom.css" />
		<link rel="stylesheet" href="http://dreamsky.github.io/main/blog/common/init.css">
		<style>
		.top-banner {
			background-color: rgba(255, 255, 255, 0.55);
		}
		.top-banner a {
			color: #019135;
		}
		h1 {
			margin-top: 100px;
			font-family: 'Microsoft Yahei';
			font-size: 36px;
			color: #019157;
		}		
		</style>
		<script type="text/javascript" src="js/modernizr.custom.46884.js"></script>
	</head>
	<body>
		<div class="container">
			<h1>Slicebox - 3D 图片滑块效果</h1>
			<div class="more">
				<ul id="sb-examples">
					<li class="selected"><a href="index.html">效果一</a></li>
					<li><a href="index2.html">效果二</a></li>
					<li><a href="index3.html">效果三</a></li>
					<li><a href="index4.html">效果四</a></li>
				</ul>
			</div>

			<div class="wrapper">
				<ul id="sb-slider" class="sb-slider">
					<li>
						<a href="http://www.cnblogs.com/lhb25/" target="_blank"><img src="images/1.jpg" alt="image1"/></a>
						<div class="sb-description">
							<h3>Creative Lifesaver</h3>
						</div>
					</li>
					<li>
						<a href="http://www.cnblogs.com/lhb25/" target="_blank"><img src="images/2.jpg" alt="image2"/></a>
						<div class="sb-description">
							<h3>Honest Entertainer</h3>
						</div>
					</li>
					<li>
						<a href="http://www.cnblogs.com/lhb25/" target="_blank"><img src="images/3.jpg" alt="image1"/></a>
						<div class="sb-description">
							<h3>Brave Astronaut</h3>
						</div>
					</li>
					<li>
						<a href="http://www.cnblogs.com/lhb25/" target="_blank"><img src="images/4.jpg" alt="image1"/></a>
						<div class="sb-description">
							<h3>Affectionate Decision Maker</h3>
						</div>
					</li>
					<li>
						<a href="http://www.cnblogs.com/lhb25/" target="_blank"><img src="images/5.jpg" alt="image1"/></a>
						<div class="sb-description">
							<h3>Faithful Investor</h3>
						</div>
					</li>
					<li>
						<a href="http://www.cnblogs.com/lhb25/" target="_blank"><img src="images/6.jpg" alt="image1"/></a>
						<div class="sb-description">
							<h3>Groundbreaking Artist</h3>
						</div>
					</li>
					<li>
						<a href="http://www.cnblogs.com/lhb25/" target="_blank"><img src="images/7.jpg" alt="image1"/></a>
						<div class="sb-description">
							<h3>Selfless Philantropist</h3>
						</div>
					</li>
				</ul>

				<div id="shadow" class="shadow"></div>

				<div id="nav-arrows" class="nav-arrows">
					<a href="#">Next</a>
					<a href="#">Previous</a>
				</div>

				<div id="nav-dots" class="nav-dots">
					<span class="nav-dot-current"></span>
					<span></span>
					<span></span>
					<span></span>
					<span></span>
					<span></span>
					<span></span>
				</div>

			</div><!-- /wrapper -->

		<div class="footer-banner" style="width:728px; margin:30px auto"></div>
		</div>
		<script type="text/javascript" src="http://cdn.staticfile.org/jquery/1.8.2/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery.slicebox.js"></script>
		<script type="text/javascript">
			$(function() {
				var Page = (function() {
					var $navArrows = $( '#nav-arrows' ).hide(),
						$navDots = $( '#nav-dots' ).hide(),
						$nav = $navDots.children( 'span' ),
						$shadow = $( '#shadow' ).hide(),
						slicebox = $( '#sb-slider' ).slicebox( {
							onReady : function() {
								$navArrows.show();
								$navDots.show();
								$shadow.show();
							},
							onBeforeChange : function( pos ) {
								$nav.removeClass( 'nav-dot-current' );
								$nav.eq( pos ).addClass( 'nav-dot-current' );
							}
						} ),
						init = function() {
							initEvents();
						},
						initEvents = function() {
							// add navigation events
							$navArrows.children( ':first' ).on( 'click', function() {
								slicebox.next();
								return false;
							} );
							$navArrows.children( ':last' ).on( 'click', function() {
								slicebox.previous();
								return false;
							} );
							$nav.each( function( i ) {
								$( this ).on( 'click', function( event ) {
									var $dot = $( this );
									if( !slicebox.isActive() ) {
										$nav.removeClass( 'nav-dot-current' );
										$dot.addClass( 'nav-dot-current' );
									}
									slicebox.jump( i + 1 );
									return false;
								} );
							} );
						};
						return { init : init };
				})();
				Page.init();
			});
		</script>
	</body>
</html>	
