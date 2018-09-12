//(function(){
//	if(!(navigator.userAgent.indexOf('Chrome') > -1)){
		goog.provide('plot.a');
		goog.provide('plot.PlotTypes');
		goog.provide('plot.p.point');
//	}
//})();

plot.a = {
    TWO_PI: Math.PI * 2,
    HALF_PI: Math.PI / 2,
    FITTING_COUNT: 100,
    ZERO_TOLERANCE: 0.0001,
};
plot.PlotTypes = {
    ARC: 'arc',
    ELLIPSE: 'ellipse',
    CURVE: 'curve',
    CLOSED_CURVE: 'closedcurve',
    LUNE: 'lune',
    SECTOR: 'sector',
    GATHERING_PLACE: 'gatheringplace',
    STRAIGHT_ARROW: 'straightarrow',
    ASSAULT_DIRECTION: 'assaultdirection',
    ATTACK_ARROW: 'attackarrow',
    TAILED_ATTACK_ARROW: 'tailedattackarrow',
    SQUAD_COMBAT: 'squadcombat',
    TAILED_SQUAD_COMBAT: 'tailedsquadcombat',
    FINE_ARROW: 'finearrow',
    CIRCLE: 'circle',
    DOUBLE_ARROW: 'doublearrow',
    POLYLINE: 'polyline',
    POLYGON: 'polygon',
    RECTANGLE: 'rectangle',
    POINT: 'point',
    Line:'line',
    Polygon:'polygon',
    RegularPolygon:'regularPolygon'
};
plot.p.point = function (pt) {
    this.fa(pt);
};
plot.p.point.prototype.fa = function (pt) {
    this.p = pt ? pt : [
    ];
    if (this.p.length >= 2) {
        this.fb();   //根据绘图类型调用相应的鼠标移动绘图方法
    }
};
//复制点坐标
plot.p.point.prototype.fc = function () {
    return this.p.slice(0);
};

//计算坐标点的个数
plot.p.point.prototype.fd = function () {
    return this.p.length;
};

plot.p.point.prototype.fe = function (pt, len) {
    if (len >= 0 && len < this.p.length) {
        this.p[len] = pt;
        this.fb();
    }
};
plot.p.point.prototype.ff = function (pt) {
    this.fe(pt, this.p.length - 1);
};
plot.p.point.prototype.fb = function () {
};
goog.provide('plot.util');
goog.require('plot.a');

/*计算方法开始*/
//计算两点之间的距离
plot.util.fa = function (pt1, pt2) {
    return Math.sqrt(Math.pow((pt1[0] - pt2[0]), 2) + Math.pow((pt1[1] - pt2[1]), 2));
};

//计算多个点之间的距离
plot.util.fb = function (pts) {
    var len = 0;
    for (var i = 0; i < pts.length - 1; i++) {
    	len += plot.util.fa(pts[i], pts[i + 1]);
    }
    return len;
};

//对多个点之间的距离进行平滑处理
plot.util.fc = function (pts) {
    return Math.pow(plot.util.fb(pts), 0.99);
};

//计算中点坐标
plot.util.fd = function (pt1, pt2) {
    return [(pt1[0] + pt2[0]) / 2,
    (pt1[1] + pt2[1]) / 2];
};

plot.util.fe = function (pt1, pt2, pt3, pt4) {
    if (pt1[1] == pt2[1]) {
        var f = (pt4[0] - pt3[0]) / (pt4[1] - pt3[1]);
        var x = f * (pt1[1] - pt3[1]) + pt3[0];
        var y = pt1[1];
        return [x,
        y];
    }
    if (pt3[1] == pt4[1]) {
        var e = (pt2[0] - pt1[0]) / (pt2[1] - pt1[1]);
        x = e * (pt3[1] - pt1[1]) + pt1[0];
        y = pt3[1];
        return [x,
        y];
    }
    e = (pt2[0] - pt1[0]) / (pt2[1] - pt1[1]);
    f = (pt4[0] - pt3[0]) / (pt4[1] - pt3[1]);
    y = (e * pt1[1] - pt1[0] - f * pt3[1] + pt3[0]) / (e - f);
    x = e * y - e * pt1[1] + pt1[0];
    return [x,
    y];
};

plot.util.ff = function (pt1, pt2, pt3) {
    var mid1 = [
        (pt1[0] + pt2[0]) / 2,
        (pt1[1] + pt2[1]) / 2
    ];
    var mid2 = [
        mid1[0] - pt1[1] + pt2[1],
        mid1[1] + pt1[0] - pt2[0]
    ];
    var mid3 = [
        (pt1[0] + pt3[0]) / 2,
        (pt1[1] + pt3[1]) / 2
    ];
    var mid4 = [
        mid3[0] - pt1[1] + pt3[1],
        mid3[1] + pt1[0] - pt3[0]
    ];
    return plot.util.fe(mid1, mid2, mid3, mid4);
};

//计算角度
plot.util.fg = function (pt1, pt2) {
    var theta;
    var ang = Math.asin(Math.abs(pt2[1] - pt1[1]) / plot.util.fa(pt1, pt2));
    if (pt2[1] >= pt1[1] && pt2[0] >= pt1[0]) {
        theta = ang + Math.PI;
    } else {
        if (pt2[1] >= pt1[1] && pt2[0] < pt1[0]) {
            theta = plot.a.TWO_PI - ang;
        } else {
            if (pt2[1] < pt1[1] && pt2[0] < pt1[0]) {
                theta = ang;
            } else {
                if (pt2[1] < pt1[1] && pt2[0] >= pt1[0]) {
                    theta = Math.PI - ang;
                }
            }
        }
    }
    return theta;
};

//计算相对角度
plot.util.fh = function (pt1, pt2, pt3) {
    var theta = plot.util.fg(pt2, pt1) - plot.util.fg(pt2, pt3);
    return (theta < 0 ? theta + plot.a.TWO_PI : theta);
};

//判断三个点的相对位置
plot.util.fi = function (pt1, pt2, pt3) {
    return ((pt3[1] - pt1[1]) * (pt2[0] - pt1[0]) > (pt2[1] - pt1[1]) * (pt3[0] - pt1[0]));
};

plot.util.fj = function (t, pt1, pt2) {
    var x = pt1[0] + (t * (pt2[0] - pt1[0]));
    var y = pt1[1] + (t * (pt2[1] - pt1[1]));
    return [x,
    y];
};

//根据控制点坐标和起点终点坐标拟合三阶贝塞尔曲线
plot.util.fk = function (t, pt1, ctrl1, ctrl2, pt2) {
    t = Math.max(Math.min(t, 1), 0);
    var tp = 1 - t;
    var t2 = t * t;
    var t3 = t2 * t;
    var tp2 = tp * tp;
    var tp3 = tp2 * tp;
    var x = (tp3 * pt1[0]) + (3 * tp2 * t * ctrl1[0]) + (3 * tp * t2 * ctrl2[0]) + (t3 * pt2[0]);
    var y = (tp3 * pt1[1]) + (3 * tp2 * t * ctrl1[1]) + (3 * tp * t2 * ctrl2[1]) + (t3 * pt2[1]);
    return [x,
    y];
};

//求箭头端点的坐标点
plot.util.fl = function (pt1, pt2, ang, len, bl) {
    var theta = plot.util.fg(pt1, pt2);
    var thetaT = bl ? theta + ang : theta - ang;
    var dx = len * Math.cos(thetaT);
    var dy = len * Math.sin(thetaT);
    return [pt2[0] + dx,
            pt2[1] + dy];
};

//拟合弧形坐标点
plot.util.fm = function (pt, len, ang1, ang2) {
    var x,
    y,
    pts = [
    ];
    var theta = ang2 - ang1;
    theta = theta < 0 ? theta + plot.a.TWO_PI : theta;
    for (var i = 0; i <= plot.a.FITTING_COUNT; i++) {
        var theta1 = ang1 + theta * i / plot.a.FITTING_COUNT;
        x = pt[0] + len * Math.cos(theta1);
        y = pt[1] + len * Math.sin(theta1);
        pts.push([x,
        y]);
    }
    return pts;
};

