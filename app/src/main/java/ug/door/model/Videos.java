package ug.door.model;

import java.util.List;

/**
 * Created by ThinkPad on 2017/10/24.
 */

public class Videos {

    /**
     * result : {"success":true,"message":"请求成功"}
     * url : [{"ad":"http://116.62.57.42/adupload/fileUploads/1.mp4","photo":"http://116.62.57.42/adupload/fileUploads/1.png","name":"1.mp4"}]
     */

    private ResultBean result;
    private List<UrlBean> url;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public List<UrlBean> getUrl() {
        return url;
    }

    public void setUrl(List<UrlBean> url) {
        this.url = url;
    }

    public static class ResultBean {
        /**
         * success : true
         * message : 请求成功
         */

        private boolean success;
        private String message;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class UrlBean {
        /**
         * ad : http://116.62.57.42/adupload/fileUploads/1.mp4
         * photo : http://116.62.57.42/adupload/fileUploads/1.png
         * name : 1.mp4
         */

        private String ad;
        private String photo;
        private String name;

        public String getAd() {
            return ad;
        }

        public void setAd(String ad) {
            this.ad = ad;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
