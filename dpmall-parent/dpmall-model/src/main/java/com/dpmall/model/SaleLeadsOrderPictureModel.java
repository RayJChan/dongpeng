package com.dpmall.model;

import java.io.Serializable;
import java.util.List;

/**
 * 留资图片
 *  cwj
 * @since 2018-05-10
 */
public class SaleLeadsOrderPictureModel implements Serializable {

    /**
     * 留资图片
     **/
    private String pictureUrl;

    /**
     * 留资图片
     **/
    private String pictureType;


    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }
}