//根据三个点的坐标反算控制点坐标
plot.util.fn = function (t, pt1, pt2, pt3) {
    var ptfo = plot.util.fo(pt1, pt2, pt3);
    var len = Math.sqrt(ptfo[0] * ptfo[0] + ptfo[1] * ptfo[1]);
    var uX = ptfo[0] / len;
    var uY = ptfo[1] / len;
    var d1 = plot.util.fa(pt1, pt2);
    var d2 = plot.util.fa(pt2, pt3);
    if (len > plot.a.ZERO_TOLERANCE) {
        if (plot.util.fi(pt1, pt2, pt3)) {
            var dt = t * d1;
            var x = pt2[0] - dt * uY;
            var y = pt2[1] + dt * uX;
            var ptResult1 = [
                x,
                y
            ];
            dt = t * d2;
            x = pt2[0] + dt * uY;
            y = pt2[1] - dt * uX;
            var ptResult2 = [
                x,
                y
            ];
        } else {
            dt = t * d1;
            x = pt2[0] + dt * uY;
            y = pt2[1] - dt * uX;
            ptResult1 = [
                x,
                y
            ];
            dt = t * d2;
            x = pt2[0] - dt * uY;
            y = pt2[1] + dt * uX;
            ptResult2 = [
                x,
                y
            ];
        }
    } else {
        x = pt2[0] + t * (pt1[0] - pt2[0]);
        y = pt2[1] + t * (pt1[1] - pt2[1]);
        ptResult1 = [
            x,
            y
        ];
        x = pt2[0] + t * (pt3[0] - pt2[0]);
        y = pt2[1] + t * (pt3[1] - pt2[1]);
        ptResult2 = [
            x,
            y
        ];
    }
    return [ptResult1,
    ptResult2];
};

plot.util.fo = function (pt1, pt2, pt3) {
    var dX1 = pt1[0] - pt2[0];
    var dY1 = pt1[1] - pt2[1];
    var d1 = Math.sqrt(dX1 * dX1 + dY1 * dY1);
    dX1 /= d1;
    dY1 /= d1;
    var dX2 = pt3[0] - pt2[0];
    var dY2 = pt3[1] - pt2[1];
    var d2 = Math.sqrt(dX2 * dX2 + dY2 * dY2);
    dX2 /= d2;
    dY2 /= d2;
    var uX = dX1 + dX2;
    var uY = dY1 + dY2;
    return [uX,
    uY];
};

//拟合贝塞尔曲线
plot.util.fp = function (t, pts) {
    var ptrs = plot.util.fq(pts);  //根据给定坐标点反算控制点(三个已知点坐标计算两个控制点坐标)
    var ctrPts = [
        ptrs
    ];
    for (var i = 0; i < pts.length - 2; i++) {
        var ctlpt1 = pts[i];
        var ctlpt2 = pts[i + 1];
        var ctlpt3 = pts[i + 2];
        var ctlpt4 = plot.util.fn(t, ctlpt1, ctlpt2, ctlpt3);
        ctrPts = ctrPts.concat(ctlpt4);
    }
    var ptfr = plot.util.fr(pts);
    ctrPts.push(ptfr);
    var result = [
    ];
    for (i = 0; i < pts.length - 1; i++) {
        ctlpt1 = pts[i];
        ctlpt2 = pts[i + 1];
        result.push(ctlpt1);
        for (var t = 0; t < plot.a.FITTING_COUNT; t++) {
            var pnt = plot.util.fk(t / plot.a.FITTING_COUNT, ctlpt1, ctrPts[i * 2], ctrPts[i * 2 + 1], ctlpt2);
            result.push(pnt);
        }
        result.push(ctlpt2);
    }
    return result;
};

plot.util.fq = function (pts) {
    var pt1 = pts[0];
    var pt2 = pts[1];
    var pt3 = pts[2];
    var ptfn = plot.util.fn(0, pt1, pt2, pt3);
    var ptfnc = ptfn[0];
    var ptfo = plot.util.fo(pt1, pt2, pt3);
    var len = Math.sqrt(ptfo[0] * ptfo[0] + ptfo[1] * ptfo[1]);
    if (len > plot.a.ZERO_TOLERANCE) {
        var mid = plot.util.fd(pt1, pt2);
        var pX = pt1[0] - mid[0];
        var pY = pt1[1] - mid[1];
        var d1 = plot.util.fa(pt1, pt2);
        var n = 2 / d1;
        var nX = - n * pY;
        var nY = n * pX;
        var a11 = nX * nX - nY * nY;
        var a12 = 2 * nX * nY;
        var a22 = nY * nY - nX * nX;
        var dX = ptfnc[0] - mid[0];
        var dY = ptfnc[1] - mid[1];
        var resultX = mid[0] + a11 * dX + a12 * dY;
        var resultY = mid[1] + a12 * dX + a22 * dY;
    } else {
        resultX = pt1[0] + t * (pt2[0] - pt1[0]);
        resultY = pt1[1] + t * (pt2[1] - pt1[1]);
    }
    return [resultX,
    resultY];
};

plot.util.fr = function (pts) {
    var ptlen = pts.length;
    var pt1 = pts[ptlen - 3];
    var pt2 = pts[ptlen - 2];
    var pt3 = pts[ptlen - 1];
    var ptfn = plot.util.fn(0, pt1, pt2, pt3);
    var ptfnc = ptfn[1];
    var ptfo = plot.util.fo(pt1, pt2, pt3);
    var len = Math.sqrt(ptfo[0] * ptfo[0] + ptfo[1] * ptfo[1]);
    if (len > plot.a.ZERO_TOLERANCE) {
        var mid = plot.util.fd(pt2, pt3);  //计算中点坐标
        var pX = pt3[0] - mid[0];
        var pY = pt3[1] - mid[1];
        var d1 = plot.util.fa(pt2, pt3);  //计算两点间长度
        var n = 2 / d1;
        var nX = - n * pY;
        var nY = n * pX;
        var a11 = nX * nX - nY * nY;
        var a12 = 2 * nX * nY;
        var a22 = nY * nY - nX * nX;
        var dX = ptfnc[0] - mid[0];
        var dY = ptfnc[1] - mid[1];
        var resultX = mid[0] + a11 * dX + a12 * dY;
        var resultY = mid[1] + a12 * dX + a22 * dY;
    } else {
    	resultX = pt3[0] + t * (pt2[0] - pt3[0]);
    	resultY = pt3[1] + t * (pt2[1] - pt3[1]);
    }
    return [resultX,
           resultY];
};

plot.util.fs = function (pts) {
    if (pts.length <= 2) {
        return pts;
    }
    var resultPts = [
    ];
    var n = pts.length - 1;
    for (var t = 0; t <= 1; t += 0.01) {
        var x = y = 0;
        for (var i = 0; i <= n; i++) {
            var ftResult = plot.util.ft(n, i);
            var a = Math.pow(t, i);
            var b = Math.pow((1 - t), (n - i));
            x += ftResult * a * b * pts[i][0];
            y += ftResult * a * b * pts[i][1];
        }
        resultPts.push([x,
        y]);
    }
    resultPts.push(pts[n]);
    return resultPts;
};

//计算n选m的组合
plot.util.ft = function (n, m) {
    return plot.util.fu(n) / (plot.util.fu(m) * plot.util.fu(n - m));
};

//计算阶乘
plot.util.fu = function (n) {
    if (n <= 1) {
        return 1;
    }
    if (n == 2) {
        return 2;
    }
    if (n == 3) {
        return 6;
    }
    if (n == 4) {
        return 24;
    }
    if (n == 5) {
        return 120;
    }
    var result = 1;
    for (var i = 1; i <= n; i++) {
    	result *= i;
    }
    return result;
};

//
plot.util.fv = function (pts) {
    if (pts.length <= 2) {
        return pts;
    }
    var n = 2;
    var resultPts = [
    ];
    var m = pts.length - n - 1;
    resultPts.push(pts[0]);
    for (var i = 0; i <= m; i++) {
        for (var t = 0; t <= 1; t += 0.05) {
            var x = y = 0;
            for (var k = 0; k <= n; k++) {
                var fwResult = plot.util.fw(k, t);
                x += fwResult * pts[i + k][0];
                y += fwResult * pts[i + k][1];
            }
            resultPts.push([x,
            y]);
        }
    }
    resultPts.push(pts[pts.length - 1]);
    return resultPts;
};

