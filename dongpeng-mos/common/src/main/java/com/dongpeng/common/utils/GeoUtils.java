package com.dongpeng.common.utils;

import com.spatial4j.core.context.SpatialContext;
import com.spatial4j.core.distance.DistanceUtils;
import com.spatial4j.core.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 地理空间计算工具类
 */
public class GeoUtils {
    private static final Logger logger = LoggerFactory.getLogger(GeoUtils.class);

    /**
     * 获取指定范围内 的 经纬度范围
     * @param lng 经度
     * @param lat 纬度
     * @param radius 查找范围 单位：千米
     * @return
     */
    public static Rectangle getPointRangeByRadius(double lng,double lat,int radius){

        SpatialContext geo = SpatialContext.GEO;
        Rectangle rectangle = geo.getDistCalc().calcBoxByDistFromPt(
                geo.makePoint(lng, lat), radius * DistanceUtils.KM_TO_DEG, geo, null);

        logger.info(rectangle.getMinX() + "-" + rectangle.getMaxX());// 经度范围
        logger.info(rectangle.getMinY() + "-" + rectangle.getMaxY());// 纬度范围

        return rectangle;
    }

    /**
     * 获取指定圆环范围内 的 经纬度范围
     * @param lng 经度
     * @param lat 纬度
     * @param insideRadius 内环范围 单位：千米
     * @param outsideRadius 外环范围 单位：千米
     * @return
     */
    public static Rectangle getPointRangeByRingRadius(double lng,double lat,int insideRadius,int outsideRadius ){

        SpatialContext geo = SpatialContext.GEO;
        //内环范围
        Rectangle insideRectangle = geo.getDistCalc().calcBoxByDistFromPt(
                geo.makePoint(lng, lat), insideRadius * DistanceUtils.KM_TO_DEG, geo, null);
        //外环范围
        Rectangle outsideRectangle = geo.getDistCalc().calcBoxByDistFromPt(
                geo.makePoint(lng, lat), outsideRadius * DistanceUtils.KM_TO_DEG, geo, null);

        //logger.info(insideRectangle.getMinX() + "-" + insideRectangle.getMaxX());// 经度范围
        //logger.info(insideRectangle.getMinY() + "-" + insideRectangle.getMaxY());// 纬度范围


        //环形范围
        Rectangle ringRectangle= geo.makeRectangle(insideRectangle.getMaxX(), outsideRectangle.getMaxX()
                , insideRectangle.getMaxY(), outsideRectangle.getMaxY());
        return ringRectangle;
    }

    /**
     * 计算两个经纬度之间的距离
     * @param lng1 经度1
     * @param lat1 纬度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return 距离 单位：千米
     */
    public static double getDistanceByPoint(double lng1,double lat1,double lng2,double lat2){

        SpatialContext geo = SpatialContext.GEO;
        double distance = geo.calcDistance(geo.makePoint(lng1, lat1), geo.makePoint(lng2, lat2))
                * DistanceUtils.DEG_TO_KM;
        System.out.println(distance);// KM

        return distance;
    }

    public static void main(String[] args){
        GeoUtils.getPointRangeByRadius(113.040946, 23.010195, 1);
        GeoUtils.getPointRangeByRadius(113.040946, 23.010195, 2);
    }
}
