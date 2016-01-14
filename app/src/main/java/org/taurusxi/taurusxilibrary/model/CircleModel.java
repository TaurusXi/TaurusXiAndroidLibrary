package org.taurusxi.taurusxilibrary.model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2015/4/7.
 * 圈子
 */
public class CircleModel extends TXBaseModel {
    public String count;
    public ArrayList<Circle> datas;

    public CircleModel() {
    }

    @Override
    public String toString() {
        return "CircleBean{" +
                "datas=" + datas +
                '}';
    }

    public static class Circle implements Serializable {
        public String coverKey;
        public String fansCount;
        public String bgKey;
        /**
         * 主持人名字
         */
        public String presentName;
        /**
         * 内容数
         */
        public String feedCount;
        public String circleDesc;
        public String isFocus;
        public String circleId;
        public String circleName;

        public Circle() {
        }

        @Override
        public String toString() {
            return "Circle{" +
                    "coverKey='" + coverKey + '\'' +
                    ", fansCount='" + fansCount + '\'' +
                    ", bgKey='" + bgKey + '\'' +
                    ", presentName='" + presentName + '\'' +
                    ", feedCount='" + feedCount + '\'' +
                    ", circleDesc='" + circleDesc + '\'' +
                    ", isFocus='" + isFocus + '\'' +
                    ", circleId='" + circleId + '\'' +
                    ", circleName='" + circleName + '\'' +
                    '}';
        }
    }
}