plot.util.fw = function (k, t) {
    if (k == 0) {
        return Math.pow(t - 1, 2) / 2;
    }
    if (k == 1) {
        return ( - 2 * Math.pow(t, 2) + 2 * t + 1) / 2;
    }
    if (k == 2) {
        return Math.pow(t, 2) / 2;
    }
    return 0;
};
/*计算法方法结束*/
//画直线
goog.provide("plot.p.plotLine");
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('ol.geom.LineString');

//构造函数
plot.p.plotLine = function(pt){
    goog.base(this, [
    ]);
    this.fa(pt);
};
goog.inherits(plot.p.plotLine, ol.geom.LineString);
goog.mixin(plot.p.plotLine.prototype, plot.p.point.prototype);
//鼠标移动作图方法
plot.p.plotLine.prototype.fb = function(){
	var ptsLen = this.fd();
	if(ptsLen < 2){
		return ;
	}else if(ptsLen === 2){
		 this.setCoordinates(this.p);
	}else {
		 this.setCoordinates(this.p);
	}
};

//画多边形
goog.provide("plot.p.Polygon");
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('ol.geom.LineString');

//构造方法（鼠标点首次点击时触发）
plot.p.Polygon= function(pt){
    goog.base(this, [
    ]);
    this.fa(pt);
};
goog.inherits(plot.p.Polygon, ol.geom.Polygon);
goog.mixin(plot.p.Polygon.prototype, plot.p.point.prototype);
//鼠标移动作图方法
plot.p.Polygon.prototype.fb = function(){
	var ptsLen = this.fd();
	if(ptsLen < 2){
		return ;
	}else if(ptsLen === 2){
		this.setCoordinates([this.p]);
	}else{
		 this.setCoordinates([this.p]);
	}
};

//画矩形
goog.provide("plot.p.RegularPolygon");
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('ol.geom.LineString');
//构造方法（鼠标点首次点击时触发）
plot.p.RegularPolygon= function(pt){
    goog.base(this, [
    ]);
    this.fixPointCount = 2;
    this.fa(pt);
};
goog.inherits(plot.p.RegularPolygon, ol.geom.Polygon);
goog.mixin(plot.p.RegularPolygon.prototype, plot.p.point.prototype);
//鼠标移动作图方法
plot.p.RegularPolygon.prototype.fb = function(){
	var ptsLen = this.fd();
    var pts = this.fc();  //复制坐标点的坐标
	if(ptsLen < 2){
		return ;
	}else if(ptsLen === 2){
		var pt1 = pts[0];
		var pt2 = pts[1];
		var pt3 = [pt2[0],pt1[1]];
		var pt4 = [pt1[0],pt2[1]];
		this.setCoordinates([[pt1,pt3,pt2,pt4,pt1]]);
	}else{
		return ;
	}
};

//画弧线
goog.provide('plot.p.plotARC');
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('ol.geom.Polygon');

//构造方法（鼠标点首次点击时触发）
plot.p.plotARC = function (pt) {
    goog.base(this, [
    ]);
    this.fixPointCount = 3;
    this.fa(pt);
};
goog.inherits(plot.p.plotARC, ol.geom.LineString);
goog.mixin(plot.p.plotARC.prototype, plot.p.point.prototype);

//鼠标移动作图方法
plot.p.plotARC.prototype.fb = function () {
    var ptLen = this.fd();
    if (ptLen < 2) {
        return ;
    }
    if (ptLen == 2) {
        this.setCoordinates(this.p);
    } else {
        var pt1 = this.p[0];
        var pt2 = this.p[1];
        var pt3 = this.p[2];
        var pt4 = plot.util.ff(pt1, pt2, pt3);
        var len = plot.util.fa(pt1, pt4);
        var theta1 = plot.util.fg(pt1, pt4);
        var theta2 = plot.util.fg(pt2, pt4);
        if (plot.util.fi(pt1, pt2, pt3)) {
            var ang1 = theta2;
            var ang2 = theta1;
        } else {
        	ang1 = theta1;
        	ang2 = theta2;
        }
        this.setCoordinates(plot.util.fm(pt4, len, ang1, ang2));
    }
};

//细直箭头
goog.provide('plot.p.plotFineArrow');
goog.require('ol.geom.Polygon');
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('plot.a');
//构造方法（鼠标点首次点击时触发）
plot.p.plotFineArrow = function (pt) {
    goog.base(this, [
    ]);
    this.tailWidthFactor = 0.15;
    this.neckWidthFactor = 0.2;
    this.headWidthFactor = 0.25;
    this.headAngle = Math.PI / 8.5;
    this.neckAngle = Math.PI / 13;
    this.fixPointCount = 2;
    this.fa(pt);
};
goog.inherits(plot.p.plotFineArrow, ol.geom.Polygon);
goog.mixin(plot.p.plotFineArrow.prototype, plot.p.point.prototype);

//鼠标移动时绘图方法
plot.p.plotFineArrow.prototype.fb = function () {
    var pts = this.fc();  //复制坐标点的坐标
    var pt1 = pts[0];
    var pt2 = pts[1];
    var len = plot.util.fc(pts);
    var len1 = len * this.tailWidthFactor;
    var len2 = len * this.neckWidthFactor;
    var len3 = len * this.headWidthFactor;
    var pt3 = plot.util.fl(pt2, pt1, plot.a.HALF_PI, len1, true);
    var pt4 = plot.util.fl(pt2, pt1, plot.a.HALF_PI, len1, false);
    var pt5 = plot.util.fl(pt1, pt2, this.headAngle, len3, false);
    var pt6 = plot.util.fl(pt1, pt2, this.headAngle, len3, true);
    var pt7 = plot.util.fl(pt1, pt2, this.neckAngle, len2, false);
    var pt8 = plot.util.fl(pt1, pt2, this.neckAngle, len2, true);
    var ptResult = [
        pt3,
        pt7,
        pt5,
        pt2,
        pt6,
        pt8,
        pt4
    ];
    this.setCoordinates([ptResult]);
};

//突击方向
goog.provide('plot.p.plotAssaultDirection');
goog.require('plot.p.plotFineArrow');
//构造方法（鼠标点首次点击时触发）
plot.p.plotAssaultDirection = function (pt) {
    goog.base(this, [
    ]);
    this.tailWidthFactor = 0.15;
    this.neckWidthFactor = 0.18;
    this.headWidthFactor = 0.22;
    this.headAngle = Math.PI / 4;
    this.neckAngle = Math.PI * 0.17741;
    this.fa(pt);
};
goog.inherits(plot.p.plotAssaultDirection, plot.p.plotFineArrow);

//攻击方向(头)
goog.provide('plot.p.plotAttackArrow');
goog.require('ol.geom.Polygon');
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('plot.a');

//构造方法（鼠标点首次点击时触发）
plot.p.plotAttackArrow = function (pt) {
    goog.base(this, [
    ]);
    this.headHeightFactor = 0.18;
    this.headWidthFactor = 0.3;
    this.neckHeightFactor = 0.85;
    this.neckWidthFactor = 0.15;
    this.headTailFactor = 0.8;
    this.fa(pt);
};
goog.inherits(plot.p.plotAttackArrow, ol.geom.Polygon);
goog.mixin(plot.p.plotAttackArrow.prototype, plot.p.point.prototype);

