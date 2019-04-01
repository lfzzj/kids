package com.hamitao.base_module.model;

/**
 * Created by linjianwen on 2018/3/12.
 */

public class StsModel {
    /**
     * responseDataObj : {"STS":{"AccessKeyId":"STS.CH1K4WQ66G59SyZ7rJq86uZjS","AccessKeySecret":"2sCYXSFRjAHsJsxmsntD9g6RGFsQGmQn6LkmGyNswco3","Expiration":"2018-03-12T09:19:53Z","RequestId":"33F5F600-A820-4949-90D2-A6D3FC936B35","SecurityToken":"CAISjAJ1q6Ft5B2yfSjIoouEAI7jvOkX8PfSUV/r03Iffbcamp/BsTz2IHxFfXBvA+gbvvwxlW5S7P8elqVoRoReREvCKM1565kPC8tAx0OF6aKP9rUhpMCPKwr6UmzGvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AFZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO7gEa4K+kkMqH8Uic3h+oiM1t/t6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB+/jPKg8qQGVE54W/db7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD/EVAk6c2t8rCjDGoABfKoCOFywGv5hpiXb4WrMTgxtf+3PTj+Ov3RmZKfJLDkgyXcMo1bvTGidVbi/P8/7apFWoVOhIzp8+SS9gIgvt2FWtxvoZfNI8jeWQokEO7+iBBsHC3T6HtFYf6f/zYZ2peXSsXOP9z6zRzeFCADtwyCImWaVqCuXJsQN5J30pEU="}}
     * result : success
     */

    private ResponseDataObjBean responseDataObj;
    private String result;

    public ResponseDataObjBean getResponseDataObj() {
        return responseDataObj;
    }

    public void setResponseDataObj(ResponseDataObjBean responseDataObj) {
        this.responseDataObj = responseDataObj;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class ResponseDataObjBean {
        /**
         * STS : {"AccessKeyId":"STS.CH1K4WQ66G59SyZ7rJq86uZjS","AccessKeySecret":"2sCYXSFRjAHsJsxmsntD9g6RGFsQGmQn6LkmGyNswco3","Expiration":"2018-03-12T09:19:53Z","RequestId":"33F5F600-A820-4949-90D2-A6D3FC936B35","SecurityToken":"CAISjAJ1q6Ft5B2yfSjIoouEAI7jvOkX8PfSUV/r03Iffbcamp/BsTz2IHxFfXBvA+gbvvwxlW5S7P8elqVoRoReREvCKM1565kPC8tAx0OF6aKP9rUhpMCPKwr6UmzGvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AFZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO7gEa4K+kkMqH8Uic3h+oiM1t/t6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB+/jPKg8qQGVE54W/db7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD/EVAk6c2t8rCjDGoABfKoCOFywGv5hpiXb4WrMTgxtf+3PTj+Ov3RmZKfJLDkgyXcMo1bvTGidVbi/P8/7apFWoVOhIzp8+SS9gIgvt2FWtxvoZfNI8jeWQokEO7+iBBsHC3T6HtFYf6f/zYZ2peXSsXOP9z6zRzeFCADtwyCImWaVqCuXJsQN5J30pEU="}
         */

        private STSBean STS;

        public STSBean getSTS() {
            return STS;
        }

        public void setSTS(STSBean STS) {
            this.STS = STS;
        }

        public static class STSBean {
            /**
             * AccessKeyId : STS.CH1K4WQ66G59SyZ7rJq86uZjS
             * AccessKeySecret : 2sCYXSFRjAHsJsxmsntD9g6RGFsQGmQn6LkmGyNswco3
             * Expiration : 2018-03-12T09:19:53Z
             * RequestId : 33F5F600-A820-4949-90D2-A6D3FC936B35
             * SecurityToken : CAISjAJ1q6Ft5B2yfSjIoouEAI7jvOkX8PfSUV/r03Iffbcamp/BsTz2IHxFfXBvA+gbvvwxlW5S7P8elqVoRoReREvCKM1565kPC8tAx0OF6aKP9rUhpMCPKwr6UmzGvqL7Z+H+U6mqGJOEYEzFkSle2KbzcS7YMXWuLZyOj+wIDLkQRRLqL0AFZrFsKxBltdUROFbIKP+pKWSKuGfLC1dysQcO7gEa4K+kkMqH8Uic3h+oiM1t/t6hecD9M5MxY8YuA47qg9YbLPSRjHRijDFR77pzgaB+/jPKg8qQGVE54W/db7uNq4I1dFIpPvZrRfQe9aTm7PRgovbUk4mywh1GMPpOVD/EVAk6c2t8rCjDGoABfKoCOFywGv5hpiXb4WrMTgxtf+3PTj+Ov3RmZKfJLDkgyXcMo1bvTGidVbi/P8/7apFWoVOhIzp8+SS9gIgvt2FWtxvoZfNI8jeWQokEO7+iBBsHC3T6HtFYf6f/zYZ2peXSsXOP9z6zRzeFCADtwyCImWaVqCuXJsQN5J30pEU=
             */

            private String AccessKeyId;
            private String AccessKeySecret;
            private String Expiration;
            private String RequestId;
            private String SecurityToken;

            public String getAccessKeyId() {
                return AccessKeyId;
            }

            public void setAccessKeyId(String AccessKeyId) {
                this.AccessKeyId = AccessKeyId;
            }

            public String getAccessKeySecret() {
                return AccessKeySecret;
            }

            public void setAccessKeySecret(String AccessKeySecret) {
                this.AccessKeySecret = AccessKeySecret;
            }

            public String getExpiration() {
                return Expiration;
            }

            public void setExpiration(String Expiration) {
                this.Expiration = Expiration;
            }

            public String getRequestId() {
                return RequestId;
            }

            public void setRequestId(String RequestId) {
                this.RequestId = RequestId;
            }

            public String getSecurityToken() {
                return SecurityToken;
            }

            public void setSecurityToken(String SecurityToken) {
                this.SecurityToken = SecurityToken;
            }
        }
    }
  /*  public String AccessKeySecret;
    public String AccessKeyId;
    public String Expiration;
    public String SecurityToken;
    */





}