//鼠标移动作图方法
plot.p.plotAttackArrow.prototype.fb = function () {
    if (this.fd() < 2) {
        return ;
    }
    if (this.fd() == 2) {
        this.setCoordinates([this.p]);
        return ;
    }
    var ptsfc = this.fc();  //复制点的坐标
    var pt1 = ptsfc[0];
    var pt2 = ptsfc[1];
    if (plot.util.fi(ptsfc[0], ptsfc[1], ptsfc[2])) {
        pt1 = ptsfc[1];
        pt2 = ptsfc[0];
    }
    var mid = plot.util.fd(pt1, pt2);  //计算中点坐标
    var ptmids = [
        mid
    ].concat(ptsfc.slice(2));
    var ffdPts = this.ffd(ptmids, pt1, pt2);
    var ffdPt1 = ffdPts[0];
    var ffdPt2 = ffdPts[4];
    var len = plot.util.fa(pt1, pt2) / plot.util.fc(ptmids);
    var ffcPts = this.ffc(ptmids, ffdPt1, ffdPt2, len);
    var ptsLen = ffcPts.length;
    var resultPts = [
        pt1
    ].concat(ffcPts.slice(0, ptsLen / 2));
    resultPts.push(ffdPt1);
    var resultTemp = [
        pt2
    ].concat(ffcPts.slice(ptsLen / 2, ptsLen));
    resultTemp.push(ffdPt2);
    resultPts = plot.util.fv(resultPts);
    resultTemp = plot.util.fv(resultTemp);
    this.setCoordinates([resultPts.concat(ffdPts, resultTemp.reverse())]);
};
plot.p.plotAttackArrow.prototype.ffd = function (pts, pt1, pt2) {
    var len = plot.util.fc(pts);  //对多个点之间的距离进行平滑处理
    var len1 = len * this.headHeightFactor;
    var lastPt = pts[pts.length - 1];
    len = plot.util.fa(lastPt, pts[pts.length - 2]);
    var len2 = plot.util.fa(pt1, pt2);
    if (len1 > len2 * this.headTailFactor) {
        len1 = len2 * this.headTailFactor;
    }
    var len3 = len1 * this.headWidthFactor;
    var len4 = len1 * this.neckWidthFactor;
    len1 = len1 > len ? len : len1;
    var len5 = len1 * this.neckHeightFactor;
    var pt3 = plot.util.fl(pts[pts.length - 2], lastPt, 0, len1, true); //求箭头两个端点的坐标点
    var pt4 = plot.util.fl(pts[pts.length - 2], lastPt, 0, len5, true);
    var pt5 = plot.util.fl(lastPt, pt3, plot.a.HALF_PI, len3, false);
    var pt6 = plot.util.fl(lastPt, pt3, plot.a.HALF_PI, len3, true);
    var pt7 = plot.util.fl(lastPt, pt4, plot.a.HALF_PI, len4, false);
    var pt8 = plot.util.fl(lastPt, pt4, plot.a.HALF_PI, len4, true);
    return [pt7,
    pt5,
    lastPt,
    pt6,
    pt8];
};
plot.p.plotAttackArrow.prototype.ffc = function (pts, pt1, pt2, len) {
    var len1 = plot.util.fb(pts); //计算多个点之间的距离
    var len2 = plot.util.fc(pts);  //对多个点之间的距离进行平滑
    var len3 = len2 * len;
    var len4 = plot.util.fa(pt1, pt2);
    var len5 = (len3 - len4) / 2;
    var len6 = 0,
    result = [
    ],
    tempResult = [
    ];
    for (var i = 1; i < pts.length - 1; i++) {
        var theta = plot.util.fh(pts[i - 1], pts[i], pts[i + 1]) / 2;
        len6 += plot.util.fa(pts[i - 1], pts[i]);
        var w = (len3 / 2 - len6 / len1 * len5) / Math.sin(theta);
        var result1 = plot.util.fl(pts[i - 1], pts[i], Math.PI - theta, w, true); //求箭头两个端点的坐标点
        var result2 = plot.util.fl(pts[i - 1], pts[i], theta, w, false); 
        result.push(result1);
        tempResult.push(result2);
    }
    return result.concat(tempResult);
};

//攻击方向（尾）
goog.provide('plot.p.plotTailedDbArrow');
goog.require('plot.p.plotAttackArrow');
goog.require('plot.util');
goog.require('plot.a');

//构造函数（鼠标首次点击时触发）
plot.p.plotTailedDbArrow = function (pt) {
    goog.base(this, [
    ]);
    this.headHeightFactor = 0.18;
    this.headWidthFactor = 0.3;
    this.neckHeightFactor = 0.85;
    this.neckWidthFactor = 0.15;
    this.tailWidthFactor = 0.1;
    this.headTailFactor = 0.8;
    this.swallowTailFactor = 1;
    this.swallowTailPnt = null;
    this.fa(pt);
};
goog.inherits(plot.p.plotTailedDbArrow, plot.p.plotAttackArrow);

//鼠标移动时绘图函数
plot.p.plotTailedDbArrow.prototype.fb = function () {
    if (this.fd() == 2) {
        this.setCoordinates([this.p]);
        return ;
    }
    var pnts = this.fc();      //复制坐标点
    var pt1 = pnts[0];
    var pt2 = pnts[1];
    if (plot.util.fi(pnts[0], pnts[1], pnts[2])) {  //判断三个点的相对位置
        pt1 = pnts[1];
        pt2 = pnts[0];
    }
    var mid = plot.util.fd(pt1, pt2);   //计算中点坐标
    var tempPts = [
        mid
    ].concat(pnts.slice(2));
    var ffdPts = this.ffd(tempPts, pt1, pt2);   //调用攻击箭头的函数
    var ffdPt1 = ffdPts[0];
    var ffdPt5 = ffdPts[4];
    var faLen = plot.util.fa(pt1, pt2);   //计算距离
    var faLen2 = plot.util.fc(tempPts);     //对多个点之间的距离进行平滑处理
    var len = faLen2 * this.tailWidthFactor * this.swallowTailFactor;
    this.swallowTailPnt = plot.util.fl(tempPts[1], tempPts[0], 0, len, true);   //计算箭头坐标
    var relativeLen = faLen / faLen2;
    var ffcPts = this.ffc(tempPts, ffdPt1, ffdPt5, relativeLen);     //调用攻击箭头的函数
    var ffcPtsLen = ffcPts.length;
    var resultPts = [
        pt1
    ].concat(ffcPts.slice(0, ffcPtsLen / 2));
    resultPts.push(ffdPt1);
    var resultPtsTemp = [
        pt2
    ].concat(ffcPts.slice(ffcPtsLen / 2, ffcPtsLen));
    resultPtsTemp.push(ffdPt5);
    resultPts = plot.util.fv(resultPts);
    resultPtsTemp = plot.util.fv(resultPtsTemp);
    this.setCoordinates([resultPts.concat(ffdPts, resultPtsTemp.reverse(), [
        this.swallowTailPnt,
        resultPts[0]
    ])]);
};

//分战斗队形（头）
goog.provide('plot.p.plotSquadCombat');
goog.require('plot.p.plotAttackArrow');
goog.require('plot.util');
goog.require('plot.a');

//构造方法（鼠标首次点击时触发）
plot.p.plotSquadCombat = function (pt) {
    goog.base(this, [
    ]);
    this.headHeightFactor = 0.18;
    this.headWidthFactor = 0.3;
    this.neckHeightFactor = 0.85;
    this.neckWidthFactor = 0.15;
    this.tailWidthFactor = 0.1;
    this.fa(pt);
};
goog.inherits(plot.p.plotSquadCombat, plot.p.plotAttackArrow);

//鼠标移动时绘图方法
plot.p.plotSquadCombat.prototype.fb = function () {
    var pnts = this.fc();   //复制点坐标
    var arrowPts = this.ffa(pnts);  //计算箭头坐标
    var ffdPts = this.ffd(pnts, arrowPts[0], arrowPts[1]);   //调用攻击箭头的函数，返回五个点的坐标
    var ffdPt1 = ffdPts[0];
    var ffdPt5 = ffdPts[4];
    var ffcPts = this.ffc(pnts, ffdPt1, ffdPt5, this.tailWidthFactor);  //调用攻击箭头的函数，返回五个点的坐标
    var ffcPtsLen = ffcPts.length;
    var resultPts = [
        arrowPts[0]
    ].concat(ffcPts.slice(0, ffcPtsLen / 2));
    resultPts.push(ffdPt1);
    var temPts = [
        arrowPts[1]
    ].concat(ffcPts.slice(ffcPtsLen / 2, ffcPtsLen));
    temPts.push(ffdPt5);
    resultPts = plot.util.fv(resultPts);
    temPts = plot.util.fv(temPts);
    this.setCoordinates([resultPts.concat(ffdPts, temPts.reverse())]);
};
plot.p.plotSquadCombat.prototype.ffa = function (pts) {
    var fcLen = plot.util.fc(pts);   //计算所有点之间的距离
    var tailWidthFactorLen = fcLen * this.tailWidthFactor;
    var arrowPt1 = plot.util.fl(pts[1], pts[0], plot.a.HALF_PI, tailWidthFactorLen, false);   //求箭头端点的坐标点
    var arrowPt2 = plot.util.fl(pts[1], pts[0], plot.a.HALF_PI, tailWidthFactorLen, true);    //求箭头端点的坐标点
    return [arrowPt1,
    arrowPt2];
};

//分战斗队形（尾）
goog.provide('plot.p.TailedSquadCombat');
goog.require('plot.p.plotAttackArrow');
goog.require('plot.util');
goog.require('plot.a');

//构造方法（鼠标首次点击时触发）
plot.p.TailedSquadCombat = function (pt) {
    goog.base(this, [
    ]);
    this.headHeightFactor = 0.18;
    this.headWidthFactor = 0.3;
    this.neckHeightFactor = 0.85;
    this.neckWidthFactor = 0.15;
    this.tailWidthFactor = 0.1;
    this.swallowTailFactor = 1;
    this.swallowTailPnt = null;
    this.fa(pt);
};
goog.inherits(plot.p.TailedSquadCombat, plot.p.plotAttackArrow);

//鼠标移动时绘图方法
plot.p.TailedSquadCombat.prototype.fb = function () {
    var pnts = this.fc();  //复制坐标点
    var ffaPts = this.ffa(pnts);
    var ffdPts = this.ffd(pnts, ffaPts[0], ffaPts[2]);    //调用攻击箭头的函数，返回五个点的坐标
    var ffdPt1 = ffdPts[0];
    var ffdPt5 = ffdPts[4];
    var ffcPts = this.ffc(pnts, ffdPt1, ffdPt5, this.tailWidthFactor);    //调用攻击箭头的函数，返回五个点的坐标
    var ffcPtsLen = ffcPts.length;
    var resultPts = [
        ffaPts[0]
    ].concat(ffcPts.slice(0, ffcPtsLen / 2));
    resultPts.push(ffdPt1);
    var resultTempPts = [
        ffaPts[2]
    ].concat(ffcPts.slice(ffcPtsLen / 2, ffcPtsLen));
    resultTempPts.push(ffdPt5);
    resultPts = plot.util.fv(resultPts);
    resultTempPts = plot.util.fv(resultTempPts);
    this.setCoordinates([resultPts.concat(ffdPts, resultTempPts.reverse(), [
        ffaPts[1],
        resultPts[0]
    ])]);
};
plot.p.TailedSquadCombat.prototype.ffa = function (pts) {
    var fcLen = plot.util.fc(pts);    //对多个点之间的距离进行平滑处理
    var tailWidthFactorLen = fcLen * this.tailWidthFactor;
    var arrowPt1 = plot.util.fl(pts[1], pts[0], plot.a.HALF_PI, tailWidthFactorLen, false);   //计算箭头坐标
    var arrowPt2 = plot.util.fl(pts[1], pts[0], plot.a.HALF_PI, tailWidthFactorLen, true);    //计算箭头坐标
    var len = tailWidthFactorLen * this.swallowTailFactor;
    var arrowPt3 = plot.util.fl(pts[1], pts[0], 0, len, true);
    return [arrowPt1,
            arrowPt3,
            arrowPt2];
};

//画圆形
goog.provide('plot.p.plotCircle');
goog.require('ol.geom.Polygon');
goog.require('plot.p.point');
goog.require('plot.a');
goog.require('plot.util');
//构造方法（鼠标点首次点击时触发）
plot.p.plotCircle = function (pt) {
    goog.base(this, [
    ]);
    this.fixPointCount = 2;
    this.fa(pt);
};
goog.inherits(plot.p.plotCircle, ol.geom.Polygon);
goog.mixin(plot.p.plotCircle.prototype, plot.p.point.prototype);
//鼠标移动作图方法
plot.p.plotCircle.prototype.fb = function () {
    var pt1 = this.p[0];
    var len = plot.util.fa(pt1, this.p[1]); //计算长度（圆形半径）
    this.setCoordinates([this.fbPoints(pt1, len)]);
};
plot.p.plotCircle.prototype.fbPoints = function (pt, len) {
    var x,
    y,
    theta,
    resultPts = [
    ];
    for (var i = 0; i <= plot.a.FITTING_COUNT; i++) {
        theta = Math.PI * 2 * i / plot.a.FITTING_COUNT;
        x = pt[0] + len * Math.cos(theta);
        y = pt[1] + len * Math.sin(theta);
        resultPts.push([x,
        y]);
    }
    return resultPts;
};

//画闭合曲线
goog.provide('plot.p.plotClosedCurve');
goog.require('ol.geom.Polygon');
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('plot.a');
//构造方法（鼠标点首次点击时触发）
plot.p.plotClosedCurve = function (pt) {
    this.t = 0.3;
    goog.base(this, [
    ]);
    this.fa(pt);
};
goog.inherits(plot.p.plotClosedCurve, ol.geom.Polygon);
goog.mixin(plot.p.plotClosedCurve.prototype, plot.p.point.prototype);
//鼠标移动作图方法
plot.p.plotClosedCurve.prototype.fb = function () {
    if (this.fd() == 2) {
        this.setCoordinates([this.p]);
    } else {
        var fcPts = this.fc(); //复制坐标点
        fcPts.push(fcPts[0], fcPts[1]);
        var ctlPts = [
        ];
        for (var i = 0; i < fcPts.length - 2; i++) {
            var ctlPtTemp = plot.util.fn(this.t, fcPts[i], fcPts[i + 1], fcPts[i + 2]);  //根据三个点坐标反算控制点坐标
            ctlPts = ctlPts.concat(ctlPtTemp);
        }
        var ctlPtsLen = ctlPts.length;
        ctlPts = [
            ctlPts[ctlPtsLen - 1]
        ].concat(ctlPts.slice(0, ctlPtsLen - 1));
        var resultPts = [
        ];
        for (i = 0; i < fcPts.length - 2; i++) {
            var pt1 = fcPts[i];
            var pt2 = fcPts[i + 1];
            resultPts.push(pt1);
            for (var t = 0; t <= plot.a.FITTING_COUNT; t++) {
                var pnt = plot.util.fk(t / plot.a.FITTING_COUNT, pt1, ctlPts[i * 2], ctlPts[i * 2 + 1], pt2); //根据控制点坐标拟合曲线点坐标
                resultPts.push(pnt);
            }
            resultPts.push(pt2);
        }
        this.setCoordinates([resultPts]);
    }
};
//画曲线
goog.provide('plot.p.plotCurve');
goog.require('ol.geom.LineString');
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('plot.a');
//构造方法（鼠标点首次点击时触发）
plot.p.plotCurve = function (pt) {
    this.t = 0.3;
    goog.base(this, [
    ]);
    this.fa(pt);
};
goog.inherits(plot.p.plotCurve, ol.geom.LineString);
goog.mixin(plot.p.plotCurve.prototype, plot.p.point.prototype);
//鼠标移动作图方法
plot.p.plotCurve.prototype.fb = function () {
    if (this.fd() == 2) {
        this.setCoordinates(this.p);
    } else {
        this.setCoordinates(plot.util.fp(this.t, this.p));
    }
};

//画双箭头
goog.provide('plot.p.plotDbArrow');
goog.require('ol.geom.Polygon');
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('plot.a');

//构造方法（鼠标点首次点击时触发）
plot.p.plotDbArrow = function (pt) {
    goog.base(this, [
    ]);
    this.headHeightFactor = 0.25;
    this.headWidthFactor = 0.3;
    this.neckHeightFactor = 0.85;
    this.neckWidthFactor = 0.15;
    this.connPoint = null;
    this.tempPoint4 = null;
    this.fixPointCount = 4;
    this.fa(pt);
};
goog.inherits(plot.p.plotDbArrow, ol.geom.Polygon);
goog.mixin(plot.p.plotDbArrow.prototype, plot.p.point.prototype);
//鼠标移动作图方法
plot.p.plotDbArrow.prototype.fb = function () {
    if (this.fd() == 2) {
        this.setCoordinates([this.p]);
        return ;
    }
    var pt1 = this.p[0];
    var pt2 = this.p[1];
    var pt3 = this.p[2];
    if (this.fd() == 3) {  //如果是3个点
        this.tempPoint4 = this.ffb(pt1, pt2, pt3);
    } else {   //如果是四个点
        this.tempPoint4 = this.p[3];
    }
    this.connPoint = plot.util.fd(pt1, pt2); //计算中点坐标
    var ffePt1s,
    ffePt2s;
    if (plot.util.fi(pt1, pt2, pt3)) { //判断三个点的相对位置
        ffePt1s = this.ffe(pt1, this.connPoint, this.tempPoint4, false);
        ffePt2s = this.ffe(this.connPoint, pt2, pt3, true);
    } else {
        ffePt1s = this.ffe(pt2, this.connPoint, pt3, false);
        ffePt2s = this.ffe(this.connPoint, pt1, this.tempPoint4, true);
    }
    var m = ffePt1s.length;
    var t = (m - 5) / 2;
    var tempPts1 = ffePt1s.slice(0, t);
    var tempPts2 = ffePt1s.slice(t, t + 5);
    var tempPts3 = ffePt1s.slice(t + 5, m);
    var tempPts4 = ffePt2s.slice(0, t);
    var tempPts5 = ffePt2s.slice(t, t + 5);
    var tempPts6 = ffePt2s.slice(t + 5, m);
    tempPts4 = plot.util.fs(tempPts4);
    var tempPts7 = plot.util.fs(tempPts6.concat(tempPts1.slice(1)));
    tempPts3 = plot.util.fs(tempPts3);
    var resultPts = tempPts4.concat(tempPts5, tempPts7, tempPts2, tempPts3);
    this.setCoordinates([resultPts]);
};
plot.p.plotDbArrow.prototype.ffe = function (pt1, pt2, pt3, bl) {
    var fdMid = plot.util.fd(pt1, pt2);
    var len = plot.util.fa(fdMid, pt3);
    var arrowPt1 = plot.util.fl(pt3, fdMid, 0, len * 0.3, true);  //计算箭头坐标
    var arrowPt2 = plot.util.fl(pt3, fdMid, 0, len * 0.5, true);
    arrowPt1 = plot.util.fl(fdMid, arrowPt1, plot.a.HALF_PI, len / 5, bl);  //计算箭头坐标
    arrowPt2 = plot.util.fl(fdMid, arrowPt2, plot.a.HALF_PI, len / 4, bl);
    var tempPts = [
        fdMid,
        arrowPt1,
        arrowPt2,
        pt3
    ];
    var ffdPts = this.ffd(tempPts, this.headHeightFactor, this.headWidthFactor, this.neckHeightFactor, this.neckWidthFactor);
    var ffdPt1 = ffdPts[0];
    var ffdPt5 = ffdPts[4];
    var len2 = plot.util.fa(pt1, pt2) / plot.util.fc(tempPts) / 2;
    var ffcPts = this.ffc(tempPts, ffdPt1, ffdPt5, len2);  //计算箭头的坐标
    var n = ffcPts.length;
    var tempPts2 = ffcPts.slice(0, n / 2);
    var tempPts3 = ffcPts.slice(n / 2, n);
    tempPts2.push(ffdPt1);
    tempPts3.push(ffdPt5);
    tempPts2 = tempPts2.reverse();
    tempPts2.push(pt2);
    tempPts3 = tempPts3.reverse();
    tempPts3.push(pt1);
    return tempPts2.reverse().concat(ffdPts, tempPts3);
};
plot.p.plotDbArrow.prototype.ffd = function (pts) {
    var len = plot.util.fc(pts);  ////对多个点之间的距离进行平滑处理
    var headHeightFactorLen = len * this.headHeightFactor;
    var lastPt = pts[pts.length - 1];
   // var _df = plot.util.fa(_db, _dc);
    var headWidthFactorLen = headHeightFactorLen * this.headWidthFactor;
    var neckWidthFactorLen = headHeightFactorLen * this.neckWidthFactor;
    var neckHeightFactorLen = headHeightFactorLen * this.neckHeightFactor;
    var flPt1 = plot.util.fl(pts[pts.length - 2], lastPt, 0, headHeightFactorLen, true);   //计算箭头坐标
    var flPt2 = plot.util.fl(pts[pts.length - 2], lastPt, 0, neckHeightFactorLen, true);   //计算箭头坐标
    var flPt3 = plot.util.fl(lastPt, flPt1, plot.a.HALF_PI, headWidthFactorLen, false);    //计算箭头坐标
    var flPt4 = plot.util.fl(lastPt, flPt1, plot.a.HALF_PI, headWidthFactorLen, true);    //计算箭头坐标
    var flPt5 = plot.util.fl(lastPt, flPt2, plot.a.HALF_PI, neckWidthFactorLen, false);    //计算箭头坐标
    var flPt6 = plot.util.fl(lastPt, flPt2, plot.a.HALF_PI, neckWidthFactorLen, true);    //计算箭头坐标
    return [flPt5,
    flPt3,
    lastPt,
    flPt4,
    flPt6];
};
plot.p.plotDbArrow.prototype.ffc =  function (pts, pt1, pt2, length) {
    var fbLen = plot.util.fb(pts);  //计算多个点之间的距离
    var len = plot.util.fc(pts);  //平滑多个点之间的距离
    var len2 = len * length;
    var faLen = plot.util.fa(pt1, pt2);  //计算两点间的距离
    var tempLen = (len2 - faLen) / 2;
    var tempLen2 = 0,
    resultPts = [
    ],
    resultPts2 = [
    ];
    for (var i = 1; i < pts.length - 1; i++) {
        var relativeAng = plot.util.fh(pts[i - 1], pts[i], pts[i + 1]) / 2;    //计算相对角度
        tempLen2 += plot.util.fa(pts[i - 1], pts[i]);
        var w = (len2 / 2 - tempLen2 / fbLen * tempLen) / Math.sin(relativeAng);
        var arrowPt = plot.util.fl(pts[i - 1], pts[i], Math.PI - relativeAng, w, true);  //计算箭头坐标
        var arrowPt2 = plot.util.fl(pts[i - 1], pts[i], relativeAng, w, false);    //计算箭头坐标
        resultPts.push(arrowPt);
        resultPts2.push(arrowPt2);
    }
    return resultPts.concat(resultPts2);
};
plot.p.plotDbArrow.prototype.ffb = function (pt1, pt2, pt3) {
    var fdmid = plot.util.fd(pt1, pt2);   //计算中点坐标
    var len = plot.util.fa(fdmid, pt3);    //计算两点间距离
    var relativeAng = plot.util.fh(pt1, fdmid, pt3);  //计算相对角度
    var resultPt,
    len1,
    len2,
    mid;
    if (relativeAng < plot.a.HALF_PI) {
        len1 = len * Math.sin(relativeAng);
        len2 = len * Math.cos(relativeAng);
        mid = plot.util.fl(pt1, fdmid, plot.a.HALF_PI, len1, false);  //计算箭头坐标
        resultPt = plot.util.fl(fdmid, mid, plot.a.HALF_PI, len2, true);
    } else {
        if (relativeAng >= plot.a.HALF_PI && relativeAng < Math.PI) {
            len1 = len * Math.sin(Math.PI - relativeAng);
            len2 = len * Math.cos(Math.PI - relativeAng);
            mid = plot.util.fl(pt1, fdmid, plot.a.HALF_PI, len1, false);  //计算箭头坐标
            resultPt = plot.util.fl(fdmid, mid, plot.a.HALF_PI, len2, false);
        } else {
            if (relativeAng >= Math.PI && relativeAng < Math.PI * 1.5) {
                len1 = len * Math.sin(relativeAng - Math.PI);
                len2 = len * Math.cos(relativeAng - Math.PI);
                mid = plot.util.fl(pt1, fdmid, plot.a.HALF_PI, len1, true);  //计算箭头坐标
                resultPt = plot.util.fl(fdmid, mid, plot.a.HALF_PI, len2, true);
            } else {
                len1 = len * Math.sin(Math.PI * 2 - relativeAng);
                len2 = len * Math.cos(Math.PI * 2 - relativeAng);
                mid = plot.util.fl(pt1, fdmid, plot.a.HALF_PI, len1, true);  //计算箭头坐标
                resultPt = plot.util.fl(fdmid, mid, plot.a.HALF_PI, len2, false);
            }
        }
    }
    return resultPt;
};

//画椭圆
goog.provide('plot.p.plotEllipse');
goog.require('ol.geom.Polygon');
goog.require('plot.p.point');
goog.require('plot.a');
goog.require('plot.util');

//构造方法（鼠标首次点击时触发）
plot.p.plotEllipse = function (pt) {
    goog.base(this, [
    ]);
    this.fixPointCount = 2;
    this.fa(pt);
};
goog.inherits(plot.p.plotEllipse, ol.geom.Polygon);
goog.mixin(plot.p.plotEllipse.prototype, plot.p.point.prototype);

//鼠标移动作图方法
plot.p.plotEllipse.prototype.fb = function () {
    if (this.fd() < 2) {
        return ;
    }
    var pnt1 = this.p[0];
    var pnt2 = this.p[1];
    var len = plot.util.fd(pnt1, pnt2);
    var ptx = Math.abs((pnt1[0] - pnt2[0]) / 2);
    var pty = Math.abs((pnt1[1] - pnt2[1]) / 2);
    this.setCoordinates([this.fbPoints(len, ptx, pty)]);
};
plot.p.plotEllipse.prototype.fbPoints = function (len, ptx, pty) {
    var x,
    y,
    theta,
    resultPts = [
    ];
    for (var i = 0; i <= plot.a.FITTING_COUNT; i++) {
        theta = Math.PI * 2 * i / plot.a.FITTING_COUNT;
        x = len[0] + ptx * Math.cos(theta);
        y = len[1] + pty * Math.sin(theta);
        resultPts.push([x,
        y]);
    }
    return resultPts;
};

//画月牙形
goog.provide('plot.p.plotLune');
goog.require('ol.geom.Polygon');
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('plot.a');

//构造方法（鼠标点首次点击时触发）
plot.p.plotLune = function (pt) {
    goog.base(this, [
    ]);
    this.fixPointCount = 3;
    this.fa(pt);
};
goog.inherits(plot.p.plotLune, ol.geom.Polygon);
goog.mixin(plot.p.plotLune.prototype, plot.p.point.prototype);

//鼠标移动作图方法
plot.p.plotLune.prototype.fb = function () {
    if (this.fd() < 2) {
        return ;
    }
    var pnts = this.fc();
    if (this.fd() == 2) {
        var mid = plot.util.fd(pnts[0], pnts[1]);
        var d = plot.util.fa(pnts[0], mid);
        var pnt = plot.util.fl(pnts[0], mid, plot.a.HALF_PI, d); //计算箭头端点的坐标点
        pnts.push(pnt);
    }
    var pnt1 = pnts[0];
    var pnt2 = pnts[1];
    var pnt3 = pnts[2];
    var ffpt = plot.util.ff(pnt1, pnt2, pnt3); 
    var len = plot.util.fa(pnt1, ffpt);  //计算距离
    var theta1 = plot.util.fg(pnt1, ffpt);   //计算角度
    var theta2 = plot.util.fg(pnt2, ffpt);   //计算角度
    if (plot.util.fi(pnt1, pnt2, pnt3)) {  //判断三个点的相对位置
        var ang1 = theta2;
        var ang2 = theta1;
    } else {
        ang1 = theta1;
        ang2 = theta2;
    }
    var pnts = plot.util.fm(ffpt, len, ang1, ang2);  //拟合弧形坐标点
    pnts.push(pnts[0]);
    this.setCoordinates([pnts]);
};

//画扇区
goog.provide('plot.p.plotSector');
goog.require('ol.geom.Polygon');
goog.require('plot.p.point');
goog.require('plot.util');

//构造方法（鼠标点首次点击时触发
plot.p.plotSector = function (pt) {
    goog.base(this, [
    ]);
    this.fixPointCount = 3;
    this.fa(pt);
};
goog.inherits(plot.p.plotSector, ol.geom.Polygon);
goog.mixin(plot.p.plotSector.prototype, plot.p.point.prototype);

//鼠标移动作图方法
plot.p.plotSector.prototype.fb = function () {
    if (this.fd() < 2) {
        return ;
    }
    if (this.fd() == 2) {
        this.setCoordinates([this.p]);
    } else {
        var pnts = this.fc();   //复制坐标点
        var pt1 = pnts[0];
        var pnt2 = pnts[1];
        var pnt3 = pnts[2];
        var len = plot.util.fa(pnt2, pt1);  //计算距离
        var theta1 = plot.util.fg(pnt2, pt1);  //计算角度
        var theta2 = plot.util.fg(pnt3, pt1);  //计算角度
        var pts = plot.util.fm(pt1, len, theta1, theta2);   ////拟合弧线坐标点
        pts.push(pt1, pts[0]);
        this.setCoordinates([pts]);
    }
};

//画直线箭头
goog.provide('plot.p.plotStraightArrow');
goog.require('plot.p.point');
goog.require('plot.util');
goog.require('ol.geom.LineString');
plot.p.plotStraightArrow = function (pt) {
    goog.base(this, [
    ]);
    this.fixPointCount = 2;
    this.maxArrowLength = 3000000;
    this.arrowLengthScale = 5;
    this.fa(pt);
};
goog.inherits(plot.p.plotStraightArrow, ol.geom.LineString);
goog.mixin(plot.p.plotStraightArrow.prototype, plot.p.point.prototype);
plot.p.plotStraightArrow.prototype.fb = function () {
    if (this.fd() < 2) {
        return ;
    }
    var pnts = this.fc();  //复制坐标点
    var pnt1 = pnts[0];
    var pnt2 = pnts[1];
    var faLen = plot.util.fa(pnt1, pnt2);  //计算距离
    var len = faLen / this.arrowLengthScale;
    len = len > this.maxArrowLength ? this.maxArrowLength : len;
    var arrowPt1 = plot.util.fl(pnt1, pnt2, Math.PI / 6, len, false);   //计算箭头坐标
    var arrowPt2 = plot.util.fl(pnt1, pnt2, Math.PI / 6, len, true);    //计算箭头坐标
    this.setCoordinates([pnt1,
    pnt2,
    arrowPt1,
    pnt2,
    arrowPt2]);
};
goog.provide('plot.PlotObject');
goog.require('plot.PlotTypes');
goog.require('plot.p.plotLine');
goog.require('plot.p.Polygon');
goog.require('plot.p.RegularPolygon');
goog.require('plot.p.plotARC');
goog.require('plot.p.plotEllipse');
goog.require('plot.p.plotCurve');
goog.require('plot.p.plotClosedCurve');
goog.require('plot.p.plotLune');
goog.require('plot.p.plotSector');
goog.require('plot.p.plotFineArrow');
goog.require('plot.p.plotStraightArrow');
goog.require('plot.p.plotAssaultDirection');
goog.require('plot.p.plotAttackArrow');
goog.require('plot.p.plotCircle');
goog.require('plot.p.plotDbArrow');
goog.require('plot.p.plotTailedDbArrow');
goog.require('plot.p.plotSquadCombat');
goog.require('plot.p.TailedSquadCombat');
plot.PlotObject.createObject = function (type, pt) {
    switch (type) {
    case plot.PlotTypes.Line:
    	return new plot.p.plotLine(pt);   //直线
    case plot.PlotTypes.Polygon:
    	return new plot.p.Polygon(pt);   //多边形
    case plot.PlotTypes.RegularPolygon:
    	return new plot.p.RegularPolygon(pt);   //矩形
    case plot.PlotTypes.Orientation:
    	return new plot.p.Orientation(pt);  //方位
    case plot.PlotTypes.ARC:
        return new plot.p.plotARC(pt);//弧形
    case plot.PlotTypes.ELLIPSE:
        return new plot.p.plotEllipse(pt); //椭圆
    case plot.PlotTypes.CURVE:  
        return new plot.p.plotCurve(pt); //曲线
    case plot.PlotTypes.CLOSED_CURVE:
        return new plot.p.plotClosedCurve(pt);  //闭合曲线
    case plot.PlotTypes.LUNE:
        return new plot.p.plotLune(pt);  //月牙形
    case plot.PlotTypes.SECTOR:
        return new plot.p.plotSector(pt);  //扇区
    case plot.PlotTypes.STRAIGHT_ARROW:
        return new plot.p.plotStraightArrow(pt);  //直线箭头
    case plot.PlotTypes.ASSAULT_DIRECTION:
        return new plot.p.plotAssaultDirection(pt);  //攻击方向
    case plot.PlotTypes.ATTACK_ARROW:
        return new plot.p.plotAttackArrow(pt);  //攻击箭头
    case plot.PlotTypes.FINE_ARROW:
        return new plot.p.plotFineArrow(pt);  //细直箭头
    case plot.PlotTypes.CIRCLE:
        return new plot.p.plotCircle(pt);   //圆形
    case plot.PlotTypes.DOUBLE_ARROW:
        return new plot.p.plotDbArrow(pt);   //双箭头
    case plot.PlotTypes.TAILED_ATTACK_ARROW:
        return new plot.p.plotTailedDbArrow(pt);    //攻击箭头(尾)
    case plot.PlotTypes.SQUAD_COMBAT:
        return new plot.p.plotSquadCombat(pt);   //分队战斗行动
    case plot.PlotTypes.TAILED_SQUAD_COMBAT:
        return new plot.p.TailedSquadCombat(pt);   //分队战斗行动（尾）
    }
    return null;
};
goog.provide('plot.event.PlotDrawEvent');
goog.provide('plot.tool.PlotDraw');
goog.require('goog.events.Event');
goog.require('ol.Map');
goog.require('ol.Observable');
goog.require('ol.Feature');
goog.require('ol.geom.Point');
goog.require('ol.layer.Vector');
goog.require('ol.style.Stroke');
goog.require('ol.style.Fill');
goog.require('ol.style.Style');
goog.require('ol.Collection');
goog.require('ol.interaction.DoubleClickZoom');
goog.require('plot.PlotTypes');
goog.require('plot.PlotObject');
plot.event.PlotDrawEvent = function (type, feature) {
    goog.base(this, type);
    this.feature = feature;
};
goog.inherits(plot.event.PlotDrawEvent, goog.events.Event);
plot.event.PlotDrawEvent.DRAW_START = 'draw_start';
plot.event.PlotDrawEvent.DRAW_END = 'draw_end';

//标绘函数入口（标绘对象构造函数）
plot.tool.PlotDraw = function (map) {
    goog.base(this);
    this.p = null;     //点坐标
    this.plot = null;   //具体的标绘对象（geometry类型）
    this.feature = null;   //要素
    this.plotType = null;   //标绘类型（直线、曲线、弧线等）
    this.plotParams = null;  //标绘参数
    this.mapViewport = null;  //标绘窗口
    this.interaction = null;   //保存ol.interaction.DoubleClickZoom对象
    var stroke = new ol.style.Stroke({
        color: '#000000',
        width: 1.25
    });
    var fill = new ol.style.Fill({
        color: 'rgba(0,0,0,0.4)'
    });
    this.style = new ol.style.Style({
        fill: fill,
        stroke: stroke
    });
    this.featureOverlay = new ol.layer.Vector({
    	source: new ol.source.Vector(),
    	style: this.style
    });
//    this.featureOverlay.setStyle(this.style);
    this.setMap(map);
};
goog.inherits(plot.tool.PlotDraw, ol.Observable);

//激活对象
plot.tool.PlotDraw.prototype.activate = function (type, plotParam) {
    this.deactivate();
    this.removeDbClickZoom();      //移除地图鼠标双击放大交互事件
    map.on('click', this.beginDraw, this);   //注册鼠标单击事件
    this.plotType = type;
    this.plotParams = plotParam;
    this.map.addLayer(this.featureOverlay);
};
plot.tool.PlotDraw.prototype.deactivate = function () {
    this.disconnectEventHandlers();
    this.map.removeLayer(this.featureOverlay);
    this.p = [
    ];
    this.plot = null;
    this.feature = null;
   // this.addDbClickZoom();
};
plot.tool.PlotDraw.prototype.setMap = function (map) {
    this.map = map;
    this.mapViewport = this.map.getViewport();
};
plot.tool.PlotDraw.prototype.beginDraw = function (e) {
    this.p.push(e.coordinate);
    this.plot = plot.PlotObject.createObject(this.plotType, this.p, this.plotParams);  //根据标绘绘图类型构造标绘对象
    this.feature = new ol.Feature(this.plot);
    this.featureOverlay.getSource().addFeature(this.feature);  //添加几何要素到FeatureOverlay
    this.map.un('click', this.beginDraw, this);
    this.map.on('click', this.continueDraw, this);    
    this.map.on('dblclick', this.finishDraw, this);
  //  goog.events.listen(this.mapViewport, goog.events.EventType.MOUSEMOVE, this.drawing, false, this);
    this.map.on('pointermove', this.drawing, this); 
};
plot.tool.PlotDraw.prototype.drawing = function (e) {
  //  var coord = map.getCoordinateFromPixel([e.clientX,
  //  e.clientY]); 
    if (plot.util.fa(e.coordinate, this.p[this.p.length - 1]) < plot.a.ZERO_TOLERANCE) {  //如果最近两个点间的距离小于指定距离
        return ;
    }
    var pnts = this.p.concat([e.coordinate]);
    this.plot.fa(pnts);    //调用plot.p.point.prototype.fa开始作图
};
plot.tool.PlotDraw.prototype.continueDraw = function (e) {
	//判断两个点之间的距离，如果小于0001，则不做处理
    if (plot.util.fa(e.coordinate, this.p[this.p.length - 1]) < plot.a.ZERO_TOLERANCE) {
        return ;
    }
    this.p.push(e.coordinate);  //鼠标点击的坐标增加到标绘点数组中
    this.plot.fa(this.p);      //调用plot.p.point.prototype.fa开始作图
    if (this.plot.fixPointCount == this.plot.fd()) { //如果标绘对象固定点个数与鼠标点击次数相等
    	this.finishDraw(e);
    }
};
plot.tool.PlotDraw.prototype.finishDraw = function (e) {
	this.removeDbClickZoom();
    this.disconnectEventHandlers();
    e.preventDefault();
    this.removeFeature();
    plot.a.isDrawing = false;
};
plot.tool.PlotDraw.prototype.disconnectEventHandlers = function () {
    this.map.un('click', this.beginDraw, this);
    this.map.un('click', this.continueDraw, this);
  //  goog.events.unlisten(this.mapViewport, goog.events.EventType.MOUSEMOVE, this.drawing, false, this);
    this.map.un('pointermove', this.drawing, this);
    this.map.un('dblclick', this.finishDraw, this);
};
plot.tool.PlotDraw.prototype.removeFeature = function () {
    this.dispatchEvent(new plot.event.PlotDrawEvent(plot.event.PlotDrawEvent.DRAW_END, this.feature));
    this.featureOverlay.getSource().removeFeature(this.feature);
 //   this.addDbClickZoom();
    this.disconnectEventHandlers();
    this.map.removeLayer(this.featureOverlay);
    this.p = [
    ];
    this.plot = null;
    this.feature = null;
};

//移除鼠标双击放大交互事件
plot.tool.PlotDraw.prototype.removeDbClickZoom = function () {
	var interactions = map.getInteractions();
	var interactionLen = interactions.getLength();;
	var tempInteraction;
	var tempInteractionArr = [];
	for(var i = 0; i< interactionLen; i++){
		tempInteraction = interactions.item(i);
    	if(tempInteraction instanceof ol.interaction.DoubleClickZoom){
    		tempInteractionArr.push(tempInteraction);
    	}
    }
	for(var i = 0;i < tempInteractionArr.length;i++){
		map.removeInteraction(tempInteractionArr[i]);
	}
};